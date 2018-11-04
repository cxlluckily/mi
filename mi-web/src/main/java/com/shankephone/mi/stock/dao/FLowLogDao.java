package com.shankephone.mi.stock.dao;

import com.shankephone.mi.model.StockFlowLogEntity;
import com.shankephone.mi.stock.dao.provider.FlowLogProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Options;
import org.springframework.stereotype.Repository;

/**
 * @author 赵亮
 * @date 2018-07-12 11:25
 */
@Repository
public interface FLowLogDao
{
    @InsertProvider(type = FlowLogProvider.class, method = "insertStockFlowLog")
    @Options(useGeneratedKeys = true, keyProperty = "flowId")
    Integer insertStockFlowLog(StockFlowLogEntity entity);
}
