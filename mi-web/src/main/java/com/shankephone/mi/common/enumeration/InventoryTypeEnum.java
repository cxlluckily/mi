package com.shankephone.mi.common.enumeration;

import lombok.Getter;

/**
 * @author 赵亮
 * @date 2018-07-12 14:43
 */
@Getter
public enum InventoryTypeEnum
{
    UNIQUE("unique", "唯一标示"),
    NOT_UNIQUE("notUnique", "非唯一标示"),;

    private String code;

    private String message;

    InventoryTypeEnum(String code, String message)
    {
        this.code = code;
        this.message = message;
    }
}
