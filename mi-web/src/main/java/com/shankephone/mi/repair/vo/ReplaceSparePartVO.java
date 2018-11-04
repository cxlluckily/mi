package com.shankephone.mi.repair.vo;

import com.shankephone.mi.model.MaintenanceChangeSparePartEntity;
import lombok.Data;

/**
 * 替换的设备
 *
 * @author 司徒彬
 * @date 2018/8/14 17:15
 */
@Data
public class ReplaceSparePartVO extends MaintenanceChangeSparePartEntity {
    private Long userDeviceId;
    private String oldInventoryType;
    private String inventoryType;
}
