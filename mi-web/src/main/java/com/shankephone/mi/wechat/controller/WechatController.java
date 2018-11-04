package com.shankephone.mi.wechat.controller;

import com.shankephone.mi.common.model.BaseFindEntity;
import com.shankephone.mi.common.vo.ResultVO;
import com.shankephone.mi.model.SysUserEntity;
import com.shankephone.mi.security.controller.LoginController;
import com.shankephone.mi.security.model.UserLoginInfo;
import com.shankephone.mi.sys.dao.SysUserDao;
import com.shankephone.mi.sys.service.SysUserService;
import com.shankephone.mi.task.service.PendingTaskService;
import com.shankephone.mi.task.vo.PendingTaskListVO;
import com.shankephone.mi.util.*;
import com.shankephone.mi.wechat.config.ProjectUrlConfig;
import com.shankephone.mi.wechat.formbean.BindWechatOpenIdFormBean;
import com.shankephone.mi.wechat.formbean.ConfigJsSDKFormEntity;
import com.shankephone.mi.wechat.formbean.KeFuMessageEntity;
import com.shankephone.mi.wechat.formbean.QrUserInfoVO;
import com.shankephone.mi.wechat.service.WechatService;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.bean.WxJsapiSignature;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.List;

/**
 * @author：赵亮
 * @date：2018-08-24 9:59
 */
@Controller
@RequestMapping("/wechat")
@Slf4j
public class WechatController
{
    @Autowired
    private WxMpService wxMpService;

    @Autowired
    private WechatService wechatService;

    @Autowired
    private PendingTaskService pendingTaskService;


    @Autowired
    private LoginController loginController;
    
    @RequestMapping(value = "/token")
    public void get(HttpServletRequest request, HttpServletResponse response) throws Exception {

        System.out.println("========WechatController========= ");
        Enumeration pNames = request.getParameterNames();
        while (pNames.hasMoreElements()) {
            String name = (String) pNames.nextElement();
            String value = request.getParameter(name);
            String values = "name =" + name + "     value =" + value;
            log.info(values);
        }

        String signature = request.getParameter("signature");/// 微信加密签名
        String timestamp = request.getParameter("timestamp");/// 时间戳
        String nonce = request.getParameter("nonce"); /// 随机数
        String echostr = request.getParameter("echostr"); // 随机字符串
        PrintWriter out = response.getWriter();
        boolean checked = wxMpService.checkSignature(timestamp, nonce, signature);
        if (checked) {
            out.print(echostr);
        }
        out.close();
    }
    
    /**
     * @author：赵亮
     * @date：2018-06-07 18:02
     */
    @ResponseBody
    @RequestMapping(value = "/authorize", method = RequestMethod.GET)
    public String authorize(@RequestParam("state") String state)
    {

        log.info("进入微信授权");
        state=StringUtils.isNotEmpty(state)?state:"mi";
        String url = ProjectUrlConfig.wechatOpenAuthorize;
        String redirectUrl = wxMpService.oauth2buildAuthorizationUrl(url, WxConsts.OAuth2Scope.SNSAPI_USERINFO, state);
        log.info(redirectUrl);
        return redirectUrl;
    }

    @ResponseBody
    @RequestMapping(value = "/userInfo", method = RequestMethod.GET)
    public ResultVO userInfo(@RequestParam("code") String code,@RequestParam("state") String state, HttpServletRequest request)
    {
        WxMpOAuth2AccessToken wxMpOAuth2AccessToken;
        try
        {
            wxMpOAuth2AccessToken = wxMpService.oauth2getAccessToken(code);
        }
        catch (WxErrorException e)
        {
            log.error("【微信网页授权】{}", e);
            throw new AuthorizationException("微信公众账号方面错误");
        }

        String openId = wxMpOAuth2AccessToken.getOpenId();
        QrUserInfoVO qrUserInfoVO = new QrUserInfoVO();
        SysUserEntity userEntity = sysUserDao.findSysUserByOpenId(openId);
        qrUserInfoVO.setOpenId(openId);
        if (ObjectUtils.isEmpty(userEntity))
        {
            qrUserInfoVO.setUrl(ProjectUrlConfig.phoneBindUrl);
        }
        else
        {
            sysUserDao.bindWechatOpenId(userEntity);
            //获取用户角色
            List<String> roles = loginController.getUserRoles(userEntity.getUserName());
            UserLoginInfo userinfo = loginController.getAuthInfo(userEntity.getUserName(), roles, IpUtil.getIpAddr(request));
            String userKey = SessionMap.putUserInfo(userinfo);
            qrUserInfoVO.setUserKey(userKey);
            qrUserInfoVO.setUrl(ProjectUrlConfig.phoneHome);
            if(state.indexOf("@")>0)
            {
                PendingTaskListVO entitytaskVO = new PendingTaskListVO();
                entitytaskVO.setUserKey(userKey);
                entitytaskVO.setOperationSubjectId(userinfo.getOperationSubjectId());
                entitytaskVO.setTaskType(state.split("@")[0]);
                entitytaskVO.setSourceId(DataSwitch.convertObjectToLong(state.split("@")[1]));
                pendingTaskService.setTaskUrl(entitytaskVO);

                String param=entitytaskVO.getPhoneUrl().indexOf("?")<0?"?operationSubjectId="+entitytaskVO.getOperationSubjectId()+"&taskNotice=true&1=1":"&operationSubjectId="+entitytaskVO.getOperationSubjectId()+"&taskNotice=true&1=1";

                qrUserInfoVO.setUrl(ProjectUrlConfig.phoneHome+entitytaskVO.getPhoneUrl()+param);
            }
        }
        // return "redirect:" + returnUrl + "?openid=" + openId;
        return ResultVOUtil.success(qrUserInfoVO);
    }

    /**
     * 更新登陆ip
     *
     * @param openId t
     * @return the ResultVO
     * @author：赵亮
     * @date：2018-09-17
     */
    @ResponseBody
    @RequestMapping(value = "/updateUserLoginIp", method = RequestMethod.POST)
    public ResultVO updateUserLoginIp(@RequestParam("openId") String openId, HttpServletRequest request)
    {
        try
        {
            SysUserEntity user = sysUserDao.findSysUserByOpenId(openId);
            log.info("IpUtil.getIpAddr(request)=",IpUtil.getIpAddr(request));
            user.setLastLoginIp(user.getCurrentLoginIp());
            user.setCurrentLoginTime(user.getCurrentLoginTime());
            user.setCurrentLoginIp(IpUtil.getIpAddr(request));
            user.setCurrentLoginTime(DateUtil.getNow());
            user.setOpenId(openId);
            return ResultVOUtil.success(sysUserDao.udpateLoginIpForWechat(user));
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex, "更新登陆ip出现异常");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/configJsSDK", method = RequestMethod.POST)
    public ResultVO configJsSDK(@RequestBody ConfigJsSDKFormEntity entity)
    {
        try
        {
            WxJsapiSignature jsapiSignature = wxMpService.createJsapiSignature(entity.getUrl());
            //https://blog.csdn.net/fengqingtao2008/article/details/51469705
            return ResultVOUtil.success(jsapiSignature);
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(-1, ex.getMessage());
        }

    }

    @Autowired
    private SysUserDao sysUserDao;
    @Autowired
    private SysUserService sysUserService;

    /**
     * 退出登录
     *
     * @param findEntity the findEntity
     * @return the ResultVO
     * @author：赵亮
     * @date：2018-09-11
     */
    @ResponseBody
    @RequestMapping(value = "/loginOut", method = RequestMethod.POST)
    public ResultVO loginOut(@RequestBody BaseFindEntity findEntity)
    {
        try
        {
            SessionMap.remove(findEntity.getUserKey());
            sysUserDao.wechatLoginOut(findEntity.getOperationUserId());
            return ResultVOUtil.success();
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex, "退出登录出现异常");
        }
    }

    /**
     * 微信绑定账号
     *
     * @param findEntity the findEntity
     * @return the ResultVO
     * @author：赵亮
     * @date：2018-09-11
     */
    @ResponseBody
    @RequestMapping(value = "/bindWechatOpenId", method = RequestMethod.POST)
    public ResultVO bindWechatOpenId(@RequestBody BindWechatOpenIdFormBean findEntity, HttpServletRequest request)
    {
        try
        {

            return sysUserService.bindWechatOpenId(findEntity, request);
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex, "微信绑定账号出现异常");
        }
    }

    /**
     * 微信绑定账号（手机验证码绑定）
     *
     * @param findEntity the findEntity
     * @return the ResultVO
     * @author：郝伟州
     * @date：2018年10月10日13:41:05
     */
    @ResponseBody
    @RequestMapping(value = "/bindWechatOpenIdByPhone", method = RequestMethod.POST)
    public ResultVO bindWechatOpenIdByPhone(@RequestBody BindWechatOpenIdFormBean findEntity, HttpServletRequest request)
    {
        try
        {

            return sysUserService.bindWechatOpenIdByPhone(findEntity, request);
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex, "微信绑定账号（手机验证码绑定）出现异常");
        }
    }

    /**
     * 微信发文本消息
     *
     * @param entity the findEntity
     * @return the ResultVO
     * @author：郝伟州
     * @date：2018年9月26日15:46:32
     */
    @ResponseBody
    @RequestMapping(value = "/sendKefuMessage", method = RequestMethod.POST)
    public ResultVO sendKefuMessage(@RequestBody KeFuMessageEntity entity)
    {
        try
        {
            return ResultVOUtil.success(wechatService.sendKefuMessage(entity));
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex, " 微信发消息出现异常");
        }
    }

    /**
     * 微信发文本消息消息
     *
     * @param entity the findEntity
     * @return the ResultVO
     * @author：郝伟州
     * @date：2018年9月26日15:46:32
     */
    @ResponseBody
    @RequestMapping(value = "/sendKefuImageMessage", method = RequestMethod.POST)
    public ResultVO sendKefuImageMessage(@RequestBody KeFuMessageEntity entity)
    {
        try
        {
            return ResultVOUtil.success(wechatService.sendKefuImageMessage(entity));
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex, " 微信发消息出现异常");
        }
    }

    /**
     * 微信发文本模板消息
     *
     * @param entity the findEntity
     * @return the ResultVO
     * @author：郝伟州
     * @date：2018年9月26日15:46:32
     */
    @ResponseBody
    @RequestMapping(value = "/sendTemplateMessage", method = RequestMethod.POST)
    public ResultVO sendTemplateMessage(@RequestBody KeFuMessageEntity entity)
    {
        try
        {
            return ResultVOUtil.success(wechatService.sendTemplateMessage(entity));
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex, " 微信发消息出现异常");
        }
    }
}
