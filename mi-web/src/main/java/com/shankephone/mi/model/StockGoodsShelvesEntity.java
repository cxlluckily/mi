package com.shankephone.mi.model;

import com.shankephone.mi.common.model.BaseModel;
import lombok.Data;
import java.sql.Timestamp;


/**
 * stock_goods_shelves  Bean
 *
 * @author 系统生成
 * @date 2018-07-05 15:47:06
 */
@Data
public class StockGoodsShelvesEntity extends BaseModel
{
	
    /**
     * 货架ID
    **/
    private Long goodsShelvesId;
    /**
     * 仓库ID
    **/
    private Long warehouseId;
    /**
     * 房间号
    **/
    private String houseNo;
    /**
     * 货柜类型
    **/
    private String containerType;
    /**
     * 货架编号
    **/
    private String shelfNumber;
    /**
     * 备注
    **/
    private String remark;
    /**
     * 状态
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

   
}

