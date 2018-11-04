package com.shankephone.mi.supersys.controller;

import com.shankephone.mi.common.vo.ResultVO;
import com.shankephone.mi.model.SysFunctionTreeEntity;
import com.shankephone.mi.supersys.formbean.MenuFindEntity;
import com.shankephone.mi.supersys.service.MenuServvice;
import com.shankephone.mi.util.ResultVOUtil;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author 赵亮
 * @date 2018-07-24 9:36
 */
@Controller
@RequestMapping("/menu")
public class MenuController
{
    @Autowired
    private MenuServvice menuServvice;

    /**
     * 获取所有菜单数据
     * @author：赵亮
     * @date：2018-07-24
     * @param findEntity  the findEntity
     * @return the ResultVO
     */
    //@RequiresPermissions("SUPER_ADMIN")
    @ResponseBody
    @RequestMapping(value = "/getAllMenuList", method = RequestMethod.POST)
    public ResultVO getAllMenuList(@RequestBody MenuFindEntity findEntity)
    {
        try
        {
            return ResultVOUtil.success(menuServvice.getAllMenuList(findEntity));
        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex,"获取所有菜单数据出现异常");
        }
    }

    /**
     * 新增菜单
     * @author：赵亮
     * @date：2018-07-24
     * @param entity  the SysFunctionTreeEntity
     * @return the ResultVO
     */
    //@RequiresPermissions("SUPER_ADMIN")
    @ResponseBody
    @RequestMapping(value = "/insertOne", method = RequestMethod.POST)
    public ResultVO insertOne(@RequestBody SysFunctionTreeEntity entity)
    {
        try
        {
            return ResultVOUtil.success(menuServvice.insertOne(entity));
        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex,"新增菜单出现异常");
        }
    }

    /**
     * 修改菜单
     * @author：赵亮
     * @date：2018-07-26
     * @param entity  the SysFunctionTreeEntity
     * @return the ResultVO
     */
    //@RequiresPermissions("SUPER_ADMIN")
    @ResponseBody
    @RequestMapping(value = "/updateOne", method = RequestMethod.POST)
    public ResultVO updateOne(@RequestBody SysFunctionTreeEntity entity)
    {
        try
        {
            return ResultVOUtil.success(menuServvice.updateOne(entity));
        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex,"修改菜单出现异常");
        }
    }
}