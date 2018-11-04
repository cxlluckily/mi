package com.shankephone.mi.inventory.formbean;

import com.shankephone.mi.common.model.BaseFindEntity;
import lombok.Data;

/**
 * 库存操作查询实体
 *
 * @author 司徒彬
 * @date 2018/7/12 10:12
 */
@Data
public class StockOperationFindEntity extends BaseFindEntity {
    private String applyType;
    private String documentNo;
    private Long applyId;
    private String applyStatus;
    private String notApplyStatus;
    private String applyUser;

}
