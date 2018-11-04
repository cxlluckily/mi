package com.shankephone.mi.inventory.dao.provider;

import com.shankephone.mi.common.enumeration.StatusEnum;
import com.shankephone.mi.common.enumeration.StockStatusEnum;
import com.shankephone.mi.inventory.formbean.BaseDataFindEntity;
import com.shankephone.mi.util.StringUtils;

/**
 * ${description}
 *
 * @author 司徒彬
 * @date 2018 /7/11 15:00
 */
public class BaseDataProvider
{

    /**
     * Gets spare part list.
     *
     * @param findEntity the find entity
     * @return the spare part list
     */
    public String getSparePartList(BaseDataFindEntity findEntity)
    {
        StringBuilder sbSql = new StringBuilder();
        if (findEntity.getOutWarehouseId() == null)
        {
            sbSql.append(" SELECT ");
            sbSql.append("   part.*, ");
            sbSql.append("   sparePartId, ");
            sbSql.append("   partName, ");
            sbSql.append("   type.categoryName, ");
            sbSql.append("   ifnull((SELECT imageUrl ");
            sbSql.append("         FROM part_spare_part_image image ");
            sbSql.append("         WHERE part.sparePartId = image.sparePartId ");
            sbSql.append("         LIMIT 1), 'noImage') AS imageUrl ");
            sbSql.append(" FROM part_spare_part part ");
            sbSql.append("   INNER JOIN part_spare_part_type type ON type.sparePartTypeId = part.sparePartTypeId ");
            sbSql.append(" WHERE operationSubjectId = #{operationSubjectId} ");
            sbSql.append("       AND ( ");
            sbSql.append("         partName LIKE concat('%', ifnull(#{searchContent},''), '%') ");
            sbSql.append("         OR specificationModel LIKE concat('%', ifnull(#{searchContent},''), '%') ");
            sbSql.append("         OR partPinYin LIKE concat('%', ifnull(#{searchContent},''), '%') ");
            sbSql.append("       ) ");
            if(StringUtils.isNotEmpty(findEntity.getCategoryName()))
            {
                sbSql.append("       AND type.categoryName LIKE concat('%', #{categoryName}, '%') ");
            }
            if(StringUtils.isNotEmpty(findEntity.getSparePartTypeId()))
            {
                sbSql.append("    AND part.sparePartTypeId = #{sparePartTypeId} ");
            }
           // sbSql.append("       AND part.status = '" + StatusEnum.START_USING.getValue() + "'");
            if(StringUtils.isEmpty(findEntity.getStatus()))
            {
                sbSql.append("       AND part.status = '" + StatusEnum.START_USING.getValue() + "'");
            }
            else if(!"all".equalsIgnoreCase(findEntity.getStatus()))
            {
                sbSql.append("      AND part.status = #{status} ");
            }
        }
        else
        {
            //手机端
            sbSql.append(" SELECT DISTINCT ");
            sbSql.append("   part.*, ");
            sbSql.append("   type.categoryName, ");
            sbSql.append("   stock_stock.status as stockStatus, ");
            sbSql.append("   ifnull((SELECT imageUrl ");
            sbSql.append("         FROM part_spare_part_image image ");
            sbSql.append("         WHERE part.sparePartId = image.sparePartId ");
            sbSql.append("         LIMIT 1), 'noImage') AS imageUrl ");
            sbSql.append(" FROM part_spare_part part ");
            sbSql.append("   INNER JOIN part_spare_part_type type ON type.sparePartTypeId = part.sparePartTypeId ");
            sbSql.append("   INNER JOIN stock_stock ON stock_stock.sparePartId = part.sparePartId");
            sbSql.append(" WHERE type.operationSubjectId = #{operationSubjectId} ");
            sbSql.append("       AND ( ");
            sbSql.append("         partName LIKE concat('%', ifnull(#{searchContent},''), '%') ");
            sbSql.append("         OR partPinYin LIKE concat('%', ifnull(#{searchContent},''), '%') ");
            sbSql.append("         OR specificationModel LIKE concat('%', ifnull(#{searchContent},''), '%') ");
            sbSql.append("       ) ");
            if(StringUtils.isNotEmpty(findEntity.getCategoryName()))
            {
                sbSql.append("       AND type.categoryName LIKE concat('%', #{categoryName}, '%') ");
            }

            if(StringUtils.isNotEmpty(findEntity.getSparePartTypeId()))
            {
                sbSql.append("    AND part.sparePartTypeId = #{sparePartTypeId} ");
            }

            sbSql.append("       AND stock_stock.warehouseId = #{outWarehouseId}");
            sbSql.append("       AND stock_stock.account > 0");
           // sbSql.append("       AND stock_stock.status = '" + StockStatusEnum.NORMAL.getCode() + "'");

            if(StringUtils.isEmpty(findEntity.getStatus()))
            {
                sbSql.append("       AND stock_stock.status = '" + StockStatusEnum.NORMAL.getCode() + "'");
            }
            else if(!"all".equalsIgnoreCase(findEntity.getStatus()))
            {
                sbSql.append("       AND stock_stock.status = #{status} ");
            }

            return sbSql.toString();
        }
        return sbSql.toString();
    }


    /**
     * Get spare part in warehouse string.
     *
     * @param findEntity the find entity
     * @return the string
     */
    public String getSparePartInWarehouse(BaseDataFindEntity findEntity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT ");
        sbSql.append("   part.*, ");
        sbSql.append("   partName, ");
        sbSql.append("   stock.account, ");
        sbSql.append("   type.categoryName, ");
        sbSql.append("   shelves.houseNo, ");
        sbSql.append("   shelves.containerType, ");
        sbSql.append("   shelves.shelfNumber, ");
        sbSql.append("   stock.stockId, ");
        sbSql.append("   stock.qrCode, ");
        sbSql.append("   stock.serialNumber, ");
        sbSql.append("   stock.inventoryType, ");
        sbSql.append("   stock.status AS stockStatus,  ");
        sbSql.append("   stock_warehouse.location,  ");
        sbSql.append("   (SELECT imageUrl  FROM part_spare_part_image  WHERE sparePartId = part.sparePartId LIMIT 1)  AS imageUrl");
        sbSql.append(" FROM part_spare_part part ");
        sbSql.append("   INNER JOIN part_spare_part_type type ON type.sparePartTypeId = part.sparePartTypeId ");
        sbSql.append("   INNER JOIN stock_stock stock ON stock.sparePartId = part.sparePartId ");
        sbSql.append("   LEFT JOIN stock_goods_shelves shelves ON shelves.goodsShelvesId = stock.goodsShelvesId ");
        sbSql.append("  LEFT JOIN  stock_warehouse  ON  stock_warehouse.warehouseId=stock.warehouseId");

        sbSql.append(" WHERE stock.warehouseId = #{warehouseId} ");
        sbSql.append("       AND ( ");
        sbSql.append("         partName LIKE concat('%', ifnull(#{searchContent}, ''), '%') ");
        sbSql.append("         OR partPinYin LIKE concat('%', ifnull(#{searchContent}, ''), '%') ");
        sbSql.append("       ) ");
        if(!"all".equals(findEntity.getStatus()))
        {
            sbSql.append("       AND stock.status = #{status} ");
        }
        if(StringUtils.isNotEmpty(findEntity.getHouseNo()))
        {
            sbSql.append("       AND shelves.houseNo = #{houseNo} ");
        }
        if(StringUtils.isNotEmpty(findEntity.getShelfNumber()))
        {
            sbSql.append("       AND shelves.shelfNumber = #{shelfNumber} ");
        }
        if(StringUtils.isNotEmpty(findEntity.getSupplierId()))
        {
            sbSql.append("       AND stock.supplierId = #{supplierId} ");
        }
        sbSql.append("       AND stock.account > 0 ");
        sbSql.append("       AND part.status = '" + StatusEnum.START_USING.getValue() + "'");

        return sbSql.toString();
    }
}
