package com.shankephone.mi.supersys.dao.provider;

import com.shankephone.mi.common.enumeration.StatusEnum;

/**
 * @author 赵亮
 * @date 2018-07-23 15:16
 */
public class FunctionTreeProvider
{
    public String getAllTreeList(String status)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT * ");
        sbSql.append(" FROM sys_function_tree ");
        sbSql.append(" WHERE status = #{status} ");

        return sbSql.toString();
    }

    public String getUserPermissionList(Long userId)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT DISTINCT sys_function_tree.permissionCode ");
        sbSql.append("   FROM sys_user ");
        sbSql.append("     INNER JOIN sys_user_role ");
        sbSql.append("       ON sys_user_role.userId = sys_user.userId ");
        sbSql.append("     INNER JOIN sys_role_tree ");
        sbSql.append("       ON sys_role_tree.roleId = sys_user_role.roleId ");
        sbSql.append("     INNER JOIN sys_function_tree ");
        sbSql.append("       ON sys_function_tree.functionTreeId = sys_role_tree.functionTreeId ");
        sbSql.append("   WHERE sys_function_tree.status = '" + StatusEnum.START_USING.getValue() + "' ");
        sbSql.append("         AND permissionCode IS NOT NULL ");
        sbSql.append("         AND permissionCode != '' ");
        sbSql.append("         AND sys_user.userId = #{userId} ");

        return sbSql.toString();

    }
}
