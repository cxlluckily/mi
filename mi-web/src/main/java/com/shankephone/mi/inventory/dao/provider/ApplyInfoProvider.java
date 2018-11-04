package com.shankephone.mi.inventory.dao.provider;

import com.shankephone.mi.common.enumeration.ApplyTypeEnum;
import com.shankephone.mi.inventory.formbean.StockOperationFindEntity;
import com.shankephone.mi.inventory.vo.ApplyInfoVO;
import com.shankephone.mi.model.StockBusinessApplyDetailEntity;
import com.shankephone.mi.model.StockBusinessApplyEntity;
import com.shankephone.mi.util.ObjectUtils;
import com.shankephone.mi.util.StringUtils;

/**
 * 库存操作provider
 *
 * @author 司徒彬
 * @date 2018 /7/12 09:58
 */
public class ApplyInfoProvider
{

    /**
     * Gets list info.
     *
     * @param findEntity the find entity
     * @return the list info
     */
    public String getPagerInfoByUser(StockOperationFindEntity findEntity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT ");
        sbSql.append("   tempApply.*, ");
        sbSql.append("   account ");
        sbSql.append(" FROM ( ");
        sbSql.append("        SELECT ");
        sbSql.append("          apply.*, ");
        sbSql.append("          outHouse.warehouseName outWarehouseName, ");
        sbSql.append("          inHouse.warehouseName  inWarehouseName ");
        sbSql.append("        FROM stock_business_apply apply ");
        sbSql.append("          LEFT JOIN stock_warehouse outHouse ON outHouse.warehouseId = apply.outWarehouseId ");
        sbSql.append("          LEFT JOIN stock_warehouse inHouse ON inHouse.warehouseId = apply.inWarehouseId ");
        sbSql.append("        WHERE applyUserId = #{operationUserId} ");
        sbSql.append("              AND applyTime BETWEEN #{beginTime} AND #{endTime} ");
        sbSql.append("              AND applyNo LIKE concat('%', #{documentNo}, '%') ");

        if(!"all".equals(findEntity.getApplyType()))
        {
            sbSql.append("              AND applyType = #{applyType} ");
        }
        if(!"all".equals(findEntity.getApplyStatus()))
        {
            sbSql.append("   AND applyStatus = #{applyStatus} ");
        }
        sbSql.append("      ) AS tempApply ");
        sbSql.append("   LEFT JOIN ( ");
        sbSql.append("               SELECT ");
        sbSql.append("                 sum(applyCount) AS account, ");
        sbSql.append("                 applyId ");
        sbSql.append("               FROM stock_business_apply_detail ");
        sbSql.append("               GROUP BY applyId ");
        sbSql.append("             ) AS tempAccount ");
        sbSql.append("     ON tempAccount.applyId = tempApply.applyId ");
        sbSql.append(" ORDER BY applyTime DESC ");
        sbSql.append(" LIMIT #{start}, #{limit} ");

        return sbSql.toString();
    }

    /**
     * Gets list info by user total.
     *
     * @param findEntity the find entity
     * @return the list info by user total
     */
    public String getPagerInfoByUserTotal(StockOperationFindEntity findEntity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT ");
        sbSql.append("   count(1) count ");
        sbSql.append(" FROM stock_business_apply apply ");
        sbSql.append("   LEFT JOIN stock_warehouse outHouse ON outHouse.warehouseId = apply.outWarehouseId ");
        sbSql.append("   LEFT JOIN stock_warehouse inHouse ON inHouse.warehouseId = apply.inWarehouseId ");
        sbSql.append(" WHERE applyUserId = #{operationUserId} ");
        sbSql.append("       AND applyTime BETWEEN #{beginTime} AND #{endTime} ");
        sbSql.append("       AND applyNo LIKE concat('%', #{documentNo}, '%') ");
        if(!"all".equals(findEntity.getApplyStatus()))
        {
            sbSql.append("   AND applyStatus = #{applyStatus} ");
        }
        if(!"all".equals(findEntity.getApplyType()))
        {
            sbSql.append("              AND applyType = #{applyType} ");
        }
        return sbSql.toString();
    }


    /**
     * Gets list info.
     *
     * @param findEntity the find entity
     * @return the list info
     */
    public String getPagerInfoByWarehouse(StockOperationFindEntity findEntity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT ");
        sbSql.append("   tempApply.*, ");
        sbSql.append("   account ");
        sbSql.append(" FROM ( ");
        sbSql.append("        SELECT ");
        sbSql.append("          apply.*, ");
        sbSql.append("          outHouse.warehouseName outWarehouseName, ");
        sbSql.append("          inHouse.warehouseName  inWarehouseName ");
        sbSql.append("        FROM stock_business_apply apply ");
        sbSql.append("          LEFT JOIN stock_warehouse outHouse ON outHouse.warehouseId = apply.outWarehouseId ");
        sbSql.append("          LEFT JOIN stock_warehouse inHouse ON inHouse.warehouseId = apply.inWarehouseId ");
        sbSql.append(" WHERE  ( apply.outWarehouseId in (" + StringUtils.listToString(findEntity.getWareHouseRange()) + ") ");
        sbSql.append(" OR  apply.inWarehouseId in (" + StringUtils.listToString(findEntity.getWareHouseRange()) + ") ) ");
        sbSql.append("              AND applyTime BETWEEN #{beginTime} AND #{endTime} ");
        sbSql.append("              AND applyNo LIKE concat('%', #{documentNo}, '%') ");
        if(!"all".equals(findEntity.getApplyType()))
        {
            sbSql.append("              AND applyType = #{applyType} ");
        }
        if(!"all".equals(findEntity.getApplyStatus()))
        {
            sbSql.append("   AND applyStatus = #{applyStatus} ");
        }
        if(StringUtils.isNotEmpty(findEntity.getNotApplyStatus()))
        {
            sbSql.append("   AND applyStatus <> #{notApplyStatus} ");
        }
        sbSql.append("      ) AS tempApply ");
        sbSql.append("   LEFT JOIN ( ");
        sbSql.append("               SELECT ");
        sbSql.append("                 sum(applyCount) AS account, ");
        sbSql.append("                 applyId ");
        sbSql.append("               FROM stock_business_apply_detail ");
        sbSql.append("               GROUP BY applyId ");
        sbSql.append("             ) AS tempAccount ");
        sbSql.append("     ON tempAccount.applyId = tempApply.applyId ");
        sbSql.append(" ORDER BY applyTime DESC ");
        sbSql.append(" LIMIT #{start}, #{limit} ");

        return sbSql.toString();
    }

    /**
     * Gets list info by user total.
     *
     * @param findEntity the find entity
     * @return the list info by user total
     */
    public String getPagerInfoByWarehouseTotal(StockOperationFindEntity findEntity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT ");
        sbSql.append("   count(1) count ");
        sbSql.append(" FROM stock_business_apply apply ");
        sbSql.append("   LEFT JOIN stock_warehouse outHouse ON outHouse.warehouseId = apply.outWarehouseId ");
        sbSql.append("   LEFT JOIN stock_warehouse inHouse ON inHouse.warehouseId = apply.inWarehouseId ");
        sbSql.append(" WHERE  ( apply.outWarehouseId in (" + StringUtils.listToString(findEntity.getWareHouseRange()) + ") ");
        sbSql.append(" OR  apply.inWarehouseId in (" + StringUtils.listToString(findEntity.getWareHouseRange()) + ") ) ");

        sbSql.append("       AND applyTime BETWEEN #{beginTime} AND #{endTime} ");
        sbSql.append("       AND applyNo LIKE concat('%', #{documentNo}, '%') ");
        if(!"all".equals(findEntity.getApplyStatus()))
        {
            sbSql.append("   AND applyStatus = #{applyStatus} ");
        }
        if(StringUtils.isNotEmpty(findEntity.getNotApplyStatus()))
        {
            sbSql.append("   AND applyStatus <> #{notApplyStatus} ");
        }
        if(!"all".equals(findEntity.getApplyType()))
        {
            sbSql.append("              AND applyType = #{applyType} ");
        }
        return sbSql.toString();
    }
    /**
     * Get pager info for audit string.
     *
     * @param findEntity the find entity
     * @return the string
     */
    public String getPagerInfoForAudit(StockOperationFindEntity findEntity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT ");
        sbSql.append("   apply.*, ");
        sbSql.append("   outHouse.warehouseName AS outWarehouseName, ");
        sbSql.append("   inHouse.warehouseName AS inWarehouseName");
        sbSql.append(" FROM stock_business_apply apply ");
        sbSql.append("   LEFT JOIN stock_warehouse outHouse ON outHouse.warehouseId = apply.outWarehouseId ");
        sbSql.append("   LEFT JOIN stock_warehouse inHouse ON inHouse.warehouseId = apply.inWarehouseId ");
        sbSql.append(" WHERE 1 = 1 ");
        sbSql.append("       AND applyTime BETWEEN #{beginTime} AND #{endTime} ");
        if (StringUtils.isNotEmpty(findEntity.getApplyUser()))
        {
            sbSql.append("       AND applyUser LIKE concat('%', #{applyUser}, '%') ");
        }
        if(!"all".equals(findEntity.getApplyType())) {
            sbSql.append("       AND applyType = #{applyType} ");
        }

        String warehouseRange = StringUtils.listToString(findEntity.getWareHouseRange());
        if (ApplyTypeEnum.RETURN.getCode().equalsIgnoreCase(findEntity.getApplyType()))
        {
            sbSql.append("       AND inWarehouseId IN (" + warehouseRange + ") ");
        }
        else
        {
            sbSql.append("       AND outWarehouseId IN (" + warehouseRange + ") ");
        }
        sbSql.append("       AND applyNo LIKE concat('%', #{documentNo}, '%') ");
        if (!"all".equals(findEntity.getApplyStatus()))
        {
            sbSql.append("   AND applyStatus = #{applyStatus} ");
        }
        if(StringUtils.isNotEmpty(findEntity.getNotApplyStatus()))
        {
            sbSql.append("       AND applyStatus <> #{notApplyStatus} ");
        }
        sbSql.append(" ORDER BY apply.applyTime DESC");
        sbSql.append("   LIMIT #{start}, #{limit} ");
        return sbSql.toString();
    }

    /**
     * Get pager info for audit total string.
     *
     * @param findEntity the find entity
     * @return the string
     */
    public String getPagerInfoForAuditTotal(StockOperationFindEntity findEntity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT ");
        sbSql.append("   count(1) ");
        sbSql.append(" FROM stock_business_apply apply ");
        sbSql.append("   LEFT JOIN stock_warehouse outHouse ON outHouse.warehouseId = apply.outWarehouseId ");
        sbSql.append("   LEFT JOIN stock_warehouse inHouse ON inHouse.warehouseId = apply.inWarehouseId ");
        sbSql.append(" WHERE 1 = 1 ");
        sbSql.append("       AND applyTime BETWEEN #{beginTime} AND #{endTime} ");
        if(!"all".equals(findEntity.getApplyType())) {
            sbSql.append("       AND applyType = #{applyType} ");
        }
        String warehouseRange = StringUtils.listToString(findEntity.getWareHouseRange());
        sbSql.append("       AND outWarehouseId IN (" + warehouseRange + ") ");
        sbSql.append("       AND applyNo LIKE concat('%', #{documentNo}, '%') ");
        if (!"all".equals(findEntity.getApplyStatus()))
        {
            sbSql.append("   AND applyStatus = #{applyStatus} ");
        }
        if(StringUtils.isNotEmpty(findEntity.getNotApplyStatus()))
        {
            sbSql.append("       AND applyStatus <> #{notApplyStatus} ");
        }
        return sbSql.toString();
    }

    /**
     * Get apply info by apply id string.
     *
     * @param applyId the apply id
     * @return the string
     */
    public String getApplyInfoByApplyId(Long applyId)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT ");
        sbSql.append("   *, ");
        sbSql.append("   outWareHouse.warehouseName AS outWareHouseName, ");
        sbSql.append("   inWareHouse.warehouseName  AS inWareHouseName, ");
        sbSql.append("   (SELECT inStockUser FROM stock_in_stock WHERE applyId=stock_business_apply.applyId LIMIT 1) AS inStockUser, ");

        sbSql.append("  (SELECT outUser FROM stock_out_stock_apply WHERE applyId=stock_business_apply.applyId LIMIT 1 )  AS outUser ");

        sbSql.append(" FROM stock_business_apply ");
        sbSql.append("   LEFT JOIN stock_warehouse outWareHouse ON stock_business_apply.outWarehouseId = outWareHouse.warehouseId ");
        sbSql.append("   LEFT JOIN stock_warehouse inWareHouse ON stock_business_apply.inWarehouseId = inWareHouse.warehouseId ");
        sbSql.append(" WHERE applyId = #{applyId} ");

        return sbSql.toString();

    }

    /**
     * Gets apply detail info by apply id.
     *
     * @param applyId the apply id
     * @return the apply detail info by apply id
     */
    public String getApplyDetailInfoByApplyId(Long applyId)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT detail.*, ");
        sbSql.append("   part.*, ");
        sbSql.append("   type.categoryName, ");
        sbSql.append("   ifnull((SELECT imageUrl ");
        sbSql.append("         FROM part_spare_part_image image ");
        sbSql.append("         WHERE detail.sparePartId = image.sparePartId ");
        sbSql.append("         LIMIT 1), 'noImage') AS imageUrl ");
        sbSql.append(" FROM stock_business_apply_detail detail ");
        sbSql.append("   INNER JOIN part_spare_part part ON detail.sparePartId = part.sparePartId ");
        sbSql.append("   INNER JOIN part_spare_part_type type ON type.sparePartTypeId = part.sparePartTypeId ");
        sbSql.append(" WHERE applyId = #{applyId} ");

        return sbSql.toString();
    }

    public String getApplySendOutDetail(Long applyId)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT ");
        sbSql.append("   sum(stock_out_stock_detail.outCount) AS alreadyOutCount, ");
        sbSql.append("   stock_out_stock_apply_detail.sparePartId, ");
        sbSql.append("   stock_stock.status ");
        sbSql.append(" FROM stock_out_stock_apply ");
        sbSql.append("   INNER JOIN stock_out_stock_apply_detail ");
        sbSql.append("     ON (stock_out_stock_apply.outStockApplyId = stock_out_stock_apply_detail.outStockApplyId ");
        sbSql.append("         AND stock_out_stock_apply.applyId = #{applyId}) ");
        sbSql.append("   INNER JOIN stock_out_stock_detail ");
        sbSql.append("     ON stock_out_stock_detail.outStockApplyDetailId = stock_out_stock_apply_detail.outStockApplyDetailId ");
        sbSql.append("   INNER JOIN stock_stock ");
        sbSql.append("     ON stock_stock.stockId = stock_out_stock_detail.stockId ");
        sbSql.append(" GROUP BY stock_out_stock_apply_detail.sparePartId, stock_stock.status ");

        return sbSql.toString();

    }

  /**
     * Update apply status string.
     *
     * @param applyEntity the apply entity
     * @return the string
     */
    public String updateApplyStatus(StockBusinessApplyEntity applyEntity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" UPDATE ");
        sbSql.append("   stock_business_apply ");
        sbSql.append(" SET ");
        sbSql.append("   applyStatus = #{applyStatus} ");
        if (ObjectUtils.isNotEmpty(applyEntity.getAuditUser()))
        {
            sbSql.append("   ,auditUser = #{auditUser} ");
        }
        if (ObjectUtils.isNotEmpty(applyEntity.getAuditUserId()))
        {
            sbSql.append("   ,auditUserId = #{auditUserId} ");
        }
        if (ObjectUtils.isNotEmpty(applyEntity.getAuditRemark()))
        {
            sbSql.append("   ,auditRemark = #{auditRemark}");
        }
        if (ObjectUtils.isNotEmpty(applyEntity.getAuditTime()))
        {
            sbSql.append("   ,auditTime = #{auditTime} ");
        }

        sbSql.append(" WHERE ");
        sbSql.append("   applyId = #{applyId} ");
        return sbSql.toString();
    }

    /**
     * Insert apply info string.
     *
     * @param applyVO the apply vo
     * @return the string
     */
    public String insertApplyInfo(ApplyInfoVO applyVO)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" INSERT INTO ");
        sbSql.append("   stock_business_apply ");
        sbSql.append("   ( ");
        sbSql.append("      outWarehouseId, ");
        sbSql.append("      inWarehouseId, ");
        sbSql.append("      applyUser, ");
        sbSql.append("      applyUserId, ");
        sbSql.append("      applyTime, ");
        sbSql.append("      applyStatus, ");
        sbSql.append("      applyType, ");
        sbSql.append("      applyRemark, ");
        sbSql.append("      applyNo ");
        sbSql.append("   ) ");
        sbSql.append(" VALUES ");
        sbSql.append("   ( ");
        sbSql.append("      #{outWarehouseId}, ");
        sbSql.append("      #{inWarehouseId}, ");
        sbSql.append("      #{operationUserName}, ");
        sbSql.append("      #{operationUserId}, ");
        sbSql.append("      #{applyTime}, ");
        sbSql.append("      #{applyStatus}, ");
        sbSql.append("      #{applyType}, ");
        sbSql.append("      #{applyRemark}, ");
        sbSql.append("      #{applyNo} ");
        sbSql.append("   ) ");
        return sbSql.toString();
    }

    /**
     * Insert apply detail string.
     *
     * @param detailEntity the detail entity
     * @return the string
     */
    public String insertApplyDetail(StockBusinessApplyDetailEntity detailEntity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" INSERT INTO ");
        sbSql.append("   stock_business_apply_detail ");
        sbSql.append("   ( ");
        sbSql.append("      applyId, ");
        sbSql.append("      sparePartId, ");
        sbSql.append("      applyCount, ");
        sbSql.append("      stockStatus, ");
        sbSql.append("      stockId ");
        sbSql.append("   ) ");
        sbSql.append(" VALUES ");
        sbSql.append("   ( ");
        sbSql.append("      #{applyId}, ");
        sbSql.append("      #{sparePartId}, ");
        sbSql.append("      #{applyCount}, ");
        sbSql.append("      #{stockStatus}, ");
        sbSql.append("      #{stockId} ");
        sbSql.append("   ) ");
        return sbSql.toString();
    }

    /**
     * Update apply info string.
     *
     * @param applyInfoVO the apply info vo
     * @return the string
     */
    public String updateApplyInfo(ApplyInfoVO applyInfoVO)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" UPDATE ");
        sbSql.append("   stock_business_apply ");
        sbSql.append(" SET ");
        sbSql.append("   outWarehouseId = #{outWarehouseId}, ");
        sbSql.append("   inWarehouseId = #{inWarehouseId}, ");
        sbSql.append("   applyRemark = #{applyRemark} ");
        sbSql.append(" WHERE ");
        sbSql.append("   applyId = #{applyId} ");
        return sbSql.toString();
    }

    /**
     * Delete apply detail string.
     *
     * @param applyId the apply id
     * @return the string
     */
    public String deleteApplyDetail(Long applyId)
    {
        String sql = "delete from stock_business_apply_detail where applyId = #{applyId}";
        return sql;
    }

    /**
     * Delete apply info string.
     *
     * @param applyId the apply id
     * @return the string
     */
    public String deleteApplyInfo(Long applyId)
    {
        String sql = "delete from stock_business_apply where applyId = #{applyId}";
        return sql;
    }

    public String getAllInfoList(Long applyId)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT ");
        sbSql.append("   applyUser, ");
        sbSql.append("   DATE_FORMAT(applyTime, '%Y-%m-%d %H:%m:%s')             AS applyTime, ");
        sbSql.append("   ifnull(applyRemark, '')                        AS applyRemark, ");
        sbSql.append("   ifnull(auditUser, '')                          AS auditUser, ");
        sbSql.append("   ifnull(DATE_FORMAT(auditTime, '%Y-%m-%d %H:%m:%s'), '') AS auditTime, ");
        sbSql.append("   ifnull(auditRemark, '')                        AS auditRemark, ");
        sbSql.append("   ifnull(outUser, '')                            AS outUser, ");
        sbSql.append("   ifnull(DATE_FORMAT(outDate, '%Y-%m-%d %H:%m:%s'), '')   AS outDate, ");
        sbSql.append("   ifnull(stock_out_stock_apply.remark, '')       AS outRemark, ");
        sbSql.append("   ifnull(inStockUser, '')                        AS inStockUser, ");
        sbSql.append("   ifnull(DATE_FORMAT(inDate, '%Y-%m-%d %H:%m:%s'), '')    AS inDate, ");
        sbSql.append("   ifnull(stock_in_stock.remark, '')              AS inRemark, ");
        sbSql.append("   ifnull(stock_business_apply.applyStatus, '')         AS applyStatus ");
        sbSql.append(" FROM stock_business_apply ");
        sbSql.append("   LEFT JOIN stock_out_stock_apply ");
        sbSql.append("     ON stock_out_stock_apply.applyId = stock_business_apply.applyId ");
        sbSql.append("   LEFT JOIN stock_in_stock ");
        sbSql.append("     ON stock_in_stock.applyId = stock_business_apply.applyId ");
        sbSql.append(" WHERE stock_business_apply.applyId = #{applyId} ");

        return sbSql.toString();

    }
}
