package com.shankephone.mi.model;

import com.shankephone.mi.common.model.BaseModel;
import lombok.Data;
import java.sql.Timestamp;
import java.math.BigDecimal;


/**
 * stock_stock  Bean
 *
 * @author 系统生成
 * @date 2018-07-05 15:47:07
 */
@Data
public class StockStockEntity extends BaseModel
{
	
    /**
     * 库存ID
    **/
    private Long stockId;
    /**
     * 仓库ID
     **/
    private Long warehouseId;
    /**
     * 运营主体ID
    **/
    //private Long operationSubjectId;

    private Long inStockDetailId;
    /**
     * 供应商ID
    **/
    private Long supplierId;

    /**
     * 货架ID
    **/
    private Long goodsShelvesId;
    /**
     * 备件ID
    **/
    private Long sparePartId;
    /**
     * 入库日期
    **/
    private Timestamp inDate;

    /**
     * 设备编号
    **/
    private String equipmentNO;

    /**
     * 系统二维码号
    **/
    private String qrCode;
    /**
     * 设备序列号
    **/
    private String serialNumber;
    /**
     * 库存总数量
    **/
    private Integer account;
    /**
     * 单价
    **/
    private BigDecimal price;
    /**
     * 库存类型
    **/
    private String inventoryType;
    /**
     * 备注
    **/
    private String remark;
    /**
     * 设备状态
    **/
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
    /**
     * 备用字段1
    **/
    private String spareField1;
    /**
     * 备用字段2
    **/
    private String spareField2;
    /**
     * 备用字段3
    **/
    private String spareField3;
    /**
     * 备用字段4
    **/
    private String spareField4;
    /**
     * 备用字段5
    **/
    private String spareField5;
    /**
     * 备用字段6
    **/
    private String spareField6;
    /**
     * 备用字段7
    **/
    private String spareField7;
    /**
     * 备用字段8
    **/
    private String spareField8;
    /**
     * 备用字段9
    **/
    private String spareField9;
    /**
     * 备用字段10
    **/
    private String spareField10;

   
}

