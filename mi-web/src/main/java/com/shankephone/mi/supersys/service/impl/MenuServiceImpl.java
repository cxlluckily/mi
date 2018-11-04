package com.shankephone.mi.supersys.service.impl;

import com.shankephone.mi.common.model.Pager;
import com.shankephone.mi.model.SysFunctionTreeEntity;
import com.shankephone.mi.supersys.dao.MenuDao;
import com.shankephone.mi.supersys.formbean.MenuFindEntity;
import com.shankephone.mi.supersys.service.MenuServvice;
import com.shankephone.mi.util.DataSwitch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author 赵亮
 * @date 2018-07-24 9:38
 */
@Service
public class MenuServiceImpl implements MenuServvice
{
    @Autowired
    private MenuDao menuDao;
    /**
     * 获取所有菜单数据
     *
     * @param findEntity
     * @author：赵亮
     * @date：2018-07-24 9:40
     */
    @Override
    public Pager<Map<String, Object>> getAllMenuList(MenuFindEntity findEntity)
    {
        try
        {
            try
            {
                List<Map<String, Object>> entitys = DataSwitch.convertListToTree(menuDao.getAllMenuList(findEntity),-1,"functionTreeId","parentTreeId","children");
                Pager pager = new Pager<>(0, entitys);
                return pager;
            }
            catch (Exception ex)
            {
                throw ex;
            }
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    /**
     * 新增菜单
     *
     * @param entity
     * @author：赵亮
     * @date：2018-07-24 9:48
     */
    @Override
    public Integer insertOne(SysFunctionTreeEntity entity)
    {
        try
        {
            return menuDao.insertOne(entity);
        }
        catch(Exception ex)
        {
            throw ex;
        }
    }

    /**
     * 修改菜单
     *
     * @param entity
     * @author：赵亮
     * @date：2018-07-26 11:33
     */
    @Override
    public Integer updateOne(SysFunctionTreeEntity entity)
    {
        try
        {
            return menuDao.updateOne(entity);
        }
        catch(Exception ex)
        {
            throw ex;
        }
    }
}
