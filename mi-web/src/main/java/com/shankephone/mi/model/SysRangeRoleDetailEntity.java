package com.shankephone.mi.model;

import com.shankephone.mi.common.model.BaseModel;
import lombok.Data;


/**
 * sys_range_role_detail  Bean
 *
 * @author 系统生成
 * @date 2018-06-20 19:05:26
 */
@Data
public class SysRangeRoleDetailEntity extends BaseModel
{
	
    /**
     * 范围权限详细ID
    **/
    private Long rangeRoleDetailId;
    /**
     * 范围权限 ID
    **/
    private Long rangeRoleId;
    /**
     * 用户Id
    **/
    private Long userId;
    /**
     * 仓库或区域ID
    **/
    private Long id;

   
}

