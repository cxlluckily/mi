package com.shankephone.mi.sys.dao;

import com.shankephone.mi.sys.dao.provider.UserRoleProvider;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 查询用户的所有角色
 * @author fengql
 * @date 2018年6月21日 下午8:28:44
 */
@Repository
public interface UserRoleDao {

	@SelectProvider(type = UserRoleProvider.class, method = "findUserRoles")
	List<Map<String,Object>> findUserRoles(String userName);

}
