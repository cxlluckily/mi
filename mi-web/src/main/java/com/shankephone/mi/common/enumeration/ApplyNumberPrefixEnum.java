package com.shankephone.mi.common.enumeration;

import lombok.Getter;

/**
 * @author 赵亮
 * @date 2018-07-12 9:58
 */
@Getter
public enum ApplyNumberPrefixEnum {
    USE_OUT_NO("LQ", "领取出库"),
    USE_IN_NO("GH", "领取出库"),
    TRANSFER_OUT_NO("DB", "调拨出库"),
    BORROW_OUT_NO("JY", "借用出库"),
    BORROW_IN_NO("JY", "借用入库"),
    RETURN_OUT_NO("FH", "返还出库"),
    RETURN_IN_NO("FH", "返还入库"),
    TRANSFER_IN_NO("DB", "领取入库"),
    NEW_IN("XP", "新品入库"),
    REPAIR("WX", "维修申请"),
    INVENTORY_TASK("PK", "盘库"),
    COMMAND_BATCH("CB", "批量发送命令"),
    ;

    private String code;

    private String message;

    ApplyNumberPrefixEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
