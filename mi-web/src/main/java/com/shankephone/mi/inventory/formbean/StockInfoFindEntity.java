package com.shankephone.mi.inventory.formbean;

import com.shankephone.mi.common.model.BaseFindEntity;
import lombok.Data;

/**
 * 库存管理查询实体
 * 仓库（下拉）、备件名称（文本）、状态（好件、坏件 下拉） 房间号（文本）、库存类型（下拉)、supplierName
 * @author 司徒彬
 * @date 2018/7/26 20:06
 */
@Data
public class StockInfoFindEntity extends BaseFindEntity {
    private Long warehouseId;
    private Long stockId;
    private Long userDeviceId;
    private String partName;
    private String status;
    private String houseNo;
    private String inventoryType;
    private Long sparePartId;
    private Long sparePartTypeId;
    private String supplierName;
    private  String type;
}
