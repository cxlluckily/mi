package com.shankephone.mi.sys.controller;

import com.shankephone.mi.common.model.BaseFindEntity;
import com.shankephone.mi.common.vo.ResultVO;
import com.shankephone.mi.model.SysUserEntity;
import com.shankephone.mi.sys.service.PersonalInformationService;
import com.shankephone.mi.sys.vo.UserInfoVO;
import com.shankephone.mi.util.DataSwitch;
import com.shankephone.mi.util.ObjectUtils;
import com.shankephone.mi.util.ResultVOUtil;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;


/**
 * 个人信息Controller
 *
 * @author 司徒彬
 * @date 2018/6/28 10:06
 */
@Controller
@RequestMapping("/personalInformation")
public class PersonalInformationController
{

    @Autowired
    private PersonalInformationService personalInformationService;

    /**
     * 获取个人信息
     *
     * @return the json object
     */
    @ResponseBody
    @RequestMapping(value = "/gerPersonalInfo", method = RequestMethod.POST)
    public ResultVO gerPersonalInfo(@RequestBody BaseFindEntity findEntity)
    {
        try
        {
            findEntity.validateUserKey();
            Map<String, Object> user = personalInformationService.gerPersonalInfo(findEntity.getOperationUserId());
            return ResultVOUtil.success(user);
        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex, "获取个人信息出现异常");
        }
    }

    /**
     * 修改个人信息
     *
     * @param photo    the photo
     * @param request  the request
     * @param response the response
     * @return the json object
     */
    @ResponseBody
    @RequestMapping(value = "/updatePersonalInfo", method = RequestMethod.POST)
    public ResultVO updatePersonalInfo(@RequestParam("photo") CommonsMultipartFile photo, HttpServletRequest request, HttpServletResponse response)
    {
        try
        {
            SysUserEntity user = DataSwitch.convertHttpServletRequestObjToEntity(SysUserEntity.class, request);
            user.validateUserKey();
            personalInformationService.updatePersonalInfo(photo, user);
            return ResultVOUtil.success();
        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex, "修改个人信息出现异常");
        }
    }

    /**
     * 修改密码
     *
     * @param userEntity the userEntity
     * @return the json object
     */
    @ResponseBody
    @RequestMapping(value = "/modifyPassword", method = RequestMethod.POST)
    public ResultVO modifyPassword(@RequestBody UserInfoVO userEntity)
    {
        try
        {
            userEntity.validateUserKey();
            if (ObjectUtils.isEmpty(userEntity.getPassword()))
            {
                return ResultVOUtil.paramError("password");
            }
            else if (ObjectUtils.isEmpty(userEntity.getNewPassword()))
            {
                return ResultVOUtil.paramError("newPassword");
            }
            else
            {
                boolean isSuccess = personalInformationService.modifyPassword(userEntity);
                if (isSuccess)
                {
                    return ResultVOUtil.success();
                }
                else
                {
                    return ResultVOUtil.error(400, "原始密码不正确");
                }
            }
        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex, "修改密码出现异常");
        }
    }
}