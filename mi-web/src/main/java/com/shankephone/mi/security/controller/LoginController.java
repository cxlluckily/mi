package com.shankephone.mi.security.controller;

import com.shankephone.mi.common.dao.FieldContentExistsDao;
import com.shankephone.mi.common.enumeration.TreeTypeEnum;
import com.shankephone.mi.common.enumeration.UserTypeEnum;
import com.shankephone.mi.common.model.BaseFindEntity;
import com.shankephone.mi.common.model.FieldContentExistsFindEntity;
import com.shankephone.mi.common.vo.ResultVO;
import com.shankephone.mi.model.SysUserEntity;
import com.shankephone.mi.security.model.UserLoginInfo;
import com.shankephone.mi.security.shiro.CustomShiroRealm;
import com.shankephone.mi.security.vo.UserLoginInfoVO;
import com.shankephone.mi.sys.formbean.UserPermissionFindEntity;
import com.shankephone.mi.sys.service.*;
import com.shankephone.mi.sys.vo.UserInfoVO;
import com.shankephone.mi.sys.vo.UserTreeFindEntity;
import com.shankephone.mi.util.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/")
@Slf4j
public class LoginController
{

    @Resource
    private UserRoleService userRoleService;
    @Resource
    private RolePermissionService rolePermissionService;
    @Resource
    private SysUserService userService;
    @Resource
    private UserPermissionService userPermissionService;
    @Resource
    private FieldContentExistsDao fieldContentExistsDao;
    @Autowired
    private PersonalInformationService personalInformationService;

    @RequestMapping(value = "/LoginPage")
    public String loginPage()
    {
        return "login";
    }

    @Autowired
    private SysFunctionTreeService sysFunctionTreeService;

    /**
     * 登出
     *
     * @param
     * @return the ResultVO
     * @author：赵亮
     * @date：2018-08-01
     */
    @RequestMapping(value = "/loginOut", method = RequestMethod.POST)
    @ResponseBody
    public ResultVO loginOut(HttpServletResponse response, @RequestBody BaseFindEntity entity)
    {
        try
        {
            UserLoginInfo userInfo = SessionMap.getUserInfo(entity.getUserKey());
            if (ObjectUtils.isNull(userInfo))
            {
                return ResultVOUtil.success();
            }
            Map<String, String> userType = new HashMap<String, String>();
            userType.put("userType", userInfo.getUserType().getValue());
            SecurityUtils.getSecurityManager().logout(SecurityUtils.getSubject());
            CookieUtils.addCookie(response, "token", "");
            SessionMap.removeUserInfo(entity.getUserKey());
            ResultVO result = ResultVOUtil.success();
            result.setData(userType);
            return result;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex, "登出出现异常");
        }
    }

    @RequestMapping(value = "/AutoLogin")
    @ResponseBody
    public ResultVO<Map<String, String>> AutoLogin(HttpServletRequest request)
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
                request.getSession().setAttribute("operationSubjectId", userinfo.getOperationSubjectId());
                request.getSession().setMaxInactiveInterval(-1);
                UserTreeFindEntity findEntity = new UserTreeFindEntity();
                findEntity.setUserId(userinfo.getUserId());
                findEntity.setTreeType(TreeTypeEnum.MANAGE.getCode());
                return ResultVOUtil.success(sysFunctionTreeService.getUserTree(findEntity));
            }
        }
        return ResultVOUtil.error(0, "请登录！");

    }


    /***
     * 实现用户登录
     *
     * @param username
     * @param password
     * @return
     */
    @RequestMapping(value = "/login")
    @ResponseBody
    public ResultVO<Map<String, String>> Login(HttpServletRequest request, HttpServletResponse response, String username, String password)
    {
        ResultVO resultVO = validateLogin(username, password);
        if (resultVO != null)
        {
            return resultVO;
        }
        //获取用户角色
        List<String> roles = getUserRoles(username);
        if (!(roles != null && roles.size() > 0))
        {
            return ResultVOUtil.error(0, "用户无权限!");
        }
        UserLoginInfo userinfo = getAuthInfo(username, roles, IpUtil.getIpAddr(request));
        String userKey = SessionMap.putUserInfo(userinfo);
        request.getSession().setAttribute("userId", userinfo.getUserId());
        request.getSession().setAttribute("userKey", userKey);
        request.getSession().setAttribute("userName", userinfo.getUserName());
        request.getSession().setAttribute("realName", userinfo.getRealName());
        request.getSession().setAttribute("operationSubjectId", userinfo.getOperationSubjectId());
        request.getSession().setAttribute("userType", "USER");
        request.getSession().setMaxInactiveInterval(-1);
        UserTreeFindEntity findEntity = new UserTreeFindEntity();
        findEntity.setUserId(userinfo.getUserId());
        findEntity.setTreeType(TreeTypeEnum.MANAGE.getCode());
        return ResultVOUtil.success(sysFunctionTreeService.getUserTree(findEntity));
    }

    /***
     * 实现用户登录
     *
     * @param username
     * @param password
     * @return
     */
    @RequestMapping(value = "/h5Login")
    @ResponseBody
    public ResultVO<Map<String, String>> h5Login(HttpServletRequest request, String username, String password)
    {
        ResultVO resultVO = validateLogin(username, password);
        if (resultVO != null)
        {
            return resultVO;
        }
        //获取用户角色
        List<String> roles = getUserRoles(username);
        if (!(roles != null && roles.size() > 0))
        {
            return ResultVOUtil.error(0, "用户无权限!");
        }
        UserLoginInfo userinfo = getAuthInfo(username, roles, IpUtil.getIpAddr(request));
        String userKey = SessionMap.putUserInfo(userinfo);
        return ResultVOUtil.success(userKey);
    }

    private ResultVO validateLogin(String username, String password)
    {
        SysUserEntity user = userService.findSysUser(username);
        if (user == null)
        {
            return ResultVOUtil.error(1, "账户不存在！");
        }
        if (!CustomShiroRealm.encrypt(password).equals(user.getPassword()))
        {
            return ResultVOUtil.error(2, "密码错误！");
        }
        return null;
        //        SecurityUtils.getSecurityManager().logout(SecurityUtils.getSubject());
        //        UserLoginInfo user = new UserLoginInfo(); //根据用户名去检索数据库
        //        user.setUserName(username);
        //        user.setPassword(password);
        //        user.setPassword(CustomShiroRealm.encrypt(user.getPassword()));
        //        // 登录后存放进shiro token
        //        UsernamePasswordToken token = new UsernamePasswordToken(
        //                user.getUserName(), user.getPassword());
        //        Subject subject = SecurityUtils.getSubject();
        //        try
        //        {
        //            subject.login(token);
        //        }
        //        catch (UnknownAccountException uae)
        //        {
        //            return ResultVOUtil.error(uae, "账户不存在！");
        //        }
        //        catch (IncorrectCredentialsException ice)
        //        {
        //            return ResultVOUtil.error(ice, "密码错误！");
        //        }
        //        catch (LockedAccountException lae)
        //        {
        //            return ResultVOUtil.error(lae, "账户已锁定！");
        //        }
        //        catch (AuthenticationException ae)
        //        {
        //            return ResultVOUtil.error(ae, "用户名或密码错误！");
        //        }
        //        return null;
    }

    private List<String> getUserPermission(List<String> roles)
    {
        String roleIds = StringUtils.listToString(roles);
        List<Map<String, Object>> permissionMaps = rolePermissionService.findRolePermissions(roleIds);
        List<String> permissionCode = permissionMaps.stream().map(map -> DataSwitch.convertObjectToString(map.get("permissionCode"))).collect(Collectors.toList());
        return permissionCode;
    }

    public List<String> getUserRoles(String username)
    {
        List<Map<String, Object>> roleMaps = userRoleService.findUserRoles(username);
        List<String> roles = new ArrayList<String>();
        for (Map<String, Object> map : roleMaps)
        {
            Long roleId = (Long) map.get("roleId");
            roles.add(roleId + "");
        }
        return roles;
    }

    public UserLoginInfo getAuthInfo(String username, List<String> roles, String ip)
    {
        SysUserEntity user = userService.findSysUser(username);

        //更新用户登录时间和登陆ip
        user.setLastLoginIp(user.getCurrentLoginIp());
        user.setLastLoginTime(user.getCurrentLoginTime());
        user.setCurrentLoginIp(ip);
        user.setCurrentLoginTime(DateUtil.getNow());
        userService.updatedengluIp(user);

        //获取用户角色
        //List<String> roles = getUserRoles(username);
        //获取用户权限
        List<String> permissions = getUserPermission(roles);
        UserPermissionFindEntity entity = new UserPermissionFindEntity();
        entity.setUserId(user.getUserId());
        Map<String, Object> map = userPermissionService.getUserAuthorizationInfoByUserId(entity);
        List<Map<String, Object>> workSectionInfo = (List<Map<String, Object>>) map.get("workSectionInfo");
        List<Long> workSectionIds = workSectionInfo.stream().map(m -> {
            return (Long) m.get("workSectionId");
        }).distinct().collect(Collectors.toList());
        List<String> sectionNames = workSectionInfo.stream().map(m -> {
            return (String) m.get("sectionName");
        }).distinct().collect(Collectors.toList());
        List<Long> warehouseIds = userPermissionService.getUserWarehouseRangeIds(entity.getUserId());
        UserLoginInfo userinfo = new UserLoginInfo();
        userinfo.setUserName(username);
        userinfo.setPassword(user.getPassword());
        userinfo.getWorkSections().addAll(workSectionIds);
        userinfo.getWarehouses().addAll(warehouseIds);
        userinfo.getSectionNames().addAll(sectionNames);
        userinfo.setUserId(user.getUserId());
        userinfo.setOperationSubjectId(user.getLoginOperationSubjectId());
        userinfo.setRoles(roles);
        userinfo.setPermissions(permissions);
        userinfo.setUserType(UserTypeEnum.USER);
        userinfo.setRealName(user.getRealName());
        return userinfo;
    }

    /***
     * 手机发送验证码

     * @return
     */
    @RequestMapping(value = "/phoneCode", method = RequestMethod.POST)
    @ResponseBody
    public ResultVO phoneCode(HttpServletRequest request, @RequestBody SysUserEntity entity) throws Exception
    {
        //SysUserEntity entity=DataSwitch.convertRequestToEntity(SysUserEntity.class,request);
        String phone = entity.getPhone();
        if (!StringUtils.isphone(phone))
        {
            return ResultVOUtil.error(0, "手机号格式不正确!");
        }

        FieldContentExistsFindEntity filedentity = new FieldContentExistsFindEntity();
        filedentity.setTablename("sys_user");
        filedentity.setFieldName("phone");
        filedentity.setFieldValue(phone);
        if (fieldContentExistsDao.selectFieldExists(filedentity) < 1)
        {
            return ResultVOUtil.error(-1, "手机号数据不存在!");
        }

        if (!"".equals(DataSwitch.convertObjectToString(entity.getUserKey())))
        {
            UserLoginInfo userLoginInfo = SessionMap.getUserInfo(entity.getUserKey());
            if (StringUtils.isEmpty(userLoginInfo))
            {
                return ResultVOUtil.error(0, "请您重新登录！");
            }
            SysUserEntity user = userService.findSysUser(userLoginInfo.getUserName());
            if (!phone.equals(user.getPhone()))
            {
                return ResultVOUtil.error(0, "登录手机号和发送验证码手机号不匹配！");
            }
        }

        Random random = new Random();
        String code = "";
        for (int i = 0; i < 6; i++)
        {
            code += random.nextInt(10);
        }

        try
        {
            String content = "尊敬的用户，您好！验证码：" + code + "，有效期为1分钟，校验码很重要，打死都不能告诉任何人哦！";
            String phonecode = DataSwitch.convertObjectToString(SessionMap.get(phone));
            if (StringUtils.isNotEmpty(phonecode))
            {
                return ResultVOUtil.error(0, "尊敬的用户，您好！您发送短信验证码过于频繁,请耐心等待！");
            }
            SendSms.sendSms(phone, content);
            //code="123456";
            SessionMap.put(phone, code, 1000L * 60);


        }
        catch (Exception ae)
        {
            return ResultVOUtil.error(ae, "手机号发送短信失败！");
        }

        return ResultVOUtil.success();

    }

    /***
     * 手机验证码登录

     * @return
     */
    @RequestMapping(value = "/phoneCodeLogin", method = RequestMethod.POST)
    @ResponseBody
    public ResultVO phoneCodeLogin(HttpServletRequest request, @RequestBody UserLoginInfoVO entity) throws Exception
    {
        //UserLoginInfoVO entity=DataSwitch.convertRequestToEntity(UserLoginInfoVO.class,request);
        String phone = entity.getPhone();
        if (!StringUtils.isphone(phone))
        {
            return ResultVOUtil.error(0, "手机号格式不正确!");
        }
        FieldContentExistsFindEntity filedentity = new FieldContentExistsFindEntity();
        //filedentity.setUserKey(entity.getUserKey());
        filedentity.setTablename("sys_user");
        filedentity.setFieldName("phone");
        filedentity.setFieldValue(phone);
        if (fieldContentExistsDao.selectFieldExists(filedentity) < 1)
        {
            return ResultVOUtil.error(-1, "手机号数据不存在!");
        }

        String code = DataSwitch.convertObjectToString(SessionMap.get(phone));
        if (!code.equals(entity.getCode()) || code.equals(""))
        {
            return ResultVOUtil.error(-1, "手机号验证码不正确!");
        }
        SessionMap.remove(phone);
        SysUserEntity sysUserEntity = userService.findSysUserbyPhone(entity.getPhone());

        ResultVO resultVO = validateCodeLogin(sysUserEntity.getUserName(), sysUserEntity.getPassword());
        if (resultVO != null)
        {
            return resultVO;
        }

        String username = sysUserEntity.getUserName();
        //获取用户角色
        List<String> roles = getUserRoles(username);
        if (!(roles != null && roles.size() > 0))
        {
            return ResultVOUtil.error(0, "用户无权限!");
        }
        UserLoginInfo userinfo = getAuthInfo(username, roles, IpUtil.getIpAddr(request));
        String userKey = SessionMap.putUserInfo(userinfo);
        request.getSession().setAttribute("userId", userinfo.getUserId());
        request.getSession().setAttribute("userKey", userKey);
        request.getSession().setAttribute("userName", userinfo.getUserName());
        request.getSession().setAttribute("realName", userinfo.getRealName());
        request.getSession().setAttribute("operationSubjectId", userinfo.getOperationSubjectId());
        request.getSession().setAttribute("userType", "USER");
        request.getSession().setMaxInactiveInterval(-1);
        UserTreeFindEntity findEntity = new UserTreeFindEntity();
        findEntity.setUserId(userinfo.getUserId());
        findEntity.setTreeType(TreeTypeEnum.MANAGE.getCode());
        return ResultVOUtil.success(sysFunctionTreeService.getUserTree(findEntity));

    }


    private ResultVO validateCodeLogin(String username, String password)
    {
        SecurityUtils.getSecurityManager().logout(SecurityUtils.getSubject());
        UserLoginInfo user = new UserLoginInfo(); //根据用户名去检索数据库
        user.setUserName(username);
        user.setPassword(password);
        // 登录后存放进shiro token
        UsernamePasswordToken token = new UsernamePasswordToken(
                user.getUserName(), user.getPassword());
        Subject subject = SecurityUtils.getSubject();
        try
        {
            subject.login(token);
        }
        catch (UnknownAccountException uae)
        {
            return ResultVOUtil.error(uae, "账户不存在！");
        }
        catch (IncorrectCredentialsException ice)
        {
            return ResultVOUtil.error(ice, "密码错误！");
        }
        catch (LockedAccountException lae)
        {
            return ResultVOUtil.error(lae, "账户已锁定！");
        }
        catch (AuthenticationException ae)
        {
            return ResultVOUtil.error(ae, "用户名或密码错误！");
        }
        return null;
    }

    /***
     * 手机端验证码用户登录

     * @return
     */
    @RequestMapping(value = "/h5phoneCodeLogin")
    @ResponseBody
    public ResultVO<Map<String, String>> h5phoneCodeLogin(HttpServletRequest request, @RequestBody UserLoginInfoVO entity)
    {
        //UserLoginInfoVO entity=DataSwitch.convertRequestToEntity(UserLoginInfoVO.class,request);
        String phone = entity.getPhone();
        if (!StringUtils.isphone(phone))
        {
            return ResultVOUtil.error(0, "手机号格式不正确!");
        }
        FieldContentExistsFindEntity filedentity = new FieldContentExistsFindEntity();
        filedentity.setTablename("sys_user");
        filedentity.setFieldName("phone");
        filedentity.setFieldValue(phone);
        if (fieldContentExistsDao.selectFieldExists(filedentity) < 1)
        {
            return ResultVOUtil.error(-1, "手机号数据不存在!");
        }
        String code = DataSwitch.convertObjectToString(SessionMap.get(phone));
        if (!code.equals(entity.getCode()) || code.equals(""))
        {
            return ResultVOUtil.error(-1, "手机号验证码不正确!");
        }
        SessionMap.remove(phone);
        SysUserEntity sysUserEntity = userService.findSysUserbyPhone(entity.getPhone());
        ResultVO resultVO = validateCodeLogin(sysUserEntity.getUserName(), sysUserEntity.getPassword());
        if (resultVO != null)
        {
            return resultVO;
        }
        String username = sysUserEntity.getUserName();
        //获取用户角色
        List<String> roles = getUserRoles(username);
        if (!(roles != null && roles.size() > 0))
        {
            return ResultVOUtil.error(0, "用户无权限!");
        }
        UserLoginInfo userinfo = getAuthInfo(username, roles, IpUtil.getIpAddr(request));
        String userKey = SessionMap.putUserInfo(userinfo);
        return ResultVOUtil.success(userKey);
    }


    /**
     * 修改密码
     *
     * @param userEntity the userEntity
     * @return the json object
     */
    @ResponseBody
    @RequestMapping(value = "/modifyPasswordbyphone", method = RequestMethod.POST)
    public ResultVO modifyPasswordbyphone(@RequestBody UserInfoVO userEntity)
    {
        try
        {
            userEntity.validateUserKey();
            if (ObjectUtils.isEmpty(userEntity.getNewPassword()))
            {
                return ResultVOUtil.paramError("newPassword");
            }
            else
            {


                String phone = userEntity.getPhone();
                if (!StringUtils.isphone(phone))
                {
                    return ResultVOUtil.error(0, "手机号格式不正确!");
                }
                FieldContentExistsFindEntity filedentity = new FieldContentExistsFindEntity();
                filedentity.setTablename("sys_user");
                filedentity.setFieldName("phone");
                filedentity.setFieldValue(phone);
                if (fieldContentExistsDao.selectFieldExists(filedentity) < 1)
                {
                    return ResultVOUtil.error(-1, "手机号数据不存在!");
                }
                String code = DataSwitch.convertObjectToString(SessionMap.get(phone));
                if (!code.equals(userEntity.getCode()) || code.equals(""))
                {
                    return ResultVOUtil.error(-1, "手机号验证码不正确!");
                }
                SessionMap.remove(phone);
                if (!"".equals(DataSwitch.convertObjectToString(userEntity.getUserKey())))
                {
                    UserLoginInfo userLoginInfo = SessionMap.getUserInfo(userEntity.getUserKey());
                    if (StringUtils.isEmpty(userLoginInfo))
                    {
                        return ResultVOUtil.error(0, "请您重新登录！");
                    }
                    SysUserEntity user = userService.findSysUser(userLoginInfo.getUserName());
                    if (!phone.equals(user.getPhone()))
                    {
                        return ResultVOUtil.error(0, "登录手机号和发送验证码手机号不匹配！");
                    }
                }
                boolean isSuccess = personalInformationService.modifyPasswordbyphone(userEntity);
                if (isSuccess)
                {
                    return ResultVOUtil.success();
                }
                return ResultVOUtil.error(0, "修改密码失败");
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
