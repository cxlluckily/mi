package com.shankephone.mi.model;

import lombok.Data;

import java.util.Date;


/**
 * device_no_regist  Bean
 *
 * @author 系统生成
 * @date 2018-10-17 11:07:14
 */
@Data
public class DeviceNoRegistEntity
{
	
    /**
     * 未注册成功的设备ID
    **/
    private Long deviceNoRegistId;
    /**
     * 设备唯一ID
    **/
    private String deviceuId;
    /**
     * 设备编号
    **/
    private String deviceCode;
    /**
     * 设备所在车站代码
    **/
    private String stationCode;
    /**
     * 创建时间
    **/
    private Date createTime;

   
}

