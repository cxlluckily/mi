package com.shankephone.mi.model;

import lombok.Data;
import com.shankephone.mi.common.model.BaseModel;

import java.util.Date;


/**
 * stock_inventory_task  Bean
 *
 * @author 系统生成
 * @date 2018-08-16 14:50:00
 */
@Data
public class StockInventoryTaskEntity extends BaseModel
{
	
    /**
     * 库存任务ID
    **/
    private Long inventoryTaskId;
    /**
     * 仓库ID
    **/
    private Long warehouseId;
    /**
     * 批次号
    **/
    private String batchNo;
    /**
     * 负责人
    **/
    private String headPerson;
    /**
     * 负责人ID
     **/
    private Long headPersonUserId;
    /**
     * 状态
    **/
    private String status;
    /**
     * 开始时间
    **/
    private Date beginTIme;
    /**
     * 结束时间
    **/
    private Date endTIme;
    /**
     * 添加人
    **/
    private String createUser;
    /**
     * 添加人Id
     **/
    private Long createUserId;
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

    /**
     * 备注
     **/
    private String remark;
}

