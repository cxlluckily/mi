package com.shankephone.mi.model;

import com.shankephone.mi.common.model.BaseModel;
import lombok.Data;

import java.sql.Timestamp;


/**
 * sys_range_role  Bean
 *
 * @author 系统生成
 * @date 2018-06-20 19:05:26
 */
@Data
public class SysRangeRoleEntity extends BaseModel
{
	
    /**
     * 范围权限 ID
    **/
    private Long rangeRoleId;
    /**
     * 用户Id
    **/
    private Long userId;
    /**
     * 范围权限类别
    **/
    private String rangeType;
    /**
     * 添加人
    **/
    private String createUser;
    /**
     * 创建时间
    **/
    private Timestamp createTime;

   
}

