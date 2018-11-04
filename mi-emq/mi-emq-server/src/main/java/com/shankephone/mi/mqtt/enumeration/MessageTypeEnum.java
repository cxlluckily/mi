package com.shankephone.mi.mqtt.enumeration;

import lombok.Getter;

/**
 * @author 赵亮
 * @date 2018-10-15 18:15
 */
@Getter
public enum MessageTypeEnum
{
    EXECUTE_COMMAND("ExecuteCommand", "执行命令");

    private String code;

    private String message;

    MessageTypeEnum(String code, String message)
    {
        this.code = code;
        this.message = message;
    }
}
