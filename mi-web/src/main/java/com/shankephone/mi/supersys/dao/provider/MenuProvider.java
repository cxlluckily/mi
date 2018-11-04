package com.shankephone.mi.supersys.dao.provider;

import com.shankephone.mi.common.enumeration.StatusEnum;
import com.shankephone.mi.model.SysFunctionTreeEntity;
import com.shankephone.mi.supersys.formbean.MenuFindEntity;
import com.shankephone.mi.util.StringUtils;

/**
 * @author 赵亮
 * @date 2018-07-24 9:37
 */
public class MenuProvider
{
    public String getAllMenuList(MenuFindEntity findEntity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT ");
        sbSql.append("     *, ");
        sbSql.append("     CASE status ");
        sbSql.append("     WHEN '" + StatusEnum.START_USING.getValue() + "' ");
        sbSql.append("       THEN '有效' ");
        sbSql.append("     ELSE '无效' END statusText ");
        sbSql.append("   FROM sys_function_tree ");
        sbSql.append("   WHERE treeType = #{treeType} ");
        if(StringUtils.isNotEmpty(findEntity.getTreeName()))
        {
            sbSql.append("   AND treeName LIKE  concat('%', #{treeName}, '%') ");
        }
        if(StringUtils.isNotEmpty(findEntity.getPermissionCode()))
        {
            sbSql.append("   AND permissionCode LIKE concat('%', #{permissionCode}, '%') ");
        }
        if(!"all".equals(findEntity.getStatus()))
        {
            sbSql.append("   AND status = #{status} ");
        }
        sbSql.append(" ORDER BY sort");
        return sbSql.toString();

    }

    public String insertOne(SysFunctionTreeEntity entity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" INSERT INTO ");
        sbSql.append("   sys_function_tree ");
        sbSql.append("   ( ");
        sbSql.append("      parentTreeId, ");
        sbSql.append("      treeName, ");
        sbSql.append("      id, ");
        sbSql.append("      routeUrl, ");
        sbSql.append("      status, ");
        sbSql.append("      permissionCode, ");
        sbSql.append("      sort, ");
        sbSql.append("      remark, ");
        sbSql.append("      identity, ");
        sbSql.append("      xclass, ");
        sbSql.append("      iconCls, ");
        sbSql.append("      createUser, ");
        sbSql.append("      modifyUser, ");
        sbSql.append("      treeType ");
        sbSql.append("   ) ");
        sbSql.append(" VALUES ");
        sbSql.append("   ( ");
        sbSql.append("      #{parentTreeId}, ");
        sbSql.append("      #{treeName}, ");
        sbSql.append("      #{id}, ");
        sbSql.append("      #{routeUrl}, ");
        sbSql.append("      #{status}, ");
        sbSql.append("      #{permissionCode}, ");
        sbSql.append("      #{sort}, ");
        sbSql.append("      #{remark}, ");
        sbSql.append("      #{identity}, ");
        sbSql.append("      #{xclass}, ");
        sbSql.append("      #{iconCls}, ");
        sbSql.append("      #{operationUserName}, ");
        sbSql.append("      #{operationUserName}, ");
        sbSql.append("      #{treeType} ");
        sbSql.append("   ) ");

        return sbSql.toString();
    }

    public String updateOne(SysFunctionTreeEntity entity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" UPDATE ");
        sbSql.append("   sys_function_tree ");
        sbSql.append(" SET ");
        sbSql.append("   parentTreeId = #{parentTreeId}, ");
        sbSql.append("   treeName = #{treeName}, ");
        sbSql.append("   routeUrl = #{routeUrl}, ");
        sbSql.append("   id = #{id}, ");
        sbSql.append("   status = #{status}, ");
        sbSql.append("   permissionCode = #{permissionCode}, ");
        sbSql.append("   sort = #{sort}, ");
        sbSql.append("   remark = #{remark}, ");
        sbSql.append("   identity = #{identity}, ");
        sbSql.append("   xclass = #{xclass}, ");
        sbSql.append("   iconCls = #{iconCls}, ");
        sbSql.append("   modifyUser = #{operationUserName}, ");
        sbSql.append("   treeType = #{treeType} ");
        sbSql.append(" WHERE ");
        sbSql.append("   functionTreeId = #{functionTreeId} ");

        return sbSql.toString();
    }
}
