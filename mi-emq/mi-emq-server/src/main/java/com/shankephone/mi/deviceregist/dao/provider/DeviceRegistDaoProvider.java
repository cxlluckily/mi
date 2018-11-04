package com.shankephone.mi.deviceregist.dao.provider;

import com.shankephone.mi.model.DeviceMqttRegistLogsEntity;
import com.shankephone.mi.model.DeviceNoRegistEntity;
import com.shankephone.mi.model.OperationsEquipmentEntity;
import com.shankephone.mi.model.OperationsEquipmentModifyLogEntity;

/**
 * @author 赵亮
 * @date 2018-10-17 10:44
 */
public class DeviceRegistDaoProvider
{
    public String getOperationsEquipmentDetail(String deviceuId)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append("   SELECT * ");
        sbSql.append("   FROM operations_equipment ");
        sbSql.append("   WHERE deviceuId = #{deviceuId} ");

        return sbSql.toString();
    }

    public String insertDeviceNoRegistEntity(DeviceNoRegistEntity entity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" INSERT INTO ");
        sbSql.append("   device_no_regist ");
        sbSql.append("   ( ");
        sbSql.append("      deviceuId, ");
        sbSql.append("      deviceCode, ");
        sbSql.append("      createTime, ");
        sbSql.append("      stationCode ");
        sbSql.append("   ) ");
        sbSql.append(" VALUES ");
        sbSql.append("   ( ");
        sbSql.append("      #{deviceuId}, ");
        sbSql.append("      #{deviceCode}, ");
        sbSql.append("      #{createTime}, ");
        sbSql.append("      #{stationCode} ");
        sbSql.append("   ) ");

        return sbSql.toString();
    }

    public String updateOperationsEquipmentStatus(OperationsEquipmentEntity entity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" UPDATE ");
        sbSql.append("   operations_equipment ");
        sbSql.append(" SET ");
        sbSql.append("   deviceuId = #{deviceuId}, ");
        sbSql.append("   deviceCode = #{deviceCode}, ");
        sbSql.append("   stationCode = #{stationCode}, ");
        sbSql.append("   deviceStatus = #{deviceStatus} ");
        sbSql.append(" WHERE ");
        sbSql.append("   equipmentId = #{equipmentId} ");
        return sbSql.toString();

    }

    public String insertDeviceMqttRegistLogsEntity(DeviceMqttRegistLogsEntity entity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" INSERT INTO ");
        sbSql.append("   device_mqtt_regist_logs ");
        sbSql.append("   ( ");
        sbSql.append("      deviceuId, ");
        sbSql.append("      sendJson, ");
        sbSql.append("      receiveJson, ");
        sbSql.append("      createUser, ");
        sbSql.append("      createTime, ");
        sbSql.append("      sendTime ");
        sbSql.append("   ) ");
        sbSql.append(" VALUES ");
        sbSql.append("   ( ");
        sbSql.append("      #{deviceuId}, ");
        sbSql.append("      #{sendJson}, ");
        sbSql.append("      #{receiveJson}, ");
        sbSql.append("      #{createUser}, ");
        sbSql.append("      #{createTime}, ");
        sbSql.append("      #{sendTime} ");
        sbSql.append("   ) ");

        return sbSql.toString();
    }

    public String insertOperationsEquipmentModifyLogEntity(OperationsEquipmentModifyLogEntity entity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" INSERT INTO ");
        sbSql.append("   operations_equipment_modify_log ");
        sbSql.append("   ( ");
        sbSql.append("      equipmentId, ");
        sbSql.append("      modifyAttribute, ");
        sbSql.append("      beforeValue, ");
        sbSql.append("      afterValue, ");
        sbSql.append("      modifyUser, ");
        sbSql.append("      modifyTime, ");
        sbSql.append("      remark ");
        sbSql.append("   ) ");
        sbSql.append(" VALUES ");
        sbSql.append("   ( ");
        sbSql.append("      #{equipmentId}, ");
        sbSql.append("      #{modifyAttribute}, ");
        sbSql.append("      #{beforeValue}, ");
        sbSql.append("      #{afterValue}, ");
        sbSql.append("      #{modifyUser}, ");
        sbSql.append("      #{modifyTime}, ");
        sbSql.append("      #{remark} ");
        sbSql.append("   ) ");

        return sbSql.toString();
    }

    public String getOneDeviceNoRegistEntity(String deviceuId)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT * ");
        sbSql.append(" FROM device_no_regist ");
        sbSql.append(" WHERE deviceuId = #{deviceuId} ");

        return sbSql.toString();

    }
}
