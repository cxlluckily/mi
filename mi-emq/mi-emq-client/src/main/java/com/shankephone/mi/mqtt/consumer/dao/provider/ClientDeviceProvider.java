package com.shankephone.mi.mqtt.consumer.dao.provider;

import com.shankephone.mi.mqtt.model.ClientDeviceEntity;
import com.shankephone.mi.mqtt.model.ClientMqttLogsEntity;

/**
 * @author 赵亮
 * @date 2018-10-16 17:53
 */
public class ClientDeviceProvider
{
    public String insertClientDevice(ClientMqttLogsEntity entity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" INSERT INTO ");
        sbSql.append("   client_mqtt_logs ");
        sbSql.append("   ( ");
        sbSql.append("      clientMqttLogId, ");
        sbSql.append("      clientDeviceId, ");
        sbSql.append("      json, ");
        sbSql.append("      logType ");
        sbSql.append("   ) ");
        sbSql.append(" VALUES ");
        sbSql.append("   ( ");
        sbSql.append("      #{clientMqttLogId}, ");
        sbSql.append("      #{clientDeviceId}, ");
        sbSql.append("      #{json}, ");
        sbSql.append("      #{logType} ");
        sbSql.append("   ) ");
        return sbSql.toString();
    }

    public String getClientDeviceDetail(ClientDeviceEntity deviceEntity )
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT * ");
        sbSql.append("   FROM client_device ");
        sbSql.append("   WHERE deviceUid = #{deviceUid} ");

        return sbSql.toString();
    }

    public String getClientDeviceList()
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT * ");
        sbSql.append("   FROM client_device ");
        return sbSql.toString();
    }
}
