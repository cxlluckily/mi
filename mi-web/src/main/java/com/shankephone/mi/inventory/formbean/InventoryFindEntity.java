package com.shankephone.mi.inventory.formbean;

import com.shankephone.mi.model.StockInStockDetailEntity;
import lombok.Data;

/**
 * ${description}
 *
 * @author 司徒彬
 * @date 2018/7/11 15:53
 */
@Data
public class InventoryFindEntity extends StockInStockDetailEntity
{
   private long inventoryTaskId;
}
