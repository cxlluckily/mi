package com.shankephone.mi.mqtt.product.service;

import com.google.gson.JsonObject;
import com.shankephone.mi.command.dao.CommandDao;
import com.shankephone.mi.model.DeviceMqttCommandLogsEntity;
import com.shankephone.mi.mqtt.enumeration.MessageTypeEnum;
import com.shankephone.mi.mqtt.enumeration.NeedReplyEnum;
import com.shankephone.mi.mqtt.formbean.DeviceAndBusiIdFormBean;
import com.shankephone.mi.mqtt.formbean.SendCommandMessageFormBean;
import com.shankephone.mi.mqtt.product.ServerProducter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author 赵亮
 * @date 2018-10-15 18:09
 */
@Service
public class ServerProductService
{

    @Autowired
    private ServerProducter serverProducter;
    @Autowired
    private CommandDao commandDao;

    /**
     * 发送命令
     *
     * @author：赵亮
     * @date：2018-10-19 10:07
     */
    public String sendCommandMessage(SendCommandMessageFormBean formBean)
    {
        String sendJson = "";
        for (DeviceAndBusiIdFormBean oneEntity : formBean.getDeviceAndBusiIdFormBeans())
        {
            JsonObject sendMessage = new JsonObject();
            JsonObject header = new JsonObject();
            header.addProperty("createTime", new Date().getTime());
            header.addProperty("messageType", MessageTypeEnum.EXECUTE_COMMAND.getCode());
            header.addProperty("wasNeedReply", NeedReplyEnum.YES.getCode());
            sendMessage.add("header", header);
            JsonObject body = new JsonObject();
            body.addProperty("deviceUid", oneEntity.getDeviceuId());
            body.addProperty("messageSessionId", oneEntity.getMessageSessionId());
            JsonObject executeCommand = new JsonObject();
            executeCommand.addProperty("commandName", formBean.getCommandName());
            executeCommand.addProperty("commandType", formBean.getCommandType());
            executeCommand.addProperty("executedCommand", formBean.getExecutedCommand());
            body.add("executeCommand", executeCommand);
            sendMessage.add("body", body);
            serverProducter.sendToMqtt(sendMessage.toString(), oneEntity.getDeviceuId());
            //更新发送json命令
            DeviceMqttCommandLogsEntity deviceMqttCommandLogsEntity = new DeviceMqttCommandLogsEntity();
            deviceMqttCommandLogsEntity.setMqttCommandLogId(Long.parseLong(oneEntity.getMessageSessionId()));
            deviceMqttCommandLogsEntity.setSendJson(sendMessage.toString());
            commandDao.updateMqttCommandLogStatusAndSendJson(deviceMqttCommandLogsEntity);
            //清空数据返回给批次表用
            if (formBean.getDeviceAndBusiIdFormBeans().size() > 0)
            {
                sendMessage.getAsJsonObject("body").addProperty("deviceUid", "");
                sendMessage.getAsJsonObject("body").addProperty("messageSessionId", "");
            }
            sendJson = sendMessage.toString();
        }

        return sendJson;
    }
}
