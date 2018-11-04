package com.shankephone.mi.inventory.formbean;

import com.shankephone.mi.common.model.BaseFindEntity;
import lombok.Data;

/**
 * 货架查询实体
 *
 * @author 司徒彬
 * @date 2018/7/6 15:48
 */
@Data
public class ShelvesFindEntity extends BaseFindEntity {
    private Long goodsShelvesId;
    private String shelfNumber;
    private String containerType;
    private String status;
    private String warehouseId;
    private String houseNo;


}
