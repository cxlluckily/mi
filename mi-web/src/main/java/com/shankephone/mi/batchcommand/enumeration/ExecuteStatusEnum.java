package com.shankephone.mi.batchcommand.enumeration;

import lombok.Getter;

/**
 * @author 赵亮
 * @date 2018-10-19 13:40
 */
@Getter
public enum ExecuteStatusEnum
{
    TO_BE_EXECUTED("to_be_executed", "待执行");

    private String code;

    private String message;

    ExecuteStatusEnum(String code, String message)
    {
        this.code = code;
        this.message = message;
    }
}
