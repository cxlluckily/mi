package com.shankephone.mi.stock.formbean;

import com.shankephone.mi.common.model.BaseModel;
import lombok.Data;

/**
 * @author 赵亮
 * @date 2018-07-12 9:27
 */
@Data
public class OutStockApplyFormBean extends BaseModel
{

    /**
     * 要出库仓库ID
     **/
    private Long outWarehouseId;
    private String remark;
    private String outDate;
}
