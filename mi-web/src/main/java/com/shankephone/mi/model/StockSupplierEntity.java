package com.shankephone.mi.model;

import com.shankephone.mi.common.model.BaseModel;
import lombok.Data;
import java.sql.Timestamp;


/**
 * stock_supplier  Bean
 *
 * @author 系统生成
 * @date 2018-07-05 15:47:07
 */
@Data
public class StockSupplierEntity extends BaseModel
{
	
    /**
     * 供应商ID
    **/
    private Long supplierId;
    /**
     * 运营主体ID
    **/
    //private Long operationSubjectId;
    /**
     * 供应商名称
    **/
    private String supplierName;
    /**
     * 拼音缩写
    **/
    private String abbreviation;
    /**
     * 品牌名称
    **/
    private String brandName;
    /**
     * 联系人
    **/
    private String contacts;
    /**
     * 联系方式
    **/
    private String contactInfo;
    /**
     * 地址
    **/
    private String address;
    /**
     * 电子邮件
    **/
    private String email;
    /**
     * 纳税人识别号
    **/
    private String taxpayerNumber;
    /**
     * 开户银行
    **/
    private String bankOfDeposit;
    /**
     * 银行账户
    **/
    private String bankAccount;
    /**
     * 划款户名
    **/
    private String accountName;
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

