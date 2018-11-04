package com.shankephone.mi.mqtt.consumer.impl;

import com.google.gson.JsonObject;
import com.shankephone.mi.command.dao.CommandDao;
import com.shankephone.mi.model.DeviceMqttCommandLogsEntity;
import com.shankephone.mi.mqtt.consumer.abs.ConsumerAbs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author 赵亮
 * @date 2018年10月19日 13:33:27
 */
@Component
public class CommandServerReceivedConsumer extends ConsumerAbs
{
    @Autowired
    private CommandDao commandDao;

    /**
     * 更新设备调用命令日志表状态
     *
     * @author：赵亮
     * @date：2018-10-19 13:38
     */
    @Override
    public void exec(JsonObject jsonObject)
    {
        JsonObject body = jsonObject.get("body").getAsJsonObject();
        String messageSessionId = body.get("messageSessionId").getAsString();
        DeviceMqttCommandLogsEntity entity = new DeviceMqttCommandLogsEntity();
        entity.setMqttCommandLogId(Long.parseLong(messageSessionId));
        entity.setReceiveJson(jsonObject.toString());
        entity.setResponseTime(new Date());
        commandDao.updateMqttCommandLogStatus(entity);
    }
}
