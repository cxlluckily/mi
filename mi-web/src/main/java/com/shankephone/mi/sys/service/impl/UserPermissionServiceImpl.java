package com.shankephone.mi.sys.service.impl;

import com.shankephone.mi.common.enumeration.RangeRoleTypeEnum;
import com.shankephone.mi.model.SysRangeRoleDetailEntity;
import com.shankephone.mi.model.SysRangeRoleEntity;
import com.shankephone.mi.model.SysUserRoleEntity;
import com.shankephone.mi.sys.dao.UserPermissionDao;
import com.shankephone.mi.sys.formbean.UserPermissionFindEntity;
import com.shankephone.mi.sys.service.UserPermissionService;
import com.shankephone.mi.sys.vo.UserPermissionVo;
import com.shankephone.mi.util.DataSwitch;
import com.shankephone.mi.util.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 用户授权
 *
 * @author 司徒彬
 * @date 2018 /6/27 10:39
 */
@Service
public class UserPermissionServiceImpl implements UserPermissionService {

    /**
     * The User permission dao.
     */
    @Autowired
    UserPermissionDao userPermissionDao;

    @Override
    public Map<String, Object> getInitPageInfoForBatchAuthorization(UserPermissionFindEntity findEntity) {
        try {
            Long operationSubjectId = findEntity.getOperationSubjectId();
            //获取角色数据
            List<Map<String, Object>> roleInfoList = userPermissionDao.getRoleInfoList(operationSubjectId);
            //获取仓库数据
            List<Map<String, Object>> warehouseList = userPermissionDao.getWarehouseInfoList(operationSubjectId);
            warehouseList = DataSwitch.convertListToTree(warehouseList, -1L, "warehouseId", "pId", "children");
            //获取工区数据
            List<Map<String, Object>> workSectionList = userPermissionDao.getWorkSectionList(operationSubjectId);

            Map<String, Object> result = new HashMap<>();
            result.put("roleInfo", roleInfoList);
            result.put("warehouse", warehouseList);
            result.put("workSection", workSectionList);
            return result;
        } catch (Exception ex) {
            throw ex;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertBatchAuthorizationInfo(UserPermissionVo userPermissionVo) {
        try {
            List<Long> userIds = userPermissionVo.getUserIds();

            for (Long userId : userIds) {
                userPermissionVo.setUserId(userId);
                authorizationByUserId(userPermissionVo);
            }

        } catch (Exception ex) {
            throw ex;
        }
    }

    @Override
    public Map<String, Object> getUserAuthorizationInfoByUserId(UserPermissionFindEntity findEntity) {
        try {
            Long userId = findEntity.getUserId();
            //获取用户信息
            Map<String, Object> userInfo = userPermissionDao.getUserInfoById(userId);
            //获取授权的角色
            List<Map<String, Object>> roleInfoList = userPermissionDao.getRoleInfoListByUserId(userId);
            roleInfoList = roleInfoList.stream().map(map -> {
                boolean isChecked = DataSwitch.convertObjectToBoolean(map.get("isChecked"));
                map.put("isChecked", isChecked);
                return map;
            }).collect(Collectors.toList());
            //获取授权的仓库信息
            findEntity.setRangeType(RangeRoleTypeEnum.WAREHOUSE.getValue());
            List<Map<String, Object>> warehouseList = userPermissionDao.getRangeRoleInfoList(findEntity);
            warehouseList = DataSwitch.convertListToTree(warehouseList, -1L, "warehouseId", "parentWarehouseId", "children");
            //获取授权的工区信息
            findEntity.setRangeType(RangeRoleTypeEnum.WORK_SECTION.getValue());
            List<Map<String, Object>> workSectionList = userPermissionDao.getRangeRoleInfoList(findEntity);
            workSectionList = workSectionList.stream().map(map -> {
                boolean isChecked = DataSwitch.convertObjectToBoolean(map.get("isChecked"));
                map.put("isChecked", isChecked);
                return map;
            }).collect(Collectors.toList());
            userInfo.put("roleInfo", roleInfoList);
            userInfo.put("warehouseInfo", warehouseList);
            userInfo.put("workSectionInfo", workSectionList);
            return userInfo;
        } catch (Exception ex) {
            throw ex;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void authorizationByUserId(UserPermissionVo userPermissionVo) {
        try {
            Long userId = userPermissionVo.getUserId();
            //删除用户角色信息
            userPermissionDao.deleteRoleInfoByUserId(userId);
            //添加用户角色信息
            List<Long> roleIds = userPermissionVo.getRoleIds();
            for (Long roleId : roleIds) {
                SysUserRoleEntity userRoleEntity = new SysUserRoleEntity();
                userRoleEntity.setRoleId(roleId);
                userRoleEntity.setUserId(userId);
                userPermissionDao.insertUserRoleInfo(userRoleEntity);
            }
            //删除用户范围权限
            userPermissionDao.deleteRangeRoleDetailByUserId(userId);
            userPermissionDao.deleteRangeRoleInfoByUserId(userId);
            //添加仓库范围权限
            List<Long> warehouseIds = userPermissionVo.getWarehouseIds();
            if (ObjectUtils.isNotEmpty(warehouseIds)) {
                //添加主表
                SysRangeRoleEntity rangeRoleEntity = new SysRangeRoleEntity();
                rangeRoleEntity.setUserId(userId);
                rangeRoleEntity.setCreateUser(userPermissionVo.getOperationUserName());
                rangeRoleEntity.setRangeType(RangeRoleTypeEnum.WAREHOUSE.getValue());
                userPermissionDao.insertRangeRoleInfo(rangeRoleEntity);
                Long rangeRoleId = rangeRoleEntity.getRangeRoleId();
                //添加副表
                for (Long warehouseId : warehouseIds) {
                    SysRangeRoleDetailEntity roleDetailEntity = new SysRangeRoleDetailEntity();
                    roleDetailEntity.setId(warehouseId);
                    roleDetailEntity.setRangeRoleId(rangeRoleId);
                    roleDetailEntity.setUserId(userId);
                    userPermissionDao.insertRangeRoleDetail(roleDetailEntity);
                }
            }
            //添加工区范围权限
            List<Long> workSectionIds = userPermissionVo.getWorkSectionIds();
            if (ObjectUtils.isNotEmpty(workSectionIds)) {
                //添加主表
                SysRangeRoleEntity rangeRoleEntity = new SysRangeRoleEntity();
                rangeRoleEntity.setUserId(userId);
                rangeRoleEntity.setCreateUser(userPermissionVo.getOperationUserName());
                rangeRoleEntity.setRangeType(RangeRoleTypeEnum.WORK_SECTION.getValue());
                userPermissionDao.insertRangeRoleInfo(rangeRoleEntity);
                Long rangeRoleId = rangeRoleEntity.getRangeRoleId();
                //添加副表
                for (Long workSectionId : workSectionIds) {
                    SysRangeRoleDetailEntity roleDetailEntity = new SysRangeRoleDetailEntity();
                    roleDetailEntity.setId(workSectionId);
                    roleDetailEntity.setRangeRoleId(rangeRoleId);
                    roleDetailEntity.setUserId(userId);
                    userPermissionDao.insertRangeRoleDetail(roleDetailEntity);
                }
            }
        } catch (Exception ex) {
            throw ex;
        }
    }

	@Override
	public Map<String, List<Long>> getAuthorizationInfoByUserId(Long userId) {
		UserPermissionFindEntity findEntity = new UserPermissionFindEntity();
		findEntity.setUserId(userId); 
		findEntity.setRangeType(RangeRoleTypeEnum.WAREHOUSE.getValue());
        List<Map<String, Object>> warehouseList = userPermissionDao.getRangeRoleInfoList(findEntity);
        List<Long> warehouseIds = warehouseList.stream().map(map -> {
            return (Long)map.get("warehouseId");
        }).collect(Collectors.toList());
        //获取授权的工区信息
        findEntity.setRangeType(RangeRoleTypeEnum.WORK_SECTION.getValue());
        List<Map<String, Object>> workSectionList = userPermissionDao.getRangeRoleInfoList(findEntity);
        List<Long> workSectionIds = workSectionList.stream().map(map -> {
            return (Long)map.get("workSectionId");
        }).collect(Collectors.toList());
        Map<String, List<Long>> userInfo = new HashMap<String, List<Long>>();
        userInfo.put("warehouseIds", warehouseIds);
        userInfo.put("workSectionIds", workSectionIds);
		return userInfo;
	}

    @Override
    public List<Long> getUserWarehouseRangeIds(Long userId)
    {
        try
        {
            return userPermissionDao.getUserWarehouseRangeIds(userId);
        }
        catch(Exception ex)
        {
            throw ex;
        }
    }


}
