package com.shankephone.mi.org.service;

import com.shankephone.mi.common.model.Pager;
import com.shankephone.mi.common.vo.ResultVO;
import com.shankephone.mi.model.OrgStationEntity;
import com.shankephone.mi.org.formbean.StationFindEntity;

import java.util.List;
import java.util.Map;

/**
 * 车站
 * @author 赵亮
 * @date 2018-06-25 17:33
 */
public interface StationService
{
    ResultVO batchInsert(List<OrgStationEntity> entity);

    ResultVO updateOne(OrgStationEntity entity);

    Pager<Map<String, Object>> getStationInfo(StationFindEntity findEntity);

    /**
     *导入车站信息列表
     *@author：郝伟州
     *@date：2018年8月30日20:32:58
     */
    ResultVO importStationList(StationFindEntity findEntity, List<String[]> list);

    /**
     *导入线路车站信息列表
     *@author：郝伟州
     *@date：2018年8月30日20:32:58
     */
    ResultVO importLineStationList(StationFindEntity findEntity, List<String[]> list);

    List<Map<String, Object>> getAllStationListMap(StationFindEntity findEntity);
}
