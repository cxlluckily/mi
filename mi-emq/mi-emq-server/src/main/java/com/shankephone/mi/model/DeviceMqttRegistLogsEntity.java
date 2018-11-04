package com.shankephone.mi.model;

import lombok.Data;

import java.util.Date;


/**
 * device_mqtt_regist_logs  Bean
 *
 * @author 系统生成
 * @date 2018-10-17 10:30:18
 */
@Data
public class DeviceMqttRegistLogsEntity
{
	
    /**
     * 设备注册信息日志ID
    **/
    private Long mqttRegistLogId;
    /**
     * 设备唯一ID
    **/
    private String deviceuId;
    /**
     * 发送json
    **/
    private String sendJson;
    /**
     * 接收json
    **/
    private String receiveJson;
    /**
     * 添加人
    **/
    private String createUser;
    /**
     * 创建时间
    **/
    private Date createTime;
    /**
     * 发送时间
    **/
    private Date sendTime;

   
}

