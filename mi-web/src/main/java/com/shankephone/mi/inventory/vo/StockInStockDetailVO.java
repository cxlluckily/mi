package com.shankephone.mi.inventory.vo;

import com.shankephone.mi.model.StockInStockDetailEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 入库申请业务实体
 *
 * @author 司徒彬
 * @date 2018/7/25 16:11
 */
@Data
public class StockInStockDetailVO extends StockInStockDetailEntity {
    private String equipmentNO;
    private String serialNumber;
    private BigDecimal price;
    private Integer alreadyInCount;
    private Long inWarehouseId;
    private String qrCode;

    private List<Long> sparePartIds;
}
