package com.shankephone.mi.common.enumeration;


/**
 * 用户类型 枚举
 *
 * @author 司徒彬
 * @date 2018-06-21 14:58
 */
public enum UserTypeEnum {
    SUPER_ADMIN("SUPER_ADMIN"), USER("USER"),;

    private String value;

    UserTypeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
