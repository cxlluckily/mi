package com.shankephone.mi.inventory.enumeration;

import lombok.Getter;

/**
 * @author 赵亮
 * @date 2018-08-16 15:17
 */
@Getter
public enum InventoryTaskEnum
{
    BEFORE("before","待盘库"),ONGING("ongoing","盘库中"), ALREADY("already","盘库完成"),;

    private String code;
    private String value;
    InventoryTaskEnum(String code, String value)
    {
        this.code = code;
        this.value = value;
    }

}
