package com.shankephone.mi.sys.dao;

import com.shankephone.mi.model.SysFunctionTreeEntity;
import com.shankephone.mi.sys.dao.provider.SysFunctionTreeProvider;
import com.shankephone.mi.sys.formbean.FunctionTreeFindEntity;

import com.shankephone.mi.sys.vo.UserTreeFindEntity;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author 赵亮
 * @date 2018-06-20 16:30
 */
@Repository
public interface SysFunctionTreeDao
{
    @SelectProvider(type= SysFunctionTreeProvider.class, method = "findByuserId")
    List<SysFunctionTreeEntity> findByuserId(UserTreeFindEntity findEntity);

    @SelectProvider(type= SysFunctionTreeProvider.class, method = "findFunctionTree")
	List<Map<String, Object>> findFunctionTree(FunctionTreeFindEntity entity);
}
