package com.shankephone.mi.inventory.dao.provider;

import com.shankephone.mi.common.enumeration.InStockStatusEnum;
import com.shankephone.mi.inventory.formbean.StockInFindEntity;
import com.shankephone.mi.inventory.vo.StockInStockDetailVO;
import com.shankephone.mi.inventory.vo.StockInVO;
import com.shankephone.mi.model.*;
import com.shankephone.mi.util.ObjectUtils;
import com.shankephone.mi.util.StringUtils;

/**
 * 入库单Provider
 *
 * @author 司徒彬
 * @date 2018 /7/17 10:16
 */
public class StockInProvider
{

    /**
     * Get pager info string.
     *
     * @param findEntity the find entity
     * @return the string
     */
    public String getPagerInfo(StockInFindEntity findEntity)
    {

        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT ");
        sbSql.append("   inStock.*, ");
        sbSql.append("   warehouseName, ");
        sbSql.append("   ( ");
        sbSql.append("     SELECT sum(detail.inStockAcount) ");
        sbSql.append("     FROM stock_in_stock_detail detail ");
        sbSql.append("     WHERE detail.inStockId = inStock.inStockId) sumOfCount, ");
        sbSql.append("   applyNo,apply.applyTime,apply.applyUser ");
        sbSql.append(" FROM stock_in_stock inStock ");
        sbSql.append("   INNER JOIN stock_warehouse warehouse ON inStock.inWarehouseId = warehouse.warehouseId ");
        sbSql.append("   LEFT JOIN stock_business_apply apply  ON apply.applyId = inStock.applyId ");
        sbSql.append(" WHERE 1=1 ");
        if (findEntity.getWarehouseId().intValue() != 0)
        {
            sbSql.append(" and warehouseId = #{warehouseId} ");
        }
        else
        {
            sbSql.append(" and warehouseId in (" + StringUtils.listToString(findEntity.getWareHouseIds()) + ") ");
        }
        sbSql.append("       AND inStockApplyNo LIKE concat('%', ifnull(#{inStockApplyNo}, ''), '%') ");
        if (StringUtils.isNotEmpty(findEntity.getApplyUser()))
        {
            sbSql.append("       AND apply.applyUser LIKE concat('%', ifnull(#{applyUser}, ''), '%') ");
        }
        sbSql.append("       AND inStock.createTime BETWEEN #{beginTime} AND #{endTime} ");
        if (!"all".equalsIgnoreCase(findEntity.getInStockStatus()))
        {
            sbSql.append("       AND inStock.inStockStatus = #{inStockStatus} ");
        }
        if (!"all".equalsIgnoreCase(findEntity.getInStockType()))
        {
            sbSql.append("       AND inStock.inStockType = #{inStockType} ");
        }
        if (StringUtils.isNotEmpty(findEntity.getOutStockApplyNO()))
        {
            sbSql.append("       AND inStock.outStockApplyNO LIKE concat('%', ifnull(#{outStockApplyNO}, ''), '%') ");
        }

        sbSql.append(" ORDER BY ifnull(inStock.inDate,sysdate())  DESC ");
        sbSql.append(" LIMIT #{start}, #{limit} ");
        return sbSql.toString();
    }

    /**
     * Get pager info total string.
     *
     * @param findEntity the find entity
     * @return the string
     */
    public String getPagerInfoTotal(StockInFindEntity findEntity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT ");
        sbSql.append("   count(1) ");
        sbSql.append(" FROM stock_in_stock inStock ");
        sbSql.append("   INNER JOIN stock_warehouse warehouse ON inStock.inWarehouseId = warehouse.warehouseId ");
        sbSql.append("   LEFT JOIN stock_business_apply apply  ON apply.applyId = inStock.applyId ");
        sbSql.append(" WHERE 1=1 ");
        if (findEntity.getWarehouseId().intValue() != 0)
        {
            sbSql.append(" and warehouseId = #{warehouseId} ");
        }
        else
        {
            sbSql.append(" and warehouseId in (" + StringUtils.listToString(findEntity.getWareHouseIds()) + ") ");
        }
        sbSql.append("       AND inStockApplyNo LIKE concat('%', ifnull(#{inStockApplyNo}, ''), '%') ");
        if (StringUtils.isNotEmpty(findEntity.getApplyUser()))
        {
            sbSql.append("       AND apply.applyUser LIKE concat('%', ifnull(#{applyUser}, ''), '%') ");
        }
        sbSql.append("       AND inStock.createTime BETWEEN #{beginTime} AND #{endTime} ");

        if (!"all".equalsIgnoreCase(findEntity.getInStockStatus()))
        {
            sbSql.append("       AND inStock.inStockStatus = #{inStockStatus} ");
        }
        if (!"all".equalsIgnoreCase(findEntity.getInStockType()))
        {
            sbSql.append("       AND inStock.inStockType = #{inStockType} ");
        }
        if (StringUtils.isNotEmpty(findEntity.getOutStockApplyNO()))
        {
            sbSql.append("       AND inStock.outStockApplyNO LIKE concat('%', ifnull(#{outStockApplyNO}, ''), '%') ");
        }
        return sbSql.toString();
    }

    /**
     * Get stock in info by id string.
     *
     * @param inStockId the in stock id
     * @return the string
     */
    public String getStockInInfoById(Long inStockId)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT ");
        sbSql.append("   inStock.*, ");
        sbSql.append("   warehouse.warehouseName as inWarehouseName, ");
        sbSql.append("   outStock.outOrderType, ");
        sbSql.append("   outStock.outUser, ");
        sbSql.append("   outStock.outUserId, ");
        sbSql.append("   outStock.outDate, ");
        sbSql.append("   outStock.outApplyStatus, ");
        sbSql.append("   outStock.applyDate, ");
        sbSql.append("   outStock.remark AS  outRemark, ");
        sbSql.append("   OutWarehouse.warehouseName as outWarehouseName, ");
        sbSql.append("   stock_business_apply.applyNo, ");
        sbSql.append("   stock_business_apply.applyUser, ");
        sbSql.append("   stock_business_apply.applyTime ");
        sbSql.append(" FROM stock_in_stock inStock ");
        sbSql.append(" LEFT JOIN stock_warehouse warehouse ON warehouse.warehouseId = inStock.inWarehouseId ");
        sbSql.append(" LEFT JOIN  stock_out_stock_apply outStock on inStock.outStockApplyNO=outStock.outStockApplyNO ");
        sbSql.append(" LEFT JOIN stock_warehouse OutWarehouse ON OutWarehouse.warehouseId = outStock.outWarehouseId ");

        sbSql.append(" LEFT JOIN stock_business_apply   ON stock_business_apply.applyId = inStock.applyId ");

        sbSql.append(" WHERE inStock.inStockId = #{inStockId} ");
        return sbSql.toString();
    }

    /**
     * Get stock in detail by in stock id string.
     *
     * @param inStockId the in stock id
     * @return the string
     */
    public String getStockInDetailByInStockId(Long inStockId)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT ");
        sbSql.append("     detail.inStockDetailId, ");
        sbSql.append("     inStockId, ");
        sbSql.append("     detail.sparePartId, ");
        sbSql.append("     CASE WHEN detail.inventoryType IS NULL ");
        sbSql.append("       THEN stock.inventoryType ");
        sbSql.append("     ELSE detail.inventoryType END inventoryType, ");
        sbSql.append("     inStockAcount, ");
        sbSql.append("     alreadyInStockAcount, ");
        sbSql.append("     detail.price, ");
        sbSql.append("     detail.remark, ");
        sbSql.append("     detail.goodsShelvesId, ");
        sbSql.append("     stock.status, ");
        sbSql.append("     detail.stockId, ");
        sbSql.append("     partName, ");
        sbSql.append("     type.categoryName, ");
        sbSql.append("     part.materiaCoding, ");
        sbSql.append("     part.lowerLimit, ");
        sbSql.append("     part.upperLimit, ");
        sbSql.append("     part.brand, ");
        sbSql.append("     CASE WHEN detail.supplierId IS NULL ");
        sbSql.append("       THEN stock.supplierId ");
        sbSql.append("     ELSE detail.supplierId END    supplierId, ");
        sbSql.append("     supplierName, ");
        sbSql.append("     equipmentNO, ");
        sbSql.append("     serialNumber, ");
        sbSql.append("     qrCode, ");
        sbSql.append("     stock.stockId, ");
        sbSql.append("     shelf.shelfNumber, ");
        sbSql.append("     shelf.houseNo, ");
        sbSql.append("     part.sparePartTypeId, ");
        sbSql.append("     supplierName ");
        sbSql.append("   FROM ");
        sbSql.append("     stock_in_stock_detail detail ");
        sbSql.append("     INNER JOIN part_spare_part part ");
        sbSql.append("       ON part.sparePartId = detail.sparePartId ");
        sbSql.append("     INNER JOIN part_spare_part_type type ");
        sbSql.append("       ON type.sparePartTypeId = part.sparePartTypeId ");
        sbSql.append("     LEFT JOIN stock_stock stock ");
        sbSql.append("       ON stock.stockId = detail.stockId ");
        sbSql.append("     LEFT JOIN stock_supplier supplier ");
        sbSql.append("       ON supplier.supplierId = stock.supplierId ");
        sbSql.append("     LEFT JOIN stock_goods_shelves shelf ");
        sbSql.append("       ON shelf.goodsShelvesId = stock.goodsShelvesId ");
        sbSql.append("   WHERE detail.inStockId = #{inStockId} ");

        return sbSql.toString();

    }


    public String getStockInDetailByInStockIdAlreadyIn(Long inStockId)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT ");
        sbSql.append("   detail.inStockDetailId, ");
        sbSql.append("   inStockId, ");
        sbSql.append("   detail.sparePartId, ");
        sbSql.append("   CASE WHEN detail.inventoryType IS NULL ");
        sbSql.append("     THEN stock.inventoryType ");
        sbSql.append("   ELSE detail.inventoryType END inventoryType, ");
        sbSql.append("   inStockAcount, ");
        sbSql.append("   alreadyInStockAcount, ");
        sbSql.append("   detail.price, ");
        sbSql.append("   detail.remark, ");
        sbSql.append("   detail.goodsShelvesId, ");
        sbSql.append("   stock.status, ");
        sbSql.append("   detail.stockId, ");
        sbSql.append("   partName, ");
        sbSql.append("   type.categoryName, ");
        sbSql.append("   part.materiaCoding, ");
        sbSql.append("   part.lowerLimit, ");
        sbSql.append("   part.upperLimit, ");
        sbSql.append("   part.brand, ");
        sbSql.append("   CASE WHEN detail.supplierId IS NULL ");
        sbSql.append("     THEN stock.supplierId ");
        sbSql.append("   ELSE detail.supplierId END    supplierId, ");
        sbSql.append("   supplierName, ");
        sbSql.append("   equipmentNO, ");
        sbSql.append("   serialNumber, ");
        sbSql.append("   stock.qrCode, ");
        sbSql.append("   stock.stockId, ");
        sbSql.append("   shelf.shelfNumber, ");
        sbSql.append("   shelf.houseNo, ");
        sbSql.append("   part.sparePartTypeId, ");
        sbSql.append("   supplierName ");
        sbSql.append(" FROM ");
        sbSql.append("   stock_in_stock_detail detail ");
        sbSql.append("   INNER JOIN stock_and_stock ");
        sbSql.append("     ON stock_and_stock.inStockDetailId = detail.inStockDetailId ");
        sbSql.append("   INNER JOIN stock_stock stock ");
        sbSql.append("     ON stock.stockId = detail.stockId ");
        sbSql.append("   INNER JOIN part_spare_part part ");
        sbSql.append("     ON part.sparePartId = detail.sparePartId ");
        sbSql.append("   INNER JOIN part_spare_part_type type ");
        sbSql.append("     ON type.sparePartTypeId = part.sparePartTypeId ");
        sbSql.append("   LEFT JOIN stock_supplier supplier ");
        sbSql.append("     ON supplier.supplierId = stock.supplierId ");
        sbSql.append("   LEFT JOIN stock_goods_shelves shelf ");
        sbSql.append("     ON shelf.goodsShelvesId = stock.goodsShelvesId ");
        sbSql.append("   WHERE detail.inStockId = #{inStockId} ");

        return sbSql.toString();
    }

    public String getStockInDetailByInStocSparePartkId(Long inStockId)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT ");
        sbSql.append("   stock_stock.inventoryType, ");
        sbSql.append("   inStockAcount AS count, ");
        sbSql.append("   detail.goodsShelvesId, ");
        sbSql.append("   partName, ");
        sbSql.append("   type.categoryName, ");
        sbSql.append("   part.materiaCoding, ");
        sbSql.append("   part.brand, ");
        sbSql.append("   supplierName, ");
        sbSql.append("   shelf.shelfNumber, ");
        sbSql.append("   shelf.houseNo, ");
        sbSql.append("   stock_stock.price, ");
        sbSql.append("   qrCode, ");
        sbSql.append("   stock_stock.status ");
        sbSql.append(" FROM ");
        sbSql.append("   stock_in_stock_detail detail ");
        sbSql.append("   INNER JOIN stock_stock ");
        sbSql.append("     ON stock_stock.stockId = detail.stockId ");
        sbSql.append("   INNER JOIN part_spare_part part ");
        sbSql.append("     ON part.sparePartId = stock_stock.sparePartId ");
        sbSql.append("   INNER JOIN part_spare_part_type type ");
        sbSql.append("     ON type.sparePartTypeId = part.sparePartTypeId ");
        sbSql.append("   LEFT JOIN stock_supplier supplier ");
        sbSql.append("     ON supplier.supplierId = stock_stock.supplierId ");
        sbSql.append("   LEFT JOIN stock_goods_shelves shelf ");
        sbSql.append("     ON shelf.goodsShelvesId = stock_stock.goodsShelvesId ");
        sbSql.append(" WHERE detail.inStockId = #{inStockId} ");

        return sbSql.toString();

    }

    /**
     * Get stock in detail by in stock id string.
     *
     * @param inStockId the in stock id
     * @return the string
     */
    public String getNewStockInDetailByInStockId(Long inStockId)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT ");
        sbSql.append("     detail.inventoryType, ");
        sbSql.append("     sum(inStockAcount)        AS inStockAcount, ");
        sbSql.append("     sum(alreadyInStockAcount) AS count, ");
        sbSql.append("     detail.goodsShelvesId, ");
        sbSql.append("     partName, ");
        sbSql.append("     type.categoryName, ");
        sbSql.append("     part.materiaCoding, ");
        sbSql.append("     part.brand, ");
        sbSql.append("     supplierName, ");
        sbSql.append("     shelf.shelfNumber, ");
        sbSql.append("     shelf.houseNo,price,detail.status ");
        sbSql.append("   FROM ");
        sbSql.append("     stock_in_stock_detail detail ");
        sbSql.append("     INNER JOIN part_spare_part part ");
        sbSql.append("       ON part.sparePartId = detail.sparePartId ");
        sbSql.append("     INNER JOIN part_spare_part_type type ");
        sbSql.append("       ON type.sparePartTypeId = part.sparePartTypeId ");
        sbSql.append("     LEFT JOIN stock_supplier supplier ");
        sbSql.append("       ON supplier.supplierId = detail.supplierId ");
        sbSql.append("     LEFT JOIN stock_goods_shelves shelf ");
        sbSql.append("       ON shelf.goodsShelvesId = detail.goodsShelvesId ");
        sbSql.append("   WHERE detail.inStockId = #{inStockId} ");
        sbSql.append("   GROUP BY detail.inventoryType, detail.goodsShelvesId, partName, type.categoryName, part.materiaCoding, part.brand, ");
        sbSql.append("     supplierName, shelf.shelfNumber, shelf.houseNo ");

        return sbSql.toString();

    }


    public String getSplitDetailInfo(String ids)
    {

        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT ");
        sbSql.append("   stock.*, ");
        sbSql.append("   log.count, ");
        sbSql.append("   partName, ");
        sbSql.append("   type.categoryName, ");
        sbSql.append("   part.materiaCoding, ");
        sbSql.append("   part.lowerLimit, ");
        sbSql.append("   part.upperLimit, ");
        sbSql.append("   part.brand, ");
        sbSql.append("   supplierName, ");
        sbSql.append("   shelf.shelfNumber, ");
        sbSql.append("   shelf.houseNo ");
        sbSql.append(" FROM stock_flow_log log ");
        sbSql.append("   INNER JOIN stock_stock stock ");
        sbSql.append("     ON stock.stockId = log.stockId ");
        sbSql.append("   INNER JOIN part_spare_part part ");
        sbSql.append("     ON part.sparePartId = stock.sparePartId ");
        sbSql.append("   INNER JOIN part_spare_part_type type ");
        sbSql.append("     ON type.sparePartTypeId = part.sparePartTypeId ");
        sbSql.append("   LEFT JOIN stock_supplier supplier ");
        sbSql.append("     ON supplier.supplierId = stock.supplierId ");
        sbSql.append("   LEFT JOIN stock_goods_shelves shelf ");
        sbSql.append("     ON shelf.goodsShelvesId = stock.goodsShelvesId ");
        sbSql.append(" WHERE sourceId IN (" + ids + ") AND type IN ('newIn','returnIn','transferIn','useIn','borrowIn') ");
        return sbSql.toString();
    }

    /**
     * Get stock info string.
     * 根据货架Id，备件Id，设备状态，厂商Id、库存类型查询是否有库存信息
     *
     * @param stockInVO the stock in vo
     * @return the string
     */
    public String getStockInfo(StockInStockDetailVO stockInVO)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT * ");
        sbSql.append("  FROM stock_stock ");
        sbSql.append("  WHERE sparePartId = #{sparePartId} ");
        if (ObjectUtils.isNotEmpty(stockInVO.getSupplierId()))
        {
            sbSql.append("        AND ifnull(supplierId,0) = #{supplierId} ");
        }
        else
        {
            sbSql.append("        AND ifnull(supplierId,0) = 0 ");
        }

        sbSql.append("        AND status = #{status} ");
        if (ObjectUtils.isNotEmpty(stockInVO.getInWarehouseId()))
        {
            sbSql.append("        AND warehouseId = #{inWarehouseId} ");
        }
        if (ObjectUtils.isNotEmpty(stockInVO.getGoodsShelvesId()))
        {
            sbSql.append("        AND ifnull(goodsShelvesId,0) = #{goodsShelvesId} ");
        }
        else
        {
            sbSql.append("        AND ifnull(goodsShelvesId,0) = 0 ");
        }
        sbSql.append("        AND inventoryType = #{inventoryType} ");
        sbSql.append("LIMIT 1 ");
        return sbSql.toString();
    }


    /**
     * Insert stock info string.
     *
     * @param stock the stock
     * @return the string
     */
    public String insertStockInfo(StockStockEntity stock)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" INSERT INTO ");
        sbSql.append("   stock_stock ");
        sbSql.append("   ( ");
        sbSql.append("      operationSubjectId, ");
        sbSql.append("      supplierId, ");
        sbSql.append("      goodsShelvesId, ");
        sbSql.append("      inStockDetailId, ");
        sbSql.append("      sparePartId, ");
        sbSql.append("      inDate, ");
        sbSql.append("      equipmentNO, ");
        sbSql.append("      serialNumber, ");
        sbSql.append("      account, ");
        sbSql.append("      price, ");
        sbSql.append("      inventoryType, ");
        sbSql.append("      remark, ");
        sbSql.append("      status, ");
        sbSql.append("      createUser, ");
        sbSql.append("      modifyUser, ");
        sbSql.append("      qrCode, ");
        sbSql.append("      warehouseId ");
        sbSql.append("   ) ");
        sbSql.append(" VALUES ");
        sbSql.append("   ( ");
        sbSql.append("      #{operationSubjectId}, ");
        sbSql.append("      #{supplierId}, ");
        sbSql.append("      #{goodsShelvesId}, ");
        sbSql.append("      #{inStockDetailId}, ");
        sbSql.append("      #{sparePartId}, ");
        sbSql.append("      #{inDate}, ");
        sbSql.append("      #{equipmentNO}, ");
        sbSql.append("      #{serialNumber}, ");
        sbSql.append("      #{account}, ");
        sbSql.append("      #{price}, ");
        sbSql.append("      #{inventoryType}, ");
        sbSql.append("      #{remark}, ");
        sbSql.append("      #{status}, ");
        sbSql.append("      #{operationUserName}, ");
        sbSql.append("      #{operationUserName}, ");
        sbSql.append("      #{qrCode}, ");
        sbSql.append("      #{warehouseId} ");
        sbSql.append("   ) ");

        return sbSql.toString();
    }

    public String updateStockInfo(StockStockEntity stock)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" UPDATE ");
        sbSql.append("   stock_stock ");
        sbSql.append(" SET ");
        sbSql.append("   operationSubjectId = #{operationSubjectId}, ");
        sbSql.append("   supplierId = #{supplierId}, ");
        sbSql.append("   goodsShelvesId = #{goodsShelvesId}, ");
        sbSql.append("   sparePartId = #{sparePartId}, ");
        sbSql.append("   inDate = #{inDate}, ");
        sbSql.append("   equipmentNO = #{equipmentNO}, ");
        sbSql.append("   qrCode = #{qrCode}, ");
        sbSql.append("   serialNumber = #{serialNumber}, ");
        sbSql.append("   account = #{account}, ");
        sbSql.append("   price = #{price}, ");
        sbSql.append("   inventoryType = #{inventoryType}, ");
        sbSql.append("   remark = #{remark}, ");
        sbSql.append("   status = #{status}, ");
        sbSql.append("   modifyUser = #{operationUserName}, ");

        sbSql.append("   warehouseId = #{warehouseId} ");
        sbSql.append(" WHERE ");
        sbSql.append("   stockId = #{stockId} ");
        return sbSql.toString();
    }

    /**
     * Insert stock log string.
     *
     * @param logEntity the log entity
     * @return the string
     */
    public String insertStockLog(StockFlowLogEntity logEntity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" INSERT INTO ");
        sbSql.append("   stock_flow_log ");
        sbSql.append("   ( ");
        sbSql.append("      stockId, ");
        sbSql.append("      type, ");
        sbSql.append("      count, ");
        sbSql.append("      flowDescribe, ");
        sbSql.append("      createUser, ");
        sbSql.append("      modifyUser, ");
        sbSql.append("      warehouseId, ");
        sbSql.append("      sparePartId, ");
        sbSql.append("      sourceId ");
        sbSql.append("   ) ");
        sbSql.append(" VALUES ");
        sbSql.append("   ( ");
        sbSql.append("      #{stockId}, ");
        sbSql.append("      #{type}, ");
        sbSql.append("      #{count}, ");
        sbSql.append("      #{flowDescribe}, ");
        sbSql.append("      #{createUser}, ");
        sbSql.append("      #{modifyUser}, ");
        sbSql.append("      #{warehouseId}, ");
        sbSql.append("      #{sparePartId}, ");
        sbSql.append("      #{sourceId} ");
        sbSql.append("   ) ");
        return sbSql.toString();
    }

    /**
     * Update stock in status string.
     *
     * @param inStockId the in stock id
     * @return the string
     */
    public String updateStockInStatus(Long inStockId)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" UPDATE stock_in_stock ");
        sbSql.append(" SET inStockStatus = '" + InStockStatusEnum.ALREADIN.getValue() + "' ");
        sbSql.append(" WHERE inStockId = #{inStockId} ");

        return sbSql.toString();
    }


    public String updateStockInEntity(StockInStockEntity stockInStockEntity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" UPDATE stock_in_stock ");
        sbSql.append(" SET remark = #{remark}  ");
        sbSql.append("  ,inStockUser = #{operationUserName}  ");
        sbSql.append("  ,inStockUserId = #{operationUserId}  ");
        if (StringUtils.isNotEmpty(stockInStockEntity.getInDate()))
        {
            sbSql.append("  ,inDate = #{inDate}  ");
        }
        sbSql.append(" WHERE inStockId = #{inStockId} ");

        return sbSql.toString();
    }

    public String insertInStockApply(StockInVO stockInVO)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" INSERT INTO ");
        sbSql.append("   stock_in_stock ");
        sbSql.append("   ( ");
        sbSql.append("      operationSubjectId, ");
        sbSql.append("      inWarehouseId, ");
        sbSql.append("      inStockApplyNo, ");
        sbSql.append("      inStockUser, ");
        sbSql.append("      inStockUserId, ");
        sbSql.append("      inDate, ");
        sbSql.append("      remark, ");
        sbSql.append("      inStockType, ");
        sbSql.append("      inStockStatus, ");
        sbSql.append("      createUser, ");
        sbSql.append("      modifyUser ");
        sbSql.append("   ) ");
        sbSql.append(" VALUES ");
        sbSql.append("   ( ");
        sbSql.append("      #{operationSubjectId}, ");
        sbSql.append("      #{inWarehouseId}, ");
        sbSql.append("      #{inStockApplyNo}, ");
        sbSql.append("      #{operationUserName}, ");
        sbSql.append("      #{operationUserId}, ");
        sbSql.append("      #{inDate}, ");
        sbSql.append("      #{remark}, ");
        sbSql.append("      #{inStockType}, ");
        sbSql.append("      #{inStockStatus}, ");
        sbSql.append("      #{operationUserName}, ");
        sbSql.append("      #{operationUserName} ");
        sbSql.append("   ) ");
        return sbSql.toString();
    }

    public String insertInStockApplyDetail(StockInStockDetailVO detailVO)
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
        sbSql.append("      status, ");
        sbSql.append("      createUser, ");
        sbSql.append("      modifyUser, ");
        sbSql.append("      stockId, ");
        sbSql.append("      supplierId ");
        sbSql.append("   ) ");
        sbSql.append(" VALUES ");
        sbSql.append("   ( ");
        sbSql.append("      #{inStockId}, ");
        sbSql.append("      #{sparePartId}, ");
        sbSql.append("      #{inventoryType}, ");
        sbSql.append("      #{inStockAcount}, ");
        sbSql.append("      #{inStockAcount}, ");
        sbSql.append("      #{price}, ");
        sbSql.append("      #{remark}, ");
        sbSql.append("      #{goodsShelvesId}, ");
        sbSql.append("      #{status}, ");
        sbSql.append("      #{createUser}, ");
        sbSql.append("      #{modifyUser}, ");
        sbSql.append("      #{stockId}, ");
        sbSql.append("      #{supplierId} ");
        sbSql.append("   ) ");
        return sbSql.toString();
    }

    public String getQrCodeInStockId(StockInStockDetailVO entity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT count(1) ");
        sbSql.append(" FROM stock_stock ");
        sbSql.append(" WHERE qrCode = #{qrCode} ");
        sbSql.append("       AND account > 0 ");

        return sbSql.toString();
    }

    public String getQrCodeOnOperationsEquipment(StockInStockDetailVO entity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT count(*) ");
        sbSql.append("  FROM operations_equipment ");
        sbSql.append("  WHERE qrCode = #{qrCode} ");

        return sbSql.toString();

    }

    public String insertStockAndStock(StockAndStockEntity entity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" INSERT INTO ");
        sbSql.append("   stock_and_stock ");
        sbSql.append("   ( ");
        sbSql.append("      inStockDetailId, ");
        sbSql.append("      stockId ");
        sbSql.append("   ) ");
        sbSql.append(" VALUES ");
        sbSql.append("   ( ");
        sbSql.append("      #{inStockDetailId}, ");
        sbSql.append("      #{stockId} ");
        sbSql.append("   ) ");

        return sbSql.toString();
    }


    public String getSpartTypeList(StockInStockDetailVO findentity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT DISTINCT sparePartTypeId  FROM part_spare_part  ");

        sbSql.append("  WHERE  sparePartId IN ("+StringUtils.listToString(findentity.getSparePartIds())+")   ");
        return sbSql.toString();
    }
}
