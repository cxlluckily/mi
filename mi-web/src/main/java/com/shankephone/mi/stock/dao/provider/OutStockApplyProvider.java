package com.shankephone.mi.stock.dao.provider;

import com.shankephone.mi.common.enumeration.StatusEnum;
import com.shankephone.mi.model.StockOutStockApplyDetailEntity;
import com.shankephone.mi.model.StockOutStockApplyEntity;
import com.shankephone.mi.model.StockOutStockDetailEntity;
import com.shankephone.mi.stock.enumeration.OutStockStatusEnum;
import com.shankephone.mi.stock.formbean.BindQrCodeFindEntity;
import com.shankephone.mi.stock.formbean.OutStockApplyFindEntity;
import com.shankephone.mi.stock.formbean.UpdateStockQrCodeFormBean;
import com.shankephone.mi.util.StringUtils;

/**
 * @author 赵亮
 * @date 2018-07-11 10:06
 */
public class OutStockApplyProvider
{
    public String getOutStockApplyInfo(OutStockApplyFindEntity findEntity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT ");
        sbSql.append("   stock_warehouse.warehouseName, ");
        sbSql.append("   stock_warehouse.warehouseId, ");
        sbSql.append("   stock_business_apply.applyId, ");
        sbSql.append("   stock_business_apply.applyNo, ");
        sbSql.append("   stock_out_stock_apply.outOrderType, ");
        sbSql.append("   stock_out_stock_apply.outStockApplyNO, ");
        sbSql.append("   stock_out_stock_apply.outUser, ");
        sbSql.append("   stock_out_stock_apply.outStockApplyId, ");
        sbSql.append("   stock_out_stock_apply.remark, ");
        sbSql.append("   stock_out_stock_apply.operationSubjectId, ");
        sbSql.append("   (SELECT inStockStatus  FROM  stock_in_stock WHERE outStockApplyNO =stock_out_stock_apply.outStockApplyNO  LIMIT 1 ) AS inStockStatus, ");
        sbSql.append("   stock_business_apply.applyUser, ");
        sbSql.append("   CASE stock_out_stock_apply.outApplyStatus ");
        sbSql.append("   WHEN '" + OutStockStatusEnum.TO_BE_OUT.getCode() + "' ");
        sbSql.append("     THEN '待出库' ");
        sbSql.append("   WHEN '" + OutStockStatusEnum.ALREADY_OUT.getCode() + "' ");
        sbSql.append("     THEN '已出库' ");
        sbSql.append("   END                                                       AS outApplyStatus, ");
        sbSql.append("   stock_out_stock_apply.outDate, ");
        sbSql.append("   stock_out_stock_apply.applyDate, ");
        sbSql.append("   (SELECT sum(outCount) ");
        sbSql.append("    FROM stock_out_stock_apply_detail ");
        sbSql.append("    WHERE outStockApplyId = stock_out_stock_apply.outStockApplyId)      AS partCount, ");
        sbSql.append("   (SELECT sum(alreadyOutCount) ");
        sbSql.append("    FROM stock_out_stock_apply_detail ");
        sbSql.append("    WHERE outStockApplyId = stock_out_stock_apply.outStockApplyId)      AS alreadyOutCount,outApplyStatus ");
        sbSql.append(getOutStockApplyInfoFromPartSql(findEntity));
        sbSql.append("   ORDER BY isnull(stock_out_stock_apply.outDate) DESC,stock_out_stock_apply.applyDate DESC ");
        sbSql.append("   LIMIT #{start}, #{limit} ");
        return sbSql.toString();

    }

    public String getOutStockApplyInfoCount(OutStockApplyFindEntity findEntity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT count(*) AS totalCount ");
        sbSql.append(getOutStockApplyInfoFromPartSql(findEntity));
        return sbSql.toString();
    }

    private String getOutStockApplyInfoFromPartSql(OutStockApplyFindEntity findEntity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" FROM stock_out_stock_apply ");
        sbSql.append("   LEFT JOIN stock_business_apply ");
        sbSql.append("     ON stock_out_stock_apply.applyId = stock_business_apply.applyId ");
        sbSql.append("   LEFT JOIN stock_warehouse ");
        sbSql.append("     ON stock_out_stock_apply.outWarehouseId = stock_warehouse.warehouseId ");
        sbSql.append(" WHERE 1 = 1 ");

        if (!"0".equals(findEntity.getOutWarehouseId())) {
            sbSql.append(" and stock_out_stock_apply.outWarehouseId  = #{outWarehouseId} ");
        } else {
            sbSql.append(" and stock_out_stock_apply.outWarehouseId  in (" + StringUtils.listToString(findEntity.getWareHouseIds()) + ") ");
        }

        //sbSql.append("       AND stock_out_stock_apply.outWarehouseId in ("+findEntity.getOutWarehouseId()+") ");

        if (StringUtils.isNotEmpty(findEntity.getOutStockApplyNO()))
        {
            sbSql.append("       AND stock_out_stock_apply.outStockApplyNO LIKE concat('%',#{outStockApplyNO},'%') ");
        }
        if (StringUtils.isNotEmpty(findEntity.getApplyUser()))
        {
            sbSql.append("       AND stock_business_apply.applyUser LIKE concat('%',#{applyUser},'%') ");
        }
        if (!"all".equals(findEntity.getOutApplyStatus()))
        {
            sbSql.append("       AND stock_out_stock_apply.outApplyStatus = #{outApplyStatus} ");
        }

        if (!"all".equals(findEntity.getOutOrderType()))
        {
            sbSql.append("       AND stock_out_stock_apply.outOrderType = #{outOrderType} ");
        }
        if(StringUtils.isNotEmpty(findEntity.getApplyNo()))
        {
            sbSql.append("       AND stock_business_apply.applyNo LIKE concat('%',#{applyNo},'%') ");
        }
        sbSql.append("       AND stock_out_stock_apply.applyDate BETWEEN #{beginTime} AND #{endTime} ");
        return sbSql.toString();
    }

    public String getApplyDetailInfoByoutStockApplyId(Long outStockApplyId)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT ");
        sbSql.append("     stock_out_stock_apply_detail.*, ");
        sbSql.append("     part_spare_part.*, ");
        sbSql.append("     part_spare_part_type.categoryName, ");
        sbSql.append("   (SELECT imageUrl  FROM part_spare_part_image  WHERE sparePartId = part_spare_part.sparePartId LIMIT 1)  AS imageUrl, ");
        sbSql.append("     stock_out_stock_apply_detail.stockStatus  ");
        sbSql.append("   FROM stock_out_stock_apply_detail ");
        sbSql.append("     INNER JOIN part_spare_part ");
        sbSql.append("       ON stock_out_stock_apply_detail.sparePartId = part_spare_part.sparePartId ");
        sbSql.append("     INNER JOIN part_spare_part_type ");
        sbSql.append("       ON part_spare_part_type.sparePartTypeId = part_spare_part.sparePartTypeId ");
        sbSql.append("   WHERE outStockApplyId = #{outStockApplyId} ");

        return sbSql.toString();
    }

    public String getOutStockDetailByoutStockApplyId(Long outStockApplyId)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT ");
        sbSql.append("   supplierName, ");
        sbSql.append("   houseNo, ");
        sbSql.append("   shelfNumber, ");
        sbSql.append("   equipmentNO, ");
        sbSql.append("   qrCode, ");
        sbSql.append("   serialNumber, ");
        sbSql.append("   brand, ");
        sbSql.append("   partName, ");
        sbSql.append("   materiaCoding, ");
        sbSql.append("   specificationModel, ");
        sbSql.append("   size, ");
        sbSql.append("   stock_stock.inventoryType, ");
        sbSql.append("   categoryName, ");
        sbSql.append("   (SELECT imageUrl  FROM part_spare_part_image  WHERE sparePartId = part_spare_part.sparePartId LIMIT 1)  AS imageUrl, ");
        sbSql.append("   stock_out_stock_detail.*, ");
        sbSql.append("     stock_stock.status AS stockStatus");
        sbSql.append(" FROM stock_stock ");
        sbSql.append("   INNER JOIN stock_out_stock_detail ");
        sbSql.append("     ON stock_out_stock_detail.stockId = stock_stock.stockId ");
        sbSql.append("   INNER JOIN part_spare_part ");
        sbSql.append("     ON stock_stock.sparePartId = part_spare_part.sparePartId ");
        sbSql.append("   INNER JOIN part_spare_part_type ");
        sbSql.append("     ON part_spare_part_type.sparePartTypeId = part_spare_part.sparePartTypeId ");
        sbSql.append("   LEFT JOIN stock_goods_shelves ");
        sbSql.append("     ON stock_goods_shelves.goodsShelvesId = stock_stock.goodsShelvesId ");
        sbSql.append("   LEFT JOIN stock_supplier ");
        sbSql.append("     ON stock_supplier.supplierId = stock_stock.supplierId ");
        sbSql.append(" WHERE outStockApplyDetailId IN (SELECT outStockApplyDetailId ");
        sbSql.append("                                 FROM stock_out_stock_apply_detail ");
        sbSql.append("                                 WHERE outStockApplyId = #{outStockApplyId}) ");

        return sbSql.toString();

    }

    public String getCanSendGoodsInfo(OutStockApplyFindEntity findEntity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT ");
        sbSql.append("   supplierName, ");
        sbSql.append("   houseNo, ");
        sbSql.append("   shelfNumber, ");
        sbSql.append("   equipmentNO, ");
        sbSql.append("   qrCode, ");
        sbSql.append("   serialNumber, ");
        sbSql.append("   account, ");
        sbSql.append("   DATE_FORMAT(inDate, '%Y-%m-%d') AS inDate, ");
        sbSql.append("   brand, ");
        sbSql.append("   partName, ");
        sbSql.append("   materiaCoding, ");
        sbSql.append("   specificationModel, ");
        sbSql.append("   size,inventoryType, ");
        sbSql.append("   stock_stock.status, ");
        sbSql.append("   stock_supplier.supplierId, ");
        sbSql.append("   (SELECT imageUrl  FROM part_spare_part_image  WHERE sparePartId = part_spare_part.sparePartId LIMIT 1)  AS imageUrl, ");
        sbSql.append("   categoryName,stock_stock.stockId,stock_stock.sparePartId ");
        sbSql.append(" FROM stock_stock ");
        sbSql.append("   INNER JOIN part_spare_part ");
        sbSql.append("     ON stock_stock.sparePartId = part_spare_part.sparePartId ");
        sbSql.append("   INNER JOIN part_spare_part_type ");
        sbSql.append("     ON part_spare_part_type.sparePartTypeId = part_spare_part.sparePartTypeId ");
        sbSql.append("   LEFT JOIN stock_goods_shelves ");
        sbSql.append("     ON stock_goods_shelves.goodsShelvesId = stock_stock.goodsShelvesId ");
        sbSql.append("   LEFT JOIN stock_supplier ");
        sbSql.append("     ON stock_supplier.supplierId = stock_stock.supplierId ");
        sbSql.append("   WHERE account > 0 ");
        if (StringUtils.isNotEmpty(findEntity.getHouseNo()))
        {
            sbSql.append("         AND houseNo LIKE concat('%', #{houseNo}, '%') ");
        }
        if (StringUtils.isNotEmpty(findEntity.getShelfNumber()))
        {
            sbSql.append("         AND shelfNumber LIKE concat('%', #{shelfNumber}, '%') ");
        }

        //sbSql.append("   AND stock_stock.sparePartId = #{sparePartId}");
        sbSql.append("   AND stock_stock.sparePartId in ("+findEntity.getSparePartId()+") ");
        
        sbSql.append("   AND stock_stock.warehouseId = #{warehouseId}");
        if (StringUtils.isNotEmpty(findEntity.getPartName()))
        {
            sbSql.append("   AND  part_spare_part.partName LIKE concat('%', #{partName}, '%') ");
        }
        if(!"all".equals(findEntity.getStatus()))
        {
            sbSql.append("   AND stock_stock.status = #{status}");
        }
        if (StringUtils.isNotEmpty(findEntity.getSupplierId()))
        {
            sbSql.append("   AND  stock_supplier.supplierId = #{supplierId}");
        }

        sbSql.append("   ORDER BY houseNo, shelfNumber ");

        return sbSql.toString();

    }

    public String outStockUpdateOutApply(StockOutStockApplyEntity applyEntity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" UPDATE ");
        sbSql.append("   stock_out_stock_apply ");
        sbSql.append(" SET ");
        sbSql.append("   outUser = #{outUser}, ");
        sbSql.append("   outUserId = #{outUserId}, ");
        sbSql.append("   outDate = #{outDate}, ");
        sbSql.append("   outApplyStatus = #{outApplyStatus}, ");
        sbSql.append("   remark = #{remark}, ");
        sbSql.append("   outDate = #{outDate}, ");
        sbSql.append("   modifyUser = #{outUser} ");
        sbSql.append(" WHERE ");
        sbSql.append("   outStockApplyId = #{outStockApplyId} ");
        return sbSql.toString();
    }

    public String outStockUpdateApplyDetail(StockOutStockApplyDetailEntity applyDetailEntity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" UPDATE ");
        sbSql.append("   stock_out_stock_apply_detail ");
        sbSql.append(" SET ");
        sbSql.append("   alreadyOutCount = #{alreadyOutCount} ");
        sbSql.append(" WHERE ");
        sbSql.append("   outStockApplyDetailId = #{outStockApplyDetailId} ");
        return sbSql.toString();
    }

    public String getCanOutCountBystockId(Long stockId)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT account ");
        sbSql.append(" FROM stock_stock ");
        sbSql.append(" WHERE stockId=#{stockId} ");

        return sbSql.toString();

    }

    public String outStockUpdateStock(StockOutStockDetailEntity entity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" UPDATE ");
        sbSql.append("   stock_stock ");
        sbSql.append(" SET ");
        sbSql.append("   account = account - #{outCount}, ");
        sbSql.append("   modifyUser = #{operationUserName} ");
        sbSql.append(" WHERE ");
        sbSql.append("   stockId = #{stockId} ");

        return sbSql.toString();
    }

    public String insertStockOutStockDetail(StockOutStockDetailEntity entity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" INSERT INTO ");
        sbSql.append("   stock_out_stock_detail ");
        sbSql.append("   ( ");
        sbSql.append("      stockId, ");
        sbSql.append("      outStockApplyDetailId, ");
        sbSql.append("      outCount ");
        sbSql.append("   ) ");
        sbSql.append(" VALUES ");
        sbSql.append("   ( ");
        sbSql.append("      #{stockId}, ");
        sbSql.append("      #{outStockApplyDetailId}, ");
        sbSql.append("      #{outCount} ");
        sbSql.append("   ) ");

        return sbSql.toString();
    }

    public String insertOutStockApply(StockOutStockApplyEntity entity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" INSERT INTO ");
        sbSql.append("   stock_out_stock_apply ");
        sbSql.append("   ( ");
        sbSql.append("      applyId, ");
        sbSql.append("      operationSubjectId, ");
        sbSql.append("      outWarehouseId, ");
        sbSql.append("      outOrderType, ");
        sbSql.append("      outStockApplyNO, ");
        sbSql.append("      outUser, ");
        sbSql.append("      outUserId, ");
        sbSql.append("      outDate, ");
        sbSql.append("      remark, ");
        sbSql.append("      outApplyStatus, ");
        sbSql.append("      modifyUser, ");
        sbSql.append("      applyDate ");
        sbSql.append("   ) ");
        sbSql.append(" VALUES ");
        sbSql.append("   ( ");
        sbSql.append("      #{applyId}, ");
        sbSql.append("      #{operationSubjectId}, ");
        sbSql.append("      #{outWarehouseId}, ");
        sbSql.append("      #{outOrderType}, ");
        sbSql.append("      #{outStockApplyNO}, ");
        sbSql.append("      #{outUser}, ");
        sbSql.append("      #{outUserId}, ");
        sbSql.append("      #{outDate}, ");
        sbSql.append("      #{remark}, ");
        sbSql.append("      #{outApplyStatus}, ");
        sbSql.append("      #{operationUserName}, ");
        sbSql.append("      #{applyDate} ");
        sbSql.append("   ) ");
        return sbSql.toString();

    }

    public String insertOutStockApplyDetail(StockOutStockApplyDetailEntity entity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" INSERT INTO ");
        sbSql.append("   stock_out_stock_apply_detail ");
        sbSql.append("   ( ");
        sbSql.append("      outStockApplyId, ");
        sbSql.append("      sparePartId, ");
        sbSql.append("      outCount, ");
        sbSql.append("      stockStatus, ");
        sbSql.append("      alreadyOutCount ");
        sbSql.append("   ) ");
        sbSql.append(" VALUES ");
        sbSql.append("   ( ");
        sbSql.append("      #{outStockApplyId}, ");
        sbSql.append("      #{sparePartId}, ");
        sbSql.append("      #{outCount}, ");
        sbSql.append("      #{stockStatus}, ");
        sbSql.append("      #{alreadyOutCount} ");
        sbSql.append("   ) ");
        return sbSql.toString();
    }

    public String getBindqrCodeStockInfo(BindQrCodeFindEntity findEntity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT ");
        sbSql.append("     stock_stock.stockId, ");
        sbSql.append("     warehouseName, ");
        sbSql.append("     houseNo, ");
        sbSql.append("     shelfNumber, ");
        sbSql.append("     equipmentNO, ");
        sbSql.append("     partName, ");
        sbSql.append("     specificationModel, ");
        sbSql.append("     partName AS categoryName, ");
        sbSql.append("     serialNumber,qrCode,part_spare_part.sparePartId ");
        sbSql.append("   FROM stock_out_stock_apply_detail ");
        sbSql.append("     INNER JOIN stock_out_stock_detail ");
        sbSql.append("       ON stock_out_stock_apply_detail.outStockApplyDetailId = stock_out_stock_detail.outStockApplyDetailId ");
        sbSql.append("     INNER JOIN stock_stock ");
        sbSql.append("       ON stock_stock.stockId = stock_out_stock_detail.stockId ");
        sbSql.append("     INNER JOIN part_spare_part ");
        sbSql.append("       ON stock_stock.sparePartId = part_spare_part.sparePartId ");
        sbSql.append("     INNER JOIN stock_warehouse ");
        sbSql.append("       ON stock_stock.warehouseId = stock_warehouse.warehouseId ");
        sbSql.append("     LEFT JOIN stock_goods_shelves ");
        sbSql.append("       ON stock_goods_shelves.goodsShelvesId = stock_stock.goodsShelvesId ");
        sbSql.append("   WHERE outStockApplyId = #{outStockApplyId} ");
        sbSql.append("         AND inventoryType = #{inventoryType} ");
        return sbSql.toString();

    }

    public String updateStockQrCode(UpdateStockQrCodeFormBean formBean)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" UPDATE ");
        sbSql.append("   stock_stock ");
        sbSql.append(" SET ");
        sbSql.append("   qrCode = #{qrCode}, ");
        sbSql.append("   modifyUser = #{operationUserName} ");
        sbSql.append(" WHERE ");
        sbSql.append("   stockId = #{stockId} ");
        return sbSql.toString();

    }

    public String getStockOutStockApplyEntity(Long outStockApplyId)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT stock_out_stock_apply.*,stock_business_apply.applyRemark,stock_business_apply.applyNo,stock_business_apply.applyUser,stock_business_apply.auditUser,stock_warehouse.warehouseName,stock_warehouse.warehouseId ");
        sbSql.append(" FROM stock_out_stock_apply");
        sbSql.append(" Left join stock_business_apply  on  stock_out_stock_apply.applyId=stock_business_apply.applyId ");
        sbSql.append(" Left join  stock_warehouse on stock_out_stock_apply.outWarehouseId=stock_warehouse.warehouseId ");
        sbSql.append(" WHERE stock_out_stock_apply.outStockApplyId = #{outStockApplyId}  limit 1 ");

        return sbSql.toString();

    }

    public String updateQrCode(UpdateStockQrCodeFormBean entity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" UPDATE stock_qr_code ");
        sbSql.append(" SET stockId = #{stockId},sparePartId = #{sparePartId} ");
        sbSql.append(" ,status = '" + StatusEnum.STOP_USING.getValue() + "' ");
        sbSql.append(" WHERE qrCode = #{qrCode} ");

        return sbSql.toString();
    }


    public String getStockSparePartByqrCode(UpdateStockQrCodeFormBean formBean)
    {

        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT stock_stock.*, ");
        sbSql.append("   part_spare_part.sparePartTypeId, ");
        sbSql.append("   part_spare_part.partName, ");
        sbSql.append("   stock_goods_shelves.houseNo, ");
        sbSql.append("   stock_goods_shelves.containerType, ");
        sbSql.append("   stock_goods_shelves.shelfNumber ");
        sbSql.append("   FROM stock_stock ");
        sbSql.append("     INNER JOIN part_spare_part ");
        sbSql.append("       ON part_spare_part.sparePartId = stock_stock.sparePartId ");
        sbSql.append("     LEFT JOIN stock_goods_shelves ");
        sbSql.append("       ON stock_goods_shelves.goodsShelvesId = stock_stock.goodsShelvesId ");
        sbSql.append("   WHERE stock_stock.qrCode = #{qrCode} ");
        sbSql.append(" ORDER BY stock_stock.stockId DESC ");
        sbSql.append(" LIMIT 1 ");//因为出入库不删除原来的数据，会有旧数据在里面，所以取最后一个
        return sbSql.toString();

    }

}