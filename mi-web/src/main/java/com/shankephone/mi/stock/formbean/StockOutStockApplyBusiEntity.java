package com.shankephone.mi.stock.formbean;

import com.shankephone.mi.model.StockOutStockApplyEntity;
import lombok.Data;

/**
 * @author 赵亮
 * @date 2018/8/24 15:29
 */
@Data
public class StockOutStockApplyBusiEntity extends StockOutStockApplyEntity
{
    //申请单号
    private String applyNo;
    //领用人
    private String applyUser;
    //出库仓库名称
    private String warehouseName;
    //审核人
    private String auditUser;

    private  String applyRemark;

}
