package com.shankephone.mi.supersys.controller;

import com.shankephone.mi.common.vo.ResultVO;
import com.shankephone.mi.model.SysAdminEntity;
import com.shankephone.mi.security.model.UserLoginInfo;
import com.shankephone.mi.supersys.formbean.DeleteAdminFormBean;
import com.shankephone.mi.supersys.formbean.SuperAdminFindEntity;
import com.shankephone.mi.supersys.service.SuperAdminService;
import com.shankephone.mi.util.*;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 超级管理员管理
 *
 * @author 赵亮
 * @date 2018-07-23 9:12
 */
@Controller
@RequestMapping("/superAdmin")
public class SuperAdminController
{
    @Autowired
    private SuperAdminService superAdminService;

    /**
     * 超级管理员添加
     *
     * @param entity the SysAdminEntity
     * @return the ResultVO
     * @author：赵亮
     * @date：2018-07-23
     */
    //@RequiresPermissions("SUPER_ADMIN")
    @ResponseBody
    @RequestMapping(value = "/insertSuperAdmin", method = RequestMethod.POST)
    public ResultVO insertSuperAdmin(@RequestBody SysAdminEntity entity)
    {
        try
        {
            return superAdminService.insertSuperAdmin(entity);
        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex, "超级管理员添加出现异常");
        }
    }

    /**
     * 更新超级管理员
     *
     * @param entity the SysAdminEntity
     * @return the ResultVO
     * @author：赵亮
     * @date：2018-07-23
     */
    //@RequiresPermissions("SUPER_ADMIN")
    @ResponseBody
    @RequestMapping(value = "/updateSuperAdmin", method = RequestMethod.POST)
    public ResultVO updateSuperAdmin(@RequestBody SysAdminEntity entity)
    {
        try
        {
            if (ObjectUtils.isEmpty(entity.getAdminId()))
            {
                return ResultVOUtil.paramError("adminId");
            }
            return ResultVOUtil.success(superAdminService.updateSuperAdmin(entity));
        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex, "更新超级管理员出现异常");
        }
    }

    /**
     * 查询管理员list
     *
     * @param findEntity the findEntity
     * @return the ResultVO
     * @author：赵亮
     * @date：2018-07-23
     */
    //@RequiresPermissions("SUPER_ADMIN")
    @ResponseBody
    @RequestMapping(value = "/getSuperAdminList", method = RequestMethod.POST)
    public ResultVO getSuperAdminList(@RequestBody SuperAdminFindEntity findEntity)
    {
        try
        {
            return ResultVOUtil.success(superAdminService.getSuperAdminList(findEntity));
        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex, "查询管理员list出现异常");
        }
    }

    /**
     * 初始化密码
     *
     * @param findEntity the findEntity
     * @return the ResultVO
     * @author：赵亮
     * @date：2018-07-23
     */
    //@RequiresPermissions("SUPER_ADMIN")
    @ResponseBody
    @RequestMapping(value = "/initPassword", method = RequestMethod.POST)
    public ResultVO initPassword(@RequestBody SuperAdminFindEntity findEntity)
    {
        try
        {
            return ResultVOUtil.success(superAdminService.initPassword(findEntity));
        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex, "初始化密码出现异常");
        }
    }


    /**
     * 管理员登陆
     *
     * @param
     * @return the ResultVO
     * @author：赵亮
     * @date：2018-07-23
     */
    @ResponseBody
    @RequestMapping(value = "/superAdminLogin", method = RequestMethod.POST)
    public ResultVO superAdminLogin(HttpServletRequest request, HttpServletResponse response, String username, String password)
    {
        try
        {
            SysAdminEntity entity = new SysAdminEntity();
            entity.setUserName(username);
            entity.setPassword(password);
            if (ObjectUtils.isEmpty(entity.getUserName()))
            {
                return ResultVOUtil.paramError("userName");
            }
            if (ObjectUtils.isEmpty(entity.getPassword()))
            {
                return ResultVOUtil.paramError("password");
            }
            return superAdminService.superAdminLogin(entity, request, response);
        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex, "管理员登陆出现异常");
        }
    }

    /**
     * 管理员登陆
     *
     * @param
     * @return the ResultVO
     * @author：赵亮
     * @date：2018-07-23
     */
    @ResponseBody
    @RequestMapping(value = "/superAdminAutoLogin", method = RequestMethod.POST)
    public ResultVO superAdminAutoLogin(HttpServletRequest request)
    {
        try
        {
            String userToken = CookieUtils.getCookieValue(request, "token");
            if (StringUtils.isNotEmpty(userToken))
            {
                UserLoginInfo userinfo = SessionMap.getUserInfo(userToken);
                if (userinfo != null)
                {
                    request.getSession().setAttribute("userId", userinfo.getUserId());
                    request.getSession().setAttribute("userKey", userToken);
                    request.getSession().setAttribute("userName", userinfo.getUserName());
                    request.getSession().setAttribute("userType", "sysAdmin");
                    return ResultVOUtil.success(userinfo);
                }
            }
            return ResultVOUtil.error(0, "请登录！");
        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex, "管理员登陆出现异常");
        }
    }

    /**
     * 批量删除
     *
     * @param
     * @return the ResultVO
     * @author：郝伟州
     * @date：2018年8月14日19:54:46
     */
    @ResponseBody
    @RequestMapping(value = "/deleteAdmin", method = RequestMethod.POST)
    public ResultVO deleteAdmin(@RequestBody DeleteAdminFormBean entity)
    {
        try
        {
            return ResultVOUtil.success(superAdminService.deleteAdmin(entity));
        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex, "批量删除出现异常");
        }
    }

}
