package com.shankephone.mi.stock.vo;

import com.shankephone.mi.model.StockBusinessApplyEntity;
import com.shankephone.mi.model.StockOutStockApplyEntity;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author 赵亮
 * @date 2018-07-11 14:32
 */
@Data
public class ApplyDetailVO
{
    private StockOutStockApplyEntity applyEntity;
    private StockBusinessApplyEntity stockBusinessApplyEntity;
    private List<Map<String, Object>> outStockApplyDetailEntities;
    private List<Map<String, Object>> outStockDetailEntities;
}
