package com.shankephone.mi.inventory.dao;

import com.shankephone.mi.inventory.dao.provider.ShelvesProvider;
import com.shankephone.mi.inventory.formbean.ShelvesFindEntity;
import com.shankephone.mi.model.StockGoodsShelvesEntity;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 货架 dao
 *
 * @author 司徒彬
 * @date 2018 /7/5 17:12
 */
@Repository
public interface ShelvesDao {

    /**
     * Gets pager list.
     *
     * @param findEntity the find entity
     * @return the pager list
     */
    @SelectProvider(type = ShelvesProvider.class, method = "getPagerList")
    List<Map<String, Object>> getPagerList(ShelvesFindEntity findEntity);

    /**
     * Gets pager count.
     *
     * @param findEntity the find entity
     * @return the pager count
     */
    @SelectProvider(type = ShelvesProvider.class, method = "getPagerCount")
    int getPagerCount(ShelvesFindEntity findEntity);

    /**
     * Gets shelves info.
     *
     * @param goodsShelvesId the goodsShelvesId
     * @return the shelves info
     */
    @SelectProvider(type = ShelvesProvider.class, method = "getShelvesInfo")
    Map<String,Object> getShelvesInfo(Long goodsShelvesId);

    /**
     * Insert shelves info.
     *
     * @param shelvesEntity the shelves entity
     */
    @InsertProvider(type = ShelvesProvider.class, method = "insertShelvesInfo")
    @Options(useGeneratedKeys = true, keyProperty = "goodsShelvesId")
    void insertShelvesInfo(StockGoodsShelvesEntity shelvesEntity);

    /**
     * Update shelves info.
     *
     * @param shelvesEntity the shelves entity
     */
    @UpdateProvider(type = ShelvesProvider.class, method = "updateShelvesInfo")
    void updateShelvesInfo(StockGoodsShelvesEntity shelvesEntity);

    /**
     * Delete shelves info.
     *
     * @param goodsShelvesId the goods shelves id
     */
    @UpdateProvider(type = ShelvesProvider.class, method = "deleteShelvesInfo")
    void deleteShelvesInfo(Long goodsShelvesId);


    /**
     * Gets shelves info by search content.
     *
     * @param findEntity the find entity
     * @return the shelves info by search content
     */
    @SelectProvider(type = ShelvesProvider.class, method = "getShelvesInfoBySearchContent")
    List<Map<String,Object>> getShelvesInfoBySearchContent(ShelvesFindEntity findEntity);


    /**
     * 根据条件获取货架信息
     *
     * @param findEntity the find entity
     * @return the pager list
     */
    @SelectProvider(type = ShelvesProvider.class, method = "getShelvesList")
    List<Map<String, Object>> getShelvesList(ShelvesFindEntity findEntity);
}
