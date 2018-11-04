package com.shankephone.mi.stock.formbean;

import com.shankephone.mi.common.model.BaseFindEntity;
import com.shankephone.mi.security.model.UserLoginInfo;
import com.shankephone.mi.util.SessionMap;
import lombok.Data;

import java.util.List;

/**
 * @author 赵亮
 * @date 2018-07-11 10:07
 */
@Data
public class OutStockApplyFindEntity extends BaseFindEntity
{
    public String outApplyStatus;
    public String outOrderType;
    public String outStockApplyNO;
    public String outWarehouseId;
    public String supplierId;
    public String status;
    /**
     *领取人
     *@author：赵亮
     *@date：2018-07-11 10:09
    */
    public String applyUser;
    public String applyNo;
    public String houseNo;
    public String shelfNumber;
    private String sparePartId;
    private String partName;
    private String warehouseId;
    public List<Long> getWareHouseIds()
    {
        UserLoginInfo userinfo = SessionMap.getUserInfo(this.getUserKey());
        return userinfo.getWarehouses();
    }
}
