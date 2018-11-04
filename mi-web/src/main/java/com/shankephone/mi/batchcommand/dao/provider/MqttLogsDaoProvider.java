package com.shankephone.mi.batchcommand.dao.provider;

import com.shankephone.mi.batchcommand.formbean.GetExecuteCommandBatchListFindEntity;
import com.shankephone.mi.batchcommand.formbean.GetMqllLogsFindEntity;
import com.shankephone.mi.batchcommand.formbean.GetMqttLogsDeviceListFindEntity;
import com.shankephone.mi.batchcommand.formbean.GetOneEquipmentMqttLogsListFindEntity;
import com.shankephone.mi.model.DeviceMqttCommandBatchEntity;
import com.shankephone.mi.model.DeviceMqttCommandLogsEntity;
import com.shankephone.mi.util.StringUtils;

/**
 * @author 赵亮
 * @date 2018-10-22 10:42
 */
public class MqttLogsDaoProvider
{
    public String getMqttLogsList(GetMqllLogsFindEntity findEntity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT ");
        sbSql.append("     mqttCommandBatchId, ");
        sbSql.append("     batchNo, ");
        sbSql.append("     commandType, ");
        sbSql.append("     commandCategory, ");
        sbSql.append("     createTime, ");
        sbSql.append("     createUser ");
        sbSql.append("   FROM device_mqtt_command_batch ");
        sbSql.append(getMqttLogsListFindSql(findEntity));
        sbSql.append("     order by createTime desc ");
        sbSql.append("   LIMIT #{start}, #{limit} ");

        return sbSql.toString();

    }

    public String getMqttLogsListTotal(GetMqllLogsFindEntity findEntity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT  count(1) count ");
        sbSql.append("   FROM device_mqtt_command_batch ");
        sbSql.append(getMqttLogsListFindSql(findEntity));
        return sbSql.toString();
    }

    private String getMqttLogsListFindSql(GetMqllLogsFindEntity findEntity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append("   WHERE createTime BETWEEN #{beginTime} AND #{endTime} ");
        if (StringUtils.isNotEmpty(findEntity.getCommandType())&&!"-1".equals(findEntity.getCommandType()))
        {
            sbSql.append("         AND commandType LIKE concat('%',#{commandType}, '%') ");
        }
        if (StringUtils.isNotEmpty(findEntity.getCreateUser()))
        {
            sbSql.append("         AND createUser LIKE concat('%',#{createUser}, '%') ");
        }
        if (StringUtils.isNotEmpty(findEntity.getCommandCategory()))
        {
            sbSql.append("         AND commandCategory LIKE concat('%',#{commandCategory}, '%') ");
        }
        return sbSql.toString();
    }

    public String getMqttLogsDeviceList(GetMqttLogsDeviceListFindEntity findEntity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT ");
        sbSql.append("   device_mqtt_command_logs.mqttCommandLogId, ");
        sbSql.append("   device_mqtt_command_logs.equipmentId, ");
        sbSql.append("   device_mqtt_command_logs.commandType, ");
        sbSql.append("   device_mqtt_command_logs.commandCategory, ");
        sbSql.append("   device_mqtt_command_logs.executeStatus, ");
        sbSql.append("   device_mqtt_command_logs.createTime, ");
        sbSql.append("   device_mqtt_command_logs.responseTime, ");
        sbSql.append("   device_mqtt_command_logs.finishTime, ");
        sbSql.append("   operations_equipment.deviceuId, ");
        sbSql.append("   operations_equipment.deviceCode, ");
        sbSql.append("   operations_equipment.stationCode, ");
        sbSql.append("   part_spare_part.partName, ");
        sbSql.append("   part_spare_part_type.categoryName ");
        sbSql.append(" FROM ");
        sbSql.append("   device_mqtt_command_logs ");
        sbSql.append("   INNER JOIN operations_equipment ");
        sbSql.append("     ON device_mqtt_command_logs.equipmentId = operations_equipment.equipmentId ");
        sbSql.append("   INNER JOIN part_spare_part ");
        sbSql.append("     ON operations_equipment.sparePartId = part_spare_part.sparePartId ");
        sbSql.append("   INNER JOIN part_spare_part_type ");
        sbSql.append("     ON part_spare_part_type.sparePartTypeId = part_spare_part.sparePartTypeId ");
        sbSql.append(getMqttLogsDeviceListFindSql(findEntity));
        sbSql.append("   LIMIT #{start}, #{limit} ");
        return sbSql.toString();

    }

    public String getMqttLogsDeviceListTotal(GetMqttLogsDeviceListFindEntity findEntity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT count(1) count ");
        sbSql.append(" FROM ");
        sbSql.append("   device_mqtt_command_logs ");
        sbSql.append("   INNER JOIN operations_equipment ");
        sbSql.append("     ON device_mqtt_command_logs.equipmentId = operations_equipment.equipmentId ");
        sbSql.append("   INNER JOIN part_spare_part ");
        sbSql.append("     ON operations_equipment.sparePartId = part_spare_part.sparePartId ");
        sbSql.append("   INNER JOIN part_spare_part_type ");
        sbSql.append("     ON part_spare_part_type.sparePartTypeId = part_spare_part.sparePartTypeId ");
        sbSql.append(getMqttLogsDeviceListFindSql(findEntity));
        return sbSql.toString();
    }

    private String getMqttLogsDeviceListFindSql(GetMqttLogsDeviceListFindEntity findEntity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" WHERE 1 = 1 ");
        if(findEntity.getLineId()!=-1)
        {
            sbSql.append("       AND operations_equipment.lineId =#{lineId}");
        }
        if(findEntity.getStationId()!=-1)
        {
            sbSql.append("       AND operations_equipment.stationId =#{stationId} ");
        }
        if(!findEntity.getExecuteStatus().equals("-1"))
        {
            sbSql.append("       AND device_mqtt_command_logs.executeStatus =#{executeStatus} ");
        }
        sbSql.append("       AND device_mqtt_command_logs.mqttCommandBatchId =#{mqttCommandBatchId} ");
        sbSql.append("       AND part_spare_part.partName LIKE concat('%', #{partName}, '%') ");
        sbSql.append("       AND operations_equipment.deviceuId LIKE concat('%', #{deviceuId}, '%') ");
        sbSql.append("       AND part_spare_part_type.categoryName LIKE concat('%', #{categoryName}, '%') ");
        return sbSql.toString();
    }

    public String getMqttLogsDeviceListDetail(DeviceMqttCommandLogsEntity entity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT * ");
        sbSql.append("   FROM device_mqtt_command_logs ");
        sbSql.append("   WHERE mqttCommandLogId = #{mqttCommandLogId} ");

        return sbSql.toString();

    }

    public String getOneEquipmentMqttLogsList(GetOneEquipmentMqttLogsListFindEntity findEntity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT ");
        sbSql.append("     mqttCommandLogId, ");
        sbSql.append("     equipmentId, ");
        sbSql.append("     commandType, ");
        sbSql.append("     commandCategory, ");
        sbSql.append("     executeStatus, ");
        sbSql.append("     createTime, ");
        sbSql.append("     responseTime, ");
        sbSql.append("     finishTime, ");
        sbSql.append("  (select batchNo from  device_mqtt_command_batch where mqttCommandBatchId=device_mqtt_command_logs.mqttCommandBatchId limit 1 ) batchNo");
        sbSql.append("   FROM ");
        sbSql.append("     device_mqtt_command_logs ");
        sbSql.append(getOneEquipmentMqttLogsListFindEntity(findEntity));
        sbSql.append(" ORDER BY createTime DESC ");
        sbSql.append("   LIMIT #{start}, #{limit} ");
        return sbSql.toString();

    }

    public String getOneEquipmentMqttLogsListTotal(GetOneEquipmentMqttLogsListFindEntity findEntity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT count(1) count");
        sbSql.append("   FROM ");
        sbSql.append("     device_mqtt_command_logs ");
        sbSql.append(getOneEquipmentMqttLogsListFindEntity(findEntity));

        return sbSql.toString();

    }

    private String getOneEquipmentMqttLogsListFindEntity(GetOneEquipmentMqttLogsListFindEntity findEntity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append("   WHERE 1 = 1 ");
        if(!findEntity.getCommandType().equals("-1")) {
            sbSql.append("         AND commandType LIKE concat('%', #{commandType}, '%') ");
        }
        sbSql.append("         AND commandCategory LIKE concat('%', #{commandCategory}, '%') ");
        if(!findEntity.getExecuteStatus().equals("-1")) {
            sbSql.append("         AND executeStatus = #{executeStatus} ");
        }
        sbSql.append("         AND equipmentId = #{equipmentId} ");

        if(StringUtils.isNotEmpty(findEntity.getSendBeginTime())&&StringUtils.isNotEmpty(findEntity.getSendEndTime())) {
            sbSql.append("         AND createTime BETWEEN #{sendBeginTime} AND #{sendEndTime} ");
        }
        if(StringUtils.isNotEmpty(findEntity.getReceiveBeginTime())&&StringUtils.isNotEmpty(findEntity.getReceiveEndTime())) {

            sbSql.append("         AND responseTime BETWEEN #{receiveBeginTime} AND #{receiveEndTime} ");
        }
        if(StringUtils.isNotEmpty(findEntity.getResultBeginTime())&&StringUtils.isNotEmpty(findEntity.getResultEndTime())) {

            sbSql.append("         AND finishTime BETWEEN #{resultBeginTime} AND #{resultEndTime} ");
        }
        return sbSql.toString();
    }

    public String getDeviceMqttCommandBatchDetail(DeviceMqttCommandBatchEntity entity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT * ");
        sbSql.append("   FROM device_mqtt_command_batch ");
        sbSql.append("   WHERE mqttCommandBatchId = #{mqttCommandBatchId} ");
        sbSql.append("   LIMIT 1 ");

        return sbSql.toString();

    }

    public String getExecuteCommandBatchList(GetExecuteCommandBatchListFindEntity findEntity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT ");
        sbSql.append("   mainTB.mqttCommandBatchId, ");
        sbSql.append("   createTime, ");
        sbSql.append("   batchNo, ");
        sbSql.append("   ifnull(sendCount, 0) AS sendCount, ");
        sbSql.append("   ifnull(receiveCount, 0) AS receiveCount, ");
        sbSql.append("   ifnull(resultCount, 0) AS resultCount ");
        sbSql.append(" FROM ( ");
        sbSql.append("        SELECT ");
        sbSql.append("          device_mqtt_command_batch.batchNo, ");
        sbSql.append("          device_mqtt_command_batch.createTime, ");
        sbSql.append("          device_mqtt_command_batch.mqttCommandBatchId ");
        sbSql.append("        FROM device_mqtt_command_batch ");
        sbSql.append("          WHERE device_mqtt_command_batch.mqttCommandBatchId IN (" + StringUtils.listToString(findEntity.getMqttCommandBatchIds()) + ") ");
        sbSql.append("      ) AS mainTB ");
        sbSql.append("   LEFT JOIN ");
        sbSql.append("   ( ");
        sbSql.append("     SELECT ");
        sbSql.append("       mqttCommandBatchId, ");
        sbSql.append("       count(1) AS sendCount ");
        sbSql.append("     FROM device_mqtt_command_logs ");
        sbSql.append("     WHERE mqttCommandBatchId IN (" + StringUtils.listToString(findEntity.getMqttCommandBatchIds()) + ") ");
        sbSql.append("           AND sendJson IS NOT NULL GROUP BY mqttCommandBatchId");
        sbSql.append("   ) AS sendTB ");
        sbSql.append("     ON mainTB.mqttCommandBatchId = sendTB.mqttCommandBatchId ");
        sbSql.append("   LEFT JOIN ");
        sbSql.append("   ( ");
        sbSql.append("     SELECT ");
        sbSql.append("       mqttCommandBatchId, ");
        sbSql.append("       count(1) AS receiveCount ");
        sbSql.append("     FROM device_mqtt_command_logs ");
        sbSql.append("     WHERE mqttCommandBatchId IN (" + StringUtils.listToString(findEntity.getMqttCommandBatchIds()) + ") ");
        sbSql.append("           AND receiveJson IS NOT NULL GROUP BY mqttCommandBatchId");
        sbSql.append("   ) AS receiveTB ");
        sbSql.append("     ON mainTB.mqttCommandBatchId = receiveTB.mqttCommandBatchId ");
        sbSql.append("   LEFT JOIN ");
        sbSql.append("   ( ");
        sbSql.append("     SELECT ");
        sbSql.append("       mqttCommandBatchId, ");
        sbSql.append("       count(1) AS resultCount ");
        sbSql.append("     FROM device_mqtt_command_logs ");
        sbSql.append("     WHERE mqttCommandBatchId IN (" + StringUtils.listToString(findEntity.getMqttCommandBatchIds()) + ") ");
        sbSql.append("           AND resultJson IS NOT NULL GROUP BY mqttCommandBatchId");
        sbSql.append("   ) AS resultTB ");
        sbSql.append("     ON mainTB.mqttCommandBatchId = resultTB.mqttCommandBatchId ");
        sbSql.append("    ORDER BY   mainTB.createTime DESC ");

        return sbSql.toString();
    }
}
