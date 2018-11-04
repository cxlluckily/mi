package com.shankephone.mi.common.enumeration;

import lombok.Getter;

/**
 * @author 赵亮
 * @date 2018-07-12 10:06
 */
@Getter
public enum StockStatusEnum
{
    NORMAL("normal", "好件"),
    BAD("bad", "坏件"),
    ;

    private String code;

    private String message;

    StockStatusEnum(String code, String message)
    {
        this.code = code;
        this.message = message;
    }
}
