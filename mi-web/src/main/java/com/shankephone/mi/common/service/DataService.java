package com.shankephone.mi.common.service;

import com.shankephone.mi.common.formbean.ValidateIsRepeatFindEntity;
import com.shankephone.mi.common.model.DataEntity;
import com.shankephone.mi.common.model.DataFindEntity;
import com.shankephone.mi.sys.formbean.DataDictFindEntity;

import java.util.List;
import java.util.Map;

public interface DataService {

	List<DataEntity> loadData(DataFindEntity dataEntity);

	List<Map<String, Object>> loadTreeData(DataFindEntity dataEntity);

	Object loadDict(DataDictFindEntity dataEntity);
 
	List<Map<String, Object>> getData(DataFindEntity dataEntity);  
	
	List<Map<String, Object>> getToUpTree(DataFindEntity dataEntity) ;

	List<Map<String, Object>> getWarehousesByUserId(DataFindEntity dataEntity);

	Integer getCountByFindEntity(ValidateIsRepeatFindEntity findEntity);
}
