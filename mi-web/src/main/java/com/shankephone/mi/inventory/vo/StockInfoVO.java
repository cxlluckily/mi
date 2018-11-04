package com.shankephone.mi.inventory.vo;

import com.shankephone.mi.common.model.BaseModel;
import com.shankephone.mi.model.StockStockEntity;
import lombok.Data;

import java.util.List;

/**
 * ${description}
 *
 * @author 司徒彬
 * @date 2018/7/31 17:05
 */
@Data
public class StockInfoVO extends BaseModel {
    Long stockId;
    List<StockStockEntity> stockEntities;
}
