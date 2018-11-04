package com.shankephone.mi.model;

import com.shankephone.mi.common.model.BaseModel;
import lombok.Data;

import java.util.Date;


/**
 * device_mqtt_command_file  Bean
 *
 * @author 系统生成
 * @date 2018/10/15 15:48:25
 */
@Data
public class DeviceMqttCommandFileEntity extends BaseModel
{
	
    /**
     * 设备调用命令文件ID
    **/
    private Long mqttCommandFileId;
    /**
     * 设备调用命令日志表ID
    **/
    private Long mqttCommandLogId;
    /**
     * 文件类型
    **/
    private String fileCategory;
    /**
     * 文件所在路径
    **/
    private String filePath;
    /**
     * 文件名称
    **/
    private String fileName;
    /**
     * 文件格式
    **/
    private String fileFormat;
    /**
     * 文件版本号
    **/
    private String fileVersionNumber;
    /**
     * 文件大小(Byte)
    **/
    private String fileSize;
    /**
     * 文件摘要(MD5)
    **/
    private String fileDigest;
    /**
     * 文件最后更新时间
    **/
    private Date fileLastTime;
    /**
     * 类型
    **/
    private String fileType;
    /**
     * 添加人
    **/
    private String createUser;
    /**
     * 创建时间
    **/
    private Date createTime;

   
}

