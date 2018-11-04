package com.shankephone.mi.common.dao.provider;

import com.shankephone.mi.common.enumeration.RangeRoleTypeEnum;
import com.shankephone.mi.common.enumeration.StatusEnum;
import com.shankephone.mi.common.formbean.ValidateIsRepeatFindEntity;
import com.shankephone.mi.common.model.DataFindEntity;
import com.shankephone.mi.sys.formbean.DataDictFindEntity;
import com.shankephone.mi.util.ObjectUtils;
import com.shankephone.mi.util.StringUtils;

/**
 * 公共数据SQL Provider
 *
 * @author fengql
 * @date 2018年6月25日 上午10:37:05
 */
public class DataProvider
{
    public String loadData(DataFindEntity findEntity)
    {
        String tableName = null;
        String colName = null;
        String idCol = null;
        String codeCol = null;
        String nameCol = null;
        Long entityId = findEntity.getEntityId();
        String type = findEntity.getType();
        Long operationSubjectId = findEntity.getOperationSubjectId();
        boolean hasSubject = true;
        switch (type)
        {
            case "operation_subject":
                tableName = "sys_operation_subject";
                idCol = "operationSubjectId";
                codeCol = "subjectCode";
                nameCol = "subjectName";
                hasSubject = true;
                break;
            case "line":
                tableName = "org_line";
                idCol = "lineId";
                codeCol = "lineCode";
                nameCol = "lineName";
                break;
            case "station":
                tableName = "org_station";
                colName = "lineId";
                idCol = "stationId";
                codeCol = "stationCode";
                nameCol = "stationName";
                hasSubject = false;
                break;
            case "work_section":
                tableName = "org_work_section";
                idCol = "workSectionId";
                codeCol = "sectionCode";
                nameCol = "sectionName";
                break;
            case "organization":
                tableName = "org_organization";
                idCol = "orgId";
                codeCol = "internalNumber";
                nameCol = "orgName";
                break;
            case "warehouse":
                tableName = "stock_warehouse";
                //仓库的code使用ID字段
                idCol = "warehouseId";
                codeCol = "warehouseId";
                nameCol = "warehouseName";
                break;
            case "region":
                tableName = "sys_region";
                colName = "regionId";
                idCol = "regionId";
                codeCol = "areaCode";
                nameCol = "areaName";
                hasSubject = false;
                break;
            case "role":
                tableName = "sys_role";
                idCol = "roleId";
                codeCol = "roleCode";
                nameCol = "roleName";
                break;
            case "device_type":
                tableName = "part_spare_part_type";
                //备件类型的code使用ID字段
                idCol = "sparePartTypeId";
                codeCol = "sparePartTypeId";
                nameCol = "categoryName";
                break;
            case "supplier":
                tableName = "stock_supplier";
                //供应商的code使用ID字段
                idCol = "supplierId";
                codeCol = "supplierId";
                nameCol = "supplierName";
                break;
            default:
                break;
        }

        StringBuilder sbSql = new StringBuilder();
        sbSql.append("   SELECT ").append(idCol).append(" as id, ");
        sbSql.append(codeCol).append(" as code, ");
        sbSql.append(nameCol).append(" as name ");
        sbSql.append("   FROM ").append(tableName);
        sbSql.append("   WHERE 1 = 1 ");
        /*if(operationSubjectId != null) {
        	sbSql.append("and operationSubjectId = ").append(operationSubjectId);status = 'start_using'
        }*/

        sbSql.append("   AND status = '" + StatusEnum.START_USING.getValue() + "' ");

        if (hasSubject)
        {
            if (operationSubjectId == null)
            {
                operationSubjectId = 0l;
            }
            sbSql.append("and operationSubjectId = ").append(operationSubjectId);
        }
        if (colName != null && !"".equals(colName) && entityId != null && !"".equals(entityId))
        {
            sbSql.append("and " + colName).append(" = ").append(entityId);
        }
        sbSql.append(StringUtils.getSortStr(findEntity));
        return sbSql.toString();
    }

    public String loadTreeData(DataFindEntity findEntity)
    {
        String tableName = null;
        String idCol = null;
        String codeCol = null;
        String nameCol = null;
        String type = findEntity.getType();
        Long operationSubjectId = findEntity.getOperationSubjectId();
        Long entityId = findEntity.getEntityId();
        boolean hasSubject = true;
        String parentCol = "parentId";
        switch (type)
        {
            case "organization":
                tableName = "org_organization";
                idCol = "orgId";
                codeCol = "internalNumber";
                nameCol = "orgName";
                parentCol = "parentOrgId";
                break;
            case "warehouse":
                tableName = "stock_warehouse";
                //仓库的code使用ID字段
                idCol = "warehouseId";
                codeCol = "warehouseId";
                nameCol = "warehouseName";
                parentCol = "parentWarehouseId";
                break;
            case "region":
                tableName = "sys_region";
                idCol = "regionId";
                codeCol = "areaCode";
                nameCol = "areaName";
                hasSubject = false;
                break;

            case "device_type":
                tableName = "part_spare_part_type";
                parentCol = "parentPartId";
                //备件类型的code使用ID字段
                idCol = "sparePartTypeId";
                codeCol = "sparePartTypeId";
                nameCol = "categoryName";
                break;

            default:
                break;
        }

        StringBuilder sbSql = new StringBuilder();
        sbSql.append("   SELECT " + parentCol + " as parentId, ").append(idCol).append(" as id, ");
        sbSql.append(codeCol).append(" as code, ");
        sbSql.append(nameCol).append(" as text ");
        sbSql.append("   FROM ").append(tableName);
        sbSql.append("   WHERE status = '" + StatusEnum.START_USING.getValue() + "' ");
        if (hasSubject)
        {
            sbSql.append(" and operationSubjectId = ").append(operationSubjectId);
        }
        if (ObjectUtils.isNotEmpty(findEntity.getRange()))
        {
            if (RangeRoleTypeEnum.WAREHOUSE.getValue().equals(findEntity.getRange()))
            {
                sbSql.append(" and warehouseId in ").append("(" +
                        " SELECT rrd.id FROM sys_range_role rr " +
                        "  JOIN sys_range_role_detail rrd " +
                        "    ON rr.rangeRoleId = rrd.rangeRoleId " +
                        " WHERE rr.rangeType = 'warehouse' " +
                        "  AND rr.userId =  #{entityId}" +
                        ")");
            }
            if (RangeRoleTypeEnum.WORK_SECTION.getValue().equals(findEntity.getRange()))
            {
                sbSql.append(" and workSectionId in ").append("(" +
                        " SELECT rrd.id FROM sys_range_role rr " +
                        "  JOIN sys_range_role_detail rrd " +
                        "    ON rr.rangeRoleId = rrd.rangeRoleId " +
                        " WHERE rr.rangeType = '"+RangeRoleTypeEnum.WORK_SECTION.getValue()+"' " +
                        "  AND rr.userId = #{entityId} " +
                        ")");
            }
        }
        sbSql.append(" order by " + parentCol);
        return sbSql.toString();
    }

    public String loadDataList(DataFindEntity findEntity)
    {
        String tableName = null;
        String idCol = null;
        String codeCol = null;
        String nameCol = null;
        String type = findEntity.getType();
        Long operationSubjectId = findEntity.getOperationSubjectId();
        boolean hasSubject = true;
        String parentCol = "parentId";
        switch (type)
        {
            case "organization":
                tableName = "org_organization";
                idCol = "orgId";
                codeCol = "internalNumber";
                nameCol = "orgName";
                parentCol = "parentOrgId";
                break;
            case "warehouse":
                tableName = "stock_warehouse";
                //仓库的code使用ID字段
                idCol = "warehouseId";
                codeCol = "warehouseId";
                nameCol = "warehouseName";
                parentCol = "parentWarehouseId";
                break;
            case "region":
                tableName = "sys_region";
                idCol = "regionId";
                codeCol = "areaCode";
                nameCol = "areaName";
                hasSubject = false;
                break;

            case "device_type":
                tableName = "part_spare_part_type";
                parentCol = "parentPartId";
                //备件类型的code使用ID字段
                idCol = "sparePartTypeId";
                codeCol = "sparePartTypeId";
                nameCol = "categoryName";
                break;

            default:
                break;
        }

        StringBuilder sbSql = new StringBuilder();
        sbSql.append("   SELECT " + parentCol + " as parentId, ").append(idCol).append(" as id, ");
        sbSql.append(codeCol).append(" as code, ");
        sbSql.append(nameCol).append(" as text ");
        sbSql.append("   FROM ").append(tableName);
        sbSql.append("   WHERE status = '" + StatusEnum.START_USING.getValue() + "' ");
        if (hasSubject)
        {
            sbSql.append(" and operationSubjectId = ").append(operationSubjectId);
        }
        sbSql.append(" order by " + parentCol);
        return sbSql.toString();
    }

    public String loadDict(DataDictFindEntity findEntity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append("   SELECT dataDictionaryId id,dataLabel,code,name,status ");
        sbSql.append("   FROM sys_data_dictionary ");
        sbSql.append("   WHERE 1 = 1 ");
        String code = findEntity.getCode();
        String name = findEntity.getName();
        String status = findEntity.getStatus();
        String dataLabel = findEntity.getDataLabel();

        if (code != null && !"".equals(code))
        {
            sbSql.append(" and code = #{code} ");
        }
        if (name != null && !"".equals(name))
        {
            sbSql.append(" and name LIKE concat('%', #{name}, '%') ");
        }
        if (dataLabel != null && !"".equals(dataLabel))
        {
            sbSql.append(" and dataLabel = #{dataLabel} ");
        }
        if (status != null && !"".equals(status))
        {
            sbSql.append(" and status = #{status} ");
        }
        return sbSql.toString();
    }


    public String findLeafs(DataFindEntity findEntity)
    {
        String sql = null;
        String type = findEntity.getType();
        switch (type)
        {
            case "warehouse":
                sql =
                        " SELECT wh.warehouseId as id, wh.warehouseId as code, "
                                + " wh.warehouseName as text, wh.parentWarehouseId as parentId "
                                + " FROM stock_warehouse wh " +
                                " WHERE wh.warehouseId =  #{entityId} order by wh.warehouseId asc ";
                break;
            default:
                break;
        }
        return sql;
    }

    public String getCountByFindEntity(ValidateIsRepeatFindEntity findEntity)
    {
        String sbStr = "SELECT count(*) FROM " + findEntity.getTableName() + " WHERE  " + findEntity.getKeyName()
                + " <> #{keyValue} AND " + findEntity.getValidateName() + " = #{validateValue}";
        if (findEntity.getIsHaveSubject() == true)
        {
            sbStr += " AND operationSubjectId = #{operationSubjectId} ";
        }
        return sbStr;
    }

}
