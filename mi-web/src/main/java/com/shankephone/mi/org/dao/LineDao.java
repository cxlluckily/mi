package com.shankephone.mi.org.dao;

import com.shankephone.mi.model.OrgLineEntity;
import com.shankephone.mi.org.dao.provider.LineProvider;
import com.shankephone.mi.org.formbean.LineFindEntity;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 线路
 * @author 赵亮
 * @date 2018-06-25 15:39
 */
@Repository
public interface LineDao
{
    /**
     *添加线路
     *@author：赵亮
     *@date：2018-06-21 15:24
     */
    @InsertProvider(type = LineProvider.class, method = "insertOne")
    @Options(useGeneratedKeys = true, keyProperty = "lineId")
    Integer insertOne(OrgLineEntity entity);

    /**
     *更新线路
     *@author：赵亮
     *@date：2018-06-22 16:58
     */
    @UpdateProvider(type = LineProvider.class, method = "updateOne")
    Integer updateOne(OrgLineEntity entity);

    @SelectProvider(type = LineProvider.class, method = "getLineInfo")
    List<Map<String, Object>> getLineInfo(LineFindEntity findEntity);

    @SelectProvider(type = LineProvider.class, method = "getLineInfoTotal")
    Integer getLineInfoTotal(LineFindEntity findEntity);
}
