package com.shankephone.mi.inventory.service;

import com.shankephone.mi.common.model.Pager;
import com.shankephone.mi.inventory.formbean.StockInFindEntity;
import com.shankephone.mi.inventory.vo.StockInVO;

import java.util.Map;

/**
 * 入库单Service
 *
 * @author 司徒彬
 * @date 2018 /7/17 10:15
 */
public interface StockInService {
    /**
     * Gets pager info.
     *
     * @param findEntity the find entity
     * @return the pager info
     */
    Pager getPagerInfo(StockInFindEntity findEntity);

    /**
     * Gets stock in info by id.
     *
     * @param inStockId the in stock id
     * @return the stock in info by id
     */
    Map<String,Object> getStockInInfoById(Long inStockId);

    /**
     * Commit stock in.
     *
     * @param stockInVO the stock in vo
     */
    void commitStockIn(StockInVO stockInVO) throws Exception;

    /**
     * Add new in apply.
     *
     * @param stockInVO the stock in vo
     */
    void addNewInApply(StockInVO stockInVO) throws Exception;


}
