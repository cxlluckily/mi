package com.shankephone.mi.inventory.dao.provider;

import com.shankephone.mi.common.enumeration.StatusEnum;
import com.shankephone.mi.inventory.formbean.WarehouseFindEntity;
import com.shankephone.mi.inventory.vo.WarehouseVO;
import com.shankephone.mi.model.StockWarehouseStationEntity;
import com.shankephone.mi.util.ObjectUtils;

/**
 * ${description}
 *
 * @author 司徒彬
 * @date 2018 /7/2 17:23
 */
public class WarehouseProvider
{

    /**
     * Gets pager info.
     *
     * @param findEntity the find entity
     * @return the pager info
     */
    public String getPagerInfo(WarehouseFindEntity findEntity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT ");
        sbSql.append("   warehouse.*, ");
        sbSql.append("   sectionName, ");
        sbSql.append("   subjectName ");
        sbSql.append(" FROM stock_warehouse warehouse ");
        sbSql.append("   INNER JOIN org_work_section section ON section.workSectionId = warehouse.workSectionId ");
        sbSql.append("   INNER JOIN sys_operation_subject subject ON subject.operationSubjectId = section.operationSubjectId ");
        sbSql.append(" WHERE section.operationSubjectId = #{operationSubjectId} ");
        if (findEntity.getWorkSectionId()!=null&&findEntity.getWorkSectionId().intValue() != 0)
        {
            sbSql.append("      AND warehouse.workSectionId IN ("+findEntity.getWorkSectionIds()+") ");
        }
        if (ObjectUtils.isNotEmpty(findEntity.getWarehouseName()))
        {
            sbSql.append("       AND warehouseName LIKE concat('%', #{warehouseName}, '%') ");
        }
        if (ObjectUtils.isNotEmpty(findEntity.getHeadPerson()))
        {
            sbSql.append("       AND ifnull(warehouse.headPerson, '') LIKE concat('%', #{headPerson}, '%') ");
        }
        if (ObjectUtils.isNotEmpty(findEntity.getContactNumber()))
        {
            sbSql.append("       AND ifnull(warehouse.contactNumber, '') LIKE concat('%', #{contactNumber}, '%') ");
        }
        if (!"all".equals(findEntity.getStatus()))
        {
            sbSql.append("       AND warehouse.status = #{status} ");
        }
        return sbSql.toString();
    }


    /**
     * Get warehouse info string.
     *
     * @param warehouseId the warehouse id
     * @return the string
     */
    public String getWarehouseInfo(Long warehouseId)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT ");
        sbSql.append("   warehouse.*, ");
        sbSql.append("   stationId ");
        sbSql.append(" FROM stock_warehouse warehouse ");
        sbSql.append("   INNER JOIN stock_warehouse_station warehouseStation ");
        sbSql.append("     ON warehouseStation.warehouseId = warehouse.warehouseId ");
        sbSql.append(" WHERE warehouse.warehouseId = #{warehouseId} ");

        return sbSql.toString();
    }

    /**
     * Get station by work section id string.
     *
     * @param workSectionId the work section id
     * @return the string
     */
    public String getStationByWorkSectionId(Long workSectionId)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT station.* ");
        sbSql.append(" FROM org_station station ");
        sbSql.append("   INNER JOIN org_work_section_station sectionStation ");
        sbSql.append("     ON station.stationId = sectionStation.stationId ");
        sbSql.append(" WHERE sectionStation.workSectionId = #{workSectionId} ");

        return sbSql.toString();
    }

    /**
     * Get station by parent warehouse id string.
     *
     * @param warehouseId the warehouse id
     * @return the string
     */
    public String getStationByParentWarehouseId(Long warehouseId)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT station.* ");
        sbSql.append(" FROM org_station station ");
        sbSql.append("   INNER JOIN stock_warehouse_station warehouseStation ");
        sbSql.append("     ON station.stationId = warehouseStation.stationId ");
        sbSql.append(" WHERE warehouseStation.warehouseId = #{warehouseId} ");

        return sbSql.toString();
    }

    /**
     * Insert warehouse string.
     *
     * @param warehouse the warehouse
     * @return the string
     */
    public String insertWarehouse(WarehouseVO warehouse)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" INSERT INTO ");
        sbSql.append("   stock_warehouse ");
        sbSql.append("   ( ");
        sbSql.append("      operationSubjectId, ");
        sbSql.append("      workSectionId, ");
        sbSql.append("      parentWarehouseId, ");
        sbSql.append("      warehouseName, ");
        sbSql.append("      description, ");
        sbSql.append("      internalNumber, ");
        sbSql.append("      headPerson, ");
        sbSql.append("      contactNumber, ");
        sbSql.append("      warehouseManager, ");
        sbSql.append("      remark, ");
        sbSql.append("      location, ");
        sbSql.append("      status, ");
        sbSql.append("      createUser, ");
        sbSql.append("      modifyUser ");
        sbSql.append("   ) ");
        sbSql.append(" VALUES ");
        sbSql.append("   ( ");
        sbSql.append("      #{operationSubjectId}, ");
        sbSql.append("      #{workSectionId}, ");
        sbSql.append("      #{parentWarehouseId}, ");
        sbSql.append("      #{warehouseName}, ");
        sbSql.append("      #{description}, ");
        sbSql.append("      #{internalNumber}, ");
        sbSql.append("      #{headPerson}, ");
        sbSql.append("      #{contactNumber}, ");
        sbSql.append("      #{warehouseManager}, ");
        sbSql.append("      #{remark}, ");
        sbSql.append("      #{location}, ");
        sbSql.append("      #{status}, ");
        sbSql.append("      #{operationUserName}, ");
        sbSql.append("      #{operationUserName} ");
        sbSql.append("   ) ");
        return sbSql.toString();
    }

    /**
     * Insert warehouse station info string.
     *
     * @param warehouseStationEntity the warehouse station entity
     * @return the string
     */
    public String insertWarehouseStationInfo(StockWarehouseStationEntity warehouseStationEntity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" INSERT INTO ");
        sbSql.append("   stock_warehouse_station ");
        sbSql.append("   ( ");
        sbSql.append("      warehouseId, ");
        sbSql.append("      stationId ");
        sbSql.append("   ) ");
        sbSql.append(" VALUES ");
        sbSql.append("   ( ");
        sbSql.append("      #{warehouseId}, ");
        sbSql.append("      #{stationId} ");
        sbSql.append("   ) ");
        return sbSql.toString();
    }

    /**
     * Update warehouse string.
     *
     * @param warehouse the warehouse
     * @return the string
     */
    public String updateWarehouse(WarehouseVO warehouse)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" UPDATE ");
        sbSql.append("   stock_warehouse ");
        sbSql.append(" SET ");
        sbSql.append("   operationSubjectId = #{operationSubjectId}, ");
        sbSql.append("   workSectionId = #{workSectionId}, ");
        sbSql.append("   parentWarehouseId = #{parentWarehouseId}, ");
        sbSql.append("   warehouseName = #{warehouseName}, ");
        sbSql.append("   description = #{description}, ");
        sbSql.append("   internalNumber = #{internalNumber}, ");
        sbSql.append("   headPerson = #{headPerson}, ");
        sbSql.append("   contactNumber = #{contactNumber}, ");
        sbSql.append("   warehouseManager = #{warehouseManager}, ");
        sbSql.append("   remark = #{remark}, ");
        sbSql.append("   location = #{location}, ");
        sbSql.append("   status = #{status}, ");
        sbSql.append("   modifyUser = #{operationUserName} ");
        sbSql.append(" WHERE ");
        sbSql.append("   warehouseId = #{warehouseId} ");
        return sbSql.toString();
    }

    /**
     * Delete warehouse station string.
     *
     * @param warehouseId the warehouse id
     * @return the string
     */
    public String deleteWarehouseStation(Long warehouseId)
    {
        String sql = "DELETE FROM stock_warehouse_station where warehouseId = #{warehouseId}";
        return sql;
    }

    public String deleteWarehouse(Long warehouseId)
    {
        String sql = "UPDATE stock_warehouse SET status = '" + StatusEnum.STOP_USING.getValue() + "' where warehouseId = #{warehouseId}";
        return sql;
    }

    public String getSameWarehouseNameCount(WarehouseVO warehouse)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT count(1) ");
        sbSql.append(" FROM stock_warehouse ");
        sbSql.append(" WHERE warehouseId NOT IN (#{warehouseId}) ");
        sbSql.append("       AND warehouseName = #{warehouseName} AND operationSubjectId = #{operationSubjectId} ");

        return sbSql.toString();

    }
}