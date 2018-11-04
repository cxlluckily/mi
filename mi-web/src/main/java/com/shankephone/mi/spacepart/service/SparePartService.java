package com.shankephone.mi.spacepart.service;

import com.shankephone.mi.common.model.Pager;
import com.shankephone.mi.common.vo.ResultVO;
import com.shankephone.mi.model.PartSparePartEntity;
import com.shankephone.mi.model.PartSparePartImageEntity;
import com.shankephone.mi.spacepart.formbean.SparePartFindEntity;
import com.shankephone.mi.spacepart.vo.SparePartVO;

import java.util.List;
import java.util.Map;

/**
 * @author 赵亮
 * @date 2018-06-29 13:41
 */
public interface SparePartService
{

    String insertOne(PartSparePartEntity entity,List<PartSparePartImageEntity> partImageEntities);

    String updateOne(PartSparePartEntity entity,List<PartSparePartImageEntity> partImageEntities);

    Pager<Map<String, Object>> getSparePartInfo(SparePartFindEntity findEntity);

    SparePartVO getSparePartDetail(Long sparePartId);

    Integer getSamePartCount(PartSparePartEntity entity);

    /**
     *导入车站信息列表
     *@author：郝伟州
     *@date：2018年8月30日20:32:58
     */
    ResultVO importSparePartList(SparePartFindEntity findEntity, List<String[]> list);

    List<Map<String, Object>> getAllSparePartListMap(SparePartFindEntity findEntity);


    ResultVO importSparePartTypeList(SparePartFindEntity findEntity, List<String[]> list);

   // List<Map<String, Object>> getAllSparePartListMap(SparePartFindEntity findEntity);
    List<Map<String, Object>> getSparePartImagesList(Long sparePartId);
}
