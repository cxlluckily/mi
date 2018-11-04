package com.shankephone.mi.common.enumeration;


/**
 * 维修申请日志 枚举
 *
 * @author 司徒彬
 * @date 2018/8/7 20:14
 */
public enum RepairApplyLogEnum {
    Reported("Reported"), Dispatched("Dispatched"), Repairing("Repairing"), Failed("Failed"), Repaired("Repaired"), Rated("Rated"),
    ;

    private String value;

    RepairApplyLogEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
