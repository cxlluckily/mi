package com.shankephone.mi.common.dao;

import com.shankephone.mi.common.dao.provider.FieldContentExistsProvider;
import com.shankephone.mi.common.model.FieldContentExistsFindEntity;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.stereotype.Repository;


/**
 * 查询数据是否存在Dao
 *
 * @author fengql
 * @date 2018年6月22日 上午11:44:31
 */
@Repository
public interface FieldContentExistsDao
{
    @SelectProvider(type = FieldContentExistsProvider.class, method = "selectFieldExists")
    Integer selectFieldExists(FieldContentExistsFindEntity entity);
}
