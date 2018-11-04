package com.shankephone.mi.stock.enumeration;

import lombok.Getter;

/**
 * @author 赵亮
 * @date 2018-07-12 10:06
 */
@Getter
public enum OutStockStatusEnum
{
    TO_BE_OUT("toBeOut", "待出库"),
    ALREADY_OUT("alreadyOut", "已出库"),
    ;

    private String code;

    private String message;

    OutStockStatusEnum(String code, String message)
    {
        this.code = code;
        this.message = message;
    }
}
