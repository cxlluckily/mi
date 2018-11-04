package com.shankephone.mi.common.enumeration;


/**
 * 维修申请状态 枚举
 *
 * @author 司徒彬
 * @date 2018/8/8 15:14
 */
public enum RepairApplyStatusEnum {
    toBeDispatch("toBeDispatch"), toBeRepair("toBeRepair"), maintenance("maintenance"), repaired("repaired"), noRepair("noRepair"),rated("rated")
    ;

    private String value;

    RepairApplyStatusEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
