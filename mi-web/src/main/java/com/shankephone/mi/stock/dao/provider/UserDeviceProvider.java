package com.shankephone.mi.stock.dao.provider;

import com.shankephone.mi.model.StockUserDeviceEntity;

/**
 * 人员持有设备
 *
 * @author 赵亮
 * @date 2018-08-08 18:17
 */
public class UserDeviceProvider
{
    public String insertOne(StockUserDeviceEntity entity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" INSERT INTO ");
        sbSql.append("   stock_user_device ");
        sbSql.append("   ( ");
        sbSql.append("      userDeviceId, ");
        sbSql.append("      userId, ");
        sbSql.append("      sparePartId, ");
        sbSql.append("      count, ");
        sbSql.append("      stockId, ");
        sbSql.append("      createUser ");
        sbSql.append("   ) ");
        sbSql.append(" VALUES ");
        sbSql.append("   ( ");
        sbSql.append("      #{userDeviceId}, ");
        sbSql.append("      #{userId}, ");
        sbSql.append("      #{sparePartId}, ");
        sbSql.append("      #{count}, ");
        sbSql.append("      #{stockId}, ");
        sbSql.append("      #{operationUserName} ");
        sbSql.append("   ) ");

        return sbSql.toString();
    }

    public String UpdateQrCode(StockUserDeviceEntity entity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" UPDATE stock_user_device ");
        sbSql.append(" SET qrCode = #{qrCode} ");
        sbSql.append(" WHERE stockId = #{stockId} ");

        return sbSql.toString();
    }
}
