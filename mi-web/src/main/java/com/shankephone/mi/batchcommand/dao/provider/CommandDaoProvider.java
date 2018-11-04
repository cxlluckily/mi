package com.shankephone.mi.batchcommand.dao.provider;

import com.shankephone.mi.common.enumeration.StatusEnum;
import com.shankephone.mi.model.DeviceMqttCommandBatchEntity;
import com.shankephone.mi.model.DeviceMqttCommandLogsEntity;

/**
 *
 * @author  赵亮
 * @date 2018-10-22 14:26
 */
public class CommandDaoProvider
{
    public String getPreinstallCommandDDL()
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT ");
        sbSql.append("     preinstallCommandId, ");
        sbSql.append("     commandName, ");
        sbSql.append("     commandContext ");
        sbSql.append("   FROM device_preinstall_command ");
        sbSql.append("   WHERE status = '" + StatusEnum.START_USING.getValue() + "' ");
        sbSql.append("   ORDER BY sort ASC ");

        return sbSql.toString();
    }

    public String insertDeviceMqttCommandBatchEntity(DeviceMqttCommandBatchEntity entity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" INSERT INTO ");
        sbSql.append("   device_mqtt_command_batch ");
        sbSql.append("   ( ");
        sbSql.append("      batchNo, ");
        sbSql.append("      commandType, ");
        sbSql.append("      commandCategory, ");
        sbSql.append("      sendJson, ");
        sbSql.append("      createUser ");
        sbSql.append("   ) ");
        sbSql.append(" VALUES ");
        sbSql.append("   ( ");
        sbSql.append("      #{batchNo}, ");
        sbSql.append("      #{commandType}, ");
        sbSql.append("      #{commandCategory}, ");
        sbSql.append("      #{sendJson}, ");
        sbSql.append("      #{operationUserName} ");
        sbSql.append("   ) ");

        return sbSql.toString();
    }

    public String insertDeviceMqttCommandLogsEntity(DeviceMqttCommandLogsEntity entity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" INSERT INTO ");
        sbSql.append("   device_mqtt_command_logs ");
        sbSql.append("   ( ");
        sbSql.append("      equipmentId, ");
        sbSql.append("      mqttCommandBatchId, ");
        sbSql.append("      commandType, ");
        sbSql.append("      commandCategory, ");
        sbSql.append("      commandContent, ");
        sbSql.append("      sendJson, ");
        sbSql.append("      receiveJson, ");
        sbSql.append("      resultJson, ");
        sbSql.append("      executeStatus, ");
        sbSql.append("      createUser ");
        sbSql.append("   ) ");
        sbSql.append(" VALUES ");
        sbSql.append("   ( ");
        sbSql.append("      #{equipmentId}, ");
        sbSql.append("      #{mqttCommandBatchId}, ");
        sbSql.append("      #{commandType}, ");
        sbSql.append("      #{commandCategory}, ");
        sbSql.append("      #{commandContent}, ");
        sbSql.append("      #{sendJson}, ");
        sbSql.append("      #{receiveJson}, ");
        sbSql.append("      #{resultJson}, ");
        sbSql.append("      #{executeStatus}, ");
        sbSql.append("      #{operationUserName} ");
        sbSql.append("   ) ");
        return sbSql.toString();
    }

    public String updateDeviceMqttCommandLogsEntity(DeviceMqttCommandLogsEntity entity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" UPDATE ");
        sbSql.append("   device_mqtt_command_logs ");
        sbSql.append(" SET ");
        sbSql.append("   equipmentId = #{equipmentId}, ");
        sbSql.append("   mqttCommandBatchId = #{mqttCommandBatchId}, ");
        sbSql.append("   commandType = #{commandType}, ");
        sbSql.append("   commandCategory = #{commandCategory}, ");
        sbSql.append("   commandContent = #{commandContent}, ");
        sbSql.append("   sendJson = #{sendJson}, ");
        sbSql.append("   receiveJson = #{receiveJson}, ");
        sbSql.append("   resultJson = #{resultJson}, ");
        sbSql.append("   executeStatus = #{executeStatus}, ");
        sbSql.append("   responseTime = #{responseTime}, ");
        sbSql.append("   finishTime = #{finishTime} ");
        sbSql.append(" WHERE ");
        sbSql.append("   mqttCommandLogId = #{mqttCommandLogId} ");

        return sbSql.toString();
    }

    public String updateCommandBatchSendJosn(DeviceMqttCommandBatchEntity entity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" UPDATE device_mqtt_command_batch ");
        sbSql.append("   SET sendJson = #{sendJson}  ");
        sbSql.append("   WHERE mqttCommandBatchId = #{mqttCommandBatchId} ");

        return sbSql.toString();

    }
}
