package com.shankephone.mi.model;

import lombok.Data;
import com.shankephone.mi.common.model.BaseModel;

import java.util.Date;


/**
 * stock_user_device  Bean
 *
 * @author 系统生成
 * @date 2018-08-08 18:15:07
 */
@Data
public class StockUserDeviceEntity extends BaseModel
{
	
    /**
     * 人员持有设备ID
    **/
    private Long userDeviceId;
    /**
     * 用户Id
    **/
    private Long userId;
    /**
     * 库存ID（有可能没有）
     **/
    private Long stockId;
    /**
     * 备件ID
    **/
    private Long sparePartId;
    /**
     * 数量
    **/
    private Integer count;

    /**
     * 添加人
    **/
    private String createUser;
    /**
     * 创建时间
    **/
    private Date createTime;

}

