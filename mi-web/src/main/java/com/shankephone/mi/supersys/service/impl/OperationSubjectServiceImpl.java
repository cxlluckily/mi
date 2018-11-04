package com.shankephone.mi.supersys.service.impl;

import com.shankephone.mi.common.enumeration.ResultEnum;
import com.shankephone.mi.common.enumeration.StatusEnum;
import com.shankephone.mi.common.model.Pager;
import com.shankephone.mi.common.vo.ResultVO;
import com.shankephone.mi.model.*;
import com.shankephone.mi.org.dao.OrgOrganizationDao;
import com.shankephone.mi.security.shiro.CustomShiroRealm;
import com.shankephone.mi.spacepart.dao.SparePartTypeDao;
import com.shankephone.mi.supersys.dao.DefaultSparePartTypeDao;
import com.shankephone.mi.supersys.dao.FunctionTreeDao;
import com.shankephone.mi.supersys.dao.OperationSubjectDao;
import com.shankephone.mi.supersys.dao.RegionDao;
import com.shankephone.mi.supersys.formbean.OperationSubjectFindEntity;
import com.shankephone.mi.supersys.service.OperationSubjectService;
import com.shankephone.mi.sys.dao.RoleInfoDao;
import com.shankephone.mi.sys.dao.SysUserDao;
import com.shankephone.mi.sys.dao.UserPermissionDao;
import com.shankephone.mi.sys.vo.RoleInfoVO;
import com.shankephone.mi.util.PinYinUtils;
import com.shankephone.mi.util.ResultVOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author 赵亮
 * @date 2018-07-23 13:36
 */
@Service
public class OperationSubjectServiceImpl implements OperationSubjectService {
    @Autowired
    private RegionDao regionDao;
    @Autowired
    private OperationSubjectDao operationSubjectDao;
    @Autowired
    private DefaultSparePartTypeDao defaultSparePartTypeDao;
    @Autowired
    private SparePartTypeDao sparePartTypeDao;

    /**
     * 返回所有地区信息
     *
     * @author：赵亮
     * @date：2018-07-23 13:41
     */
    @Override
    public List<Map<String, Object>> getAllRegion() {
        try {
            //            return DataSwitch.convertListToTree(regionDao.getAllRegion(StatusEnum.START_USING.getValue()),-1,"regionId","parentId","children");
            return regionDao.getAllRegion(StatusEnum.START_USING.getValue());
        } catch (Exception ex) {
            throw ex;
        }
    }


    @Autowired
    private RoleInfoDao roleInfoDao;
    @Autowired
    private FunctionTreeDao functionTreeDao;
    @Autowired
    private SysUserDao sysUserDao;
    @Autowired
    private UserPermissionDao userPermissionDao;
    @Autowired
    private OrgOrganizationDao orgOrganizationDao;

    /**
     * 新增运营主体
     *
     * @param entity
     * @author：赵亮
     * @date：2018-07-23 13:44
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO insertOne(SysOperationSubjectEntity entity) {
        try {
            //验证用户名是否重复
            Integer total = sysUserDao.findCountByUserName(entity.getLoginName());
            if (total > 0) {
                return ResultVOUtil.error(ResultEnum.USER_NAME_REPEAT.getCode(), ResultEnum.USER_NAME_REPEAT.getMessage());
            }
            //新增主体数据
            operationSubjectDao.insertOne(entity);
            //拷贝默认备件类型
            List<PartDefaultSparePartTypeEntity> allDefaultSparePartList =
                    defaultSparePartTypeDao.getAllDefaultSparePartList(StatusEnum.START_USING.getValue());
            int sort = 1;
            for (PartDefaultSparePartTypeEntity oneDefaultEntity : allDefaultSparePartList) {
                if (oneDefaultEntity.getParentPartId().equals(-1l)) {
                    PartSparePartTypeEntity partTypeEntity = new PartSparePartTypeEntity();
                    partTypeEntity.setUserKey(entity.getUserKey());
                    partTypeEntity.setCategoryName(oneDefaultEntity.getPartTypeName());
                    partTypeEntity.setOperationSubjectId(entity.getOperationSubjectId());
                    partTypeEntity.setPartTypePinYin(PinYinUtils.getCn2FirstSpell(partTypeEntity.getCategoryName()));
                    partTypeEntity.setParentPartId(-1l);
                    partTypeEntity.setRemark("");
                    partTypeEntity.setSort(sort++);
                    partTypeEntity.setStatus(oneDefaultEntity.getStatus());
                    sparePartTypeDao.insertOne(partTypeEntity);
                    partTypeEntity.setSparePartTypeIds(partTypeEntity.getSparePartTypeId() + "@");
                    sparePartTypeDao.updateOne(partTypeEntity);
                    insertPartSparePartType(oneDefaultEntity.getDefaultSparePartTypeId(), partTypeEntity.getSparePartTypeId(), entity.getOperationSubjectId(), allDefaultSparePartList, entity.getUserKey(), partTypeEntity.getSparePartTypeIds());
                }
            }
            //新增角色
            RoleInfoVO roleEntity = new RoleInfoVO();
            roleEntity.setOperationSubjectId(entity.getOperationSubjectId());
            roleEntity.setRoleCode("001");
            roleEntity.setRoleName("系统管理员");
            roleEntity.setUserKey(entity.getUserKey());
            roleEntity.setStatus(StatusEnum.START_USING.getValue());
            roleEntity.setWasCanDelete(false);
            roleInfoDao.insertRoleInfo(roleEntity);
            //给角色分配权限(角色功能树)
            List<SysFunctionTreeEntity> functionTreeEntities = functionTreeDao.getAllTreeList(StatusEnum.START_USING.getValue());
            for (SysFunctionTreeEntity oneTreeEntity : functionTreeEntities) {
                SysRoleTreeEntity sysRoleTreeEntity = new SysRoleTreeEntity();
                sysRoleTreeEntity.setRoleId(roleEntity.getRoleId());
                sysRoleTreeEntity.setFunctionTreeId(oneTreeEntity.getFunctionTreeId());
                roleInfoDao.insertRoleTreeInfo(sysRoleTreeEntity);
            }
            //创建一个组织机构根数据
            OrgOrganizationEntity orgOrganizationEntity = new OrgOrganizationEntity();
            orgOrganizationEntity.setOperationSubjectId(entity.getOperationSubjectId());
            orgOrganizationEntity.setParentOrgId(-1l);
            orgOrganizationEntity.setOrgName(entity.getSubjectName());
            orgOrganizationEntity.setInternalNumber("001");
            orgOrganizationEntity.setWasCanDelete(false);
            orgOrganizationEntity.setStatus(StatusEnum.START_USING.getValue());
            orgOrganizationEntity.setUserKey(entity.getUserKey());
            orgOrganizationDao.insertOne(orgOrganizationEntity);
            //创建一个账户
            SysUserEntity sysUser = new SysUserEntity();
            sysUser.setStatus(StatusEnum.START_USING.getValue());
            sysUser.setOperationSubjectId(entity.getOperationSubjectId());
            sysUser.setUserName(entity.getLoginName());
            sysUser.setPassword(CustomShiroRealm.encrypt(entity.getPassword()));
            sysUser.setUserKey(entity.getUserKey());
            sysUser.setOrgId(orgOrganizationEntity.getOrgId());
            sysUser.setRealName(entity.getLoginName());
            sysUser.setSex("male");//默认男
            sysUser.setWasDefaultManager("1");//默认管理员
            sysUserDao.insertOne(sysUser);
            //给用户分配角色
            SysUserRoleEntity sysUserRoleEntity = new SysUserRoleEntity();
            sysUserRoleEntity.setRoleId(roleEntity.getRoleId());
            sysUserRoleEntity.setUserId(sysUser.getUserId());
            userPermissionDao.insertUserRoleInfo(sysUserRoleEntity);
            return ResultVOUtil.success();
        } catch (Exception ex) {
            throw ex;
        }
    }

    private void insertPartSparePartType(Long pid, Long sparePartTypeId, Long operationSubjectId, List<PartDefaultSparePartTypeEntity> allDefaultSparePartList, String userKey, String sparePartTypeIds) {
        List<PartDefaultSparePartTypeEntity> defaultSparePartTypeEntities = allDefaultSparePartList.stream()
                .filter(m -> m.getParentPartId().equals(pid)).collect(Collectors.toList());
        if (null != defaultSparePartTypeEntities && defaultSparePartTypeEntities.size() > 0) {
            int sort = 1;
            for (PartDefaultSparePartTypeEntity oneDefaultEntity : defaultSparePartTypeEntities) {
                PartSparePartTypeEntity partTypeEntity = new PartSparePartTypeEntity();
                partTypeEntity.setCategoryName(oneDefaultEntity.getPartTypeName());
                partTypeEntity.setOperationSubjectId(operationSubjectId);
                partTypeEntity.setPartTypePinYin(PinYinUtils.getCn2FirstSpell(partTypeEntity.getCategoryName()));
                partTypeEntity.setParentPartId(sparePartTypeId);
                partTypeEntity.setStatus(oneDefaultEntity.getStatus());
                partTypeEntity.setRemark("");
                partTypeEntity.setSort(sort++);
                partTypeEntity.setUserKey(userKey);
                sparePartTypeDao.insertOne(partTypeEntity);
                partTypeEntity.setSparePartTypeIds(sparePartTypeIds + partTypeEntity.getSparePartTypeId() + "@");
                sparePartTypeDao.updateOne(partTypeEntity);
                insertPartSparePartType(oneDefaultEntity.getDefaultSparePartTypeId(), partTypeEntity.getSparePartTypeId(), operationSubjectId, allDefaultSparePartList, userKey, partTypeEntity.getSparePartTypeIds());
            }
        }
    }


    /**
     * 更新运营主体
     *
     * @param entity
     * @author：赵亮
     * @date：2018-07-23 16:09
     */
    @Override
    public Integer updateOne(SysOperationSubjectEntity entity) {
        try {
            return operationSubjectDao.updateOne(entity);
        } catch (Exception ex) {
            throw ex;
        }
    }

    /**
     * 查询list
     *
     * @param findEntity
     * @author：赵亮
     * @date：2018-07-23 16:14
     */
    @Override
    public Pager<Map<String, Object>> getOperationList(OperationSubjectFindEntity findEntity) {
        try {
            try {
                Integer total = operationSubjectDao.getOperationListCount(findEntity);
                List<Map<String, Object>> entitys = operationSubjectDao.getOperationList(findEntity);
                Pager pager = new Pager<>(total, entitys);
                return pager;
            } catch (Exception ex) {
                throw ex;
            }
        } catch (Exception ex) {
            throw ex;
        }
    }

    @Override
    public Integer updateAdminPassword(OperationSubjectFindEntity findEntity)
    {
        try
        {
            findEntity.setPassword(CustomShiroRealm.encrypt("123456"));
            return operationSubjectDao.updateAdminPassword(findEntity);
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }
}
