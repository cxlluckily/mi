package com.shankephone.mi.inventory.dao;

import com.shankephone.mi.inventory.dao.provider.StockInfoProvider;
import com.shankephone.mi.inventory.formbean.StockInfoFindEntity;
import com.shankephone.mi.model.StockStockEntity;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 库存管理
 *
 * @author 司徒彬
 * @date 2018 /7/26 20:04
 */
@Repository
public interface StockInfoDao {
    /**
     * Gets pager info.
     *
     * @param findEntity the find entity
     * @return the pager info
     */
    @SelectProvider(type = StockInfoProvider.class, method = "getPagerInfo")
    List<Map<String,Object>> getPagerInfo(StockInfoFindEntity findEntity);

    /**
     * Gets pager info total.
     *
     * @param findEntity the find entity
     * @return the pager info total
     */
    @SelectProvider(type = StockInfoProvider.class, method = "getPagerInfoTotal")
    Integer getPagerInfoTotal(StockInfoFindEntity findEntity);

    /**
     * Gets pager detail info.
     *
     * @param findEntity the find entity
     * @return the pager detail info
     */
    @SelectProvider(type = StockInfoProvider.class, method = "getPagerDetailInfo")
    List<Map<String,Object>> getPagerDetailInfo(StockInfoFindEntity findEntity);

    /**
     * Gets pager detail info total.
     *
     * @param findEntity the find entity
     * @return the pager detail info total
     */
    @SelectProvider(type = StockInfoProvider.class, method = "getPagerDetailInfoTotal")
    Integer getPagerDetailInfoTotal(StockInfoFindEntity findEntity);

    /**
     * Gets stock info by id.
     *
     * @param stockId the stock id
     * @return the stock info by id
     */
    @SelectProvider(type = StockInfoProvider.class, method = "getStockInfoById")
    StockStockEntity getStockInfoById(Long stockId);

    /**
     * Update stock info.
     *
     * @param entity the entity
     */
    @SelectProvider(type = StockInfoProvider.class, method = "updateStockInfo")
    void updateStockInfo(StockStockEntity entity);

    /**
     * Gets stock info by info.
     *
     * @param entity the entity
     * @return the stock info by info
     */
    @SelectProvider(type = StockInfoProvider.class, method = "getStockInfoByInfo")
    StockStockEntity getStockInfoByInfo(StockStockEntity entity);

    @InsertProvider(type = StockInfoProvider.class, method = "insertStockInfo")
    @Options(useGeneratedKeys = true, keyProperty = "stockId")
    Long insertStockInfo(StockStockEntity entity);


    /**
     * Gets stock info by id.
     *
     * @param sparePartId the stock id
     * @return the stock info by id
     */
   // @SelectProvider(type = StockInfoProvider.class, method = "getSparePartImages")
   // List<Map<String,Object>> getSparePartImages(Long sparePartId);

    /**
     * Gets stock info by id.
     *
     * @param findEntity the stock id

     * @return the stock info by id
     */
    @SelectProvider(type = StockInfoProvider.class, method = "getSparePartEntity")
    Map<String,Object> getSparePartEntity(StockInfoFindEntity findEntity);

    @SelectProvider(type = StockInfoProvider.class, method = "getStocKSparePartEntity")
    Map<String,Object> getStocKSparePartEntity(StockInfoFindEntity findEntity);

    /**
     * Gets 获取仓库和货架信息
     *
     * @param operationSubjectId the find entity
     * @return the pager detail info
     */
    @SelectProvider(type = StockInfoProvider.class, method = "getWarhouseAndshelves")
    List<Map<String,Object>> getWarhouseAndshelves(Long operationSubjectId);
}
