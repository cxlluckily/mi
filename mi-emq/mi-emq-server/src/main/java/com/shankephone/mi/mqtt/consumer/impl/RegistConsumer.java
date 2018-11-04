package com.shankephone.mi.mqtt.consumer.impl;

import com.google.gson.JsonObject;
import com.shankephone.mi.deviceregist.dao.DeviceRegistDao;
import com.shankephone.mi.model.DeviceMqttRegistLogsEntity;
import com.shankephone.mi.model.DeviceNoRegistEntity;
import com.shankephone.mi.model.OperationsEquipmentEntity;
import com.shankephone.mi.model.OperationsEquipmentModifyLogEntity;
import com.shankephone.mi.mqtt.consumer.abs.ConsumerAbs;
import com.shankephone.mi.mqtt.product.ServerProducter;
import com.shankephone.mi.util.ObjectUtils;
import com.shankephone.mi.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.Date;

/**
 * @author 赵亮
 * @date 2018-10-12 14:44
 */
@Component
public class RegistConsumer extends ConsumerAbs
{
    @Autowired
    private DeviceRegistDao deviceRegistDao;
    @Autowired
    private ServerProducter serverProducter;

    @Override
    public void exec(JsonObject jsonObject) throws UnsupportedEncodingException
    {
        JsonObject body = jsonObject.get("body").getAsJsonObject();
        JsonObject registMainInfo = body.get("registMainInfo").getAsJsonObject();
        String deviceUid = registMainInfo.get("deviceUid").getAsString();
        String deviceCode = registMainInfo.get("deviceCode").getAsString();
        String stationCode = registMainInfo.get("stationCode").getAsString();
        String deviceStatus = registMainInfo.get("deviceStatus").getAsString();
        OperationsEquipmentEntity operationsEquipmentEntity = deviceRegistDao.getOperationsEquipmentDetail(deviceUid);
        DeviceMqttRegistLogsEntity deviceMqttRegistLogsEntity = new DeviceMqttRegistLogsEntity();
        //初始化注册日志
        deviceMqttRegistLogsEntity.setCreateTime(new Date());
        deviceMqttRegistLogsEntity.setCreateUser("系统服务");
        deviceMqttRegistLogsEntity.setReceiveJson(jsonObject.toString());
        deviceMqttRegistLogsEntity.setDeviceuId(deviceUid);
        String sendJson;
        if (ObjectUtils.isNotNull(operationsEquipmentEntity))
        {
            //判断是否修改了设备编号，如果修改了记录日志
            if (StringUtils.isEmpty(operationsEquipmentEntity.getDeviceCode()) || !operationsEquipmentEntity.getDeviceCode().equals(deviceCode))
            {
                OperationsEquipmentModifyLogEntity operationsEquipmentModifyLogEntity = new OperationsEquipmentModifyLogEntity();
                operationsEquipmentModifyLogEntity.setModifyAttribute("deviceCode");
                operationsEquipmentModifyLogEntity.setAfterValue(operationsEquipmentEntity.getDeviceCode());
                operationsEquipmentModifyLogEntity.setBeforeValue(deviceCode);
                operationsEquipmentModifyLogEntity.setEquipmentId(operationsEquipmentEntity.getEquipmentId());
                operationsEquipmentModifyLogEntity.setModifyTime(new Date());
                operationsEquipmentModifyLogEntity.setModifyUser("设备注册自动修改");
                operationsEquipmentModifyLogEntity.setRemark("设备自动注册，系统自动同步设备编号");
                deviceRegistDao.insertOperationsEquipmentModifyLogEntity(operationsEquipmentModifyLogEntity);
                operationsEquipmentEntity.setDeviceCode(deviceCode);
            }
            //判断是否修改了设备所在车站，如果修改了记录日志
            if (StringUtils.isEmpty(operationsEquipmentEntity.getStationCode()) || !operationsEquipmentEntity.getStationCode().equals(stationCode))
            {
                OperationsEquipmentModifyLogEntity operationsEquipmentModifyLogEntity = new OperationsEquipmentModifyLogEntity();
                operationsEquipmentModifyLogEntity.setModifyAttribute("stationCode");
                operationsEquipmentModifyLogEntity.setAfterValue(operationsEquipmentEntity.getStationCode());
                operationsEquipmentModifyLogEntity.setBeforeValue(stationCode);
                operationsEquipmentModifyLogEntity.setEquipmentId(operationsEquipmentEntity.getEquipmentId());
                operationsEquipmentModifyLogEntity.setModifyTime(new Date());
                operationsEquipmentModifyLogEntity.setModifyUser("设备注册自动修改");
                operationsEquipmentModifyLogEntity.setRemark("设备自动注册，系统自动同步设备所在车站");
                deviceRegistDao.insertOperationsEquipmentModifyLogEntity(operationsEquipmentModifyLogEntity);
                operationsEquipmentEntity.setStationCode(stationCode);
            }
            //更新状态
            operationsEquipmentEntity.setDeviceStatus(deviceStatus);
            deviceRegistDao.updateOperationsEquipmentStatus(operationsEquipmentEntity);
            //给设备返回注册结果信息
            sendJson = sendReturnMessage(jsonObject,true,deviceUid);
        }
        else//线上设备没有该设备
        {
            //判断未注册成功的设备是否已经有这个设备
            DeviceNoRegistEntity deviceNoRegistEntity = deviceRegistDao.getOneDeviceNoRegistEntity(deviceUid);
            boolean isHave = ObjectUtils.isEmpty(deviceNoRegistEntity);
            if(isHave)
            {
                //如果没有新增
               if(ObjectUtils.isEmpty( deviceRegistDao.getOneDeviceNoRegistEntity(deviceUid)))
               {
                deviceNoRegistEntity = new DeviceNoRegistEntity();
                deviceNoRegistEntity.setCreateTime(new Date());
                deviceNoRegistEntity.setDeviceCode(deviceCode);
                deviceNoRegistEntity.setDeviceuId(deviceUid);
                deviceNoRegistEntity.setStationCode(stationCode);
                deviceRegistDao.insertDeviceNoRegistEntity(deviceNoRegistEntity);
               }
            }
            //给设备返回注册结果信息
            sendJson = sendReturnMessage(jsonObject,false,deviceUid);
        }
        //新增设备注册mqtt调用日志表
        deviceMqttRegistLogsEntity.setSendJson(sendJson);
        deviceMqttRegistLogsEntity.setSendTime(new Date());
        //新增注册日志
        deviceRegistDao.insertDeviceMqttRegistLogsEntity(deviceMqttRegistLogsEntity);
    }

    /**
     * 给设备返回注册结果信息
     *
     * @author：赵亮
     * @date：2018-10-17 11:32
     */
    private String sendReturnMessage(JsonObject jsonObject,boolean isRegist,String topic) throws UnsupportedEncodingException
    {
        JsonObject messageJson = new JsonObject();
        JsonObject header = new JsonObject();
        header.addProperty("createTime", "时间戳");
        header.addProperty("messageType", "DeviceRegistrationReply");
        header.addProperty("wasNeedReply", "no");
        JsonObject body = new JsonObject();
        body.addProperty("deviceUid", topic);
        if(isRegist)
        {
            body.addProperty("returnCode", "0");
            JsonObject fileServerConfig = new JsonObject();
            fileServerConfig.addProperty("serverName", "服务器名字");
            fileServerConfig.addProperty("serverProtocol", "默认。其他待定");
            fileServerConfig.addProperty("serverAddress", "IP地址或域名");
            fileServerConfig.addProperty("serverPort", "端口号");
            fileServerConfig.addProperty("serverHomePath", "用户根目录");
            fileServerConfig.addProperty("authorization", "使用 username:password形式。其他待定");
            fileServerConfig.addProperty("serverSettings", "预留");
            body.add("fileServerConfig", fileServerConfig);
        }
        else
        {
            body.addProperty("returnCode", "-1");
        }
        messageJson.add("header", header);
        messageJson.add("body", body);

        String message = new String(messageJson.toString().getBytes("UTF-8"));
        serverProducter.sendToMqtt(message, topic);

        return messageJson.toString();
    }
}
