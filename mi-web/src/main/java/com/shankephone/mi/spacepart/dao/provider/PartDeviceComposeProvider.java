package com.shankephone.mi.spacepart.dao.provider;

import com.shankephone.mi.model.PartDeviceComposeEntity;
import com.shankephone.mi.spacepart.formbean.ComposeListFindEntity;
import com.shankephone.mi.spacepart.formbean.OneTreeDataFindEntity;

/**
 * 设备组成
 *
 * @author 赵亮
 * @date 2018-08-13 11:07
 */
public class PartDeviceComposeProvider
{
    public String insertOne(PartDeviceComposeEntity entity)
    {

        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" INSERT INTO ");
        sbSql.append("   part_device_compose ");
        sbSql.append("   ( ");
        sbSql.append("      sparePartId, ");
        sbSql.append("      brand, ");
        sbSql.append("      name, ");
        sbSql.append("      composePid, ");
        sbSql.append("      composeModel, ");
        sbSql.append("      createUser, ");
        sbSql.append("      modifyUser, ");
        sbSql.append("      status, ");
        sbSql.append("      sparePartTypeId, ");
        sbSql.append("      composePids ");
        sbSql.append("   ) ");
        sbSql.append(" VALUES ");
        sbSql.append("   ( ");
        sbSql.append("      #{sparePartId}, ");
        sbSql.append("      #{brand}, ");
        sbSql.append("      #{name}, ");
        sbSql.append("      #{composePid}, ");
        sbSql.append("      #{composeModel}, ");
        sbSql.append("      #{operationUserName}, ");
        sbSql.append("      #{operationUserName}, ");
        sbSql.append("      #{status}, ");
        sbSql.append("      #{sparePartTypeId}, ");
        sbSql.append("      #{composePids} ");
        sbSql.append("   ) ");
        return sbSql.toString();

    }

    public String updateOne(PartDeviceComposeEntity entity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" UPDATE ");
        sbSql.append("   part_device_compose ");
        sbSql.append(" SET ");
        sbSql.append("   sparePartId = #{sparePartId}, ");
        sbSql.append("   composePids = #{composePids}, ");
        sbSql.append("   sparePartTypeId = #{sparePartTypeId}, ");
        sbSql.append("   brand = #{brand}, ");
        sbSql.append("   name = #{name}, ");
        sbSql.append("   composePid = #{composePid}, ");
        sbSql.append("   composeModel = #{composeModel}, ");
        sbSql.append("   modifyUser = #{operationUserName} ");
        sbSql.append(" WHERE ");
        sbSql.append("   deviceComposeId = #{deviceComposeId} ");
        return sbSql.toString();
    }

    public String getComposeList(ComposeListFindEntity findEntity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT ");
        sbSql.append("     part_device_compose.*, ");
        sbSql.append("     partName, ");
        sbSql.append("     categoryName, ");
        sbSql.append("     DATE_FORMAT(part_device_compose.createTime, '%Y-%m-%d') AS insertDate ");
        sbSql.append("   FROM part_device_compose ");
        sbSql.append("     INNER JOIN part_spare_part ");
        sbSql.append("       ON part_device_compose.sparePartId = part_spare_part.sparePartId ");
        sbSql.append("     INNER JOIN part_spare_part_type ");
        sbSql.append("       ON part_spare_part_type.sparePartTypeId = part_spare_part.sparePartTypeId ");
        sbSql.append("   WHERE part_spare_part_type.operationSubjectId = #{operationSubjectId} ");
        sbSql.append("         AND ifnull(partName,'') LIKE concat('%', #{partName}, '%') ");
        sbSql.append("         AND ifnull(categoryName,'') LIKE concat('%', #{categoryName}, '%') ");
        sbSql.append("         AND ifnull(part_device_compose.name,'') LIKE concat('%', #{name}, '%') ");
        sbSql.append("         AND part_device_compose.composePid = -1 ");
        if (!"all".equals(findEntity.getStatus()))
        {
            sbSql.append("         AND part_device_compose.status = #{status} ");
        }
        sbSql.append(" ORDER BY partName,categoryName DESC ");
        sbSql.append(" LIMIT #{start}, #{limit} ");
        return sbSql.toString();

    }

    public String getComposeListCount(ComposeListFindEntity findEntity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT count(*) AS totalCount  ");
        sbSql.append("   FROM part_device_compose ");
        sbSql.append("     INNER JOIN part_spare_part ");
        sbSql.append("       ON part_device_compose.sparePartId = part_spare_part.sparePartId ");
        sbSql.append("     INNER JOIN part_spare_part_type ");
        sbSql.append("       ON part_spare_part_type.sparePartTypeId = part_spare_part.sparePartTypeId ");
        sbSql.append("   WHERE part_spare_part_type.operationSubjectId = #{operationSubjectId} ");
        sbSql.append("         AND ifnull(partName,'') LIKE concat('%', #{partName}, '%') ");
        sbSql.append("         AND ifnull(categoryName,'') LIKE concat('%', #{categoryName}, '%') ");
        sbSql.append("         AND ifnull(part_device_compose.name,'') LIKE concat('%', #{name}, '%') ");
        sbSql.append("         AND part_device_compose.composePid = -1 ");
        if (!"all".equals(findEntity.getStatus()))
        {
            sbSql.append("         AND part_device_compose.status = #{status} ");
        }
        return sbSql.toString();

    }

    public String getUserIDCount(PartDeviceComposeEntity entity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT count(1) AS total ");
        sbSql.append("   FROM operations_equipment ");
        sbSql.append("   WHERE deviceComposeId = #{deviceComposeId} ");

        return sbSql.toString();

    }

    public String deleteOne(PartDeviceComposeEntity entity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" DELETE FROM part_device_compose ");
        sbSql.append("   WHERE deviceComposeId = #{deviceComposeId} ");

        return sbSql.toString();
    }

    public String getOneTreeData(OneTreeDataFindEntity findEntity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT ");
        sbSql.append("     part_device_compose.*, ");
        sbSql.append("     partName, ");
        sbSql.append("     categoryName,parentPartId, ");
        sbSql.append("     DATE_FORMAT(part_device_compose.createTime, '%Y-%m-%d') AS insertDate ");
        sbSql.append("  FROM part_device_compose ");
        sbSql.append("    INNER JOIN part_spare_part ");
        sbSql.append("      ON part_device_compose.sparePartId = part_spare_part.sparePartId ");
        sbSql.append("    INNER JOIN part_spare_part_type ");
        sbSql.append("      ON part_spare_part_type.sparePartTypeId = part_spare_part.sparePartTypeId ");
        sbSql.append(" WHERE composePids LIKE concat(#{deviceComposeId},'%') ");

        return sbSql.toString();

    }

}
