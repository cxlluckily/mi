package com.shankephone.mi.sys.dao;

import com.shankephone.mi.sys.dao.provider.RolePermissionProvider;
import com.shankephone.mi.sys.vo.UserPermissionVo;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


/**
 * 查询角色权限
 * @author fengql
 * @date 2018年6月21日 下午8:29:19
 */
@Repository
public interface RolePermissionDao {

	@SelectProvider(type = RolePermissionProvider.class, method = "findRolePermission")
	public List<Map<String,Object>> findRolePermission(String roleIds);

	@SelectProvider(type = RolePermissionProvider.class, method = "findUserByPermission")
	public List<Map<String,Object>> findUserByPermission(UserPermissionVo entity);

	@SelectProvider(type = RolePermissionProvider.class, method = "findUserByWarehousePermission")
	public List<Map<String,Object>> findUserByWarehousePermission(UserPermissionVo entity);
	
}
