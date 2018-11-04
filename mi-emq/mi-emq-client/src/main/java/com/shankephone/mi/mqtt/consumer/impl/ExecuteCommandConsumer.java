package com.shankephone.mi.mqtt.consumer.impl;

import com.google.gson.JsonObject;
import com.shankephone.mi.mqtt.consumer.abs.ConsumerAbs;
import com.shankephone.mi.mqtt.consumer.dao.ClientDeviceDao;
import com.shankephone.mi.mqtt.model.ClientDeviceEntity;
import com.shankephone.mi.mqtt.model.ClientMqttLogsEntity;
import com.shankephone.mi.mqtt.product.DeviceProducter;
import com.shankephone.mi.mqtt.product.service.DeviceProductService;
import com.shankephone.mi.util.PropertiesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 命令处理
 *
 * @author 赵亮
 * @date 2018-10-12 14:44
 */
@Component
public class ExecuteCommandConsumer extends ConsumerAbs
{
    @Autowired
    private ClientDeviceDao clientDeviceDao;
    @Autowired
    private DeviceProducter serverProducter;
    @Autowired
    private DeviceProductService deviceProductService;

    @Override
    public void exec(JsonObject jsonObject) throws Exception
    {
        JsonObject body = jsonObject.get("body").getAsJsonObject();
        String deviceUid = body.get("deviceUid").getAsString();
        String logType = super.getMessageType();
        ClientDeviceEntity deviceEntity = new ClientDeviceEntity();
        deviceEntity.setDeviceUid(deviceUid);
        deviceEntity = clientDeviceDao.getClientDeviceDetail(deviceEntity);
        ClientMqttLogsEntity clientMqttLogsEntity = new ClientMqttLogsEntity();
        clientMqttLogsEntity.setClientDeviceId(deviceEntity.getClientDeviceId());
        clientMqttLogsEntity.setCreateTime(new Date());
        clientMqttLogsEntity.setJson(jsonObject.toString());
        clientMqttLogsEntity.setLogType(logType);
        clientDeviceDao.insertClientDevice(clientMqttLogsEntity);
        //响应已经接到命令
        serverProducter.sendToMqtt(deviceProductService.deviceCommandExecuteReceive(body.get("messageSessionId").getAsString()), PropertiesUtil.MQTT_SERVER_DEFAULT_TOPIC);
        //执行完成
        serverProducter.sendToMqtt(deviceProductService.deviceCommandExecuteFinished(body.get("messageSessionId").getAsString()), PropertiesUtil.MQTT_SERVER_DEFAULT_TOPIC);
    }
}
