package com.shankephone.mi.sys.dao;

import com.shankephone.mi.model.SysRangeRoleDetailEntity;
import com.shankephone.mi.model.SysRangeRoleEntity;
import com.shankephone.mi.model.SysUserRoleEntity;
import com.shankephone.mi.sys.dao.provider.UserPermissionProvider;
import com.shankephone.mi.sys.formbean.UserPermissionFindEntity;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 用户授权 Dao
 *
 * @author 司徒彬
 * @date 2018 /6/27 10:40
 */
@Repository
public interface UserPermissionDao {
    /**
     * Gets role info list.
     *
     * @param operationSubjectId the operation subject id
     * @return the role info list
     */
    @SelectProvider(type = UserPermissionProvider.class, method = "getRoleInfoList")
    List<Map<String,Object>> getRoleInfoList(Long operationSubjectId);

    @SelectProvider(type = UserPermissionProvider.class, method = "getWarehouseInfoList")
    List<Map<String,Object>> getWarehouseInfoList(Long operationSubjectId);

    @SelectProvider(type = UserPermissionProvider.class, method = "getWorkSectionList")
    List<Map<String,Object>> getWorkSectionList(Long operationSubjectId);

    @InsertProvider(type = UserPermissionProvider.class, method = "insertRangeRoleInfo")
    @Options(useGeneratedKeys = true, keyProperty = "rangeRoleId")
    void insertRangeRoleInfo(SysRangeRoleEntity rangeRoleEntity);

    @InsertProvider(type = UserPermissionProvider.class, method = "insertRangeRoleDetail")
    void insertRangeRoleDetail(SysRangeRoleDetailEntity roleDetailEntity);

    @InsertProvider(type =UserPermissionProvider.class, method = "insertUserRoleInfo")
    void insertUserRoleInfo(SysUserRoleEntity userRoleEntity);

    @SelectProvider(type = UserPermissionProvider.class, method = "getUserInfoById")
    Map<String,Object> getUserInfoById(Long userId);

    @SelectProvider(type = UserPermissionProvider.class, method = "getRoleInfoListByUserId")
    List<Map<String,Object>> getRoleInfoListByUserId(Long userId);

    @SelectProvider(type = UserPermissionProvider.class, method = "getRangeRoleInfoList")
    List<Map<String,Object>> getRangeRoleInfoList(UserPermissionFindEntity findEntity);

    @DeleteProvider(type = UserPermissionProvider.class, method = "deleteRoleInfoByUserId")
    void deleteRoleInfoByUserId(Long userId);

    @DeleteProvider(type = UserPermissionProvider.class, method = "deleteRangeRoleDetailByUserId")
    void deleteRangeRoleDetailByUserId(Long userId);

    @DeleteProvider(type = UserPermissionProvider.class, method = "deleteRangeRoleInfoByUserId")
    void deleteRangeRoleInfoByUserId(Long userId);

    @SelectProvider(type = UserPermissionProvider.class, method = "getUserWarehouseRangeIds")
    List<Long> getUserWarehouseRangeIds(Long userId);
}
