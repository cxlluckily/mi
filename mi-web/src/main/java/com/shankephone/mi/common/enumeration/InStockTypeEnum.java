package com.shankephone.mi.common.enumeration;


/**
 * 入库类型 枚举
 *
 * @author 司徒彬
 * @date 2018/7/25 17:12
 */
public enum InStockTypeEnum {
    NEW_IN("newIn"), RETURN_IN("returnIn"), TRANSFER_IN("transferIn"),USE_IN("useIn"),;

    private String value;

    InStockTypeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
