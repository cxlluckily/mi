package com.shankephone.mi.command.enumeration;

import lombok.Getter;

/**
 * @author 赵亮
 * @date 2018-10-19 13:40
 */
@Getter
public enum ExecuteStatusEnum
{
    EXECUTING("executing", "执行中"), FINISHED("finished", "执行完成");

    private String code;

    private String message;

    ExecuteStatusEnum(String code, String message)
    {
        this.code = code;
        this.message = message;
    }
}
