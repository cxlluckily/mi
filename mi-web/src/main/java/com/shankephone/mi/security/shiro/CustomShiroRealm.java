package com.shankephone.mi.security.shiro;

import com.shankephone.mi.model.SysUserEntity;
import com.shankephone.mi.security.model.UserLoginInfo;
import com.shankephone.mi.sys.formbean.UserPermissionFindEntity;
import com.shankephone.mi.sys.service.RolePermissionService;
import com.shankephone.mi.sys.service.SysUserService;
import com.shankephone.mi.sys.service.UserPermissionService;
import com.shankephone.mi.sys.service.UserRoleService;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author 赵亮
 * @date 2018-06-12 9:35
 * 编写自定义Realm
 */
@Component("customShiroRealm")
@Slf4j
@Getter
@Setter
public class CustomShiroRealm extends AuthorizingRealm
{

    private static final String hashAlgorithmName = "MD5";
    private static final int hashIterations = 1024;
    private HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();

    public CustomShiroRealm()
    {
        matcher.setHashAlgorithmName(hashAlgorithmName);
        matcher.setHashIterations(hashIterations);
        //this.setCredentialsMatcher(matcher);
        this.setAuthenticationCachingEnabled(true);
//        SecurityUtils.getSubject().getSession().setTimeout(-1000l);
    }

    @Resource
    private UserRoleService userRoleService;
    @Resource
    private RolePermissionService rolePermissionService;
    @Resource
    private SysUserService userService;
    @Resource
    private UserPermissionService userPermissionService;

    /***
     * 获取授权信息
     */
    //根据自己系统规则的需要编写获取授权信息，这里为了快速入门只获取了用户对应角色的资源url信息
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection pc)
    {
        try
        {
            log.info("doGetAuthorizationInfo+获取授权信息");
            String username = (String) pc.fromRealm(getName()).iterator().next();
            //判断是否是超级管理员端登陆，如果是单独处理逻辑
            if (username.indexOf("@") > 0)//管理员
            {
                CustomSimpleAuthorizationInfo info = new CustomSimpleAuthorizationInfo();
                info.addStringPermissions(Arrays.asList("SUPER_ADMIN"));
                return info;
            }
            //查找用户
            SysUserEntity user = userService.findSysUser(username);
            UserLoginInfo userinfo = new UserLoginInfo();
            userinfo.setUserName(username);
            userinfo.setPassword(user.getPassword());
            //获取用户角色
            List<String> roles = getUserRoles(username);
            //获取用户权限
            //        List<String> permissions = getUserPermission(roles);
            List<String> permissions = rolePermissionService.getUserPermissionList(user.getUserId());
            userinfo.setOperationSubjectId(user.getOperationSubjectId());
            userinfo.setRoles(roles);
            userinfo.setPermissions(permissions);

            CustomSimpleAuthorizationInfo info = new CustomSimpleAuthorizationInfo();
            info.addRoles(roles);
            info.addStringPermissions(permissions);
            UserPermissionFindEntity entity = new UserPermissionFindEntity();
            entity.setUserId(user.getUserId());
            Map<String, Object> map = userPermissionService.getUserAuthorizationInfoByUserId(entity);
            List<Map<String, Object>> warehouseInfo = (List<Map<String, Object>>) map.get("warehouseInfo");
            List<Long> warehouseIds = warehouseInfo.stream().map(m -> {
                return (Long) m.get("warehouseId");
            }).collect(Collectors.toList());
            List<Map<String, Object>> workSectionInfo = (List<Map<String, Object>>) map.get("workSectionInfo");
            List<Long> workSectionIds = workSectionInfo.stream().map(m -> {
                return (Long) m.get("workSectionId");
            }).collect(Collectors.toList());
            info.addWorkSections(workSectionIds);
            info.addWarehouses(warehouseIds);
            return info;


        }
        catch(Exception ex)
        {
           // throw new AuthorizationException();
            return new CustomSimpleAuthorizationInfo();
        }

    }

    private List<String> getUserRoles(String username)
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

    /***
     * 获取认证信息
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(
            AuthenticationToken at) throws AuthenticationException
    {
        try
        {
            log.info("doGetAuthenticationInfo+获取认证信息");
            UsernamePasswordToken token = (UsernamePasswordToken) at;
            // 通过表单接收的用户名
            String username = token.getUsername();
            if (username.indexOf("@") > 0)//管理员
            {
                SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(token.getUsername(), token.getPassword(), getName());
                return info;
            }
            else
            {
                SysUserEntity user = userService.findSysUser(username);
                if (user == null)
                {
                    return null;
                }
                UserLoginInfo userinfo = new UserLoginInfo();
                userinfo.setUserName(username);
                if (user.getPassword() == null)
                {
                    return null;
                }
                userinfo.setOperationSubjectId(user.getOperationSubjectId());
                if (user != null)
                {
                    SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user.getUserName(), user.getPassword(), getName());
                    return info;
                }
            }
            return null;
        }
        catch(Exception ex)
        {
           // throw new AuthorizationException();
            return null;
        }

    }

    /**
     * 密码加密
     *
     * @param
     * @return
     */
    public static String encrypt(String password)
    {
        SimpleHash hash = new SimpleHash(hashAlgorithmName, password, null, hashIterations);
        return hash.toString();
    }


}
