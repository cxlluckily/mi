package com.shankephone.mi.supersys.dao.provider;

import com.shankephone.mi.common.enumeration.StatusEnum;
import com.shankephone.mi.model.SysAdminEntity;
import com.shankephone.mi.supersys.formbean.DeleteAdminFormBean;
import com.shankephone.mi.supersys.formbean.SuperAdminFindEntity;
import com.shankephone.mi.util.StringUtils;

/**
 * @author 赵亮
 * @date 2018-07-23 9:18
 */
public class SuperAdminProvider
{
    public String insertAdmin(SysAdminEntity entity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" INSERT INTO ");
        sbSql.append("   sys_admin ");
        sbSql.append("   ( ");
        sbSql.append("      userName, ");
        sbSql.append("      password, ");
        sbSql.append("      realName, ");
        sbSql.append("      phone, ");
        sbSql.append("      status, ");
        sbSql.append("      salt, ");
        sbSql.append("      createUser, ");
        sbSql.append("      modifyUser ");
        sbSql.append("   ) ");
        sbSql.append(" VALUES ");
        sbSql.append("   ( ");
        sbSql.append("      #{userName}, ");
        sbSql.append("      #{password}, ");
        sbSql.append("      #{realName}, ");
        sbSql.append("      #{phone}, ");
        sbSql.append("      #{status}, ");
        sbSql.append("      #{salt}, ");
        sbSql.append("      #{operationUserName}, ");
        sbSql.append("      #{operationUserName} ");
        sbSql.append("   ) ");

        return sbSql.toString();
    }

    public String updateAdmin(SysAdminEntity entity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" UPDATE ");
        sbSql.append("   sys_admin ");
        sbSql.append(" SET ");
//        sbSql.append("   userName = #{userName}, ");
//        sbSql.append("   password = #{password}, ");
        sbSql.append("   realName = #{realName}, ");
        sbSql.append("   phone = #{phone}, ");
        sbSql.append("   status = #{status}, ");
        sbSql.append("   salt = #{salt}, ");
        sbSql.append("   modifyUser = #{operationUserName} ");
        sbSql.append(" WHERE ");
        sbSql.append("   adminId = #{adminId} ");
        return sbSql.toString();
    }

    public String getSuperAdminList(SuperAdminFindEntity findEntity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT adminId,userName,realName,phone,status,createUser,createTime,modifyUser,DATE_FORMAT(createTime, '%Y-%m-%d') AS insertDate ");
        sbSql.append(" FROM sys_admin ");
        sbSql.append(" WHERE 1 = 1 ");
        if (StringUtils.isNotEmpty(findEntity.getUserName()))
        {
            sbSql.append("       AND userName LIKE concat('%', #{userName}, '%') ");
        }
        if (StringUtils.isNotEmpty(findEntity.getRealName()))
        {
            sbSql.append("       AND realName LIKE concat('%', #{realName}, '%') ");
        }
        if (StringUtils.isNotEmpty(findEntity.getPhone()))
        {
            sbSql.append("       AND phone LIKE concat('%', #{phone}, '%') ");
        }
        if(!"all".equals(findEntity.getStatus()))
        {
            sbSql.append("       AND status = #{status} ");
        }
        sbSql.append("       AND status <> '"+ StatusEnum.DELETE_USING+"'");
        sbSql.append(" ORDER BY insertDate,userName DESC ");
        sbSql.append(" LIMIT #{start}, #{limit} ");
        return sbSql.toString();
    }

    public String getSuperAdminListCount(SuperAdminFindEntity findEntity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT count(*) AS totalCount  ");
        sbSql.append(" FROM sys_admin ");
        sbSql.append(" WHERE 1 = 1 ");
        if (StringUtils.isNotEmpty(findEntity.getUserName()))
        {
            sbSql.append("       AND userName LIKE concat('%', #{userName}, '%') ");
        }
        if (StringUtils.isNotEmpty(findEntity.getRealName()))
        {
            sbSql.append("       AND realName LIKE concat('%', #{realName}, '%') ");
        }
        if (StringUtils.isNotEmpty(findEntity.getPhone()))
        {
            sbSql.append("       AND phone LIKE concat('%', #{phone}, '%') ");
        }
        if(!"all".equals(findEntity.getStatus()))
        {
            sbSql.append("       AND status = #{status} ");
        }
        sbSql.append("       AND status <> '"+ StatusEnum.DELETE_USING+"'");
        return sbSql.toString();
    }

    public String initPassword(SuperAdminFindEntity findEntity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" UPDATE sys_admin ");
        sbSql.append(" SET password = #{password}, ");
        sbSql.append("     modifyUser = #{operationUserName} ");
        sbSql.append(" WHERE adminId IN (" + findEntity.getAdminIds() + ") ");
        return sbSql.toString();
    }

    public String findCountByUserName(String userName)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT count(*)  AS hasCount ");
        sbSql.append(" FROM sys_admin ");
        sbSql.append(" WHERE userName = #{userName} ");
        return sbSql.toString();
    }

    public String getAdminEntityByUserName(String userName)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT * ");
        sbSql.append(" FROM sys_admin ");
        sbSql.append(" WHERE userName = #{userName} ");

        return sbSql.toString();

    }

    public String deleteAdmin(DeleteAdminFormBean entity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" delete ");
        sbSql.append(" FROM sys_admin ");
        sbSql.append(" WHERE adminId in ( "+StringUtils.listToString(entity.getAdminIdarr())+") ");
        return sbSql.toString();
    }

}
