package com.shankephone.mi.inventory.vo;

import com.shankephone.mi.model.StockBusinessApplyDetailEntity;
import com.shankephone.mi.model.StockBusinessApplyEntity;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 业务单据业务实体
 *
 * @author 司徒彬
 * @date 2018/7/12 13:36
 */
@Data
public class ApplyInfoVO  extends StockBusinessApplyEntity {
    private String  outWareHouseName;
    private String inWareHouseName;
    //入库人
    private String inStockUser;
    //出库人
    private String outUser;
    List<StockBusinessApplyDetailEntity> detailEntities;
    List<Map<String, Object>> details;
    List<Map<String, Object>> infoList;
}
