package com.shankephone.mi.spacepart.service;

import com.shankephone.mi.common.vo.ResultVO;
import com.shankephone.mi.model.PartSparePartTypeEntity;
import com.shankephone.mi.spacepart.formbean.PartSparePartTypeBusiEntity;
import com.shankephone.mi.spacepart.formbean.PartSparePartTypeFindEntity;

import java.util.List;
import java.util.Map;

/**
 * @author 赵亮
 * @date 2018-06-28 20:45
 */
public interface SparePartTypeService
{
    ResultVO insertOne(PartSparePartTypeBusiEntity entity);

    ResultVO updateOne(PartSparePartTypeEntity entity);

    List<Map<String, Object>> getSparePartTypeInfo(PartSparePartTypeFindEntity findEntity);

    List<Map<String, Object>> getSparePartTypeKongGeList(PartSparePartTypeFindEntity findEntity);
}
