package com.shankephone.mi.supersys.dao;

import com.shankephone.mi.model.SysFunctionTreeEntity;
import com.shankephone.mi.supersys.dao.provider.FunctionTreeProvider;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 赵亮
 * @date 2018-07-23 15:16
 */
@Repository
public interface FunctionTreeDao
{
    @SelectProvider(type = FunctionTreeProvider.class, method = "getAllTreeList")
    List<SysFunctionTreeEntity> getAllTreeList(String status);

    @SelectProvider(type = FunctionTreeProvider.class, method = "getUserPermissionList")
    List<String> getUserPermissionList(Long userId);
}
