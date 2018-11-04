package com.shankephone.mi.common.model;

import lombok.Data;

/**
 * 公共数据查询实体
 * @author fengql
 * @date 2018年6月25日 上午9:53:22
 */
@Data
public class FieldContentExistsFindEntity extends BaseFindEntity{
	
	//主键ID
	private String idName;
	//主键ID值
	private Long idValue;
	//字段名称
	private String fieldName;
	//字段值
	private String fieldValue;
	//运营主体id
	//private long operationSubjectId=0;
	//表名称
	private String tablename;

	// 其它条件 and name=value
	private  String condition="";

	
}
