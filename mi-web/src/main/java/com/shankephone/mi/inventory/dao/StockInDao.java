package com.shankephone.mi.inventory.dao;

import com.shankephone.mi.inventory.dao.provider.StockInProvider;
import com.shankephone.mi.inventory.formbean.StockInFindEntity;
import com.shankephone.mi.inventory.vo.StockInStockDetailVO;
import com.shankephone.mi.inventory.vo.StockInVO;
import com.shankephone.mi.model.*;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 入库单Dao
 *
 * @author 司徒彬
 * @date 2018 /7/17 10:15
 */
@Repository
public interface StockInDao {
    /**
     * Gets pager info.
     *
     * @param findEntity the find entity
     * @return the pager info
     */
    @SelectProvider(type = StockInProvider.class, method = "getPagerInfo")
    List<Map<String,Object>> getPagerInfo(StockInFindEntity findEntity);

    /**
     * Gets pager info total.
     *
     * @param findEntity the find entity
     * @return the pager info total
     */
    @SelectProvider(type = StockInProvider.class, method = "getPagerInfoTotal")
    int getPagerInfoTotal(StockInFindEntity findEntity);

    /**
     * Gets stock in info by id.
     *
     * @param inStockId the in stock id
     * @return the stock in info by id
     */
    @SelectProvider(type = StockInProvider.class, method = "getStockInInfoById")
    Map<String,Object> getStockInInfoById(Long inStockId);

    /**
     * Gets stock in detail by in stock id.
     *
     * @param inStockId the in stock id
     * @return the stock in detail by in stock id
     */
    @SelectProvider(type = StockInProvider.class, method = "getStockInDetailByInStockId")
    List<Map<String,Object>> getStockInDetailByInStockId(Long inStockId);

    @SelectProvider(type = StockInProvider.class, method = "getStockInDetailByInStockIdAlreadyIn")
    List<Map<String,Object>> getStockInDetailByInStockIdAlreadyIn(Long inStockId);
    @SelectProvider(type = StockInProvider.class, method = "getStockInDetailByInStocSparePartkId")
    List<Map<String,Object>> getStockInDetailByInStocSparePartkId(Long inStockId);

    @SelectProvider(type = StockInProvider.class, method = "getNewStockInDetailByInStockId")
    List<Map<String,Object>> getNewStockInDetailByInStockId(Long inStockId);

    /**
     * Gets stock info.
     *
     * @param detailEntity the detail entity
     * @return the stock info
     */
    @SelectProvider(type = StockInProvider.class, method = "getStockInfo")
    StockStockEntity getStockInfo(StockInStockDetailVO detailEntity);



    /**
     * Insert stock info.
     *
     * @param stockEntity the stock entity
     */
    @InsertProvider(type = StockInProvider.class, method = "insertStockInfo")
    @Options(useGeneratedKeys = true, keyProperty = "stockId")
    void insertStockInfo(StockStockEntity stockEntity);

    /**
     * Insert stock log.
     *
     * @param logEntity the log entity
     */
    @InsertProvider(type = StockInProvider.class, method = "insertStockLog")
    @Options(useGeneratedKeys = true, keyProperty = "flowId")
    void insertStockLog(StockFlowLogEntity logEntity);

    /**
     * Update stock in status.
     *
     * @param inStockId the in stock id
     */
    @UpdateProvider(type = StockInProvider.class, method = "updateStockInStatus")
    void updateStockInStatus(Long inStockId);

    /**
     * Update stock in status.
     *
     * @param stockInStockEntity the in stock id
     */
    @UpdateProvider(type = StockInProvider.class, method = "updateStockInEntity")
    void updateStockInEntity(StockInStockEntity stockInStockEntity);

    /**
     * Insert in stock apply.
     *
     * @param stockInVO the stock in vo
     */
    @InsertProvider(type = StockInProvider.class, method = "insertInStockApply")
    @Options(useGeneratedKeys = true, keyProperty = "inStockId")
    void insertInStockApply(StockInVO stockInVO);

    /**
     * Insert in stock apply detail.
     *
     * @param detailEntity the detail entity
     */
    @InsertProvider(type = StockInProvider.class, method = "insertInStockApplyDetail")
    @Options(useGeneratedKeys = true, keyProperty = "inStockDetailId")
    void insertInStockApplyDetail(StockInStockDetailVO detailEntity);

    @UpdateProvider(type = StockInProvider.class,method = "updateStockInfo")
    void updateStockInfo(StockStockEntity stockEntity);

    @SelectProvider(type = StockInProvider.class, method = "getSplitDetailInfo")
    List<Map<String,Object>> getSplitDetailInfo(String ids);

    @SelectProvider(type = StockInProvider.class, method = "getQrCodeInStockId")
    Integer getQrCodeInStockId(StockInStockDetailVO entity);

    @SelectProvider(type = StockInProvider.class, method = "getQrCodeOnOperationsEquipment")
    Integer getQrCodeOnOperationsEquipment(StockInStockDetailVO entity);

    @InsertProvider(type = StockInProvider.class, method = "insertStockAndStock")
    @Options(useGeneratedKeys = true, keyProperty = "stockAndStockId")
    Integer insertStockAndStock(StockAndStockEntity entity);


    @SelectProvider(type = StockInProvider.class, method = "getSpartTypeList")
    List<Map<String,Object>> getSpartTypeList(StockInStockDetailVO findentity);
}
