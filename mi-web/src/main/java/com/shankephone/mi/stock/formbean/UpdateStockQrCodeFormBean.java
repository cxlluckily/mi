package com.shankephone.mi.stock.formbean;

import com.shankephone.mi.common.model.BaseFindEntity;
import lombok.Data;

/**
 * @author 赵亮
 * @date 2018-07-12 14:54
 */
@Data
public class UpdateStockQrCodeFormBean extends BaseFindEntity
{
    private Long stockId;
    private Long sparePartId;
    private String qrCode;
    private boolean canBeUse=false;
}
