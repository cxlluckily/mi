package com.shankephone.mi.sys.service;

import java.util.List;
import java.util.Map;

public interface UserRoleService {

	public List<Map<String,Object>> findUserRoles(String userName);
	
}
