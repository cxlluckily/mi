package com.shankephone.mi.mqtt.enumeration;

import lombok.Getter;

/**
 * @author 赵亮
 * @date 2018-10-15 18:15
 */
@Getter
public enum MessageTypeEnum
{
    REGIST("Regist", "设备注册"), STATUS_CHANGE("StatusChange", "状态变化"),
    COMMAND_SERVER_RECEIVED("CommandServerReceived", "命令接收到ACK响应"),
    EXECUTE_COMMAND_RESULT("ExecuteCommandResult", "执行结果");

    private String code;

    private String message;

    MessageTypeEnum(String code, String message)
    {
        this.code = code;
        this.message = message;
    }
}
