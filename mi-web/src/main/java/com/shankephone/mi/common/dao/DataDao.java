package com.shankephone.mi.common.dao;

import com.shankephone.mi.common.dao.provider.DataProvider;
import com.shankephone.mi.common.formbean.ValidateIsRepeatFindEntity;
import com.shankephone.mi.common.model.DataEntity;
import com.shankephone.mi.common.model.DataFindEntity;
import com.shankephone.mi.sys.formbean.DataDictFindEntity;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 获取下拉数据Dao
 *
 * @author fengql
 * @date 2018年6月22日 上午11:44:31
 */
@Repository
public interface DataDao
{


    @SelectProvider(type = DataProvider.class, method = "loadData")
    List<DataEntity> loadData(DataFindEntity entity);

    @SelectProvider(type = DataProvider.class, method = "loadTreeData")
    List<Map<String, Object>> loadTreeData(DataFindEntity dataEntity);

    @SelectProvider(type = DataProvider.class, method = "loadDict")
    List<DataEntity> loadDict(DataDictFindEntity dataEntity);

    @SelectProvider(type = DataProvider.class, method = "findLeafs")
    List<Map<String, Object>> findLeafs(DataFindEntity dataEntity);

    @SelectProvider(type = DataProvider.class, method = "loadDataList")
    List<Map<String, Object>> loadDataList(DataFindEntity dataEntity);

    @SelectProvider(type = DataProvider.class, method = "getCountByFindEntity")
    Integer getCountByFindEntity(ValidateIsRepeatFindEntity findEntity);
}
