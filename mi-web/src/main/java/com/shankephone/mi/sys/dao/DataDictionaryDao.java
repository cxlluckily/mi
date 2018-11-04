package com.shankephone.mi.sys.dao;

import com.shankephone.mi.model.SysDataDictionaryEntity;
import com.shankephone.mi.sys.dao.provider.DataDictionaryProvider;
import com.shankephone.mi.sys.formbean.DataDictFindEntity;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 数据字典
 * @author fengql
 * @date 2018年6月27日 上午10:19:39
 */
@Repository
public interface DataDictionaryDao {
	
	@SelectProvider(type = DataDictionaryProvider.class, method = "getPagerInfo")
	List<Map<String, Object>> getDictPagerInfo(DataDictFindEntity findEntity);

	@SelectProvider(type = DataDictionaryProvider.class, method = "getPagerTotal")
	int getPagerTotal(DataDictFindEntity findEntity);

	@SelectProvider(type = DataDictionaryProvider.class, method = "addDataDict")
	Integer addDataDict(SysDataDictionaryEntity entity);

	@SelectProvider(type = DataDictionaryProvider.class, method = "updateDataDict")
	Integer updateDataDict(SysDataDictionaryEntity entity);
	
	@SelectProvider(type = DataDictionaryProvider.class, method = "getDataDict")
	SysDataDictionaryEntity getDataDict(DataDictFindEntity findEntity);

	@SelectProvider(type = DataDictionaryProvider.class, method = "deleteDataDict")
	void deleteDataDict(DataDictFindEntity findEntity);

	@SelectProvider(type = DataDictionaryProvider.class, method = "deleteBatch")
	void deleteBatch(DataDictFindEntity findEntity);

	@SelectProvider(type = DataDictionaryProvider.class, method = "GetIsCodeExists")
	Integer GetIsCodeExists(SysDataDictionaryEntity entity);

}
