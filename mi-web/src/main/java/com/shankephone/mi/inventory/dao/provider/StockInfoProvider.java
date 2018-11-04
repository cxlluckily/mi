package com.shankephone.mi.inventory.dao.provider;

import com.shankephone.mi.inventory.formbean.StockInfoFindEntity;
import com.shankephone.mi.model.StockStockEntity;
import com.shankephone.mi.util.ObjectUtils;
import com.shankephone.mi.util.StringUtils;

/**
 * 库存管理Provider
 *
 * @author 司徒彬
 * @date 2018 /7/26 20:05
 */
public class StockInfoProvider {
    /**
     * Gets pager info.
     *
     * @param findEntity the find entity
     * @return the pager info
     */
    public String getPagerInfo(StockInfoFindEntity findEntity) {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT ");
        sbSql.append("   sum(account) account, ");
        sbSql.append("   partName, ");
        sbSql.append("   part.sparePartId, ");
        sbSql.append("   warehouseName, ");
        sbSql.append("   stock.warehouseId , ");
        sbSql.append("   inventoryType, ");
        sbSql.append("   (SELECT imageUrl  FROM part_spare_part_image  WHERE sparePartId = stock.sparePartId LIMIT 1)  AS imageUrl, ");
        sbSql.append("   stock.status,categoryName ");
        sbSql.append(" FROM stock_stock stock ");
        sbSql.append("   INNER JOIN part_spare_part part ON part.sparePartId = stock.sparePartId ");
        sbSql.append("   INNER JOIN part_spare_part_type type ON part.sparePartTypeId = type.sparePartTypeId ");
        sbSql.append("   INNER JOIN stock_warehouse warehouse ON warehouse.warehouseId = stock.warehouseId ");
        sbSql.append(" WHERE 1 = 1 ");
        sbSql.append("       AND stock.warehouseId IN (" + findEntity.getWarehouseId() + ") ");
        sbSql.append("       AND stock.account > 0 ");
        if (ObjectUtils.isNotEmpty(findEntity.getPartName())) {
            sbSql.append("       AND partName LIKE concat('%', ifnull(#{partName}, ''), '%') ");
        }
        if(!"all".equals(findEntity.getStatus()))
        {
            sbSql.append("       AND stock.status = #{status} ");
        }
        if(!"all".equals(findEntity.getInventoryType()))
        {
            sbSql.append("       AND inventoryType = #{inventoryType} ");
        }
        if(StringUtils.isNotEmpty(findEntity.getSparePartTypeId()))
        {
            sbSql.append("       AND type.sparePartTypeId = #{sparePartTypeId} ");
        }
        sbSql.append(" GROUP BY stock.warehouseId, part.sparePartId, inventoryType, stock.status ");
        sbSql.append("   LIMIT #{start}, #{limit} ");

        return sbSql.toString();
    }

    /**
     * Gets pager info total.
     *
     * @param findEntity the find entity
     * @return the pager info total
     */
    public String getPagerInfoTotal(StockInfoFindEntity findEntity) {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT count(1) ");
        sbSql.append(" FROM (SELECT stockId ");
        sbSql.append("       FROM stock_stock stock ");
        sbSql.append("              INNER JOIN part_spare_part part ON part.sparePartId = stock.sparePartId ");
        sbSql.append("              INNER JOIN stock_warehouse warehouse ON warehouse.warehouseId = stock.warehouseId ");
        sbSql.append("       WHERE 1 = 1 ");
        sbSql.append("       AND stock.warehouseId IN (" + findEntity.getWarehouseId() + ") ");
        if (ObjectUtils.isNotEmpty(findEntity.getPartName())) {
            sbSql.append("       AND partName LIKE concat('%', ifnull(#{partName}, ''), '%') ");
        }
        if(!"all".equals(findEntity.getStatus()))
        {
            sbSql.append("       AND stock.status = #{status} ");
        }
        if(!"all".equals(findEntity.getInventoryType()))
        {
            sbSql.append("       AND inventoryType = #{inventoryType} ");
        }
        sbSql.append("       AND stock.account > 0 ");
        sbSql.append("       GROUP BY stock.warehouseId, part.sparePartId, inventoryType, stock.status) temp; ");

        return sbSql.toString();
    }

    /**
     * Get pager detail info string.
     *
     * @param findEntity the find entity
     * @return the string
     */
    public String getPagerDetailInfo(StockInfoFindEntity findEntity) {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT stock.*, ");
        sbSql.append("   supplierName, ");
        sbSql.append("   partName, ");
        sbSql.append("   warehouseName, ");
        sbSql.append("   houseNo, ");
        sbSql.append("   shelfNumber,materiaCoding ");
        sbSql.append(" FROM ");
        sbSql.append("   stock_stock stock ");
        sbSql.append("     INNER JOIN part_spare_part part ");
        sbSql.append("       ON part.sparePartId = stock.sparePartId ");
        sbSql.append("     INNER JOIN stock_warehouse warehouse ");
        sbSql.append("       ON warehouse.warehouseId = stock.warehouseId ");
        sbSql.append("     LEFT JOIN stock_supplier supplier ");
        sbSql.append("       ON stock.supplierId = supplier.supplierId ");
        sbSql.append("     LEFT JOIN stock_goods_shelves shelf ");
        sbSql.append("       ON shelf.goodsShelvesId = stock.goodsShelvesId ");
//        sbSql.append(" WHERE 1 = 1 AND stock.warehouseId = #{warehouseId} AND partName LIKE concat('%', ifnull(#{partName}, ''), '%') ");
//        sbSql.append("   AND stock.account <> 0 ");
//        sbSql.append("   AND part.sparePartId = #{sparePartId} ");
//        sbSql.append("   AND ifnull(houseNo,'') LIKE concat('%', ifnull(#{houseNo}, ''), '%') ");
//        sbSql.append("   AND ifnull(supplierName,'') LIKE concat('%', ifnull(#{supplierName}, ''), '%') ");
        sbSql.append(" WHERE 1 = 1 ");
        if(StringUtils.isNotEmpty(findEntity.getWarehouseId()))
        {
            sbSql.append("       AND stock.warehouseId = #{warehouseId} ");
        }
        if(StringUtils.isNotEmpty(findEntity.getPartName()))
        {
            sbSql.append("       AND partName LIKE concat('%', ifnull(#{partName}, ''), '%') ");
        }
        sbSql.append("   AND stock.account <> 0 ");

        sbSql.append("       AND stock.status = #{status} ");
        if(StringUtils.isNotEmpty(findEntity.getInventoryType()))
        {
            sbSql.append("       AND inventoryType = #{inventoryType} ");

        }
        if(StringUtils.isNotEmpty(findEntity.getSparePartId()))
        {
            sbSql.append("       AND part.sparePartId = #{sparePartId} ");
        }
        if(StringUtils.isNotEmpty(findEntity.getHouseNo()))
        {
            sbSql.append("   AND ifnull(houseNo,'') LIKE concat('%', ifnull(#{houseNo}, ''), '%') ");
        }
        if(StringUtils.isNotEmpty(findEntity.getHouseNo()))
        {
            sbSql.append("   AND ifnull(supplierName,'') LIKE concat('%', ifnull(#{supplierName}, ''), '%') ");
        }
        sbSql.append("   LIMIT #{start}, #{limit} ");

        return sbSql.toString();
    }

    /**
     * Get pager detail info total string.
     *
     * @param findEntity the find entity
     * @return the string
     */
    public String getPagerDetailInfoTotal(StockInfoFindEntity findEntity) {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT ");
        sbSql.append("    count(1) ");
        sbSql.append(" FROM stock_stock stock ");
        sbSql.append("     INNER JOIN part_spare_part part ");
        sbSql.append("       ON part.sparePartId = stock.sparePartId ");
        sbSql.append("     INNER JOIN stock_warehouse warehouse ");
        sbSql.append("       ON warehouse.warehouseId = stock.warehouseId ");
        sbSql.append("     LEFT JOIN stock_supplier supplier ");
        sbSql.append("       ON stock.supplierId = supplier.supplierId ");
        sbSql.append("     LEFT JOIN stock_goods_shelves shelf ");
        sbSql.append("       ON shelf.goodsShelvesId = stock.goodsShelvesId ");
        sbSql.append(" WHERE 1 = 1 ");
        if(StringUtils.isNotEmpty(findEntity.getWarehouseId()))
        {
            sbSql.append("       AND stock.warehouseId = #{warehouseId} ");
        }
        if(StringUtils.isNotEmpty(findEntity.getPartName()))
        {
            sbSql.append("       AND partName LIKE concat('%', ifnull(#{partName}, ''), '%') ");
        }
        sbSql.append("   AND stock.account <> 0 ");

        sbSql.append("       AND stock.status = #{status} ");
        if(StringUtils.isNotEmpty(findEntity.getInventoryType()))
        {
            sbSql.append("       AND inventoryType = #{inventoryType} ");

        }
        if(StringUtils.isNotEmpty(findEntity.getSparePartId()))
        {
            sbSql.append("       AND part.sparePartId = #{sparePartId} ");
        }
        if(StringUtils.isNotEmpty(findEntity.getHouseNo()))
        {
            sbSql.append("   AND ifnull(houseNo,'') LIKE concat('%', ifnull(#{houseNo}, ''), '%') ");
        }
        if(StringUtils.isNotEmpty(findEntity.getHouseNo()))
        {
            sbSql.append("   AND ifnull(supplierName,'') LIKE concat('%', ifnull(#{supplierName}, ''), '%') ");
        }
        return sbSql.toString();
    }

    /**
     * Get stock info by id string.
     *
     * @param stockId the stock id
     * @return the string
     */
    public String getStockInfoById(Long stockId) {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT * ");
        sbSql.append(" FROM ");
        sbSql.append("   stock_stock ");
        sbSql.append(" WHERE stockId = #{stockId} ");

        return sbSql.toString();
    }

    public String insertStockInfo(StockStockEntity stockEntity) {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" INSERT INTO ");
        sbSql.append("   stock_stock ");
        sbSql.append("   ( ");
        sbSql.append("      operationSubjectId, ");
        sbSql.append("      supplierId, ");
        sbSql.append("      goodsShelvesId, ");
        sbSql.append("      sparePartId, ");
        sbSql.append("      inDate, ");
        sbSql.append("      equipmentNO, ");
        sbSql.append("      qrCode, ");
        sbSql.append("      serialNumber, ");
        sbSql.append("      account, ");
        sbSql.append("      price, ");
        sbSql.append("      inventoryType, ");
        sbSql.append("      remark, ");
        sbSql.append("      status, ");
        sbSql.append("      createUser, ");
        sbSql.append("      modifyUser, ");
        sbSql.append("      spareField1, ");
        sbSql.append("      spareField2, ");
        sbSql.append("      spareField3, ");
        sbSql.append("      spareField4, ");
        sbSql.append("      spareField5, ");
        sbSql.append("      spareField6, ");
        sbSql.append("      spareField7, ");
        sbSql.append("      spareField8, ");
        sbSql.append("      spareField9, ");
        sbSql.append("      spareField10, ");
        sbSql.append("      warehouseId ");
        sbSql.append("   ) ");
        sbSql.append(" VALUES ");
        sbSql.append("   ( ");
        sbSql.append("      #{operationSubjectId}, ");
        sbSql.append("      #{supplierId}, ");
        sbSql.append("      #{goodsShelvesId}, ");
        sbSql.append("      #{sparePartId}, ");
        sbSql.append("      #{inDate}, ");
        sbSql.append("      #{equipmentNO}, ");
        sbSql.append("      #{qrCode}, ");
        sbSql.append("      #{serialNumber}, ");
        sbSql.append("      #{account}, ");
        sbSql.append("      #{price}, ");
        sbSql.append("      #{inventoryType}, ");
        sbSql.append("      #{remark}, ");
        sbSql.append("      #{status}, ");
        sbSql.append("      #{createUser}, ");
        sbSql.append("      #{modifyUser}, ");
        sbSql.append("      #{spareField1}, ");
        sbSql.append("      #{spareField2}, ");
        sbSql.append("      #{spareField3}, ");
        sbSql.append("      #{spareField4}, ");
        sbSql.append("      #{spareField5}, ");
        sbSql.append("      #{spareField6}, ");
        sbSql.append("      #{spareField7}, ");
        sbSql.append("      #{spareField8}, ");
        sbSql.append("      #{spareField9}, ");
        sbSql.append("      #{spareField10}, ");
        sbSql.append("      #{warehouseId} ");
        sbSql.append("   ) ");
        return sbSql.toString();
    }

    /**
     * Update stock info string.
     * <p>
     * 供应商、设备编号、设备序列号、存放位置、设备状态、数量
     *
     * @param stockEntity the stock entity
     * @return the string
     */
    public String updateStockInfo(StockStockEntity stockEntity) {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" UPDATE stock_stock ");
        sbSql.append(" SET ");
        sbSql.append("   supplierId = #{supplierId}, ");
        sbSql.append("   equipmentNO = #{equipmentNO}, ");
        sbSql.append("   serialNumber = #{serialNumber}, ");
        sbSql.append("   goodsShelvesId = #{goodsShelvesId}, ");
        sbSql.append("   status = #{status}, ");
        sbSql.append("   account = #{account} ");
        sbSql.append(" WHERE stockId = #{stockId} ");

        return sbSql.toString();
    }

    /**
     * Gets stock info by info.
     *
     * @param stockEntity the stock entity
     * @return the stock info by info
     */
    public String getStockInfoByInfo(StockStockEntity stockEntity) {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT * ");
        sbSql.append(" FROM stock_stock ");
        sbSql.append(" WHERE sparePartId = #{sparePartId} ");
        sbSql.append("   AND status = #{status} ");
        if (ObjectUtils.isNotNull(stockEntity.getSupplierId())) {
            sbSql.append("   AND supplierId = #{supplierId} ");
        } else {
            sbSql.append("   AND supplierId IS NULL ");
        }

        sbSql.append("   AND inventoryType = #{inventoryType} ");

        if (StringUtils.isNotEmpty(stockEntity.getWarehouseId())) {
            sbSql.append("   AND warehouseId = #{warehouseId} ");
        }

        if (ObjectUtils.isNotNull(stockEntity.getGoodsShelvesId())) {
            sbSql.append("   AND goodsShelvesId = #{goodsShelvesId} ");
        } else {
            sbSql.append("   AND goodsShelvesId IS NULL ");
        }
        sbSql.append(" Limit 1 ");
        return sbSql.toString();

    }

    /**
     * Gets equipment images.
     *
     * @param sparePartId the equipment id
     * @return the equipment images
     */
//    public String getSparePartImages(Long sparePartId) {
//        StringBuilder sbSql = new StringBuilder();
//        sbSql.append(" SELECT * ");
//        sbSql.append(" FROM part_spare_part_image image WHERE  image.sparePartId= #{sparePartId}");
//        return sbSql.toString();
//    }
    /**
     * Gets equipment images.
     *
     * @param findEntity the equipment id

     * @return the equipment images
     */
    public String getSparePartEntity(StockInfoFindEntity findEntity) {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT part_spare_part.*, ");
        sbSql.append(" ifnull(stock_stock.serialNumber,'') as serialNumber,");
        sbSql.append(" ifnull(stock_stock.qrCode,'') as qrCode, ");
        sbSql.append(" ifnull(stock_stock.equipmentNO,'') as equipmentNO, ");
        sbSql.append(" ifnull(stock_stock.inventoryType,'') as inventoryType, ");
        sbSql.append(" ifnull(stock_stock.status,'') as  kcStatus, ");
        sbSql.append(" ifnull(stock_warehouse.warehouseName,'') as warehouseName, ");
        sbSql.append(" part_spare_part_type.categoryName ");
        sbSql.append(" FROM  stock_user_device   ");
        sbSql.append(" INNER JOIN part_spare_part ON  stock_user_device.sparePartId=part_spare_part.sparePartId ");
        sbSql.append(" INNER JOIN part_spare_part_type ON  part_spare_part_type.sparePartTypeId=part_spare_part.sparePartTypeId ");
        sbSql.append(" LEFT JOIN stock_stock ON (");
       // if(StringUtils.isNotEmpty(findEntity.getStockId()))//我持有的备件有可能没有库存
      //  {
            sbSql.append("  stock_user_device.stockId=stock_stock.stockId ");
      //  }
//        else
//        {
//            sbSql.append("    part_spare_part.qrCode = stock_stock.qrCode ");
//        }
        sbSql.append(")  ");
        sbSql.append(" LEFT JOIN stock_warehouse ON stock_stock.warehouseId= stock_warehouse.warehouseId ");
        sbSql.append(" WHERE 1=1 ");
        if(StringUtils.isNotEmpty(findEntity.getUserDeviceId()))
        {
            sbSql.append(" AND  stock_user_device.userDeviceId=#{userDeviceId} ");
        }
        if(StringUtils.isNotEmpty(findEntity.getSparePartId()))
        {
            sbSql.append(" AND  stock_user_device.sparePartId=#{sparePartId} ");
        }
        sbSql.append(" LIMIT 1  ");
        return sbSql.toString();
    }

    public String getStocKSparePartEntity(StockInfoFindEntity findEntity) {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT part_spare_part.*, ");
        sbSql.append(" ifnull(stock_stock.serialNumber,'') as serialNumber,");
        sbSql.append(" ifnull(stock_stock.qrCode,'') as qrCode, ");
        sbSql.append(" ifnull(stock_stock.equipmentNO,'') as equipmentNO, ");
        sbSql.append(" ifnull(stock_stock.inventoryType,'') as inventoryType, ");
        sbSql.append(" ifnull(stock_stock.status,'') as  kcStatus, ");
        sbSql.append(" ifnull(stock_warehouse.warehouseName,'') as warehouseName, ");
        sbSql.append(" part_spare_part_type.categoryName ");
        sbSql.append(" FROM   part_spare_part  ");
        sbSql.append(" INNER JOIN part_spare_part_type ON  part_spare_part_type.sparePartTypeId=part_spare_part.sparePartTypeId ");
        sbSql.append(" LEFT JOIN stock_stock ON (  part_spare_part.sparePartId=stock_stock.sparePartId  )  ");
        sbSql.append(" LEFT JOIN stock_warehouse ON stock_stock.warehouseId= stock_warehouse.warehouseId ");
        sbSql.append(" WHERE 1=1 ");

        if(StringUtils.isNotEmpty(findEntity.getSparePartId()))
        {
            sbSql.append(" AND  part_spare_part.sparePartId=#{sparePartId} ");
        }
        if(StringUtils.isNotEmpty(findEntity.getWarehouseId()))
        {
            sbSql.append(" AND stock_stock.warehouseId=#{warehouseId} ");
        }
        sbSql.append(" LIMIT 1  ");
        return sbSql.toString();
    }

    public String getWarhouseAndshelves(Long operationSubjectId)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append("  select     ");
        sbSql.append("    warehouse.warehouseId,    ");
        sbSql.append("    warehouse.warehouseName,    ");
        sbSql.append("    shelf.goodsShelvesId,    ");
        sbSql.append("    shelf.houseNo,    ");
        sbSql.append("    shelf.shelfNumber    ");
        sbSql.append("   from  stock_warehouse warehouse ");
        sbSql.append("     LEFT JOIN stock_goods_shelves shelf ");
        sbSql.append("       ON shelf.warehouseId = warehouse.warehouseId ");
        sbSql.append("    where warehouse.operationSubjectId =#{operationSubjectId}");


        return sbSql.toString();
    }
}
