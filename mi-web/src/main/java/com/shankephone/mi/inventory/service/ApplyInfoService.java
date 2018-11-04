package com.shankephone.mi.inventory.service;

import com.shankephone.mi.common.enumeration.ApplyStatusEnum;
import com.shankephone.mi.common.model.Pager;
import com.shankephone.mi.common.vo.ResultVO;
import com.shankephone.mi.inventory.formbean.StockOperationFindEntity;
import com.shankephone.mi.inventory.vo.ApplyInfoVO;
import com.shankephone.mi.model.StockBusinessApplyEntity;

/**
 * 库存操作Service
 *
 * @author 司徒彬
 * @date 2018 /7/12 09:56
 */
public interface ApplyInfoService {
    /**
     * Gets list info.
     *
     * @param findEntity the find entity
     * @return the list info
     */
    Pager getPagerInfoByUser(StockOperationFindEntity findEntity);

    /**
     * Gets list info.
     *
     * @param findEntity the find entity
     * @return the list info
     */
    Pager getPagerInfoByWarehouse(StockOperationFindEntity findEntity);

    /**
     * Gets apply info by apply id.
     *
     * @param applyId the apply id
     * @return the apply info by apply id
     */
    ApplyInfoVO getApplyInfoByApplyId(Long applyId);


    /**
     * Gets pager info for audit.
     *
     * @param findEntity the find entity
     * @return the pager info for audit
     */
    Pager getPagerInfoForAudit(StockOperationFindEntity findEntity);

    /**
     * Audit apply info.
     *
     * @param applyEntity the applyEntity
     * @throws Exception the exception
     */
    ResultVO auditApplyInfo(ApplyInfoVO applyEntity) throws Exception;

    /**
     * Update apply status.
     *
     * @param applyId    the apply id
     * @param statusEnum the status enum
     */
    void updateApplyStatus(Long applyId, ApplyStatusEnum statusEnum);

    /**
     * Update apply status.
     *
     * @param applyEntity the apply entity
     */
    void updateApplyStatus(StockBusinessApplyEntity applyEntity);

    /**
     * Add apply info.
     *
     * @param applyInfoVO the apply info vo
     * @throws Exception the exception
     */
    void addApplyInfo(ApplyInfoVO applyInfoVO) throws Exception;

    /**
     * Update apply info.
     *
     * @param applyInfoVO the apply info vo
     */
    void updateApplyInfo(ApplyInfoVO applyInfoVO) throws Exception;

    /**
     * Delete apply info.
     *
     * @param applyId the apply id
     */
    void deleteApplyInfo(Long applyId);
}
