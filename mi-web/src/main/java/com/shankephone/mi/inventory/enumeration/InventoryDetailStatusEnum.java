package com.shankephone.mi.inventory.enumeration;

/**
 * @author 赵亮
 * @date 2018-08-16 15:17
 */
public enum InventoryDetailStatusEnum
{
    NOT_BEGINNING("not_beginning"), LOSS("loss"),PROFIT("profit"),NORMAL("normal");

    private String value;

    InventoryDetailStatusEnum(String value)
    {
        this.value = value;
    }

    public String getValue()
    {
        return value;
    }
}
