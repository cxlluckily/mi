package com.shankephone.mi.model;

import com.shankephone.mi.common.model.BaseModel;
import lombok.Data;

import java.util.Date;


/**
 * device_mqtt_command_batch  Bean
 *
 * @author 系统生成
 * @date 2018/10/15 15:48:25
 */
@Data
public class DeviceMqttCommandBatchEntity extends BaseModel
{
	
    /**
     * 设备命令批次表ID
    **/
    private Long mqttCommandBatchId;
    /**
     * 批次
    **/
    private String batchNo;
    /**
     * 命令类型 
    **/
    private String commandType;
    /**
     * 命令类别
    **/
    private String commandCategory;
    /**
     * 发送json
    **/
    private String sendJson;
    /**
     * 创建时间
    **/
    private Date createTime;
    /**
     * 添加人
    **/
    private String createUser;

   
}

