package com.shankephone.mi.model;

import lombok.Data;
import com.shankephone.mi.common.model.BaseModel;

import java.util.Date;


/**
 * stock_inventory_detail  Bean
 *
 * @author 系统生成
 * @date 2018-08-16 15:02:09
 */
@Data
public class StockInventoryDetailEntity extends BaseModel
{
	
    /**
     * 盘库详情ID
    **/
    private Long inventoryDetailId;
    /**
     * 库存任务ID
    **/
    private Long inventoryTaskId;
    /**
     * 备件ID
    **/
    private Long sparePartId;
    /**
     * 盘库前数量
    **/
    private Integer beforeAccount;
    /**
     * 盘库后数量
    **/
    private Integer aftierAccount;
    /**
     * 设备状态
    **/
    private String status;
    /**
     * 描述
    **/
    private String inventoryDescribe;
    /**
     * 添加人
    **/
    private String createUser;
    /**
     * 创建时间
    **/
    private Date createTime;
    /**
     * 修改人
    **/
    private String modifyUser;
    /**
     * 修改时间
    **/
    private Date modifyTime;

   
}

