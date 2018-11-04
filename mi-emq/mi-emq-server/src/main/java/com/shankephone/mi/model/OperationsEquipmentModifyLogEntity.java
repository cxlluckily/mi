package com.shankephone.mi.model;

import lombok.Data;

import java.util.Date;


/**
 * operations_equipment_modify_log  Bean
 *
 * @author 系统生成
 * @date 2018-10-17 13:10:53
 */
@Data
public class OperationsEquipmentModifyLogEntity
{
	
    /**
     * 运营设备属性修改日志ID
    **/
    private Long equipmentModifyLogId;
    /**
     * 运营设备ID
    **/
    private Long equipmentId;
    /**
     * 修改属性
    **/
    private String modifyAttribute;
    /**
     * 修改前值
    **/
    private String beforeValue;
    /**
     * 修改后值
    **/
    private String afterValue;
    /**
     * 修改人
    **/
    private String modifyUser;
    /**
     * 修改时间
    **/
    private Date modifyTime;
    /**
     * 备注
    **/
    private String remark;

   
}

