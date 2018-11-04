package com.shankephone.mi.devicestatuschange.dao.provider;

import com.shankephone.mi.model.DeviceStatusMonitorLogsEntity;
import com.shankephone.mi.model.OperationsEquipmentEntity;

/**
 * @author 赵亮
 * @date 2018-10-17 15:05
 */
public class DeviceStatusChangeDaoProvider
{
    public String insertDeviceStatusMonitorLogsEntity(DeviceStatusMonitorLogsEntity entity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" INSERT INTO ");
        sbSql.append("   device_status_monitor_logs ");
        sbSql.append("   ( ");
        sbSql.append("      equipmentId, ");
        sbSql.append("      receiveJson, ");
        sbSql.append("      receiveTime ");
        sbSql.append("   ) ");
        sbSql.append(" VALUES ");
        sbSql.append("   ( ");
        sbSql.append("      #{equipmentId}, ");
        sbSql.append("      #{receiveJson}, ");
        sbSql.append("      #{receiveTime}, ");
        sbSql.append("   ) ");

        return sbSql.toString();
    }

    public String updateOperationsEquipmentStatus(OperationsEquipmentEntity entity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" UPDATE operations_equipment ");
        sbSql.append("   SET deviceStatus = #{deviceStatus} ");
        sbSql.append("   WHERE equipmentId = #{equipmentId} ");

        return sbSql.toString();
    }
}
