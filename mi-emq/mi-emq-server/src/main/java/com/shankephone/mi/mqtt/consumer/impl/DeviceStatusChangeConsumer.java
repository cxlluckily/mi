package com.shankephone.mi.mqtt.consumer.impl;

import com.google.gson.JsonObject;
import com.shankephone.mi.deviceregist.dao.DeviceRegistDao;
import com.shankephone.mi.devicestatuschange.dao.DeviceStatusChangeDao;
import com.shankephone.mi.model.DeviceStatusMonitorLogsEntity;
import com.shankephone.mi.model.OperationsEquipmentEntity;
import com.shankephone.mi.model.OperationsEquipmentModifyLogEntity;
import com.shankephone.mi.mqtt.consumer.abs.ConsumerAbs;
import com.shankephone.mi.util.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author 赵亮
 * @date 2018-10-12 14:44
 */
@Component
public class DeviceStatusChangeConsumer extends ConsumerAbs
{
    @Autowired
    private DeviceRegistDao deviceRegistDao;
    @Autowired
    private DeviceStatusChangeDao deviceStatusChangeDao;

    @Override
    public void exec(JsonObject jsonObject)
    {
        JsonObject body = jsonObject.get("body").getAsJsonObject();
        String deviceUid = body.get("deviceUid").getAsString();
        String deviceStatus = body.get("deviceStatus").getAsString();
        OperationsEquipmentEntity operationsEquipmentEntity = deviceRegistDao.getOperationsEquipmentDetail(deviceUid);
        if (ObjectUtils.isNotEmpty(operationsEquipmentEntity))
        {
            if (!operationsEquipmentEntity.getDeviceStatus().equals(deviceStatus))
            {
                //变更设备状态
                operationsEquipmentEntity.setDeviceStatus(deviceStatus);
                deviceStatusChangeDao.updateOperationsEquipmentStatus(operationsEquipmentEntity);
                //新增运营设备属性修改日志
                OperationsEquipmentModifyLogEntity operationsEquipmentModifyLogEntity = new OperationsEquipmentModifyLogEntity();
                operationsEquipmentModifyLogEntity.setModifyAttribute("deviceStatus");
                operationsEquipmentModifyLogEntity.setAfterValue(operationsEquipmentEntity.getStatus());
                operationsEquipmentModifyLogEntity.setBeforeValue(deviceStatus);
                operationsEquipmentModifyLogEntity.setEquipmentId(operationsEquipmentEntity.getEquipmentId());
                operationsEquipmentModifyLogEntity.setModifyTime(new Date());
                operationsEquipmentModifyLogEntity.setModifyUser("设备注册自动修改");
                operationsEquipmentModifyLogEntity.setRemark("设备状态变更，自动同步修改");
                deviceRegistDao.insertOperationsEquipmentModifyLogEntity(operationsEquipmentModifyLogEntity);
            }
            //新增设备状态监控日志
            DeviceStatusMonitorLogsEntity deviceStatusMonitorLogsEntity = new DeviceStatusMonitorLogsEntity();
            deviceStatusMonitorLogsEntity.setEquipmentId(operationsEquipmentEntity.getEquipmentId());
            deviceStatusMonitorLogsEntity.setReceiveJson(jsonObject.toString());
            deviceStatusMonitorLogsEntity.setReceiveTime(new Date());
            deviceStatusChangeDao.insertDeviceStatusMonitorLogsEntity(deviceStatusMonitorLogsEntity);

        }
    }
}
