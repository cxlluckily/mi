package com.shankephone.mi.org.service;

import com.shankephone.mi.common.model.Pager;
import com.shankephone.mi.common.vo.ResultVO;
import com.shankephone.mi.org.formbean.StationFindEntity;
import com.shankephone.mi.org.formbean.WorkSectionFIndEntity;
import com.shankephone.mi.org.formbean.WorkSectionFormBean;
import com.shankephone.mi.org.vo.LineVO;

import java.util.List;
import java.util.Map;

/**
 * @author 赵亮
 * @date 2018-06-28 10:57
 */
public interface WorkSectionService
{
    ResultVO insertOne(WorkSectionFormBean entity);

    List<LineVO> getLineAndStationInfo(WorkSectionFIndEntity findEntity);

    ResultVO updateOne(WorkSectionFormBean entity);

    Pager<Map<String, Object>> getWorkSectionInfo(WorkSectionFIndEntity findEntity);

    /**
     *导入工区线路车站信息列表
     *@author：郝伟州
     *@date：2018年10月23日17:54:21
     */
    ResultVO importWorkSectionLineStationList(StationFindEntity findEntity, List<String[]> list);
}
