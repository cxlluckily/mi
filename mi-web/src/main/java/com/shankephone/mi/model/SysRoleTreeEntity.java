package com.shankephone.mi.model;

import com.shankephone.mi.common.model.BaseModel;
import lombok.Data;


/**
 * sys_role_tree  Bean
 *
 * @author 系统生成
 * @date 2018-06-20 19:05:27
 */
@Data
public class SysRoleTreeEntity extends BaseModel
{
	
    /**
     * 角色功能树ID
    **/
    private Long roleTreeId;
    /**
     * 角色Id
    **/
    private Long roleId;
    /**
     * 功能树ID
    **/
    private Long functionTreeId;

   
}

