package com.shankephone.mi.model;

import com.shankephone.mi.common.model.BaseModel;
import lombok.Data;
import java.sql.Timestamp;


/**
 * stock_out__stock_apply  Bean
 *
 * @author 系统生成
 * @date 2018-07-05 15:47:07
 */
@Data
public class StockOutStockApplyEntity extends BaseModel
{
	
    /**
     * 出库单ID
    **/
    private Long outStockApplyId;
    /**
     * 业务申请ID
    **/
    private Long applyId;
    /**
     * 运营主体ID
    **/
    //private Long operationSubjectId;
    /**
     * 要出库仓库ID
    **/
    private Long outWarehouseId;
    /**
     * 订单类型
    **/
    private String outOrderType;
    /**
     * 单据号
    **/
    private String outStockApplyNO;
    /**
     * 出库人
    **/
    private String outUser;
    /**
     * 出库人ID
    **/
    private Long outUserId;
    /**
     * 出库时间
    **/
    private Timestamp outDate;
    /**
     * 出库备注
    **/
    private String remark;
    /**
     * 订单状态
    **/
    private String outApplyStatus;
    /**
     * 修改人
    **/
    private String modifyUser;
    /**
     * 修改时间
    **/
    private Timestamp modifyTime;
    /**
     * 申请时间
     **/
    private Timestamp applyDate;
}

