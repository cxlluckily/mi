package com.shankephone.mi.model;

import com.shankephone.mi.common.model.BaseModel;
import lombok.Data;


/**
 * stock_out_stock_detail  Bean
 *
 * @author 系统生成
 * @date 2018-07-05 15:47:07
 */
@Data
public class StockOutStockDetailEntity extends BaseModel
{
	
    /**
     * 库存出库详情
    **/
    private Long outStockDetailId;
    /**
     * 库存ID
    **/
    private Long stockId;
    /**
     * 出库详情ID
    **/
    private Long outStockApplyDetailId;
    /**
     * 出库数量
    **/
    private Integer outCount;
}

