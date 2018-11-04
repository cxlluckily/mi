package com.shankephone.mi.model;

import lombok.Data;
import com.shankephone.mi.common.model.BaseModel;

import java.util.Date;


/**
 * operations_equipment_parts  Bean
 *
 * @author 系统生成
 * @date 2018-08-15 18:13:47
 */
@Data
public class OperationsEquipmentPartsEntity extends BaseModel
{
	
    /**
     * 主键
    **/
    private Long equipmentPartsId;
    /**
     * 运营设备ID
    **/
    private Long equipmentId;
    /**
     * 备件ID
    **/
    private Long sparePartId;
    /**
     * 库存id
    **/
    private Long stockId;
    /**
     * 维修申请单ID
    **/
    private Long maintenanceApplyId;
    /**
     * 状态
    **/
    private String status;
    /**
     * 添加人
    **/
    private String createUser;
    /**
     * 创建时间
    **/
    private Date createTime;

   
}

