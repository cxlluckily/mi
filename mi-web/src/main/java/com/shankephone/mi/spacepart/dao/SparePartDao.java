package com.shankephone.mi.spacepart.dao;

import com.shankephone.mi.model.PartSparePartEntity;
import com.shankephone.mi.model.PartSparePartImageEntity;
import com.shankephone.mi.spacepart.dao.provider.SparePartProvider;
import com.shankephone.mi.spacepart.formbean.SparePartFindEntity;
import com.shankephone.mi.spacepart.formbean.SparePartListForBreakdownFindEntity;
import com.shankephone.mi.spacepart.vo.SparePartVO;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author 赵亮
 * @date 2018-06-29 13:43
 */
@Repository
public interface SparePartDao
{
    @InsertProvider(type = SparePartProvider.class, method = "insertOne")
    @Options(useGeneratedKeys = true, keyProperty = "sparePartId")
    Integer insertOne(PartSparePartEntity entity);

    @InsertProvider(type = SparePartProvider.class, method = "insertPartImage")
    @Options(useGeneratedKeys = true, keyProperty = "sparePartImageId")
    Integer insertPartImage(PartSparePartImageEntity entity);

    @UpdateProvider(type = SparePartProvider.class, method = "updateOne")
    Integer updateOne(PartSparePartEntity entity);

    @SelectProvider(type = SparePartProvider.class, method = "getSparePartInfo")
    List<Map<String, Object>> getSparePartInfo(SparePartFindEntity findEntity);

    @SelectProvider(type = SparePartProvider.class, method = "getSparePartInfoCount")
    Integer getSparePartInfoCount(SparePartFindEntity findEntity);

    @DeleteProvider(type = SparePartProvider.class, method = "deletePartImageBysparePartId")
    Integer deletePartImageBysparePartId(Long sparePartId);

    @SelectProvider(type = SparePartProvider.class, method = "getSparePartDetail")
    List<SparePartVO> getSparePartDetail(Long sparePartId);

    @SelectProvider(type = SparePartProvider.class, method = "getSparePartImagesList")
    List<Map<String, Object>> getSparePartImagesList(Long sparePartId);

    @SelectProvider(type = SparePartProvider.class, method = "getSparePartListForBreakdown")
    List<PartSparePartEntity> getSparePartListForBreakdown(SparePartListForBreakdownFindEntity findEntity);

    @SelectProvider(type = SparePartProvider.class, method = "getPartListBySparePartTypeId")
    List<Map<String, Object>> getPartListBySparePartTypeId(PartSparePartEntity entity);

    @SelectProvider(type = SparePartProvider.class, method = "getSamePartCount")
    Integer getSamePartCount(PartSparePartEntity entity);

    @SelectProvider(type = SparePartProvider.class, method = "getPartListAndParentType")
    List<Map<String, Object>> getPartListAndParentType(SparePartFindEntity findEntity);
}
