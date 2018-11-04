package com.shankephone.mi.model;

import lombok.Data;

import java.util.Date;


/**
 * device_preinstall_command  Bean
 *
 * @author 系统生成
 * @date 2018/10/15 15:48:25
 */
@Data
public class DevicePreinstallCommandEntity
{
	
    /**
     * 设备预设命令ID
    **/
    private Long preinstallCommandId;
    private Integer sort;
    private String commandName;
    private String commandContext;
    private String status;
    /**
     * 创建时间
    **/
    private Date createTime;
    /**
     * 添加人
    **/
    private String createUser;

   
}

