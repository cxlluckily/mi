package com.shankephone.mi.batchcommand.service.impl;

import com.google.gson.JsonObject;
import com.shankephone.mi.batchcommand.dao.CommandDao;
import com.shankephone.mi.batchcommand.enumeration.ExecuteStatusEnum;
import com.shankephone.mi.batchcommand.formbean.*;
import com.shankephone.mi.batchcommand.service.CommandService;
import com.shankephone.mi.common.enumeration.ApplyNumberPrefixEnum;
import com.shankephone.mi.common.enumeration.ContentTypeEnum;
import com.shankephone.mi.common.enumeration.RequestTypeEnum;
import com.shankephone.mi.common.vo.ResultVO;
import com.shankephone.mi.model.DeviceMqttCommandBatchEntity;
import com.shankephone.mi.model.DeviceMqttCommandLogsEntity;
import com.shankephone.mi.util.DataSwitch;
import com.shankephone.mi.util.DateUtil;
import com.shankephone.mi.util.NumberFactory;
import com.shankephone.mi.util.UrlTools;
import com.shankephone.mi.wechat.config.ProjectUrlConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 赵亮
 * @date 2018-10-22 14:26
 */
@Service
public class CommandServiceImpl implements CommandService
{
    @Autowired
    private CommandDao commandDao;

    /**
     * 返回预设命令ddl数据
     *
     * @author：赵亮
     * @date：2018-10-22 14:27
     */
    @Override
    public List<Map<String, Object>> getPreinstallCommandDDL()
    {
        try
        {
            return commandDao.getPreinstallCommandDDL();
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    /**
     * 发送命令
     *
     * @param entity
     * @author：赵亮
     * @date：2018-10-24 14:31
     */
    @Override
    public SendCommandVO sendCommand(SendCommandFormBean entity) throws Exception
    {
        try
        {
            //1、新增批次表
            DeviceMqttCommandBatchEntity deviceMqttCommandBatchEntity = new DeviceMqttCommandBatchEntity();
            deviceMqttCommandBatchEntity.setBatchNo(NumberFactory.getCommandBatchNo(ApplyNumberPrefixEnum.COMMAND_BATCH.getCode()));
            deviceMqttCommandBatchEntity.setCommandType(entity.getCommandType());
            deviceMqttCommandBatchEntity.setCommandCategory(entity.getCommandCategory());
            deviceMqttCommandBatchEntity.setUserKey(entity.getUserKey());
            commandDao.insertDeviceMqttCommandBatchEntity(deviceMqttCommandBatchEntity);

            //2、循环新增设备调用命令日志表
            SendCommandMessageFormBean sendCommandMessageFormBean = new SendCommandMessageFormBean();
            sendCommandMessageFormBean.setCommandName(entity.getCommandCategory());
            sendCommandMessageFormBean.setCommandType(entity.getCommandType());
            sendCommandMessageFormBean.setExecutedCommand(entity.getCommandContent());
            sendCommandMessageFormBean.setDeviceAndBusiIdFormBeans(new ArrayList<>());
            for (SendCommandDeviceFormBean deviceFormBean : entity.getDeviceFormBeans())
            {
                DeviceMqttCommandLogsEntity deviceMqttCommandLogsEntity = new DeviceMqttCommandLogsEntity();
                deviceMqttCommandLogsEntity.setExecuteStatus(ExecuteStatusEnum.TO_BE_EXECUTED.getCode());
                deviceMqttCommandLogsEntity.setEquipmentId(deviceFormBean.getEquipmentId());
                deviceMqttCommandLogsEntity.setMqttCommandBatchId(deviceMqttCommandBatchEntity.getMqttCommandBatchId());
                deviceMqttCommandLogsEntity.setCommandType(entity.getCommandType());
                deviceMqttCommandLogsEntity.setCommandCategory(entity.getCommandCategory());
                deviceMqttCommandLogsEntity.setCommandContent(entity.getCommandContent());
                deviceMqttCommandLogsEntity.setUserKey(entity.getUserKey());
                commandDao.insertDeviceMqttCommandLogsEntity(deviceMqttCommandLogsEntity);
                DeviceAndBusiIdFormBean deviceAndBusiIdFormBean = new DeviceAndBusiIdFormBean();
                deviceAndBusiIdFormBean.setMessageSessionId(deviceMqttCommandLogsEntity.getMqttCommandLogId().toString());
                deviceAndBusiIdFormBean.setDeviceuId(deviceFormBean.getDeviceuId());
                sendCommandMessageFormBean.getDeviceAndBusiIdFormBeans().add(deviceAndBusiIdFormBean);
            }
            //3、给mqtt发消息
            String mqttParasJsonStr = DataSwitch.convertObjectToJson(sendCommandMessageFormBean).toString();
            String postUrl = ProjectUrlConfig.mqttServer + "serverProducter/sendCommandMessage";
            JsonObject returnObj = DataSwitch.convertStringToJsonObject(UrlTools.call(postUrl,mqttParasJsonStr,RequestTypeEnum.Post,ContentTypeEnum.application_json));
            ResultVO resultVO = DataSwitch.convertJsonToEntity(returnObj,ResultVO.class);
            if(!resultVO.getResult().equals("success"))
            {
                throw  new Exception("mqtt操作失败，请重试");
            }
            String sendJson = resultVO.getData().toString();
            //4、更新发送Json
            deviceMqttCommandBatchEntity.setSendJson(sendJson);
            commandDao.updateCommandBatchSendJosn(deviceMqttCommandBatchEntity);
            //5、初始化返回数据
            SendCommandVO sendCommandVO = new SendCommandVO();
            sendCommandVO.setMqttCommandBatchId(deviceMqttCommandBatchEntity.getMqttCommandBatchId());
            sendCommandVO.setSendJson(sendJson);
            sendCommandVO.setSendTime(DateUtil.getDateString());
            return sendCommandVO;
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }
}
