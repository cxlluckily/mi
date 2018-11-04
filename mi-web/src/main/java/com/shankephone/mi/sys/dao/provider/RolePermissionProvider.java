package com.shankephone.mi.sys.dao.provider;

import com.shankephone.mi.common.enumeration.RangeRoleTypeEnum;
import com.shankephone.mi.common.enumeration.StatusEnum;
import com.shankephone.mi.sys.vo.UserPermissionVo;
import com.shankephone.mi.util.StringUtils;

/**
 * 角色权限查询
 * @author fengql
 * @date 2018年6月29日 上午2:05:03
 */
public class RolePermissionProvider {
	
	public String findRolePermission(String roleIds){
		/*String sql = "select p.permissionCode from sys_role r join sys_role_permission rp on r.roleId = rp.roleId join sys_permission p on rp.permissionId = p.permissionId ";
		if(roleCodes == null || "".equals(roleCodes)) {
			sql += " where 1 <> 1 ";
			return sql;
		}*/
		
		String sql = " SELECT ft.permissionCode FROM sys_role_tree rt JOIN sys_function_tree ft ON rt.functionTreeId = ft.functionTreeId\r\n" + 
				" WHERE rt.roleId in (" + roleIds + ") AND ft.permissionCode IS NOT NULL AND ft.permissionCode <> ''";
		
		return sql;
	}

	public  String findUserByPermission(UserPermissionVo entity)
	{

		StringBuilder sbSql = new StringBuilder();

		sbSql.append(" SELECT distinct  sys_user.userId,sys_user.userName,sys_user.realName from sys_function_tree ");
		sbSql.append(" INNER JOIN sys_role_tree on (sys_function_tree.permissionCode in ("+StringUtils.listToString(entity.getPermissionCodes()) +") and  sys_function_tree.functionTreeId=sys_role_tree.functionTreeId) ");
		sbSql.append(" INNER JOIN sys_user_role on sys_role_tree.roleId =sys_user_role.roleId ");
		sbSql.append(" INNER  JOIN sys_user on sys_user_role.userId=sys_user.userId ");
		sbSql.append(" WHERE  sys_user.status = '" + StatusEnum.START_USING.getValue() + "' ");
		sbSql.append(" and sys_user.operationSubjectId = #{operationSubjectId} ");
		return sbSql.toString();
	}
	public  String findUserByWarehousePermission(UserPermissionVo entity)
	{

		StringBuilder sbSql = new StringBuilder();
		sbSql.append(" SELECT distinct  sys_user.userId,sys_user.userName,sys_user.realName from sys_function_tree ");
		sbSql.append(" INNER JOIN sys_role_tree on (sys_function_tree.permissionCode in ("+StringUtils.listToString(entity.getPermissionCodes()) +") and  sys_function_tree.functionTreeId=sys_role_tree.functionTreeId) ");
		sbSql.append(" INNER JOIN sys_user_role on sys_role_tree.roleId =sys_user_role.roleId ");
		sbSql.append(" INNER  JOIN sys_user on sys_user_role.userId=sys_user.userId ");
		sbSql.append("  INNER  JOIN sys_range_role on sys_range_role.userId=sys_user.userId");
		sbSql.append("   INNER JOIN sys_range_role_detail ");
		sbSql.append("     ON sys_range_role.rangeRoleId = sys_range_role_detail.rangeRoleId ");
		sbSql.append(" WHERE sys_range_role.rangeType = '" + RangeRoleTypeEnum.WAREHOUSE.getValue() + "' ");
		sbSql.append("       AND sys_range_role_detail.id = #{warehouseId} ");
		sbSql.append(" AND  sys_user.status = '" + StatusEnum.START_USING.getValue() + "' ");
		sbSql.append(" AND sys_user.operationSubjectId = #{operationSubjectId} ");
		return sbSql.toString();
	}

}
