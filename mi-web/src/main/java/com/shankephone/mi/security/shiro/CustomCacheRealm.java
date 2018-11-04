package com.shankephone.mi.security.shiro;

import lombok.extern.slf4j.Slf4j;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.realm.CachingRealm;
import org.springframework.stereotype.Component;

import com.shankephone.mi.model.SysUserEntity;
import com.shankephone.mi.security.model.UserLoginInfo;

@Slf4j
@Component("customCacheRealm")
public class CustomCacheRealm extends CachingRealm{

	@Override
	public boolean supports(AuthenticationToken token) {
		return true;
	}

	@Override
	public AuthenticationInfo getAuthenticationInfo(AuthenticationToken token)
			throws AuthenticationException {
		log.info("doGetAuthenticationInfo");
        UsernamePasswordToken tk = (UsernamePasswordToken) token;
        // 通过表单接收的用户名
        String username  = tk.getUsername();
        SysUserEntity user = (SysUserEntity)tk.getPrincipal();
        UserLoginInfo userinfo = new UserLoginInfo();
    	userinfo.setUserName(username);
    	userinfo.setPassword(user.getPassword());
    	userinfo.setOperationSubjectId(user.getOperationSubjectId());
        if(user!=null){
            return new SimpleAuthenticationInfo(userinfo, user.getPassword(), getName());
        }
        return null;
	}

}
