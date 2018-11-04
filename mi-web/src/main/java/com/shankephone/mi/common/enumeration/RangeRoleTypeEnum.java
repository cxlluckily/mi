package com.shankephone.mi.common.enumeration;


/**
 * 范围权限枚举 枚举
 *
 * @author 司徒彬
 * @date 2018/6/27 16:13
 */
public enum RangeRoleTypeEnum {
    WAREHOUSE("warehouse"), WORK_SECTION("work_section"),;

    private String value;

    RangeRoleTypeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
