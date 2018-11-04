package com.shankephone.mi.stock.dao.provider;

import com.shankephone.mi.model.StockFlowLogEntity;

/**
 * @author 赵亮
 * @date 2018-07-12 11:24
 */
public class FlowLogProvider
{
    public String insertStockFlowLog(StockFlowLogEntity entity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" INSERT INTO ");
        sbSql.append("   stock_flow_log ");
        sbSql.append("   ( ");
        sbSql.append("      stockId, ");
        sbSql.append("      sparePartId, ");
        sbSql.append("      warehouseId, ");
        sbSql.append("      sourceId, ");
        sbSql.append("      type, ");
        sbSql.append("      count, ");
        sbSql.append("      flowDescribe, ");
        sbSql.append("      createUser, ");
        sbSql.append("      modifyUser ");
        sbSql.append("   ) ");
        sbSql.append(" VALUES ");
        sbSql.append("   ( ");
        sbSql.append("      #{stockId}, ");
        sbSql.append("      #{sparePartId}, ");
        sbSql.append("      #{warehouseId}, ");
        sbSql.append("      #{sourceId}, ");
        sbSql.append("      #{type}, ");
        sbSql.append("      #{count}, ");
        sbSql.append("      #{flowDescribe}, ");
        sbSql.append("      #{operationUserName}, ");
        sbSql.append("      #{operationUserName} ");
        sbSql.append("   ) ");

        return sbSql.toString();
    }
}
