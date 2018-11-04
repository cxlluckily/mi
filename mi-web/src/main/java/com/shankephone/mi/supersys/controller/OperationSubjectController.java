package com.shankephone.mi.supersys.controller;

import com.shankephone.mi.common.vo.ResultVO;
import com.shankephone.mi.model.SysOperationSubjectEntity;
import com.shankephone.mi.supersys.formbean.OperationSubjectFindEntity;
import com.shankephone.mi.supersys.service.OperationSubjectService;
import com.shankephone.mi.util.ObjectUtils;
import com.shankephone.mi.util.ResultVOUtil;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 运营主体管理
 * @author 赵亮
 * @date 2018-07-23 13:34
 */
@Controller
@RequestMapping("/subject")
public class OperationSubjectController
{
    @Autowired
    private OperationSubjectService operationSubjectService;
    /**
     * 返回所有地区信息
     * @author：赵亮
     * @date：2018-07-23
     * @param
     * @return the ResultVO
     */
    //@RequiresPermissions("SUPER_ADMIN")
    @ResponseBody
    @RequestMapping(value = "/getAllRegion", method = RequestMethod.POST)
    public ResultVO getAllRegion()
    {
        try
        {

            return ResultVOUtil.success(operationSubjectService.getAllRegion());
        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex,"返回所有地区信息出现异常");
        }
    }

    /**
     * 新增运营主体
     *
     * @param entity the SysOperationSubjectEntity
     * @return the ResultVO
     * @author：赵亮
     * @date：2018-07-23
     */
    //@RequiresPermissions("SUPER_ADMIN")
    @ResponseBody
    @RequestMapping(value = "/insertOne", method = RequestMethod.POST)
    public ResultVO insertOne(@RequestBody SysOperationSubjectEntity entity)
    {
        try
        {
            return operationSubjectService.insertOne(entity);
        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex, "新增运营主体出现异常");
        }
    }

    /**
     * 更新运营主体
     *
     * @param entity the SysOperationSubjectEntity
     * @return the ResultVO
     * @author：赵亮
     * @date：2018-07-23
     */
    //@RequiresPermissions("SUPER_ADMIN")
    @ResponseBody
    @RequestMapping(value = "/updateOne", method = RequestMethod.POST)
    public ResultVO updateOne(@RequestBody SysOperationSubjectEntity entity)
    {
        try
        {
            if (ObjectUtils.isEmpty(entity.getOperationSubjectId()))
            {
                return ResultVOUtil.paramError("operationSubjectId");
            }
            return ResultVOUtil.success(operationSubjectService.updateOne(entity));
        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex, "更新运营主体出现异常");
        }
    }

    /**
     * 查询运营主体用户信息list
     *
     * @param findEntity the findEntity
     * @return the ResultVO
     * @author：赵亮
     * @date：2018-07-23
     */
    //@RequiresPermissions("SUPER_ADMIN")
    @ResponseBody
    @RequestMapping(value = "/getOperationList", method = RequestMethod.POST)
    public ResultVO getOperationList(@RequestBody OperationSubjectFindEntity findEntity)
    {
        try
        {
            return ResultVOUtil.success(operationSubjectService.getOperationList(findEntity));
        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex, "查询list出现异常");
        }
    }


    /**
     * 批量初始化运营主体管理员密码
     *
     * @param findEntity the findEntity
     * @return the ResultVO
     * @author：赵亮
     * @date：2018-06-25
     */
    //@RequiresPermissions("SUPER_ADMIN")
    @ResponseBody
    @RequestMapping(value = "/updateAdminPassword", method = RequestMethod.POST)
    public ResultVO updateAdminPassword(@RequestBody OperationSubjectFindEntity findEntity)
    {
        try
        {
            findEntity.validateUserKey();
            if (ObjectUtils.isEmpty(findEntity.getOperationSubjectIds()))
            {
                return ResultVOUtil.paramError("operationSubjectIds");
            }
            return ResultVOUtil.success(operationSubjectService.updateAdminPassword(findEntity));
        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex, "批量初始化运营主体管理员密码出现异常");
        }
    }
}
