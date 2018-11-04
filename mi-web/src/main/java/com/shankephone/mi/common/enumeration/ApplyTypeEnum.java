package com.shankephone.mi.common.enumeration;

import lombok.Getter;

/**
 * @author 赵亮
 * @date 2018-07-12 13:42
 */
@Getter
public enum ApplyTypeEnum {
    NEW_IN("newIn", "新品入库"),
    TRANSFER_IN("transferIn", "调拨入库"),
    BORROW_IN("borrowIn", "借用入库"),
    RETURN_IN("returnIn", "返还人库"),
    USE_IN("useIn", "归还入库"),
    TRANSFER_OUT("transferOut", "调拨出库"),
    BORROW_OUT("borrowOut", "借用出库"),
    RETURN_OUT("returnOut", "返还出库"),
    USE_OUT("useOut", "领用出库"),

    STOCK_MODIFY("stockModify","库存调整"),


    TRANSFER("transfer", "调拨"),
    RETURN("return", "返还"),
    USE("use", "领用"),;
    private String code;

    private String message;

    ApplyTypeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
