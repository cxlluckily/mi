package com.shankephone.mi.repair.dao.provider;

import com.shankephone.mi.common.enumeration.StockStatusEnum;
import com.shankephone.mi.repair.formbean.UpdateMySqparePartCountFormBean;
import com.shankephone.mi.repair.vo.MySparePartFindEntity;

/**
 * @author 赵亮
 * @date 2018-08-06 9:34
 */
public class MySparePartProvider
{
    public String getMySparePartList(MySparePartFindEntity findEntity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT ");
        sbSql.append("   stock_user_device.userDeviceId, ");
        sbSql.append("   stock_user_device.stockId, ");

        sbSql.append("   ifnull(stock_warehouse.warehouseName,'') AS warehouseName, ");
        sbSql.append("   part_spare_part_type.categoryName, ");
        sbSql.append("   tbSparePart.partName, ");
        sbSql.append("   ifnull(tbSparePart.brand,'') AS brand, ");
        sbSql.append("   tbSparePart.materiaCoding, ");
        sbSql.append("   ifnull(tbSparePart.specificationModel,'') AS specificationModel, ");
        sbSql.append("   tbSparePart.size, ");
        sbSql.append("   tbSparePart.material, ");
        sbSql.append("   tbSparePart.units, ");
        sbSql.append("   tbSparePart.sparePartId, ");
        sbSql.append("   sum(stock_user_device.count)       AS applyCount, ");
        sbSql.append("   DATE_FORMAT(stock_user_device.createTime, '%Y-%m-%d') AS applyTime, ");
        sbSql.append("   CASE stock_stock.status ");
        sbSql.append("   WHEN '" + StockStatusEnum.NORMAL.getCode() + "' ");
        sbSql.append("     THEN '"+StockStatusEnum.NORMAL.getMessage()+"' ");
        sbSql.append("   ELSE '"+StockStatusEnum.BAD.getMessage()+"' END AS status,");
        sbSql.append("   (SELECT imageUrl  FROM part_spare_part_image  WHERE sparePartId = tbSparePart.sparePartId LIMIT 1)  AS imageUrl ");
        sbSql.append(" FROM stock_user_device ");
        sbSql.append("   LEFT JOIN stock_stock ");
        sbSql.append("     ON stock_stock.stockId = stock_user_device.stockId ");
        sbSql.append("   LEFT JOIN stock_warehouse ");
        sbSql.append("     ON stock_warehouse.warehouseId = stock_stock.warehouseId ");
        sbSql.append("   LEFT JOIN part_spare_part AS tbSparePart ");
        sbSql.append("     ON tbSparePart.sparePartId = stock_user_device.sparePartId ");
        sbSql.append("   LEFT JOIN part_spare_part_type ");
        sbSql.append("     ON part_spare_part_type.sparePartTypeId = tbSparePart.sparePartTypeId ");
        sbSql.append(" WHERE stock_user_device.userId = #{operationUserId} and stock_user_device.count>0 ");
        sbSql.append(" GROUP BY stock_warehouse.warehouseName,stock_user_device.sparePartId ");

        sbSql.append(" ORDER BY warehouseName, categoryName, partName    ");
        sbSql.append(" LIMIT #{start}, #{limit} ");
        return sbSql.toString();

    }

    public String getMySparePartListTotal(MySparePartFindEntity findEntity)
    {
        StringBuilder sbSql = new StringBuilder();
//        sbSql.append(" SELECT count(*) AS totalCount  ");
//        sbSql.append(" FROM stock_user_device ");
//        sbSql.append("   LEFT JOIN stock_stock ");
//        sbSql.append("     ON stock_stock.stockId = stock_user_device.stockId ");
//        sbSql.append("   LEFT JOIN stock_warehouse ");
//        sbSql.append("     ON stock_warehouse.warehouseId = stock_stock.warehouseId ");
//        sbSql.append("   LEFT JOIN part_spare_part AS tbSparePart ");
//        sbSql.append("     ON tbSparePart.sparePartId = stock_user_device.sparePartId ");
//        sbSql.append("   LEFT JOIN part_spare_part_type ");
//        sbSql.append("     ON part_spare_part_type.sparePartTypeId = tbSparePart.sparePartTypeId ");
//        sbSql.append(" WHERE stock_user_device.userId = #{operationUserId} ");

        sbSql.append(" SELECT  count(1) from (");
        sbSql.append(" SELECT 1 ");
        sbSql.append(" FROM stock_user_device ");
        sbSql.append("   LEFT JOIN stock_stock ");
        sbSql.append("     ON stock_stock.stockId = stock_user_device.stockId ");
        sbSql.append("   LEFT JOIN stock_warehouse ");
        sbSql.append("     ON stock_warehouse.warehouseId = stock_stock.warehouseId ");
        sbSql.append("   LEFT JOIN part_spare_part AS tbSparePart ");
        sbSql.append("     ON tbSparePart.sparePartId = stock_user_device.sparePartId ");
        sbSql.append("   LEFT JOIN part_spare_part_type ");
        sbSql.append("     ON part_spare_part_type.sparePartTypeId = tbSparePart.sparePartTypeId ");
        sbSql.append(" WHERE stock_user_device.userId = #{operationUserId} and stock_user_device.count>0  ");
        sbSql.append(" GROUP BY stock_warehouse.warehouseName,stock_user_device.sparePartId ");
        sbSql.append(" )temp ");

        return sbSql.toString();

    }

    public String updateMySqparePartCount(UpdateMySqparePartCountFormBean entity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" UPDATE stock_user_device ");
        sbSql.append(" SET count = 0 ");
        sbSql.append(" WHERE stockId IN ( ");
        sbSql.append("   SELECT stockId ");
        sbSql.append("   FROM stock_out_stock_apply ");
        sbSql.append("     INNER JOIN stock_out_stock_apply_detail ");
        sbSql.append("       ON stock_out_stock_apply_detail.outStockApplyId = stock_out_stock_apply.outStockApplyId ");
        sbSql.append("     INNER JOIN stock_out_stock_detail ");
        sbSql.append("       ON stock_out_stock_detail.outStockApplyDetailId = stock_out_stock_apply_detail.outStockApplyDetailId ");
        sbSql.append("   WHERE applyId = #{applyId} ");
        sbSql.append(" ) ");
        sbSql.append("       AND userId = #{applyUserId} ");

        return sbSql.toString();

    }
}

