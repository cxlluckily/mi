package com.shankephone.mi.model;

import lombok.Data;

import java.util.Date;


/**
 * device_status_monitor_logs  Bean
 *
 * @author 系统生成
 * @date 2018-10-17 15:03:44
 */
@Data
public class DeviceStatusMonitorLogsEntity
{
	
    /**
     * 设备状态监控日志ID
    **/
    private Long deviceStatusMonitorLogId;
    /**
     * 运营设备ID
    **/
    private Long equipmentId;
    /**
     * 接收json
    **/
    private String receiveJson;
    /**
     * 创建时间
    **/
    private Date receiveTime;

   
}

