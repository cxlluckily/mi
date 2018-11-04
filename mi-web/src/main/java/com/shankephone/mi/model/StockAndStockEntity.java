package com.shankephone.mi.model;

import lombok.Data;
import com.shankephone.mi.common.model.BaseModel;



/**
 * stock_and_stock  Bean
 *
 * @author 系统生成
 * @date 2018-09-10 20:03:10
 */
@Data
public class StockAndStockEntity extends BaseModel
{
	
    private Long stockAndStockId;
    /**
     * 入库详单ID
    **/
    private Long inStockDetailId;
    /**
     * 库存ID
    **/
    private Long stockId;

   
}

