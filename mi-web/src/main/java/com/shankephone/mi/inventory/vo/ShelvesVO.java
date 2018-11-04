package com.shankephone.mi.inventory.vo;

import com.shankephone.mi.model.StockGoodsShelvesEntity;
import lombok.Data;

/**
 * 货架业务实体
 *
 * @author 司徒彬
 * @date 2018/7/6 16:27
 */
@Data
public class ShelvesVO  extends StockGoodsShelvesEntity {
    private Integer layerNumber;
    private Integer cellNumber;
    private String code;
}
