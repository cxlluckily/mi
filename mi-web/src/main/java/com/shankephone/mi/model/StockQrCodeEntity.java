package com.shankephone.mi.model;

import lombok.Data;
import com.shankephone.mi.common.model.BaseModel;

import java.util.Date;


/**
 * stock_qr_code  Bean
 *
 * @author 系统生成
 * @date 2018-07-24 10:13:00
 */
@Data
public class StockQrCodeEntity extends BaseModel
{
	
    /**
     * 二维码ID
    **/
    private Integer qrCodeId;
    /**
     * 二维码号码
    **/
    private String qrCode;
    /**
     * 库存ID
    **/
    private Long stockId;
    /**
     * 备件ID
     **/
    private Long sparePartId;
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
    private Date createTime;
    /**
     * 修改人
    **/
    private String modifyUser;
    /**
     * 修改时间
    **/
    private Date modifyTime;


    private Long operationSubjectId;
   
}

