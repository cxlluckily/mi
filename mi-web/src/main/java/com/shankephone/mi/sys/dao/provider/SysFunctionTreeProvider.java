package com.shankephone.mi.sys.dao.provider;

import com.shankephone.mi.common.enumeration.StatusEnum;
import com.shankephone.mi.sys.formbean.FunctionTreeFindEntity;
import com.shankephone.mi.sys.vo.UserTreeFindEntity;

/**
 * @author 赵亮
 * @date 2018-06-21 14:22
 */
public class SysFunctionTreeProvider
{
    public String findByuserId(UserTreeFindEntity findEntity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT DISTINCT sys_function_tree.* ");
        sbSql.append(" FROM sys_user_role ");
        sbSql.append("   INNER JOIN sys_role_tree ");
        sbSql.append("     ON sys_user_role.roleId = sys_role_tree.roleId ");
        sbSql.append("   INNER JOIN sys_function_tree ");
        sbSql.append("     ON sys_function_tree.functionTreeId = sys_role_tree.functionTreeId ");
        sbSql.append(" WHERE userId = #{userId} ");
        sbSql.append("       AND status='" + StatusEnum.START_USING.getValue() + "' ");
        sbSql.append("       AND treeType= #{treeType}");
        sbSql.append(" ORDER BY sort ");
        return sbSql.toString();
    }

    public String findFunctionTree(FunctionTreeFindEntity entity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT functionTreeId dataNodeId, parentTreeId dataParentNodeId, treeName text, permissionCode code ");
        sbSql.append(" from sys_function_tree ");
        sbSql.append(" WHERE status = '" + StatusEnum.START_USING.getValue() + "' and treeType = #{treeType} ");
        return sbSql.toString();
    }
}
