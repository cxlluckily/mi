package com.shankephone.mi.sys.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.shankephone.mi.sys.dao.UserRoleDao;
import com.shankephone.mi.sys.service.UserRoleService;

@Service
public class UserRoleServiceImpl implements UserRoleService {
	
	@Resource
	private UserRoleDao userRoleDao;
	
	public List<Map<String,Object>> findUserRoles(String userName){
		return userRoleDao.findUserRoles(userName);
	}
	

}
