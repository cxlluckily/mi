package com.shankephone.mi.stock.enumeration;

import lombok.Getter;

/**
 * @author 赵亮
 * @date 2018-08-08 18:24
 */
@Getter
public enum UserDeviceStatusEnum
{
    HOLD("hold", "持有"),
    RETURN("return", "归还"),
    IN_USE("inUse", "使用中"),
    ;

    private String code;

    private String message;

    UserDeviceStatusEnum(String code, String message)
    {
        this.code = code;
        this.message = message;
    }
}
