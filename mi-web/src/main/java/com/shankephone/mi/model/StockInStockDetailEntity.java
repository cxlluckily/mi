package com.shankephone.mi.model;

import com.shankephone.mi.common.model.BaseModel;
import lombok.Data;
import java.math.BigDecimal;
import java.sql.Timestamp;


/**
 * stock_in_stock_detail  Bean
 *
 * @author 系统生成
 * @date 2018-07-05 15:47:06
 */
@Data
public class StockInStockDetailEntity extends BaseModel
{
	
    /**
     * 入库详单ID
    **/
    private Long inStockDetailId;
    /**
     * 入库单ID
    **/
    private Long inStockId;
    /**
     * 库存ID（返还和借用）
     **/
    private Long stockId;
    /**
     * 备件ID
    **/
    private Long sparePartId;
    /**
     * 库存类型
    **/
    private String inventoryType;
    /**
     * 计划入库数量
    **/
    private Integer inStockAcount;
    /**
     * 已入库数量
    **/
    private Integer alreadyInStockAcount;
    /**
     * 单价
    **/
    private BigDecimal price;
    /**
     * 备注
    **/
    private String remark;
    /**
     * 货架Id
    **/
    private Long goodsShelvesId;

    /**
     * 供应商Id
     */
    private Long supplierId;
    /**
     * 备件状态
     */
    private String status;
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

   
}

