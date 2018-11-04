package com.shankephone.mi.stock.formbean;

import com.shankephone.mi.common.model.BaseFindEntity;
import lombok.Data;

/**
 * @author 赵亮
 * @date 2018-07-12 14:47
 */
@Data
public class BindQrCodeFindEntity extends BaseFindEntity
{
    private Long outStockApplyId;
    private String inventoryType;
}
