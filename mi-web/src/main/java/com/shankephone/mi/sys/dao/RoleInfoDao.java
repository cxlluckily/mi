package com.shankephone.mi.sys.dao;

import com.shankephone.mi.model.SysRoleEntity;
import com.shankephone.mi.model.SysRoleTreeEntity;
import com.shankephone.mi.sys.dao.provider.RoleInfoProvider;
import com.shankephone.mi.sys.formbean.RoleInfoFindEntity;
import com.shankephone.mi.sys.vo.RoleInfoVO;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * The interface Role info dao.
 */
@Repository
public interface RoleInfoDao {

    /**
     * Gets role pager info.
     *
     * @param findEntity the find entity
     * @return the role pager info
     */
    @SelectProvider(type = RoleInfoProvider.class, method = "getRolePagerInfo")
    List<Map<String, Object>> getRolePagerInfo(RoleInfoFindEntity findEntity);

    /**
     * Gets role pager total.
     *
     * @param findEntity the find entity
     * @return the role pager total
     */
    @SelectProvider(type = RoleInfoProvider.class, method = "getRolePagerTotal")
    int getRolePagerTotal(RoleInfoFindEntity findEntity);


    /**
     * Insert role info.
     *
     * @param roleEntity the role entity
     */
    @InsertProvider(type = RoleInfoProvider.class, method = "insertRoleInfo")
    @Options(useGeneratedKeys = true, keyProperty = "roleId")
    void insertRoleInfo(RoleInfoVO roleEntity);

    /**
     * Insert role tree info.
     *
     * @param roleTreeEntity the role tree entity
     */
    @InsertProvider(type = RoleInfoProvider.class, method = "insertRoleTreeInfo")
    @Options(useGeneratedKeys = true, keyProperty = "roleTreeId")
    void insertRoleTreeInfo(SysRoleTreeEntity roleTreeEntity);

    /**
     * Update role info.
     *
     * @param roleInfoVO the role info vo
     */
    @UpdateProvider(type = RoleInfoProvider.class, method = "updateRoleInfo")
    void updateRoleInfo(RoleInfoVO roleInfoVO);

    /**
     * Delete role tree.
     *
     * @param roleId the role id
     */
    @DeleteProvider(type = RoleInfoProvider.class, method = "deleteRoleTree")
    void deleteRoleTree(Long roleId);

    /**
     * Delete role info.
     *
     * @param roleId the role id
     */
    @UpdateProvider(type = RoleInfoProvider.class, method = "deleteRoleInfo")
    void deleteRoleInfo(Long roleId);

    /**
     * Gets role info.
     *
     * @param roleId the role id
     * @return the role info
     */
    @SelectProvider(type = RoleInfoProvider.class, method = "getRoleInfo")
    RoleInfoVO getRoleInfo(Long roleId);

    /**
     * Gets tree info.
     *
     * @param roleId the role id
     * @return the tree info
     */
    @SelectProvider(type = RoleInfoProvider.class, method = "getTreeInfo")
    List<Map<String, Object>> getTreeInfo(Long roleId);

    /**
     * Validate role name exist int.
     *
     * @param findEntity the find entity
     * @return the int
     */
    @SelectProvider(type = RoleInfoProvider.class, method = "validateRoleNameExist")
    int validateRoleNameExist(RoleInfoFindEntity findEntity);

    @SelectProvider(type = RoleInfoProvider.class, method = "getAllRoles")
	List<SysRoleEntity> getAllRoles(RoleInfoFindEntity findEntity);
}
