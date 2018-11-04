package com.shankephone.mi.mqtt.product.controller;

import com.google.gson.JsonObject;
import com.shankephone.mi.mqtt.product.DeviceProducter;
import com.shankephone.mi.mqtt.product.service.DeviceProductService;
import com.shankephone.mi.util.PropertiesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 赵亮
 * @date 2018-09-18 11:06
 */
@RestController
public class ProducterController
{

    @Autowired
    private DeviceProducter serverProducter;

    /**
     *设备注册后返回给服务器测试api
     *@author：赵亮
     *@date：2018-10-18 10:05
    */
    @RequestMapping("/serverProducter/deviceRegistrationReply")
    public String deviceRegistrationReply()
    {
        try
        {

            JsonObject messageJson = new JsonObject();
            JsonObject header = new JsonObject();
            header.addProperty("createTime", "时间戳");
            header.addProperty("messageType", "DeviceRegistrationReply");
            header.addProperty("wasNeedReply", "no");
            JsonObject body = new JsonObject();
            body.addProperty("deviceUid", "device001");
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
            messageJson.add("header", header);
            messageJson.add("body", body);

            String message = new String(messageJson.toString().getBytes("UTF-8"));
            serverProducter.sendToMqtt(message, "device001");
            return "success";
        }
        catch (Exception ex)
        {
            return "fail";
        }

    }

    @Autowired
    private DeviceProductService deviceProductService;

    /**
     *设备上线注册
     *@author：赵亮
     *@date：2018-10-18 17:13
    */
    @RequestMapping("/serverProducter/deviceOnLineRegist")
    public String deviceOnLineRegist(@RequestParam(value = "deviceuId") String deviceuId)
    {
        try
        {
            serverProducter.sendToMqtt(deviceProductService.onLineRegist(deviceuId), PropertiesUtil.MQTT_SERVER_DEFAULT_TOPIC);
            return "success";
        }
        catch (Exception ex)
        {
            return "fail";
        }

    }

    /**
     *设备状态变更
     *@author：赵亮
     *@date：2018-10-18 17:14
    */
    @RequestMapping("/serverProducter/deviceStatusChange")
    public String deviceStatusChange(@RequestParam(value = "deviceuId") String deviceuId, @RequestParam(value = "deviceStatus") String deviceStatus)
    {
        try
        {
            serverProducter.sendToMqtt(deviceProductService.deviceStatusChange(deviceuId, deviceStatus), PropertiesUtil.MQTT_SERVER_DEFAULT_TOPIC);
            return "success";
        }
        catch (Exception ex)
        {
            return "fail";
        }

    }

    /**
     *设备执行完成后返回结果
     *@author：赵亮
     *@date：2018-10-18 17:14
     */
    @RequestMapping("/serverProducter/deviceCommandExecuteFinished")
    public String deviceCommandExecuteFinished(@RequestParam(value = "messageSessionId") String messageSessionId)
    {
        try
        {
            serverProducter.sendToMqtt(deviceProductService.deviceCommandExecuteFinished(messageSessionId), PropertiesUtil.MQTT_SERVER_DEFAULT_TOPIC);
            return "success";
        }
        catch (Exception ex)
        {
            return "fail";
        }

    }
}
