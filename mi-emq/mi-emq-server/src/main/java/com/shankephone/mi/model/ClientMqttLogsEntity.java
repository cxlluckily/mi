package com.shankephone.mi.model;

import lombok.Data;

import java.util.Date;


/**
 * client_mqtt_logs  Bean
 *
 * @author 系统生成
 * @date 2018/10/15 15:48:25
 */
@Data
public class ClientMqttLogsEntity
{
	
    /**
     * 设备端接受命令日志ID
    **/
    private Long clientMqttLogId;
    /**
     * 客户端设备ID
    **/
    private Long clientDeviceId;
    /**
     * 创建时间
    **/
    private Date createTime;
    /**
     * 发送json
    **/
    private String json;
    /**
     * 类型
    **/
    private String logType;

   
}

