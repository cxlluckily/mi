package com.shankephone.mi.mqtt.consumer.impl;

import com.google.gson.JsonObject;
import com.shankephone.mi.command.dao.CommandDao;
import com.shankephone.mi.command.enumeration.ExecuteStatusEnum;
import com.shankephone.mi.model.DeviceMqttCommandLogsEntity;
import com.shankephone.mi.mqtt.consumer.abs.ConsumerAbs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author 赵亮
 * @date 2018年10月19日 13:52:16
 */
@Component
public class ExecuteCommandResultConsumer extends ConsumerAbs
{
    @Autowired
    private CommandDao commandDao;

    @Override
    public void exec(JsonObject jsonObject)
    {
        try
        {
            JsonObject body = jsonObject.get("body").getAsJsonObject();
            String messageSessionId = body.get("messageSessionId").getAsString();
            DeviceMqttCommandLogsEntity entity = new DeviceMqttCommandLogsEntity();
            entity.setResultJson(jsonObject.toString());
            entity.setExecuteStatus(ExecuteStatusEnum.FINISHED.getCode());
            entity.setMqttCommandLogId(Long.parseLong(messageSessionId));
            entity.setFinishTime(new Date());
            commandDao.updateMqttCommandLogStatusAndResultJson(entity);
        }
        catch(Exception ex)
        {
            throw ex;
        }
    }
}
