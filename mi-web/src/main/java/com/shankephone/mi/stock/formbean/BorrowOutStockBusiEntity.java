package com.shankephone.mi.stock.formbean;

import com.shankephone.mi.common.model.BaseModel;
import lombok.Data;

import java.util.List;

/**
 * @author 赵亮
 * @date 2018-07-12 9:26
 */
@Data
public class BorrowOutStockBusiEntity extends BaseModel
{
    private OutStockApplyFormBean applyFormBean;
    private List<OutStockApplyDetailFormBean> detailFormBeans;
}
