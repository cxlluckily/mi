package com.shankephone.mi.org.dao;

import com.shankephone.mi.model.OrgStationEntity;
import com.shankephone.mi.org.dao.provider.StationProvider;
import com.shankephone.mi.org.formbean.StationFindEntity;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 车站
 * @author 赵亮
 * @date 2018-06-25 17:29
 */
@Repository
public interface StationDao
{
    @InsertProvider(type = StationProvider.class, method = "insertOne")
    @Options(useGeneratedKeys = true, keyProperty = "stationId")
    Integer insertOne(OrgStationEntity entity);

    @UpdateProvider(type = StationProvider.class, method = "updateOne")
    Integer updateOne(OrgStationEntity entity);

    @SelectProvider(type = StationProvider.class, method = "getStationInfo")
    List<Map<String, Object>> getStationInfo(StationFindEntity findEntity);

    @SelectProvider(type = StationProvider.class, method = "getStationInfoTotal")
    Integer getStationInfoTotal(StationFindEntity findEntity);

    @SelectProvider(type = StationProvider.class, method = "getLineStationInfo")
    List<Map<String, Object>> getLineStationInfo(StationFindEntity findEntity);

}
