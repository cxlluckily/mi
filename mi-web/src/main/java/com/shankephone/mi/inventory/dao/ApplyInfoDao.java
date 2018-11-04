package com.shankephone.mi.inventory.dao;

import com.shankephone.mi.inventory.dao.provider.ApplyInfoProvider;
import com.shankephone.mi.inventory.formbean.StockOperationFindEntity;
import com.shankephone.mi.inventory.vo.ApplyInfoVO;
import com.shankephone.mi.model.StockBusinessApplyDetailEntity;
import com.shankephone.mi.model.StockBusinessApplyEntity;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 库存操作
 *
 * @author 司徒彬
 * @date 2018 /7/12 09:58
 */
@Repository
public interface ApplyInfoDao
{
    /**
     * Gets list info.
     *
     * @param findEntity the find entity
     * @return the list info
     */
    @SelectProvider(type = ApplyInfoProvider.class, method = "getPagerInfoByUser")
    List<Map<String, Object>> getPagerInfoByUser(StockOperationFindEntity findEntity);

    /**
     * Gets list info by user total.
     *
     * @param findEntity the find entity
     * @return the list info by user total
     */
    @SelectProvider(type = ApplyInfoProvider.class, method = "getPagerInfoByUserTotal")
    int getPagerInfoByUserTotal(StockOperationFindEntity findEntity);

    /**
     * Gets list info.
     *
     * @param findEntity the find entity
     * @return the list info
     */
    @SelectProvider(type = ApplyInfoProvider.class, method = "getPagerInfoByWarehouse")
    List<Map<String, Object>> getPagerInfoByWarehouse(StockOperationFindEntity findEntity);

    /**
     * Gets list info by user total.
     *
     * @param findEntity the find entity
     * @return the list info by user total
     */
    @SelectProvider(type = ApplyInfoProvider.class, method = "getPagerInfoByWarehouseTotal")
    int getPagerInfoByWarehouseTotal(StockOperationFindEntity findEntity);

    /**
     * Gets pager info for audit.
     *
     * @param findEntity the find entity
     * @return the pager info for audit
     */
    @SelectProvider(type = ApplyInfoProvider.class, method = "getPagerInfoForAudit")
    List<Map<String, Object>> getPagerInfoForAudit(StockOperationFindEntity findEntity);

    /**
     * Gets pager info for audit total.
     *
     * @param findEntity the find entity
     * @return the pager info for audit total
     */
    @SelectProvider(type = ApplyInfoProvider.class, method = "getPagerInfoForAuditTotal")
    int getPagerInfoForAuditTotal(StockOperationFindEntity findEntity);

    @SelectProvider(type = ApplyInfoProvider.class, method = "getApplyInfoByApplyId")
    ApplyInfoVO getApplyInfoByApplyId(Long applyId);

    @SelectProvider(type = ApplyInfoProvider.class, method = "getApplyDetailInfoByApplyId")
    List<Map<String, Object>> getApplyDetailInfoByApplyId(Long applyId);

    @SelectProvider(type = ApplyInfoProvider.class, method = "getApplySendOutDetail")
    List<Map<String, Object>> getApplySendOutDetail(Long applyId);

    @UpdateProvider(type = ApplyInfoProvider.class, method = "updateApplyStatus")
    void updateApplyStatus(StockBusinessApplyEntity applyEntity);

    @InsertProvider(type = ApplyInfoProvider.class, method = "insertApplyInfo")
    @Options(useGeneratedKeys = true, keyProperty = "applyId")
    void insertApplyInfo(ApplyInfoVO applyInfoVO);

    @InsertProvider(type = ApplyInfoProvider.class, method = "insertApplyDetail")
    @Options(useGeneratedKeys = true, keyProperty = "applyDetailId")
    void insertApplyDetail(StockBusinessApplyDetailEntity detailEntity);

    @UpdateProvider(type = ApplyInfoProvider.class, method = "updateApplyInfo")
    void updateApplyInfo(ApplyInfoVO applyInfoVO);

    @DeleteProvider(type = ApplyInfoProvider.class, method = "deleteApplyDetail")
    void deleteApplyDetail(Long applyId);

    @DeleteProvider(type = ApplyInfoProvider.class, method = "deleteApplyInfo")
    void deleteApplyInfo(Long applyId);

    @SelectProvider(type = ApplyInfoProvider.class, method = "getAllInfoList")
    List<Map<String, Object>> getAllInfoList(Long applyId);
}
