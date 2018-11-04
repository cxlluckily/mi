package com.shankephone.mi.stock.formbean;

import com.shankephone.mi.common.model.BaseModel;
import com.shankephone.mi.model.StockOutStockApplyDetailEntity;
import com.shankephone.mi.model.StockOutStockApplyEntity;
import com.shankephone.mi.model.StockOutStockDetailEntity;
import lombok.Data;

import java.util.List;

/**
 * @author 赵亮
 * @date 2018-07-11 13:31
 */
@Data
public class UseAndTransfeOutStockBusiEntity extends BaseModel
{
    private StockOutStockApplyEntity applyEntity;
    private List<StockOutStockDetailEntity> outStockDetailEntities;
    private List<StockOutStockApplyDetailEntity> outStockApplyDetailEntities;
}
