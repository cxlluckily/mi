package com.shankephone.mi.common.model;

import lombok.Getter;
import lombok.Setter;

import com.shankephone.mi.common.model.BaseModel;

/**
 * 公共数据实体
 * @author fengql
 * @date 2018年6月25日 上午10:29:35
 */
@Getter
@Setter
public class DataEntity extends BaseModel{
	
	private static final long serialVersionUID = 1L;

	private Long id;
	
	private String code;
	
	private String name;
	
	private String dataLabel;
	
}
