package com.shankephone.mi.org.dao.provider;

import com.shankephone.mi.model.OrgLineEntity;
import com.shankephone.mi.org.formbean.LineFindEntity;
import com.shankephone.mi.util.StringUtils;

/**
 * 线路管理
 *
 * @author 赵亮
 * @date 2018-06-25 15:39
 */
public class LineProvider
{
    public String insertOne(OrgLineEntity entity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" INSERT INTO ");
        sbSql.append("   org_line ");
        sbSql.append("   ( ");
        sbSql.append("      operationSubjectId, ");
        sbSql.append("      lineName, ");
        sbSql.append("      lineCode, ");
        sbSql.append("      headPerson, ");
        sbSql.append("      phone, ");
        sbSql.append("      remark, ");
        sbSql.append("      status, ");
        sbSql.append("      createUser, ");
        sbSql.append("      modifyUser ");
        sbSql.append("   ) ");
        sbSql.append(" VALUES ");
        sbSql.append("   ( ");
        sbSql.append("      #{operationSubjectId}, ");
        sbSql.append("      #{lineName}, ");
        sbSql.append("      #{lineCode}, ");
        sbSql.append("      #{headPerson}, ");
        sbSql.append("      #{phone}, ");
        sbSql.append("      #{remark}, ");
        sbSql.append("      #{status}, ");
        sbSql.append("      #{operationUserName}, ");
        sbSql.append("      #{operationUserName} ");
        sbSql.append("   ) ");
        return sbSql.toString();
    }

    public String updateOne(OrgLineEntity entity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" UPDATE ");
        sbSql.append("   org_line ");
        sbSql.append(" SET ");
        sbSql.append("   operationSubjectId = #{operationSubjectId}, ");
        sbSql.append("   lineName = #{lineName}, ");
        sbSql.append("   lineCode = #{lineCode}, ");
        sbSql.append("   headPerson = #{headPerson}, ");
        sbSql.append("   phone = #{phone}, ");
        sbSql.append("   remark = #{remark}, ");
        sbSql.append("   status = #{status}, ");
        sbSql.append("   modifyUser = #{operationUserName} ");
        sbSql.append(" WHERE ");
        sbSql.append("   lineId = #{lineId} ");
        return sbSql.toString();
    }

    /**
     * 根据查询条件返回线路信息
     *
     * @author：赵亮
     * @date：2018-06-22 11:07
     */
    public String getLineInfo(LineFindEntity findEntity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT * ");
        sbSql.append(" FROM org_line ");
        sbSql.append(" WHERE 1=1 ");
        if(StringUtils.isNotEmpty(findEntity.getOperationSubjectId()))
        {
            sbSql.append("       AND operationSubjectId = #{operationSubjectId} ");
        }
        sbSql.append(" ORDER BY lineCode ASC ");
        return sbSql.toString();

    }

    /**
     * 根据查询条件返回线路信息总数
     *
     * @author：赵亮
     * @date：2018-06-22 11:07
     */
    public String getLineInfoTotal(LineFindEntity findEntity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT count(*) AS totalCount ");
        sbSql.append(" FROM org_line ");
        sbSql.append(" WHERE 1=1 ");
        if(StringUtils.isNotEmpty(findEntity.getOperationSubjectId()))
        {
            sbSql.append("       AND operationSubjectId = #{operationSubjectId} ");
        }

        return sbSql.toString();

    }
}
