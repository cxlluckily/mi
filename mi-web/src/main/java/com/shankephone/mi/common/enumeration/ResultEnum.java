package com.shankephone.mi.common.enumeration;

import lombok.Getter;

/**
 * Created by 廖师兄
 * 2017-06-11 18:56
 */
@Getter
public enum ResultEnum
{

    SUCCESS(200, "成功"),

    ERROR(-1, "操作失败"),

    Authentication_Error(401, "没有授权"),

    Request_Timeout(408, "请求超时"),
    Parameter_Error(403, "参数错误"),

    LOGIN_FAIL(25, "登录失败, 登录信息不正确"),
    USER_NAME_REPEAT(11, "手机号重复，请修改后重试！"),
    PIC_UPLOAD_ERROR(12, "图片上传失败"),
    INVENTORY_SHORTAGE(13, "库存不足"),
    LOGOUT_SUCCESS(26, "登出成功"),
    STOCK_OUT_APPLY_STATUS_ERROR(14,"出库申请单状态错误"),
    SUPER_LOGIN_ERROR(0,"登录失败，请重新输入手机号或密码")
    ;

    private Integer code;

    private String message;

    ResultEnum(Integer code, String message)
    {
        this.code = code;
        this.message = message;
    }
}
