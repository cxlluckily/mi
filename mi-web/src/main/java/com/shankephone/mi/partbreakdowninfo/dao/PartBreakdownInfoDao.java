package com.shankephone.mi.partbreakdowninfo.dao;

import com.shankephone.mi.model.PartBreakdownInfoEntity;
import com.shankephone.mi.partbreakdowninfo.dao.provider.PartBreakdownInfoProvider;
import com.shankephone.mi.partbreakdowninfo.formbean.PartBreakdownInfoDDLFindEntity;
import com.shankephone.mi.partbreakdowninfo.formbean.PartBreakdownInfoFindEntity;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author 赵亮
 * @date 2018-08-02 9:52
 */
@Repository
public interface PartBreakdownInfoDao
{
    @InsertProvider(type = PartBreakdownInfoProvider.class, method = "insertOne")
    @Options(useGeneratedKeys = true, keyProperty = "breakdownInfoId")
    Integer insertOne(PartBreakdownInfoEntity entity);

    @UpdateProvider(type = PartBreakdownInfoProvider.class, method = "updateOne")
    Integer updateOne(PartBreakdownInfoEntity entity);

    @SelectProvider(type = PartBreakdownInfoProvider.class, method = "getBreakdownInfoList")
    List<Map<String,Object>> getBreakdownInfoList(PartBreakdownInfoFindEntity findEntity);

    @SelectProvider(type = PartBreakdownInfoProvider.class, method = "getBreakdownInfoListCount")
    Integer getBreakdownInfoListCount(PartBreakdownInfoFindEntity findEntity);

    @SelectProvider(type = PartBreakdownInfoProvider.class, method = "getPartBreakdownInfoDDLList")
    List<PartBreakdownInfoEntity> getPartBreakdownInfoDDLList(PartBreakdownInfoDDLFindEntity findEntity);

    @SelectProvider(type = PartBreakdownInfoProvider.class, method = "getSparePartList")
    List<Map<String,Object>> getSparePartList(PartBreakdownInfoFindEntity findEntity);
}
