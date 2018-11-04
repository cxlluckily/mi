package com.shankephone.mi.model;

import com.shankephone.mi.common.model.BaseModel;
import lombok.Data;


/**
 * stock_warehouse_station  Bean
 *
 * @author 系统生成
 * @date 2018-07-05 15:47:08
 */
@Data
public class StockWarehouseStationEntity extends BaseModel
{
	
    /**
     * 仓库车站Id
    **/
    private Long warehostStationId;
    /**
     * 仓库ID
    **/
    private Long warehouseId;
    /**
     * 车站Id
    **/
    private Long stationId;

   
}

