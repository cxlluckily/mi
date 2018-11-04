package com.shankephone.mi.supplier.formbean;

import com.shankephone.mi.common.model.BaseFindEntity;

import lombok.Data;

@Data
public class SupplierFindEntity extends BaseFindEntity{
	
	/**
	 * 供应商ID
	 */
	private Long supplierId;
	/**
	 * 供应商名称
	 */
	private String supplierName;
	/**
	 * 品牌名称
	 */
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
     * 状态
     */
    private String status;

}
