package com.shankephone.mi.inventory.formbean;

import com.shankephone.mi.common.model.BaseModel;
import com.shankephone.mi.model.StockInventoryDetailEntity;
import lombok.Data;

import java.util.List;

/**
 * @author 赵亮
 * @date 2018-08-27 9:30
 */
@Data
public class UpdateInventoryTaskFormBean extends BaseModel
{
    private List<StockInventoryDetailEntity> entities;
    private Long inventoryTaskId;
    private String status;
}
