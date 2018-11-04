package com.shankephone.mi.model;

import lombok.Data;



/**
 * client_device  Bean
 *
 * @author 系统生成
 * @date 2018/10/15 15:48:25
 */
@Data
public class ClientDeviceEntity
{
	
    /**
     * 客户端设备ID
    **/
    private Long clientDeviceId;
    /**
     * 设备唯一ID
    **/
    private String deviceUid;
    /**
     * 设备编号
    **/
    private String deviceCode;
    /**
     * 设备所在车站代码
    **/
    private String stationCode;
    /**
     * 设备状态
    **/
    private String deviceStatus;

   
}

