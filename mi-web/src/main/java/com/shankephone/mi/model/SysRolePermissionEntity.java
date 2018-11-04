package com.shankephone.mi.model;

import com.shankephone.mi.common.model.BaseModel;
import lombok.Data;


/**
 * sys_role_permission  Bean
 *
 * @author 系统生成
 * @date 2018-06-20 19:05:27
 */
@Data
public class SysRolePermissionEntity  extends BaseModel
{
	
    /**
     * 角色权限Id
    **/
    private Long rolePermissionId;
    /**
     * 角色ID
    **/
    private Long roleId;
    /**
     * 权限id
    **/
    private Long permissionId;
    /**
     * 权限代码
    **/
    private String permissionCode;

   
}

