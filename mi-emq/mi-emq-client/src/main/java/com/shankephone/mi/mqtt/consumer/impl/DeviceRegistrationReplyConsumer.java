package com.shankephone.mi.mqtt.consumer.impl;

import com.google.gson.JsonObject;
import com.shankephone.mi.mqtt.consumer.abs.ConsumerAbs;
import com.shankephone.mi.mqtt.consumer.dao.ClientDeviceDao;
import com.shankephone.mi.mqtt.model.ClientDeviceEntity;
import com.shankephone.mi.mqtt.model.ClientMqttLogsEntity;
import com.shankephone.mi.mqtt.product.DeviceProducter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author 赵亮
 * @date 2018-10-12 14:44
 */
@Component
@Slf4j
public class DeviceRegistrationReplyConsumer extends ConsumerAbs
{
    @Autowired
    private ClientDeviceDao clientDeviceDao;
    @Autowired
    private DeviceProducter deviceProducter;

    @Override
    public void exec(JsonObject jsonObject) throws Exception
    {
        try
        {
            JsonObject body = jsonObject.get("body").getAsJsonObject();
            String deviceUid = body.get("deviceUid").getAsString();
            log.info("设备编号是=" + deviceUid + "接收到了命令，内容是" + jsonObject.toString());
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
            //给服务端响应
//            String messageSessionId = body.get("messageSessionId").getAsString();
//            JsonObject sendMessage = new JsonObject();
//            JsonObject header = new JsonObject();
//            header.addProperty("createTime", new Date().getTime());
//            header.addProperty("messageType", MessageTypeEnum.COMMAND_SERVER_RECEIVED.getCode());
//            header.addProperty("wasNeedReply", NeedReplyEnum.NO.getCode());
//            sendMessage.add("header", header);
//            body = new JsonObject();
//            body.addProperty("messageSessionId", messageSessionId);
//            sendMessage.add("body", body);
//            deviceProducter.sendToMqtt(sendMessage.toString(), PropertiesUtil.MQTT_SERVER_DEFAULT_TOPIC);
        }
        catch(Exception ex)
        {
            throw ex;
        }
    }
}
