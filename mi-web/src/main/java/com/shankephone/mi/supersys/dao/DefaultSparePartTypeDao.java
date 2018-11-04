package com.shankephone.mi.supersys.dao;

import com.shankephone.mi.model.PartDefaultSparePartTypeEntity;
import com.shankephone.mi.supersys.dao.provider.DefaultSparePartTypeProvider;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 赵亮
 * @date 2018-07-23 13:51
 */
@Repository
public interface DefaultSparePartTypeDao
{
    @SelectProvider(type = DefaultSparePartTypeProvider.class, method = "getAllDefaultSparePartList")
    List<PartDefaultSparePartTypeEntity> getAllDefaultSparePartList(String status);
}
