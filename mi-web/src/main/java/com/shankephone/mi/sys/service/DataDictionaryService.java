package com.shankephone.mi.sys.service;

import com.shankephone.mi.common.model.Pager;
import com.shankephone.mi.common.vo.ResultVO;
import com.shankephone.mi.model.SysDataDictionaryEntity;
import com.shankephone.mi.sys.formbean.DataDictFindEntity;

import java.util.Map;

public interface DataDictionaryService {

	Pager<Map<String,Object>> getDictPagerInfo(DataDictFindEntity findEntity);

	ResultVO addDataDict(SysDataDictionaryEntity findEntity);

	ResultVO updateDataDict(SysDataDictionaryEntity findEntity);

	void deleteDataDict(DataDictFindEntity findEntity);

	SysDataDictionaryEntity getDataDict(DataDictFindEntity findEntity);

	void deleteBatch(DataDictFindEntity findEntity);




}
