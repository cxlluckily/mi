package com.shankephone.mi.task.enumeration;

import lombok.Getter;

/**
 * @author 赵亮
 * @date 2018-08-06 10:30
 */
@Getter
public enum PendTaskTypeEnum
{
    USE_APPLY("useApply", "领用申请", "", "UseExamination"),
    TRANSFER("transfer", "调拨申请", "", "UseExamination"),
    RETURN("return", "返还申请", "", "UseExamination"),

    REVIEW_NO_PASS("reviewNoPass", "审批不通过", "", "UseExaminationlook"),

    NEW_IN("newIn", "新品入库", "", ""),
    TRANSFER_IN("transferIn", "调拨入库", "", "PutStorageEdit"),
    BORROW_IN("borrowIn", "借用入库", "", "PutStorageEdit"),
    RETURN_IN("returnIn", "返还入库", "", "PutStorageEdit"),
    USE_IN("returnIn", "归还入库", "", "PutStorageEdit"),

    USE_OUT("useOut", "领用出库", "", "OutboundThrow"),
    TRANSFER_OUT("transferOut", "调拨出库", "", "OutboundThrow"),
    BORROW_OUT("borrowOut", "借用出库", "", "OutboundThrow"),
    RETURN_OUT("returnOut", "返还出库", "", "OutboundThrow"),



    TO_BE_DISPATCH("toBeDispatch", "待派单", "", "RDSendSingle"),
    TO_BE_REPAIR("toBeRepair", "待接修", "", "EMAfterRepairing"),
    TO_BE_INPUT_RESULT("toBoInputResult", "待录入维修结果", "", "EMCompleteMaintenance"),
    TO_BE_EVALUATE("toBeEvaluate", "待评价", "", "REDetails"),
    NO_REPAIR("noRepair", "未修复", "", "RDSendSingle"),

    BEFORE("before", "待盘库", "", "InventoryDetaile");



    private String code;
    private String message;
    private String manageUrl;
    private String phoneUrl;

    PendTaskTypeEnum(String code, String message, String manageUrl, String phoneUrl)
    {
        this.code = code;
        this.message = message;
        this.manageUrl = manageUrl;
        this.phoneUrl = phoneUrl;
    }
}
