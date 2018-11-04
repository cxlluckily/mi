package com.shankephone.mi.spacepart.dao;

import com.shankephone.mi.model.PartSparePartTypeEntity;
import com.shankephone.mi.spacepart.dao.provider.SparePartTypeProvider;
import com.shankephone.mi.spacepart.formbean.PartDeviceComposeFindEntity;
import com.shankephone.mi.spacepart.formbean.PartSparePartTypeFindEntity;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author 赵亮
 * @date 2018-06-28 20:49
 */
@Repository
public interface SparePartTypeDao
{
    @InsertProvider(type = SparePartTypeProvider.class, method = "insertOne")
    @Options(useGeneratedKeys = true, keyProperty = "sparePartTypeId")
    Integer insertOne(PartSparePartTypeEntity entity);

    @UpdateProvider(type = SparePartTypeProvider.class, method = "updateOne")
    Integer updateOne(PartSparePartTypeEntity entity);

    @SelectProvider(type = SparePartTypeProvider.class, method = "getSparePartTypeInfo")
    List<Map<String, Object>> getSparePartTypeInfo(PartSparePartTypeFindEntity findEntity);

    @SelectProvider(type = SparePartTypeProvider.class, method = "getSparePartByCompose")
    List<PartSparePartTypeEntity> getSparePartByCompose(PartDeviceComposeFindEntity findEntity);


    @SelectProvider(type = SparePartTypeProvider.class, method = "getSparePartTypeKongGeList")
    List<Map<String, Object>> getSparePartTypeKongGeList(PartSparePartTypeFindEntity findEntity);
}
