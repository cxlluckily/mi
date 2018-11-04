package com.shankephone.mi.model;

import com.shankephone.mi.common.model.BaseModel;
import lombok.Data;
import java.sql.Timestamp;


/**
 * stock_business_apply  Bean
 *
 * @author 系统生成
 * @date 2018-07-05 15:47:06
 */
@Data
public class StockBusinessApplyEntity extends BaseModel
{
	
    private Long applyId;

    private String applyNo;
    /**
     * 要出库仓库ID
    **/
    private Long outWarehouseId;
    /**
     * 申请仓库ID（调拨、返回）
    **/
    private Long inWarehouseId;
    /**
     * 申请人
    **/
    private String applyUser;
    /**
     * 申请人ID
    **/
    private Long applyUserId;
    /**
     * 申请时间
    **/
    private Timestamp applyTime;
    /**
     * 申请单状态
    **/
    private String applyStatus;
    /**
     * 申请类型
    **/
    private String applyType;
    /**
     * 审核人
    **/
    private String auditUser;
    /**
     * 审核人ID
    **/
    private Long auditUserId;
    /**
     * 审核时间
    **/
    private Timestamp auditTime;
    /**
     * 审核意见
    **/
    private String auditRemark;
    /**
     * 订单备注
    **/
    private String applyRemark;

   
}

