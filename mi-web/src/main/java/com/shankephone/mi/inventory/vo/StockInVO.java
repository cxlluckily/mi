package com.shankephone.mi.inventory.vo;

import com.shankephone.mi.model.StockInStockEntity;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 入库单据业务实体
 *
 * @author 司徒彬
 * @date 2018/7/17 16:49
 */
@Data
public class StockInVO extends StockInStockEntity {
    List<Map<String, Object>> details;
    List<StockInStockDetailVO> detailEntities;
}
