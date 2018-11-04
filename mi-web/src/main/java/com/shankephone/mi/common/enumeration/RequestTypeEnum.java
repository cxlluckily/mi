package com.shankephone.mi.common.enumeration;


/**
 * 请求类型 枚举
 *
 * @author 司徒彬
 * @date 2017-04-10 16:05
 */
public enum RequestTypeEnum
{
    Post("POST"), Get("GET"),;

    private String value;

    RequestTypeEnum(String value)
    {
        this.value = value;
    }

    public String getValue()
    {
        return value;
    }
}
