package com.shankephone.mi.mqtt.enumeration;

import lombok.Getter;

/**
 * @author 赵亮
 * @date 2018-10-15 18:47
 */
@Getter
public enum DeviceStatusEnum
{
    INSERVICE("INSERVICE", "正常服务"),
    MAINTAINCE("MAINTAINCE", "维修模式"),
    OUTSERVICE("OUTSERVICE", "暂停服务"),
    CLOSESERAVICE("CLOSESERAVICE", "关闭服务"),
    ;

    private String code;

    private String message;

    DeviceStatusEnum(String code, String message)
    {
        this.code = code;
        this.message = message;
    }
}
