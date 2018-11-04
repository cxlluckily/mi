package com.shankephone.mi.inventory.formbean;

import com.shankephone.mi.common.model.BaseFindEntity;
import lombok.Data;

/**
 * ${description}
 *
 * @author 司徒彬
 * @date 2018/7/11 15:53
 */
@Data
public class BaseDataFindEntity extends BaseFindEntity {
    private Long warehouseId;
    private String status;
    private String categoryName;
    private Long outWarehouseId;
    private Long sparePartTypeId;
    private  String houseNo;
    private  String shelfNumber;
    private  String supplierId;
}
