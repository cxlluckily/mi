package com.shankephone.mi.supersys.dao;

import com.shankephone.mi.model.SysOperationSubjectEntity;
import com.shankephone.mi.supersys.dao.provider.OperationSubjectProvider;
import com.shankephone.mi.supersys.formbean.OperationSubjectFindEntity;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author 赵亮
 * @date 2018-07-23 13:36
 */
@Repository
public interface OperationSubjectDao
{
    @InsertProvider(type = OperationSubjectProvider.class, method = "insertOne")
    @Options(useGeneratedKeys = true, keyProperty = "operationSubjectId")
    Integer insertOne(SysOperationSubjectEntity entity);

    @UpdateProvider(type = OperationSubjectProvider.class, method = "updateOne")
    Integer updateOne(SysOperationSubjectEntity entity);

    @SelectProvider(type = OperationSubjectProvider.class, method = "getOperationList")
    List<Map<String,Object>> getOperationList(OperationSubjectFindEntity findEntity);

    @SelectProvider(type = OperationSubjectProvider.class, method = "getOperationListCount")
    Integer getOperationListCount(OperationSubjectFindEntity findEntity);

    @UpdateProvider(type = OperationSubjectProvider.class, method = "updateAdminPassword")
    Integer updateAdminPassword(OperationSubjectFindEntity findEntity);
}
