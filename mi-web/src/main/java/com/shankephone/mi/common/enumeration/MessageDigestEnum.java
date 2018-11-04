package com.shankephone.mi.common.enumeration;


/**
 * 信息摘要算法类别 枚举
 *
 * @author 司徒彬
 * @date 2017-04-12 10:47
 */
public enum MessageDigestEnum
{
    MD5("MD5"), SHA1("SHA1"),;

    private String value;

    MessageDigestEnum(String value)
    {
        this.value = value;
    }

    public String getValue()
    {
        return value;
    }
}
