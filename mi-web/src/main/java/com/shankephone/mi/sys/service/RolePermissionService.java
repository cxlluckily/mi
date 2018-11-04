package com.shankephone.mi.sys.service;

import com.shankephone.mi.sys.vo.UserPermissionVo;

import java.util.List;
import java.util.Map;

public interface RolePermissionService {

	public List<Map<String,Object>> findRolePermissions(String roleCodes);

	List<String> getUserPermissionList(Long userId);

	public List<Map<String,Object>>  findUserByPermission(UserPermissionVo entity);

	public List<Map<String,Object>>  findUserByWarehousePermission(UserPermissionVo entity);
	
}
