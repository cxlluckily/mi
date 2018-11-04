package com.shankephone.mi.model;

import com.shankephone.mi.common.model.BaseModel;
import lombok.Data;

import java.util.Date;


/**
 * device_mqtt_command_logs  Bean
 *
 * @author 系统生成
 * @date 2018/10/15 15:48:25
 */
@Data
public class DeviceMqttCommandLogsEntity extends BaseModel
{
	
    /**
     * 设备调用命令日志表ID
    **/
    private Long mqttCommandLogId;
    /**
     * 运营设备ID
    **/
    private Long equipmentId;
    /**
     * 设备命令批次表ID
    **/
    private Long mqttCommandBatchId;
    /**
     * 命令类型 
    **/
    private String commandType;
    /**
     * 命令类别
    **/
    private String commandCategory;
    /**
     * 命令内容
    **/
    private String commandContent;
    /**
     * 发送json
    **/
    private String sendJson;
    /**
     * 应答json
    **/
    private String receiveJson;
    /**
     * 结果json
    **/
    private String resultJson;
    /**
     * 执行状态
    **/
    private String executeStatus;
    /**
     * 添加人
    **/
    private String createUser;
    /**
     * 创建时间
    **/
    private Date createTime;
    /**
     * 应答时间
    **/
    private Date responseTime;
    /**
     * 完成时间
    **/
    private Date finishTime;

   
}

