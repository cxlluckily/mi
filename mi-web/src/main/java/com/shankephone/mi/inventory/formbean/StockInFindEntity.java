package com.shankephone.mi.inventory.formbean;

import com.shankephone.mi.common.model.BaseFindEntity;
import com.shankephone.mi.security.model.UserLoginInfo;
import com.shankephone.mi.util.SessionMap;
import lombok.Data;

import java.util.List;

/**
 * 入库申请查询实体
 *
 * @author 司徒彬
 * @date 2018/7/17 14:30
 */
@Data
public class StockInFindEntity extends BaseFindEntity {
    private Long inStockId;
    private Long  warehouseId;
    private String inStockApplyNo;
    private String inStockStatus;
    private String inStockType;
    private String applyUser;
    private String outStockApplyNO;
    public List<Long> getWareHouseIds()
    {
        UserLoginInfo userinfo = SessionMap.getUserInfo(this.getUserKey());
        return userinfo.getWarehouses();
    }
}
