package com.shankephone.mi.inventory.vo;

import com.shankephone.mi.model.StockWarehouseEntity;
import lombok.Data;

import java.util.List;

/**
 * ${description}
 *
 * @author 司徒彬
 * @date 2018/7/3 20:58
 */
@Data
public class WarehouseVO extends StockWarehouseEntity {
    private List<Long> selectedStationIdList;
}
