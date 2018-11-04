package com.shankephone.mi.common.enumeration;

import lombok.Getter;

/**
 * @author 赵亮
 * @date 2018-07-12 10:06
 */
@Getter
public enum  ApplyStatusEnum
{
    TO_BE_OUT("toBeOut", "待出库"),
    AFTER_OUT("afterOut", "已出库"),
    TO_BE_IN("toBeIn", "领取入库"),
    OVER_APPLY("over","完成"),
    BROWWER_OVER("browwerOver","已出库"),
    TO_BE_REVIEW("toBeReview","待审核"),
    REVIEW_PASS("reviewPass","审核通过"),
    REVIEW_NO_PASS("reviewNoPass","审核不通过"),

    //    USE_OUT("LQ", "领取出库"),
    //    USE_OUT("LQ", "领取出库"),
    ;

    private String code;

    private String message;

    ApplyStatusEnum(String code, String message)
    {
        this.code = code;
        this.message = message;
    }
}
