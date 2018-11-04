package com.shankephone.mi.task.dao.provider;

import com.shankephone.mi.model.TaskMessageEntity;

/**
 * @author 郝伟州
 * @date 2018年9月26日10:04:32
 */
public class TaskMessageProvider
{
    public String insertOne(TaskMessageEntity entity)
    {

        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" INSERT INTO ");
        sbSql.append("   task_message ");
        sbSql.append("   ( ");
        sbSql.append("      receiveId, ");
        sbSql.append("      messageType, ");
        sbSql.append("      content, ");
        sbSql.append("      status, ");
        sbSql.append("      createUser, ");
        sbSql.append("      sourceId ");
        sbSql.append("   ) ");
        sbSql.append(" VALUES ");
        sbSql.append("   ( ");
        sbSql.append("      #{receiveId}, ");
        sbSql.append("      #{messageType}, ");
        sbSql.append("      #{content}, ");
        sbSql.append("      #{status}, ");
        sbSql.append("      #{operationUserName}, ");
        sbSql.append("      #{sourceId} ");
        sbSql.append("   ) ");
        return sbSql.toString();
    }

    public String updateOne(TaskMessageEntity entity)
    {
        StringBuilder sbSql = new StringBuilder();

        sbSql.append(" UPDATE ");
        sbSql.append("   task_message ");
        sbSql.append(" SET ");
        sbSql.append("   receiveId = #{receiveId}, ");
        sbSql.append("   messageType = #{messageType}, ");
        sbSql.append("   content = #{content}, ");
        sbSql.append("   status = #{status}, ");
        sbSql.append("   sourceId = #{sourceId} ");
        sbSql.append(" WHERE ");
        sbSql.append("   messageId = #{messageId} ");
        return sbSql.toString();
    }


}
