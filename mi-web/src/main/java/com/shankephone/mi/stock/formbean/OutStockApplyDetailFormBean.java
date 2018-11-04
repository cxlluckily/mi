package com.shankephone.mi.stock.formbean;

import lombok.Data;

/**
 * @author 赵亮
 * @date 2018-07-12 9:40
 */
@Data
public class OutStockApplyDetailFormBean
{
    /**
     * 备件ID
     **/
    private Long sparePartId;
    /**
     * 库存ID
     **/
    private Long stockId;
    /**
     * 出库数量
     **/
    private Integer outCount;

    /**
     * 出库详情ID，前台不用传
     **/
    private Long outStockApplyDetailId;

    private String stockStatus;
}
