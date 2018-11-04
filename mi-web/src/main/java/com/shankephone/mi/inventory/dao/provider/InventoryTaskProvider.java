package com.shankephone.mi.inventory.dao.provider;

import com.shankephone.fdfs.FdfsClient;
import com.shankephone.mi.inventory.formbean.GetInventoryTaskListFindEntity;
import com.shankephone.mi.inventory.formbean.InventoryFindEntity;
import com.shankephone.mi.model.StockInventoryDetailEntity;
import com.shankephone.mi.model.StockInventoryTaskEntity;
import com.shankephone.mi.util.StringUtils;

/**
 * 盘库
 *
 * @author 赵亮
 * @date 2018-08-16 14:13
 */
public class InventoryTaskProvider
{
    public String insertOne(StockInventoryTaskEntity entity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" INSERT INTO ");
        sbSql.append("   stock_inventory_task ");
        sbSql.append("   ( ");
        sbSql.append("      warehouseId, ");
        sbSql.append("      batchNo, ");
        sbSql.append("      headPerson, ");
        sbSql.append("      headPersonUserId, ");
        sbSql.append("      status, ");
        sbSql.append("      beginTIme, ");
        sbSql.append("      endTIme, ");
        sbSql.append("      createUser, ");
        sbSql.append("      createUserId, ");
        sbSql.append("      modifyUser, ");
        sbSql.append("      remark ");
        sbSql.append("   ) ");
        sbSql.append(" VALUES ");
        sbSql.append("   ( ");
        sbSql.append("      #{warehouseId}, ");
        sbSql.append("      #{batchNo}, ");
        sbSql.append("      #{headPerson}, ");
        sbSql.append("      #{headPersonUserId}, ");
        sbSql.append("      #{status}, ");
        sbSql.append("      #{beginTIme}, ");
        sbSql.append("      #{endTIme}, ");
        sbSql.append("      #{operationUserName}, ");
        sbSql.append("      #{operationUserId}, ");
        sbSql.append("      #{operationUserName}, ");
        sbSql.append("      #{remark} ");
        sbSql.append("   ) ");
        return sbSql.toString();

    }
    public String updateOne(StockInventoryTaskEntity entity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" UPDATE ");
        sbSql.append("   stock_inventory_task ");
        sbSql.append(" SET ");
        sbSql.append("   warehouseId = #{warehouseId}, ");
        //sbSql.append("   batchNo = #{batchNo}, ");
        sbSql.append("   headPerson = #{headPerson}, ");
        //sbSql.append("   status = #{status}, ");
        sbSql.append("   beginTIme = #{beginTIme}, ");
        sbSql.append("   endTIme = #{endTIme}, ");
        sbSql.append("   modifyUser = #{operationUserName}, ");
        sbSql.append("   remark = #{remark}, ");
        sbSql.append("   headPersonUserId = #{headPersonUserId} ");
        sbSql.append(" WHERE ");
        sbSql.append("   inventoryTaskId = #{inventoryTaskId} ");
        return sbSql.toString();
    }

    public String insertInventoryDetail(StockInventoryDetailEntity entity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" INSERT INTO ");
        sbSql.append("   stock_inventory_detail ");
        sbSql.append("   ( ");
        sbSql.append("      inventoryTaskId, ");
        sbSql.append("      sparePartId, ");
        sbSql.append("      beforeAccount, ");
        sbSql.append("      aftierAccount, ");
        sbSql.append("      status, ");
        sbSql.append("      inventoryDescribe, ");
        sbSql.append("      createUser, ");
        sbSql.append("      modifyUser ");
        sbSql.append("   ) ");
        sbSql.append(" VALUES ");
        sbSql.append("   ( ");
        sbSql.append("      #{inventoryTaskId}, ");
        sbSql.append("      #{sparePartId}, ");
        sbSql.append("      #{beforeAccount}, ");
        sbSql.append("      #{aftierAccount}, ");
        sbSql.append("      #{status}, ");
        sbSql.append("      #{inventoryDescribe}, ");
        sbSql.append("      #{operationUserName}, ");
        sbSql.append("      #{operationUserName} ");
        sbSql.append("   ) ");
        return sbSql.toString();

    }

    public String getInsertDetailList(Long warehouseId)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT ");
        sbSql.append("     sparePartId, ");
        sbSql.append("     sum(account) AS account,status ");
        sbSql.append("   FROM stock_stock ");
        sbSql.append("   WHERE warehouseId = #{warehouseId} ");
        sbSql.append("   GROUP BY sparePartId,status ");
        sbSql.append("   HAVING account > 0 ");
        sbSql.append("   ORDER BY sparePartId");
        return sbSql.toString();
    }

    public String getInventoryTaskList(GetInventoryTaskListFindEntity findEntity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT ");
        sbSql.append("   stock_inventory_task.*, ");
        sbSql.append("   DATE_FORMAT(beginTIme, '%Y-%m-%d') AS beginTImeTxt, ");
        sbSql.append("   DATE_FORMAT(endTIme, '%Y-%m-%d') AS endTImeTxt, ");
        sbSql.append("   warehouseName ");
        sbSql.append(" FROM stock_inventory_task ");
        sbSql.append("   INNER JOIN stock_warehouse ");
        sbSql.append("     ON stock_warehouse.warehouseId = stock_inventory_task.warehouseId ");
        sbSql.append(" WHERE 1 = 1 ");

        if("headPersonUserId".equals(findEntity.getSearchType()))
        {
            sbSql.append("       AND stock_inventory_task.headPersonUserId = #{operationUserId} ");
        }
        else  if("createUserId".equals(findEntity.getSearchType()))
        {
            sbSql.append("       AND stock_inventory_task.createUserId = #{operationUserId} ");
        }
        else
        {
            sbSql.append("       AND stock_inventory_task.warehouseId IN (" + StringUtils.listToString(findEntity.getUserLoginInfo().getWarehouses()) + ") ");
        }

        if (StringUtils.isNotEmpty(findEntity.getHeadPerson()))
        {
            sbSql.append("       AND stock_inventory_task.headPerson LIKE concat('%', #{headPerson}, '%') ");
        }
        if (!"all".equals(findEntity.getStatus()))
        {
            sbSql.append("       AND stock_inventory_task.status = #{status} ");
        }
        if (StringUtils.isNotEmpty(findEntity.getWarehouseName()))
        {
            sbSql.append("       AND warehouseName LIKE concat('%', #{warehouseName}, '%') ");
        }
        if (StringUtils.isNotEmpty(findEntity.getBeginTime()))
        {
            sbSql.append("       AND beginTIme > #{beginTime} ");
        }
        if (StringUtils.isNotEmpty(findEntity.getEndTime()))
        {
            sbSql.append("       AND endTIme < #{endTime} ");
        }
        sbSql.append("      ORDER BY stock_inventory_task.createTime desc ");
        return sbSql.toString();
    }

    public String getInventoryTaskListCount(GetInventoryTaskListFindEntity findEntity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT ");
        sbSql.append("   count(1) count ");
        sbSql.append(" FROM stock_inventory_task ");
        sbSql.append("   INNER JOIN stock_warehouse ");
        sbSql.append("     ON stock_warehouse.warehouseId = stock_inventory_task.warehouseId ");
        sbSql.append(" WHERE 1 = 1 ");
        if("headPersonUserId".equals(findEntity.getSearchType()))
        {
            sbSql.append("       AND stock_inventory_task.headPersonUserId = #{operationUserId} ");
        }
        else if("createUserId".equals(findEntity.getSearchType()))
        {
            sbSql.append("       AND stock_inventory_task.createUserId = #{operationUserId} ");
        }
        else
        {
            sbSql.append("       AND stock_inventory_task.warehouseId IN (" + StringUtils.listToString(findEntity.getUserLoginInfo().getWarehouses()) + ") ");
        }
        if (StringUtils.isNotEmpty(findEntity.getHeadPerson()))
        {
            sbSql.append("       AND stock_inventory_task.headPerson LIKE concat('%', #{headPerson}, '%') ");
        }
        if (!"all".equals(findEntity.getStatus()))
        {
            sbSql.append("       AND stock_inventory_task.status = #{status} ");
        }
        if (StringUtils.isNotEmpty(findEntity.getWarehouseName()))
        {
            sbSql.append("       AND warehouseName LIKE concat('%', #{warehouseName}, '%') ");
        }
        if (StringUtils.isNotEmpty(findEntity.getBeginTime()))
        {
            sbSql.append("       AND beginTIme > #{beginTime} ");
        }
        if (StringUtils.isNotEmpty(findEntity.getEndTime()))
        {
            sbSql.append("       AND endTIme < #{endTime} ");
        }
        return sbSql.toString();
    }

    public String getInventoryDetailEntity(InventoryFindEntity entity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT ");
        sbSql.append("     warehouseName, ");
        sbSql.append("     partName, ");
        sbSql.append("     categoryName, ");
        sbSql.append("     taskDetail.*, ");
        sbSql.append("   concat('" + FdfsClient.getDownloadServer() + "',(SELECT imageUrl FROM part_spare_part_image image WHERE image.sparePartId = taskDetail.sparePartId LIMIT 1)) AS imageUrl ");
        sbSql.append("   FROM stock_inventory_task ");
        sbSql.append("     INNER JOIN stock_warehouse ");
        sbSql.append("       ON stock_warehouse.warehouseId = stock_inventory_task.warehouseId ");
        sbSql.append("     INNER JOIN stock_inventory_detail taskDetail ");
        sbSql.append("       ON stock_inventory_task.inventoryTaskId = taskDetail.inventoryTaskId ");
        sbSql.append("     INNER JOIN part_spare_part ");
        sbSql.append("       ON part_spare_part.sparePartId = taskDetail.sparePartId ");
        sbSql.append("     INNER JOIN part_spare_part_type ");
        sbSql.append("       ON part_spare_part_type.sparePartTypeId = part_spare_part.sparePartTypeId ");
        sbSql.append("   WHERE stock_inventory_task.inventoryTaskId = #{inventoryTaskId} ");
        return sbSql.toString();
    }


    public String selectInventoryTaskentity(InventoryFindEntity entity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" select stock_inventory_task.*,stock_warehouse.warehouseName," +
                "  (select   sum(aftierAccount) - sum(beforeAccount) from stock_inventory_detail where    inventoryTaskId=stock_inventory_task.inventoryTaskId) Count " +
                " from stock_inventory_task " +
                " left join stock_warehouse  on stock_inventory_task.warehouseId= stock_warehouse.warehouseId ");

        sbSql.append("   WHERE stock_inventory_task.inventoryTaskId = #{inventoryTaskId} ");

        return sbSql.toString();
    }

    public String updateInventoryTaskStatus(StockInventoryTaskEntity entity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" UPDATE stock_inventory_task ");
        sbSql.append("   SET status = #{status} ");
        sbSql.append("   WHERE inventoryTaskId = #{inventoryTaskId} ");

        return sbSql.toString();
    }

    public String updateInventoryDetail(StockInventoryDetailEntity entity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append("   UPDATE stock_inventory_detail ");
        sbSql.append("   SET inventoryDescribe = #{inventoryDescribe}, ");
        sbSql.append("     aftierAccount=#{aftierAccount} ");
        sbSql.append("   WHERE inventoryDetailId = #{inventoryDetailId} ");

        return sbSql.toString();

    }


}
