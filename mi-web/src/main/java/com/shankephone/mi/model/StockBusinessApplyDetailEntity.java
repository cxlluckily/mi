package com.shankephone.mi.model;

import com.shankephone.mi.common.model.BaseModel;
import lombok.Data;


/**
 * stock_business_apply_detail  Bean
 *
 * @author 系统生成
 * @date 2018-07-05 15:47:06
 */
@Data
public class StockBusinessApplyDetailEntity extends BaseModel
{
	
    /**
     * 业务申请详单ID
    **/
    private Long applyDetailId;
    /**
     * 业务申请ID
    **/
    private Long applyId;
    /**
     * 备件ID
    **/
    private Long sparePartId;
    /**
     * 申请数量
    **/
    private Integer applyCount;
    /**
     * 库存ID（返还申请用）
     **/
    private Long stockId;

    /**
     * 设备状态
     **/
    private String stockStatus;

}

