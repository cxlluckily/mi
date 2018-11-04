package com.shankephone.mi.model;

import com.shankephone.mi.common.model.BaseModel;
import lombok.Data;

import java.sql.Timestamp;


/**
 * sys_role  Bean
 *
 * @author 系统生成
 * @date 2018-06-20 19:05:27
 */
@Data
public class SysRoleEntity  extends BaseModel
{
	
    /**
     * 角色Id
    **/
    private Long roleId;
    /**
     * 运营主体ID
    **/
    //private Long operationSubjectId;
    /**
     * 角色代码
    **/
    private String roleCode;
    /**
     * 角色名称
    **/
    private String roleName;
    /**
     * 状态
    **/
    private String status;
    /**
     * 是否可以删除
    **/
    private Boolean wasCanDelete;
    /**
     * 添加人
    **/
    private String createUser;
    /**
     * 创建时间
    **/
    private Timestamp createTime;
    /**
     * 修改人
    **/
    private String modifyUser;
    /**
     * 修改时间
    **/
    private Timestamp modifyTime;

   
}

