package com.shankephone.mi.security.shiro;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;

public class ShiroCredentialsMatcher extends HashedCredentialsMatcher {
	@Override
	public boolean doCredentialsMatch(AuthenticationToken token,
			AuthenticationInfo info) {
		UsernamePasswordToken autoken = (UsernamePasswordToken) token;
		String inputCredential = String.valueOf(autoken.getPassword());
		
		// 生成的加密是大写，但mysql不区分大小写，对比会失败
		String accountCredentials = String.valueOf(getCredentials(info))
				.toUpperCase();
		
		boolean match = equals(inputCredential, accountCredentials);
		if (match) {
			// passwordRetryCache.remove(username);
		}
		return match;
	}
}
