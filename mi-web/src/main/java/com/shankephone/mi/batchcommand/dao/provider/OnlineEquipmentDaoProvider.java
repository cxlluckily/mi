package com.shankephone.mi.batchcommand.dao.provider;

import com.shankephone.mi.batchcommand.formbean.DeviceNoRegistFindEntity;
import com.shankephone.mi.batchcommand.formbean.GetOnlineEquipmentListFindEntity;
import com.shankephone.mi.common.enumeration.StatusEnum;
import com.shankephone.mi.common.model.BaseFindEntity;
import com.shankephone.mi.model.OperationsEquipmentEntity;
import com.shankephone.mi.model.OrgLineEntity;
import com.shankephone.mi.util.StringUtils;

/**
 *
 * @author 赵亮
 * @date 2018-10-23 9:50
 */
public class OnlineEquipmentDaoProvider
{
    public String getOnlineEquipmentList(GetOnlineEquipmentListFindEntity findEntity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT ");
        sbSql.append("     operations_equipment.equipmentId, ");
        sbSql.append("     org_line.lineName, ");
        sbSql.append("     org_station.stationName, ");
        sbSql.append("     part_spare_part.partName, ");
        sbSql.append("     part_spare_part_type.categoryName, ");
        sbSql.append("     operations_equipment.qrCode, ");
        sbSql.append("     operations_equipment.equipmentNo, ");
        sbSql.append("     operations_equipment.status, ");
        sbSql.append("     operations_equipment.deviceStatus, ");
        sbSql.append("     operations_equipment.location, ");
        sbSql.append("     operations_equipment.deviceuId, ");
        sbSql.append("     operations_equipment.deviceCode, ");
        sbSql.append("     operations_equipment.stationCode ");
        sbSql.append("   FROM operations_equipment ");
        sbSql.append("     INNER JOIN part_spare_part ");
        sbSql.append("       ON operations_equipment.sparePartId = part_spare_part.sparePartId ");
        sbSql.append("     INNER JOIN part_spare_part_type ");
        sbSql.append("       ON part_spare_part_type.sparePartTypeId = part_spare_part.sparePartTypeId ");
        sbSql.append("     INNER JOIN org_line ");
        sbSql.append("       ON org_line.lineId = operations_equipment.lineId ");
        sbSql.append("     INNER JOIN org_station ");
        sbSql.append("       ON org_station.stationId = operations_equipment.stationId ");
        sbSql.append(getOnlineEquipmentListFindSql(findEntity));
        sbSql.append("ORDER BY org_line.lineName, org_station.stationName, part_spare_part_type.categoryName, part_spare_part.partName,operations_equipment.deviceuId");
        sbSql.append("   LIMIT #{start}, #{limit} ");
        return sbSql.toString();

    }

    public String getOnlineEquipmentListTotal(GetOnlineEquipmentListFindEntity findEntity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT count(1) count");
        sbSql.append("   FROM operations_equipment ");
        sbSql.append("     INNER JOIN part_spare_part ");
        sbSql.append("       ON operations_equipment.sparePartId = part_spare_part.sparePartId ");
        sbSql.append("     INNER JOIN part_spare_part_type ");
        sbSql.append("       ON part_spare_part_type.sparePartTypeId = part_spare_part.sparePartTypeId ");
        sbSql.append("     INNER JOIN org_line ");
        sbSql.append("       ON org_line.lineId = operations_equipment.lineId ");
        sbSql.append("     INNER JOIN org_station ");
        sbSql.append("       ON org_station.stationId = operations_equipment.stationId ");
        sbSql.append(getOnlineEquipmentListFindSql(findEntity));
        sbSql.append("ORDER BY org_line.lineName, org_station.stationName, part_spare_part_type.categoryName, part_spare_part.partName,operations_equipment.deviceuId");
        return sbSql.toString();

    }

    private String getOnlineEquipmentListFindSql(GetOnlineEquipmentListFindEntity findEntity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append("   WHERE 1 = 1 ");
        if (StringUtils.isNotEmpty(findEntity.getDeviceStatus())&&!findEntity.getDeviceStatus().equals("-1"))
        {
            sbSql.append("         AND operations_equipment.deviceStatus = #{deviceStatus} ");
        }
        if (StringUtils.isNotEmpty(findEntity.getDeviceuId()))
        {
            sbSql.append("         AND operations_equipment.deviceuId LIKE concat('%', #{deviceuId}, '%') ");
        }
        if (StringUtils.isNotEmpty(findEntity.getLineId())&&findEntity.getLineId() != -1)
        {
            sbSql.append("         AND operations_equipment.lineId = #{lineId} ");
        }
        if (StringUtils.isNotEmpty(findEntity.getStationId())&&findEntity.getStationId() != -1)
        {
            sbSql.append("         AND operations_equipment.stationId = #{stationId} ");
        }
        if (StringUtils.isNotEmpty(findEntity.getStationCode()))
        {
            sbSql.append("         AND org_station.stationCode = #{stationCode} ");
        }
        if (StringUtils.isNotEmpty(findEntity.getPartName()))
        {
            sbSql.append("         AND part_spare_part.partName LIKE concat('%', #{partName}, '%') ");
        }
        if (StringUtils.isNotEmpty(findEntity.getSparePartTypeId())&&findEntity.getSparePartTypeId() != -1)
        {
            sbSql.append("         AND part_spare_part_type.sparePartTypeId = #{sparePartTypeId} ");
        }
        if(StringUtils.isEmpty(findEntity.getRegistType()))
        {
            sbSql.append(" AND ifnull(operations_equipment.deviceuId,'')<>'' ");
        }
        else
        {
            sbSql.append(" AND ifnull(operations_equipment.deviceuId,'')='' ");
        }
        return sbSql.toString();
    }

    public String getAllLine(BaseFindEntity findEntity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT ");
        sbSql.append("     lineId, ");
        sbSql.append("     lineName ");
        sbSql.append("   FROM org_line ");
        sbSql.append("   WHERE status = '" + StatusEnum.START_USING.getValue() + "' ");
        sbSql.append("   AND operationSubjectId = #{operationSubjectId}");
        sbSql.append("   ORDER BY lineCode DESC ");
        return sbSql.toString();

    }

    public String getStationListByLineId(OrgLineEntity entity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT ");
        sbSql.append("     stationId, ");
        sbSql.append("     stationName ");
        sbSql.append("   FROM org_station ");
        sbSql.append("     INNER JOIN org_line ");
        sbSql.append("       ON org_line.lineId = org_station.lineId ");
        sbSql.append("   WHERE 1 = 1 ");
        sbSql.append("         AND org_station.status = '" + StatusEnum.START_USING.getValue() + "' ");
        if (entity.getLineId() != -1)
        {
            sbSql.append("         AND org_station.lineId = #{lineId} ");
        }
        sbSql.append("         AND org_line.operationSubjectId = #{operationSubjectId} ");
        sbSql.append("   ORDER BY org_station.stationCode DESC ");

        return sbSql.toString();

    }

    public String  getDeviceNoRegistList(DeviceNoRegistFindEntity findEntity)
    {
        //TODO 需要确定是否需要运营主体
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT * ");
        sbSql.append("  FROM device_no_regist ");
        sbSql.append(getDeviceNoRegistListFindSql(findEntity));
        sbSql.append("   LIMIT #{start}, #{limit} ");

        return sbSql.toString();
    }
    public String  getDeviceNoRegistListTotal(DeviceNoRegistFindEntity findEntity)
    {
        //TODO 需要确定是否需要运营主体
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT  count(1) count ");
        sbSql.append("  FROM device_no_regist ");
        sbSql.append(getDeviceNoRegistListFindSql(findEntity));

        return sbSql.toString();
    }
    private String getDeviceNoRegistListFindSql(DeviceNoRegistFindEntity findEntity)
    {
        //TODO 需要确定是否需要运营主体
        StringBuilder sbSql = new StringBuilder();
        sbSql.append("   WHERE 1 = 1 ");
        if (StringUtils.isNotEmpty(findEntity.getDeviceCode()))
        {
            sbSql.append("         AND deviceCode  LIKE concat('%', #{deviceCode}, '%') ");
        }
        if (StringUtils.isNotEmpty(findEntity.getDeviceuId()))
        {
            sbSql.append("         AND deviceuId  LIKE concat('%', #{deviceuId}, '%') ");
        }
        if (StringUtils.isNotEmpty(findEntity.getStationCode()))
        {
            sbSql.append("         AND stationCode  LIKE concat('%', #{stationCode}, '%') ");
        }
        return sbSql.toString();
    }

    public String updateOperationsEquipment(OperationsEquipmentEntity entity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" UPDATE ");
        sbSql.append("   operations_equipment ");
        sbSql.append(" SET ");
        sbSql.append("   deviceuId = #{deviceuId}, ");
        sbSql.append("   deviceCode = #{deviceCode}, ");
        sbSql.append("   stationCode = #{stationCode} ");
        //sbSql.append("   ,deviceStatus = #{deviceStatus} ");
        sbSql.append(" WHERE ");
        sbSql.append("   equipmentId = #{equipmentId} ");
        return sbSql.toString();

    }

    public String deleteDeviceNoRegist(String deviceuId)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" DELETE FROM  ");
        sbSql.append("   device_no_regist ");

        sbSql.append(" WHERE ");
        sbSql.append("   deviceuId = #{deviceuId} ");
        return sbSql.toString();

    }
}
