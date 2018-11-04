package com.shankephone.mi.inventory.service;

import com.shankephone.mi.common.model.Pager;
import com.shankephone.mi.inventory.formbean.ShelvesFindEntity;
import com.shankephone.mi.inventory.vo.ShelvesVO;
import com.shankephone.mi.model.StockGoodsShelvesEntity;

import java.util.List;
import java.util.Map;

/**
 * 货架Service
 *
 * @author 司徒彬
 * @date 2018 /7/5 17:11
 */
public interface ShelvesService {
    /**
     * Gets pager list.
     *
     * @param findEntity the find entity
     * @return the pager list
     */
    Pager getPagerList(ShelvesFindEntity findEntity);

    /**
     * Gets shelves info.
     *
     * @param findEntity the find entity
     * @return the shelves info
     */
    Map<String,Object> getShelvesInfo(ShelvesFindEntity findEntity);

    /**
     * Add shelves info.
     *
     * @param shelvesVO the shelves vo
     * @throws Exception the exception
     */
    void addShelvesInfo(ShelvesVO shelvesVO) throws Exception;

    /**
     * Update shelves info.
     *
     * @param shelvesEntity the shelves entity
     */
    void updateShelvesInfo(StockGoodsShelvesEntity shelvesEntity);

    /**
     * Delete shelves info.
     *
     * @param goodsShelvesId the goods shelves id
     */
    void deleteShelvesInfo(Long goodsShelvesId);

    /**
     * Gets shelves info by search content.
     *
     * @param findEntity the find entity
     * @return the shelves info by search content
     */
    List<Map<String,Object>> getShelvesInfoBySearchContent(ShelvesFindEntity findEntity);


    /**
     * 获取货架房间号list
     *
     * @param findEntity the find entity
     * @return the shelves info by search content
     */
    List<Map<String,Object>> getShelvesHouseList(ShelvesFindEntity findEntity);

    /**
     *获取货柜货架list
     *
     * @param findEntity the find entity
     * @return the shelves info by search content
     */
    Map<String,Object> getShelvesMap(ShelvesFindEntity findEntity);
}
