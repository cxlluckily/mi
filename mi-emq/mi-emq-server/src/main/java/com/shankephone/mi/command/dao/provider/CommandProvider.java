package com.shankephone.mi.command.dao.provider;

import com.shankephone.mi.command.enumeration.ExecuteStatusEnum;
import com.shankephone.mi.model.DeviceMqttCommandLogsEntity;

/**
 * @author 郝伟州
 * @date 2018年10月16日11:15:05
 */
public class CommandProvider
{


    /**
     * 更新设备调用命令日志表状态
     *
     * @author：赵亮
     * @date：2018-10-19 13:38
     */
    public String updateMqttCommandLogStatus(DeviceMqttCommandLogsEntity entity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" UPDATE device_mqtt_command_logs ");
        sbSql.append("   SET executeStatus = '" + ExecuteStatusEnum.EXECUTING.getCode() + "', ");
        sbSql.append("       receiveJson = #{receiveJson},");
        sbSql.append("       responseTime = #{responseTime}");
        sbSql.append("   WHERE mqttCommandLogId = #{mqttCommandLogId} ");

        return sbSql.toString();

    }

    public String updateMqttCommandLogStatusAndResultJson(DeviceMqttCommandLogsEntity entity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" UPDATE device_mqtt_command_logs ");
        sbSql.append("   SET executeStatus = #{executeStatus}  ");
        sbSql.append("       ,resultJson = #{resultJson}  ");
        sbSql.append("       ,finishTime = #{finishTime}  ");
        sbSql.append("   WHERE mqttCommandLogId = #{mqttCommandLogId} ");

        return sbSql.toString();

    }

    public String updateMqttCommandLogStatusAndSendJson(DeviceMqttCommandLogsEntity entity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" UPDATE device_mqtt_command_logs ");
        sbSql.append("   SET sendJson = #{sendJson}  ");
        sbSql.append("   WHERE mqttCommandLogId = #{mqttCommandLogId} ");

        return sbSql.toString();

    }


}
