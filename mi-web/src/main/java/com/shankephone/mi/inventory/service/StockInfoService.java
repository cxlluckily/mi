package com.shankephone.mi.inventory.service;

import com.shankephone.mi.common.model.Pager;
import com.shankephone.mi.common.vo.ResultVO;
import com.shankephone.mi.inventory.formbean.GetPagerDetailInfoVO;
import com.shankephone.mi.inventory.formbean.StockInfoFindEntity;
import com.shankephone.mi.inventory.vo.StockInfoVO;

import java.util.List;
import java.util.Map;

/**
 * 库存管理接口
 *
 * @author 司徒彬
 * @date 2018 /7/26 20:04
 */
public interface StockInfoService {
    /**
     * Gets pager info.
     *
     * @param findEntity the find entity
     * @return the pager info
     */
    Pager getPagerInfo(StockInfoFindEntity findEntity);

    /**
     * Gets pager detail info.
     *
     * @param findEntity the find entity
     * @return the pager detail info
     */
    Pager getPagerDetailInfo(StockInfoFindEntity findEntity);

    /**
     * Update stock info.
     *
     * @param stockInfoVO the stock info vo
     */
    void updateStockInfo(StockInfoVO stockInfoVO) throws Exception;

    GetPagerDetailInfoVO getPagermyPartDetailInfo(StockInfoFindEntity findEntity);

    GetPagerDetailInfoVO getPagerstockPartDetailInfo(StockInfoFindEntity findEntity);

    /**
     *导入库存信息列表
     *@author：郝伟州
     *@date：2018年9月7日19:21:36
     */
    ResultVO importSotckList(StockInfoFindEntity findEntity, List<String[]> list);


    List<Map<String, Object>> getAllSotckListMap(StockInfoFindEntity findEntity);
}
