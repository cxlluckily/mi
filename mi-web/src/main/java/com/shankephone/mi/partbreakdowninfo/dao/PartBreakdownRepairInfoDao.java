package com.shankephone.mi.partbreakdowninfo.dao;

import com.shankephone.mi.model.PartBreakdownRepairInfoEntity;
import com.shankephone.mi.partbreakdowninfo.dao.provider.PartBreakdownRepairInfoProvider;
import com.shankephone.mi.partbreakdowninfo.formbean.PartBreakdownRepairInfoFindEntity;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author 赵亮
 * @date 2018-08-02 13:43
 */
@Repository
public interface PartBreakdownRepairInfoDao
{
    @InsertProvider(type = PartBreakdownRepairInfoProvider.class, method = "insertOne")
    @Options(useGeneratedKeys = true, keyProperty = "repairInfoId")
    Integer insertOne(PartBreakdownRepairInfoEntity entity);

    @UpdateProvider(type = PartBreakdownRepairInfoProvider.class, method = "updateOne")
    Integer updateOne(PartBreakdownRepairInfoEntity entity);

    @SelectProvider(type = PartBreakdownRepairInfoProvider.class, method = "getPartBreakdownRepairInfoList")
    List<Map<String,Object>> getPartBreakdownRepairInfoList(PartBreakdownRepairInfoFindEntity findEntity);

    @SelectProvider(type = PartBreakdownRepairInfoProvider.class, method = "getPartBreakdownRepairInfoListCount")
    Integer getPartBreakdownRepairInfoListCount(PartBreakdownRepairInfoFindEntity findEntity);
}
