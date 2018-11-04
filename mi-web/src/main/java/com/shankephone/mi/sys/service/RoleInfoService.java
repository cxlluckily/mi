package com.shankephone.mi.sys.service;

import com.shankephone.mi.common.model.Pager;
import com.shankephone.mi.common.vo.ResultVO;
import com.shankephone.mi.model.SysRoleEntity;
import com.shankephone.mi.sys.formbean.RoleInfoFindEntity;
import com.shankephone.mi.sys.vo.RoleInfoVO;

import java.util.List;
import java.util.Map;

/**
 * 角色信息Service  接口
 *
 * @author 司徒彬
 * @date 2018 -06-21 11:13
 */
public interface RoleInfoService
{
    /**
     * Gets role pager info.
     *
     * @param findEntity the find entity
     * @return the role pager info
     */
    Pager<Map<String,Object>> getRolePagerInfo(RoleInfoFindEntity findEntity);

    /**
     * Add role info.
     *
     * @param roleEntity the role entity
     */
    ResultVO addRoleInfo(RoleInfoVO roleEntity);

    /**
     * Update role info.
     *
     * @param roleEntity the role entity
     */
    ResultVO updateRoleInfo(RoleInfoVO roleEntity);

    /**
     * Delete role info.
     *
     * @param roleId the role id
     */
    void deleteRoleInfo(Long roleId);
    /**
     * 获取角色信息
     * @param roleId
     * @return
     */
    RoleInfoVO getRoleInfo(Long roleId);
    /**
     * 角色验证
     * @param findEntity
     * @return
     */
    boolean validateRoleNameExist(RoleInfoFindEntity findEntity);
    
    /**
     * 获取运营主体下的全部角色
     * @param findEntity
     * @return
     */
	List<SysRoleEntity> getAllRoles(RoleInfoFindEntity findEntity);
}
