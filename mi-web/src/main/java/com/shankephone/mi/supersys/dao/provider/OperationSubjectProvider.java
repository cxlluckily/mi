package com.shankephone.mi.supersys.dao.provider;

import com.shankephone.mi.model.SysOperationSubjectEntity;
import com.shankephone.mi.supersys.formbean.OperationSubjectFindEntity;
import com.shankephone.mi.util.StringUtils;

/**
 * @author 赵亮
 * @date 2018-07-23 13:36
 */
public class OperationSubjectProvider
{
    public String insertOne(SysOperationSubjectEntity entity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" INSERT INTO ");
        sbSql.append("   sys_operation_subject ");
        sbSql.append("   ( ");
        sbSql.append("      regionId, ");
        sbSql.append("      subjectCode, ");
        sbSql.append("      subjectName, ");
        sbSql.append("      contacts, ");
        sbSql.append("      contactPhone, ");
        sbSql.append("      remark, ");
        sbSql.append("      loginName, ");
        sbSql.append("      password, ");
        sbSql.append("      status, ");
        sbSql.append("      createUser, ");
        sbSql.append("      modifyUser ");
        sbSql.append("   ) ");
        sbSql.append(" VALUES ");
        sbSql.append("   ( ");
        sbSql.append("      #{regionId}, ");
        sbSql.append("      #{subjectCode}, ");
        sbSql.append("      #{subjectName}, ");
        sbSql.append("      #{contacts}, ");
        sbSql.append("      #{contactPhone}, ");
        sbSql.append("      #{remark}, ");
        sbSql.append("      #{loginName}, ");
        sbSql.append("      #{password}, ");
        sbSql.append("      #{status}, ");
        sbSql.append("      #{operationUserName}, ");
        sbSql.append("      #{operationUserName} ");
        sbSql.append("   ) ");

        return sbSql.toString();
    }

    public String updateOne(SysOperationSubjectEntity entity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" UPDATE ");
        sbSql.append("   sys_operation_subject ");
        sbSql.append(" SET ");
        sbSql.append("   regionId = #{regionId}, ");
        sbSql.append("   subjectCode = #{subjectCode}, ");
        sbSql.append("   subjectName = #{subjectName}, ");
        sbSql.append("   contacts = #{contacts}, ");
        sbSql.append("   contactPhone = #{contactPhone}, ");
        sbSql.append("   remark = #{remark}, ");
        sbSql.append("   status = #{status}, ");
        sbSql.append("   modifyUser = #{operationUserName} ");
        sbSql.append(" WHERE ");
        sbSql.append("   operationSubjectId = #{operationSubjectId} ");
        return sbSql.toString();

    }

    public String getOperationList(OperationSubjectFindEntity findEntity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT sys_operation_subject.*,areaName,DATE_FORMAT(sys_operation_subject.createTime, '%Y-%m-%d') AS insertDate ");
        sbSql.append(" FROM sys_operation_subject INNER JOIN sys_region ON sys_region.regionId = sys_operation_subject.regionId ");
        sbSql.append(" WHERE 1 = 1 ");
        if(!"all".equals(findEntity.getStatus()))
        {
            sbSql.append("       AND sys_operation_subject.status = #{status} ");
        }
        if(StringUtils.isNotEmpty(findEntity.getSubjectCode()))
        {
            sbSql.append("       AND subjectCode LIKE concat('%', #{subjectCode}, '%') ");
        }
        if(StringUtils.isNotEmpty(findEntity.getSubjectName()))
        {
            sbSql.append("       AND subjectName LIKE concat('%', #{subjectName}, '%') ");
        }
        if(StringUtils.isNotEmpty(findEntity.getContacts()))
        {
            sbSql.append("       AND contacts LIKE concat('%', #{contacts}, '%') ");
        }
        sbSql.append("  ORDER BY insertDate DESC ");
        return sbSql.toString();

    }

    public String getOperationListCount(OperationSubjectFindEntity findEntity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT count(*) AS totalCount  ");
        sbSql.append(" FROM sys_operation_subject ");
        sbSql.append(" WHERE 1 = 1 ");
        if(!"all".equals(findEntity.getStatus()))
        {
            sbSql.append("       AND status = #{status} ");
        }
        if(StringUtils.isNotEmpty(findEntity.getSubjectCode()))
        {
            sbSql.append("       AND subjectCode LIKE concat('%', #{subjectCode}, '%') ");
        }
        if(StringUtils.isNotEmpty(findEntity.getSubjectName()))
        {
            sbSql.append("       AND subjectName LIKE concat('%', #{subjectName}, '%') ");
        }
        if(StringUtils.isNotEmpty(findEntity.getContacts()))
        {
            sbSql.append("       AND contacts LIKE concat('%', #{contacts}, '%') ");
        }
        return sbSql.toString();

    }

    public String updateAdminPassword(OperationSubjectFindEntity findEntity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" UPDATE sys_user ");
        sbSql.append(" SET password = #{password}, ");
        sbSql.append("     modifyUser = #{operationUserName}, ");
        sbSql.append("     openId = '' ");
        sbSql.append(" WHERE operationSubjectId IN (" + findEntity.getOperationSubjectIds() + ") ");

        sbSql.append(" AND wasDefaultManager =1 ");
        return sbSql.toString();
    }
}
