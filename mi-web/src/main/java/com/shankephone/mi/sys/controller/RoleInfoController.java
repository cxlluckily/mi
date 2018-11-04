package com.shankephone.mi.sys.controller;

import com.shankephone.mi.common.model.Pager;
import com.shankephone.mi.common.vo.ResultVO;
import com.shankephone.mi.sys.formbean.RoleInfoFindEntity;
import com.shankephone.mi.sys.service.RoleInfoService;
import com.shankephone.mi.sys.vo.RoleInfoVO;
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

import java.util.Map;


/**
 * 角色管理Controller
 *
 * @author 司徒彬
 * @date 2018-06-21 11:12
 */
@Controller
@RequestMapping("/roleInfo")
public class RoleInfoController
{
    @Autowired
    private RoleInfoService roleInfoService;

    //@RequiresPermissions(value = {"jsgl", "plsqgl", "yhgl"}, logical = Logical.OR)
    @ResponseBody
    @RequestMapping(value = "/all", method = RequestMethod.POST)
    public ResultVO getAllRoles(@RequestBody RoleInfoFindEntity findEntity)
    {
        try
        {
            findEntity.validateUserKey();
            return ResultVOUtil.success(roleInfoService.getAllRoles(findEntity));
        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex);
        }
    }

    /**
     * 获取角色扉页数据
     *
     * @param findEntity the findEntity
     * @return the json object
     */

    //@RequiresPermissions(value = {"jsgl", "plsqgl", "yhgl"}, logical = Logical.OR)
    @ResponseBody
    @RequestMapping(value = "/getRolePagerInfo", method = RequestMethod.POST)
    public ResultVO<Pager<Map<String, Object>>> getRolePagerInfo(@RequestBody RoleInfoFindEntity findEntity)
    {
        try
        {
            findEntity.validateUserKey();
            Pager<Map<String, Object>> pager = roleInfoService.getRolePagerInfo(findEntity);
            return ResultVOUtil.success(pager);
        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex);
        }

    }

    /**
     * 根据 Id 查询角色信息
     *
     * @param findEntity the findEntity
     * @return the json object
     */

    //@RequiresPermissions(value = {"jsgl", "plsqgl", "yhgl"}, logical = Logical.OR)
    @ResponseBody
    @RequestMapping(value = "/getRoleInfo", method = RequestMethod.POST)
    public ResultVO getRoleInfo(@RequestBody RoleInfoFindEntity findEntity)
    {
        try
        {
            findEntity.validateUserKey();
            if (ObjectUtils.isEmpty(findEntity.getRoleId()))
            {
                return ResultVOUtil.paramError("roleId");
            }
            else
            {
                RoleInfoVO roleInfoVO = roleInfoService.getRoleInfo(findEntity.getRoleId());
                return ResultVOUtil.success(roleInfoVO);
            }

        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex, "根据 Id 查询角色信息出现异常");
        }
    }

    /**
     * 添加角色信息
     *
     * @param roleEntity the roleEntity
     * @return the json object
     */
    //@RequiresPermissions("jsgl")
    @ResponseBody
    @RequestMapping(value = "/addRoleInfo", method = RequestMethod.POST)
    public ResultVO addRoleInfo(@RequestBody RoleInfoVO roleEntity)
    {
        try
        {
            roleEntity.validateUserKey();
            if (ObjectUtils.isEmpty(roleEntity.getRoleName()))
            {
                //return ResultVOUtil.paramError("roleName");
                return ResultVOUtil.error(-1, "角色名称不能为空！");
            }
            else if (ObjectUtils.isEmpty(roleEntity.getFunctionTreeIds()))
            {
                return ResultVOUtil.error(-1, "角色权限不能为空！");
            }
            else if (ObjectUtils.isEmpty(roleEntity.getOperationSubjectId()))
            {
                // return ResultVOUtil.paramError("operationSubjectId");
                return ResultVOUtil.error(-1, "出现问题，请重新登录！");
            }
            else
            {
                return roleInfoService.addRoleInfo(roleEntity);
            }
        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex, "添加角色信息出现异常");
        }
    }

    /**
     * 更新角色信息
     *
     * @param roleEntity the roleEntity
     * @return the json object
     */
    //@RequiresPermissions("jsgl")
    @ResponseBody
    @RequestMapping(value = "/updateRoleInfo", method = RequestMethod.POST)
    public ResultVO updateRoleInfo(@RequestBody RoleInfoVO roleEntity)
    {
        try
        {
            roleEntity.validateUserKey();
            if (ObjectUtils.isEmpty(roleEntity.getRoleId()))
            {
                // return ResultVOUtil.paramError("roleId");

                return ResultVOUtil.error(-1, "出现问题，请重新登录！");
            }
            else if (ObjectUtils.isEmpty(roleEntity.getRoleName()))
            {
                return ResultVOUtil.error(-1, "角色名称不能为空！");
            }
            else if (ObjectUtils.isEmpty(roleEntity.getFunctionTreeIds()))
            {

                return ResultVOUtil.error(-1, "角色权限不能为空！");
            }
            else
            {
                return roleInfoService.updateRoleInfo(roleEntity);
            }
        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex, "更新角色信息出现异常");
        }
    }

    /**
     * 删除角色信息
     *
     * @param findEntity the findEntity
     * @return the json object
     */
    //@RequiresPermissions("jsgl")
    @ResponseBody
    @RequestMapping(value = "/deleteRoleInfo", method = RequestMethod.POST)
    public ResultVO deleteRoleInfo(@RequestBody RoleInfoVO findEntity)
    {
        try
        {
            findEntity.validateUserKey();
            if (ObjectUtils.isEmpty(findEntity.getRoleId()))
            {
                return ResultVOUtil.paramError("roleId");
            }
            else
            {
                roleInfoService.deleteRoleInfo(findEntity.getRoleId());
                return ResultVOUtil.success();
            }

        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex, "删除角色信息出现异常");
        }
    }

    /**
     * 验证角色名称是否存在
     *
     * @param findEntity the findEntity
     * @return the json object
     */
    //@RequiresPermissions("jsgl")
    @ResponseBody
    @RequestMapping(value = "/validateRoleNameExist", method = RequestMethod.POST)
    public ResultVO validateRoleNameExist(@RequestBody RoleInfoFindEntity findEntity)
    {
        try
        {
            findEntity.validateUserKey();
            if (ObjectUtils.isEmpty(findEntity.getUserKey()))
            {
                return ResultVOUtil.paramError("userKey");
            }
            else if (ObjectUtils.isEmpty(findEntity.getRoleId()))
            {
                return ResultVOUtil.paramError("roleId");
            }
            else if (ObjectUtils.isEmpty(findEntity.getOperationSubjectId()))
            {
                return ResultVOUtil.paramError("operationSubjectId");
            }
            else if (ObjectUtils.isEmpty(findEntity.getRoleName()))
            {
                return ResultVOUtil.paramError("roleName");
            }
            else
            {
                boolean isExist = roleInfoService.validateRoleNameExist(findEntity);
                if (!isExist)
                {
                    return ResultVOUtil.notExist();
                }
                else
                {
                    return ResultVOUtil.exist();
                }
            }

        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex, "验证角色名称是否存在出现异常");
        }
    }


}