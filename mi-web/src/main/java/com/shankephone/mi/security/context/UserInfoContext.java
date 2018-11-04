package com.shankephone.mi.security.context;

import com.shankephone.mi.security.model.UserLoginInfo;

public class UserInfoContext {
	
	private static ThreadLocal<UserLoginInfo> context = new ThreadLocal<UserLoginInfo>();
	
	public static UserLoginInfo get(){
		return context.get();
	}
	
	public static void set(UserLoginInfo user){
		context.set(user);
	}

}
