package com.shankephone.mi.stock.dao.provider;

import com.shankephone.mi.model.StockInStockDetailEntity;
import com.shankephone.mi.model.StockInStockEntity;

/**
 * @author 赵亮
 * @date 2018-07-12 11:34
 */
public class InStockProvider
{
    public String insertInStock(StockInStockEntity entity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" INSERT INTO ");
        sbSql.append("   stock_in_stock ");
        sbSql.append("   ( ");
        sbSql.append("      applyId, ");
        sbSql.append("      operationSubjectId, ");
        sbSql.append("      inWarehouseId, ");
        sbSql.append("      inStockApplyNo, ");
        sbSql.append("      inStockUser, ");
        sbSql.append("      inStockUserId, ");
        sbSql.append("      inDate, ");
        sbSql.append("      inStockType, ");
        sbSql.append("      inStockStatus, ");
        sbSql.append("      outStockApplyNO, ");
        sbSql.append("      createUser, ");
        sbSql.append("      modifyUser ");
        sbSql.append("   ) ");
        sbSql.append(" VALUES ");
        sbSql.append("   ( ");
        sbSql.append("      #{applyId}, ");
        sbSql.append("      #{operationSubjectId}, ");
        sbSql.append("      #{inWarehouseId}, ");
        sbSql.append("      #{inStockApplyNo}, ");
        sbSql.append("      #{inStockUser}, ");
        sbSql.append("      #{inStockUserId}, ");
        sbSql.append("      #{inDate}, ");
        sbSql.append("      #{inStockType}, ");
        sbSql.append("      #{inStockStatus}, ");
        sbSql.append("      #{outStockApplyNO}, ");
        sbSql.append("      #{operationUserName}, ");
        sbSql.append("      #{operationUserName} ");
        sbSql.append("   ) ");

        return sbSql.toString();
    }

    public String insertInStockDetail(StockInStockDetailEntity entity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" INSERT INTO ");
        sbSql.append("   stock_in_stock_detail ");
        sbSql.append("   ( ");
        sbSql.append("      inStockId, ");
        sbSql.append("      sparePartId, ");
        sbSql.append("      inventoryType, ");
        sbSql.append("      inStockAcount, ");
        sbSql.append("      alreadyInStockAcount, ");
        sbSql.append("      price, ");
        sbSql.append("      remark, ");
        sbSql.append("      goodsShelvesId, ");
        sbSql.append("      createUser, ");
        sbSql.append("      modifyUser, ");
        sbSql.append("      stockId ");
        sbSql.append("   ) ");
        sbSql.append(" VALUES ");
        sbSql.append("   ( ");
        sbSql.append("      #{inStockId}, ");
        sbSql.append("      #{sparePartId}, ");
        sbSql.append("      #{inventoryType}, ");
        sbSql.append("      #{inStockAcount}, ");
        sbSql.append("      #{alreadyInStockAcount}, ");
        sbSql.append("      #{price}, ");
        sbSql.append("      #{remark}, ");
        sbSql.append("      #{goodsShelvesId}, ");
        sbSql.append("      #{operationUserName}, ");
        sbSql.append("      #{operationUserName}, ");
        sbSql.append("      #{stockId} ");
        sbSql.append("   ) ");


        return sbSql.toString();

    }
}
