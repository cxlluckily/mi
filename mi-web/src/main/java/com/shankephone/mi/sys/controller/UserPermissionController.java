/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.shankephone.mi.sys.controller;

import com.shankephone.mi.common.vo.ResultVO;
import com.shankephone.mi.sys.formbean.UserPermissionFindEntity;
import com.shankephone.mi.sys.service.RolePermissionService;
import com.shankephone.mi.sys.service.UserPermissionService;
import com.shankephone.mi.sys.vo.UserPermissionVo;
import com.shankephone.mi.util.ObjectUtils;
import com.shankephone.mi.util.ResultVOUtil;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * 用户授权Controller
 *
 * @author 司徒彬
 * @date 2018/6/27 10:37
 */
@Controller
@RequestMapping("/userPermission")
public class UserPermissionController
{
    @Autowired
    private UserPermissionService userPermissionService;
    @Autowired
    private RolePermissionService rolePermissionService;

    /**
     * 获取用户批量授权初始化页面信息
     *
     * @param findEntity the findEntity
     * @return the json object
     */
    //@RequiresPermissions(value = {"yhgl", "plsqgl"}, logical = Logical.OR)
    @ResponseBody
    @RequestMapping(value = "/getInitPageDataForBatchAuthorization", method = RequestMethod.POST)
    public ResultVO getInitPageDataForBatchAuthorization(@RequestBody UserPermissionFindEntity findEntity)
    {
        try
        {
            findEntity.validateUserKey();
            Map<String, Object> data = userPermissionService.getInitPageInfoForBatchAuthorization(findEntity);
            return ResultVOUtil.success(data);
        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex, "获取用户批量授权初始化页面信息出现异常");
        }
    }

    /**
     * 批量授权添加
     *
     * @param userPermissionVo the userPermissionVo
     * @return the json object
     */
    //@RequiresPermissions(value = {"yhgl", "plsqgl"}, logical = Logical.OR)
    @ResponseBody
    @RequestMapping(value = "/insertBatchAuthorizationInfo", method = RequestMethod.POST)
    public ResultVO insertBatchAuthorizationInfo(@RequestBody UserPermissionVo userPermissionVo)
    {
        try
        {
            userPermissionVo.validateUserKey();
            if (ObjectUtils.isEmpty(userPermissionVo.getUserIds()))
            {
                return ResultVOUtil.paramError("userIds");
            }
            else if (ObjectUtils.isEmpty(userPermissionVo.getRoleIds()))
            {
                return ResultVOUtil.paramError("roleIds");
            }
            else
            {
                userPermissionService.insertBatchAuthorizationInfo(userPermissionVo);
                return ResultVOUtil.success();
            }

        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex, "批量授权添加出现异常");
        }
    }

    /**
     * 根据 用户 Id 获取授权信息
     *
     * @param findEntity the findEntity
     * @return the json object
     */
    //@RequiresPermissions(value = {"yhgl", "plsqgl"}, logical = Logical.OR)
    @ResponseBody
    @RequestMapping(value = "/getUserAuthorizationInfoByUserId", method = RequestMethod.POST)
    public ResultVO getUserAuthorizationInfoByUserId(@RequestBody UserPermissionFindEntity findEntity)
    {
        try
        {
            findEntity.validateUserKey();
            if (ObjectUtils.isEmpty(findEntity.getUserId()))
            {
                return ResultVOUtil.paramError("userId");
            }
            else
            {
                Map<String, Object> result = userPermissionService.getUserAuthorizationInfoByUserId(findEntity);
                return ResultVOUtil.success(result);
            }

        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex, "根据 用户 Id 获取授权信息出现异常");
        }
    }

    /**
     * 根据用户 Id 单个授权
     *
     * @param userPermissionVo the userPermissionVo
     * @return the json object
     */
    //@RequiresPermissions(value = {"yhgl", "plsqgl"}, logical = Logical.OR)
    @ResponseBody
    @RequestMapping(value = "/authorizationByUserId", method = RequestMethod.POST)
    public ResultVO authorizationByUserId(@RequestBody UserPermissionVo userPermissionVo)
    {
        try
        {
            userPermissionVo.validateUserKey();
            if (ObjectUtils.isEmpty(userPermissionVo.getUserId()))
            {
                return ResultVOUtil.paramError("userId");
            }
            else if (ObjectUtils.isEmpty(userPermissionVo.getRoleIds()))
            {
                return ResultVOUtil.paramError("roleIds");
            }
            else
            {
                userPermissionService.authorizationByUserId(userPermissionVo);
                return ResultVOUtil.success();
            }

        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex, "根据用户 Id 单个授权出现异常");
        }
    }

    /**
     * 根据盘库管理节点权限获取用户列表
     *
     * @param userPermissionVo the userPermissionVo
     * @return the json object
     */
    @ResponseBody
    @RequestMapping(value = "/findUserByPermission", method = RequestMethod.POST)
    public ResultVO findUserByPermission(@RequestBody UserPermissionVo userPermissionVo)
    {
        try
        {
            userPermissionVo.validateUserKey();

            List<String> permissionCode=new ArrayList<>();
            permissionCode.add("'pkgl'");
            permissionCode.add("'kcpd'");
            userPermissionVo.setPermissionCodes(permissionCode);
            return ResultVOUtil.success(rolePermissionService.findUserByPermission(userPermissionVo));


        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex, "根据盘库管理节点权限获取用户列表出现异常");
        }
    }

    /**
     * 根据盘库管理节点权限和仓库id获取用户列表
     *
     * @param userPermissionVo the userPermissionVo
     * @return the json object
     */
    @ResponseBody
    @RequestMapping(value = "/findUserByWarehousePermission", method = RequestMethod.POST)
    public ResultVO findUserByWarehousePermission(@RequestBody UserPermissionVo userPermissionVo)
    {
        try
        {
            userPermissionVo.validateUserKey();
            if (ObjectUtils.isEmpty(userPermissionVo.getWarehouseId()))
            {
                return ResultVOUtil.paramError("warehouseId");
            }
            List<String> permissionCode=new ArrayList<>();
            permissionCode.add("'pkgl'");
            permissionCode.add("'kcpd'");
            userPermissionVo.setPermissionCodes(permissionCode);
            return ResultVOUtil.success(rolePermissionService.findUserByWarehousePermission(userPermissionVo));
        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex, "根据盘库管理节点权限获取用户列表出现异常");
        }
    }

}