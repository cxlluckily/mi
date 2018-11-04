package com.shankephone.mi.supersys.dao;

import com.shankephone.mi.model.SysFunctionTreeEntity;
import com.shankephone.mi.supersys.dao.provider.MenuProvider;
import com.shankephone.mi.supersys.formbean.MenuFindEntity;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author 赵亮
 * @date 2018-07-24 9:37
 */
@Repository
public interface MenuDao
{
    @SelectProvider(type = MenuProvider.class, method = "getAllMenuList")
    List<Map<String,Object>> getAllMenuList(MenuFindEntity findEntity);

    @InsertProvider(type = MenuProvider.class, method = "insertOne")
    @Options(useGeneratedKeys = true, keyProperty = "functionTreeId")
    Integer insertOne(SysFunctionTreeEntity entity);

    @UpdateProvider(type = MenuProvider.class, method = "updateOne")
    Integer updateOne(SysFunctionTreeEntity entity);
}
