package com.shankephone.mi.common.enumeration;


/**
 * 入库申请单状态 枚举
 *
 * @author 司徒彬
 * @date 2018/7/26 10:24
 */
public enum InStockStatusEnum {
    TOBEIN("toBeIn"), ALREADIN("alreadyIn"),;

    private String value;

    InStockStatusEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
