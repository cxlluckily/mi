package com.shankephone.mi.model;

import com.shankephone.mi.common.model.BaseModel;
import lombok.Data;
import java.sql.Timestamp;


/**
 * stock_in_stock  Bean
 *
 * @author 系统生成
 * @date 2018-07-05 15:47:06
 */
@Data
public class StockInStockEntity extends BaseModel
{
	
    /**
     * 入库单ID
    **/
    private Long inStockId;
    /**
     * 业务申请ID
    **/
    private Long applyId;
    /**
     * 运营主体ID
    **/
    //private Long operationSubjectId;
    /**
     * 要入库仓库ID
    **/
    private Long inWarehouseId;
    /**
     * 单据号
    **/
    private String inStockApplyNo;
    /**
     * 入库人
    **/
    private String inStockUser;
    /**
     * 入库人ID
    **/
    private Long inStockUserId;
    /**
     * 入库日期
    **/
    private Timestamp inDate;
    /**
     * 入库类型
    **/
    private String inStockType;
    /**
     * 入库状态
    **/
    private String inStockStatus;
    /**
     * 添加人
    **/
    private String createUser;
    /**
     * 创建时间
    **/
    private Timestamp createTime;
    /**
     * 修改人
    **/
    private String modifyUser;
    /**
     * 修改时间
    **/
    private Timestamp modifyTime;

    private String outStockApplyNO;
    private String remark;
}

