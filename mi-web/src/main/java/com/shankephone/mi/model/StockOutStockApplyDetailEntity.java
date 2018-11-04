package com.shankephone.mi.model;

import com.shankephone.mi.common.model.BaseModel;
import lombok.Data;


/**
 * stock_out_stock_apply_detail  Bean
 *
 * @author 系统生成
 * @date 2018-07-05 15:47:07
 */
@Data
public class StockOutStockApplyDetailEntity extends BaseModel
{
	
    /**
     * 发放详情ID
    **/
    private Long outStockApplyDetailId;
    /**
     * 出库单ID
    **/
    private Long outStockApplyId;
    /**
     * 备件ID
    **/
    private Long sparePartId;
    /**
     * 待出库数量
    **/
    private Integer outCount;
    /**
     * 已出库数量
    **/
    private Integer alreadyOutCount;

    private String stockStatus;
}

