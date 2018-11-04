package com.shankephone.mi.spacepart.dao;

import com.shankephone.mi.model.PartDeviceComposeEntity;
import com.shankephone.mi.spacepart.dao.provider.PartDeviceComposeProvider;
import com.shankephone.mi.spacepart.formbean.ComposeListFindEntity;
import com.shankephone.mi.spacepart.formbean.OneTreeDataFindEntity;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author 赵亮
 * @date 2018-08-13 11:08
 */
@Repository
public interface PartDeviceComposeDao
{

    @InsertProvider(type = PartDeviceComposeProvider.class, method = "insertOne")
    @Options(useGeneratedKeys = true, keyProperty = "deviceComposeId")
    Integer insertOne(PartDeviceComposeEntity entity);

    @UpdateProvider(type = PartDeviceComposeProvider.class, method = "updateOne")
    Integer updateOne(PartDeviceComposeEntity entity);

    @SelectProvider(type = PartDeviceComposeProvider.class, method = "getComposeList")
    List<Map<String, Object>> getComposeList(ComposeListFindEntity findEntity);

    @SelectProvider(type = PartDeviceComposeProvider.class, method = "getComposeListCount")
    Integer getComposeListCount(ComposeListFindEntity findEntity);
    @SelectProvider(type = PartDeviceComposeProvider.class, method = "getUserIDCount")
    Integer getUserIDCount(PartDeviceComposeEntity entity);

    @DeleteProvider(type = PartDeviceComposeProvider.class, method = "deleteOne")
    Integer deleteOne(PartDeviceComposeEntity entity);

    @SelectProvider(type = PartDeviceComposeProvider.class, method = "getOneTreeData")
    List<Map<String, Object>>getOneTreeData(OneTreeDataFindEntity findEntity);
}
