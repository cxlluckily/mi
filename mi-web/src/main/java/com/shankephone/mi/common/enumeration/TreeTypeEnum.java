package com.shankephone.mi.common.enumeration;

import lombok.Getter;

/**
 * @author 赵亮
 * @date 2018-08-01 9:59
 */
@Getter
public enum TreeTypeEnum
{
    MANAGE("manage", "电脑端"),
    PHONE("phone", "移动端"),
    ;
    private String code;

    private String message;

    TreeTypeEnum(String code, String message)
    {
        this.code = code;
        this.message = message;
    }
}
