package com.shankephone.mi.sys.formbean;

import lombok.Data;

import com.shankephone.mi.common.model.BaseFindEntity;

/**
 * 数据字典查询实体
 * @author fengql
 * @date 2018年6月27日 上午10:24:11
 */
@Data
public class DataDictFindEntity extends BaseFindEntity {
	
	private Long dataDictionaryId;
	private String dataLabel;
	private String code;
	private String name;
	private String status;
	private String ids;
	
}
