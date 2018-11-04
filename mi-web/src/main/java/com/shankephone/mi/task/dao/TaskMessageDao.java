package com.shankephone.mi.task.dao;

import com.shankephone.mi.model.TaskMessageEntity;
import com.shankephone.mi.task.dao.provider.TaskMessageProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.UpdateProvider;
import org.springframework.stereotype.Repository;

/**
 * @author 郝伟州
 * @date 2018年9月26日10:01:15
 */
@Repository
public interface TaskMessageDao
{
    @InsertProvider(type = TaskMessageProvider.class, method = "insertOne")
    @Options(useGeneratedKeys = true, keyProperty = "messageId")
    Integer insertOne(TaskMessageEntity entity);

    @UpdateProvider(type = TaskMessageProvider.class, method = "updateOne")
    Integer updateOne(TaskMessageEntity entity);

}
