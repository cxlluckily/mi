package com.shankephone.mi.common.model;

import lombok.Data;

/**
 * 公共数据查询实体
 * @author fengql
 * @date 2018年6月25日 上午9:53:22
 */
@Data
public class DataFindEntity extends BaseFindEntity{
	
	//外键关联的ID
	private Long entityId;
	//查询的数据分类，与DataCategoryEnum枚举类型对应
	private String type;
	//数据字典标签
	private String code;
	//数据返回的展示模式，包括树状和列表，对应值为list和tree
	private String showMode;
	//数据范围
	private String range;
	
}
