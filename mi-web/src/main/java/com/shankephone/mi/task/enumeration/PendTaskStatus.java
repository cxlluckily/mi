package com.shankephone.mi.task.enumeration;

import lombok.Getter;

/**
 * @author 赵亮
 * @date 2018-08-06 10:39
 */
@Getter
public enum PendTaskStatus
{
    TO_BE_READ("toBeRead", "待阅读"),
    ALREADY_READ("alreadyRead", "已阅读"),
    OVER("over", "已完结"),;

    private String code;

    private String message;

    PendTaskStatus(String code, String message)
    {
        this.code = code;
        this.message = message;
    }
}
