package com.shankephone.mi.mqtt.enumeration;

import lombok.Getter;

/**
 * @author 赵亮
 * @date 2018-10-15 18:18
 */
@Getter
public enum NeedReplyEnum
{
    YES("yes ", "是"),NO("no ", "否"),;

    private String code;

    private String message;

    NeedReplyEnum(String code, String message)
    {
        this.code = code;
        this.message = message;
    }
}
