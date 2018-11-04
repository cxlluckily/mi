package com.shankephone.mi.model;

import com.shankephone.mi.common.model.BaseModel;
import lombok.Data;

import java.sql.Timestamp;


/**
 * sys_permission  Bean
 *
 * @author 系统生成
 * @date 2018-06-20 19:05:26
 */
@Data
public class SysPermissionEntity extends BaseModel
{
	
    /**
     * 权限id
    **/
    private Long permissionId;
    /**
     * 权限名称
    **/
    private String permissionName;
    /**
     * 权限代码
    **/
    private String permissionCode;
    /**
     * 所属类别
    **/
    private String category;
    /**
     * 添加人
    **/
    private String createUser;
    /**
     * 状态
    **/
    private String status;
    /**
     * 创建时间
    **/
    private Timestamp createTime;
    /**
     * 修改人
    **/
    private String modifyUser;
    /**
     * 更新时间
    **/
    private Timestamp modifyTime;

   
}

