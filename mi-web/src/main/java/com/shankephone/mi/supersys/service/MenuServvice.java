package com.shankephone.mi.supersys.service;

import com.shankephone.mi.common.model.Pager;
import com.shankephone.mi.model.SysFunctionTreeEntity;
import com.shankephone.mi.supersys.formbean.MenuFindEntity;

import java.util.Map;

/**
 * @author 赵亮
 * @date 2018-07-24 9:37
 */
public interface MenuServvice
{
    /**
     *获取所有菜单数据
     *@author：赵亮
     *@date：2018-07-24 9:40
    */
    Pager<Map<String, Object>> getAllMenuList(MenuFindEntity findEntity);

    /**
     *新增菜单
     *@author：赵亮
     *@date：2018-07-24 10:00
    */
    Integer insertOne(SysFunctionTreeEntity entity);

    /**
     *修改菜单
     *@author：赵亮
     *@date：2018-07-26 22:19
    */
    Integer updateOne(SysFunctionTreeEntity entity);
}
