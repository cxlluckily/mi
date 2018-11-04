package com.shankephone.mi.supersys.dao;

import com.shankephone.mi.supersys.dao.provider.RegionProvider;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author 赵亮
 * @date 2018-07-23 13:37
 */
@Repository
public interface RegionDao
{
    @SelectProvider(type = RegionProvider.class, method = "getAllRegion")
    List<Map<String,Object>> getAllRegion(String status);
}
