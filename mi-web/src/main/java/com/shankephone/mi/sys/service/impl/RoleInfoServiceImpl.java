package com.shankephone.mi.sys.service.impl;

import com.shankephone.mi.common.dao.FieldContentExistsDao;
import com.shankephone.mi.common.enumeration.TreeTypeEnum;
import com.shankephone.mi.common.model.FieldContentExistsFindEntity;
import com.shankephone.mi.common.model.Pager;
import com.shankephone.mi.common.vo.ResultVO;
import com.shankephone.mi.model.SysRoleEntity;
import com.shankephone.mi.model.SysRoleTreeEntity;
import com.shankephone.mi.sys.dao.RoleInfoDao;
import com.shankephone.mi.sys.formbean.RoleInfoFindEntity;
import com.shankephone.mi.sys.service.RoleInfoService;
import com.shankephone.mi.sys.vo.RoleInfoVO;
import com.shankephone.mi.util.DataSwitch;
import com.shankephone.mi.util.ObjectUtils;
import com.shankephone.mi.util.ResultVOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 角色信息Service 实现
 *
 * @author 司徒彬
 * @date 2018 -06-21 11:16
 */
@Service
public class RoleInfoServiceImpl implements RoleInfoService {

    @Autowired
    private RoleInfoDao roleInfoDao;

    @Autowired
    private FieldContentExistsDao fieldContentExistsDao;
    /**
     * 获得角色分页数据
     *
     * @param findEntity
     * @return
     */
    @Override
    public Pager getRolePagerInfo(RoleInfoFindEntity findEntity) {
        try {
            List<Map<String, Object>> rows = roleInfoDao.getRolePagerInfo(findEntity);
            int total = roleInfoDao.getRolePagerTotal(findEntity);
            Pager<Map<String, Object>> pager = new Pager<>(total, rows);
            return pager;
        } catch (Exception ex) {
            throw ex;
        }
    }

    /**
     * 添加角色
     *
     * @param roleEntity the role entity
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO addRoleInfo(RoleInfoVO roleEntity) {
        try {

            FieldContentExistsFindEntity filedentity=new FieldContentExistsFindEntity();
            filedentity.setUserKey(roleEntity.getUserKey());
            filedentity.setTablename("sys_role");
            filedentity.setFieldName("roleName");
            filedentity.setFieldValue(roleEntity.getRoleName());
            if( fieldContentExistsDao.selectFieldExists(filedentity)>0)
            {
                return ResultVOUtil.error(-1,"角色名称已存在,请重新填写！");
            }

            roleEntity.setRoleCode("");
            roleInfoDao.insertRoleInfo(roleEntity);
            Long roleId = roleEntity.getRoleId();
            List<Long> functionTreeIds = roleEntity.getFunctionTreeIds();
            for (Long functionTreeId : functionTreeIds) {
                SysRoleTreeEntity roleTreeEntity = new SysRoleTreeEntity();
                roleTreeEntity.setFunctionTreeId(functionTreeId);
                roleTreeEntity.setRoleId(roleId);
                roleInfoDao.insertRoleTreeInfo(roleTreeEntity);
            }
            return ResultVOUtil.success();
        } catch (Exception ex) {
            throw ex;
        }
    }

    /**
     * 更新角色信息
     *
     * @param roleEntity the role entity
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO updateRoleInfo(RoleInfoVO roleEntity) {
        try {
            FieldContentExistsFindEntity filedentity=new FieldContentExistsFindEntity();
            filedentity.setUserKey(roleEntity.getUserKey());
            filedentity.setTablename("sys_role");
            filedentity.setIdName("roleId");
            filedentity.setIdValue(roleEntity.getRoleId());
            filedentity.setFieldName("roleName");
            filedentity.setFieldValue(roleEntity.getRoleName());

            if( fieldContentExistsDao.selectFieldExists(filedentity)>0)
            {
                return ResultVOUtil.error(-1,"角色名称已存在,请重新填写！");
            }

            roleEntity.setRoleCode("");
            roleInfoDao.updateRoleInfo(roleEntity);
            Long roleId = roleEntity.getRoleId();
            roleInfoDao.deleteRoleTree(roleId);
            List<Long> functionTreeIds = roleEntity.getFunctionTreeIds();
            for (Long functionTreeId : functionTreeIds) {
                SysRoleTreeEntity roleTreeEntity = new SysRoleTreeEntity();
                roleTreeEntity.setFunctionTreeId(functionTreeId);
                roleTreeEntity.setRoleId(roleId);
                roleInfoDao.insertRoleTreeInfo(roleTreeEntity);
            }
            return ResultVOUtil.success();
        } catch (Exception ex) {
            throw ex;
        }
    }

    /**
     * 删除角色信息
     *
     * @param roleId the role id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteRoleInfo(Long roleId) {
        try {
            roleInfoDao.deleteRoleInfo(roleId);
        } catch (Exception ex) {
            throw ex;
        }
    }

    /**
     * 根据 Id 获取角色信息
     *
     * @param roleId
     * @return
     */
    @Override
    public RoleInfoVO getRoleInfo(Long roleId) {

        try {
            RoleInfoVO roleEntity = new RoleInfoVO();
            if (roleId != 0) {
                roleEntity = roleInfoDao.getRoleInfo(roleId);
            }
            if (ObjectUtils.isNull(roleEntity)) {
                roleEntity = new RoleInfoVO();
            }
            //获取角色树
            List<Map<String, Object>> treeInfo = roleInfoDao.getTreeInfo(roleId);

            List<Map<String, Object>> backgroundTree = treeInfo.stream()
                    .filter(tree -> DataSwitch.convertObjectToString(tree.get("treeType")).equals(TreeTypeEnum.MANAGE.getCode())).collect(Collectors.toList());
            backgroundTree = DataSwitch.convertListToTree(backgroundTree, -1, "dataNodeId", "dataParentNodeId", "children");
            List<Map<String, Object>> appTree = treeInfo.stream()
                    .filter(tree -> DataSwitch.convertObjectToString(tree.get("treeType")).equals(TreeTypeEnum.PHONE.getCode())).collect(Collectors.toList());
            appTree = DataSwitch.convertListToTree(appTree, -1, "dataNodeId", "dataParentNodeId", "children");

            roleEntity.setTreeInfoList(backgroundTree);
            roleEntity.setAppTreeInfoList(appTree);
            return roleEntity;
        } catch (Exception ex) {
            throw ex;
        }
    }

    @Override
    public boolean validateRoleNameExist(RoleInfoFindEntity findEntity) {
        try {
            int count = roleInfoDao.validateRoleNameExist(findEntity);
            return count > 0;
        } catch (Exception ex) {
            throw ex;
        }
    }

	@Override
	public List<SysRoleEntity> getAllRoles(RoleInfoFindEntity findEntity) {
		return roleInfoDao.getAllRoles(findEntity);
	}


}
