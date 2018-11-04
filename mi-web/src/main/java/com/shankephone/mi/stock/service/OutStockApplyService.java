package com.shankephone.mi.stock.service;

import com.shankephone.mi.common.model.Pager;
import com.shankephone.mi.common.vo.ResultVO;
import com.shankephone.mi.model.StockOutStockApplyEntity;
import com.shankephone.mi.stock.formbean.*;
import com.shankephone.mi.stock.vo.ApplyDetailVO;

import java.util.List;
import java.util.Map;

/**
 * @author 赵亮
 * @date 2018-07-11 10:39
 */
public interface OutStockApplyService
{
    Pager<Map<String, Object>> getOutStockApplyInfo(OutStockApplyFindEntity userFindEntity);

    ApplyDetailVO getApplyDetailInfoByoutStockApplyId(Long outStockApplyId);

    ResultVO useAndTransfeOutStockAndReturn(UseAndTransfeOutStockBusiEntity busiEntity) throws Exception;

    List<Map<String,Object>> getCanSendGoodsInfo(OutStockApplyFindEntity findEntity);

    ResultVO insertBorrowOutStock(BorrowOutStockBusiEntity busiEntity) throws Exception;

    ResultVO returnOutStock(StockOutStockApplyEntity entity);

    List<Map<String,Object>> getBindqrCodeStockInfo(BindQrCodeFindEntity entity);

    ResultVO updateStockQrCode(List<UpdateStockQrCodeFormBean> formBeans);

    ResultVO updateStockOutQrCode(UpdateStockQrCodeFormBean formBean);

    /**
     * 根据qrcode获取库存备件信息
     * @param formBean the UpdateStockQrCodeBusiEntity
     * @return the ResultVO
     * @author：郝伟州
     * @date：2018年9月26日14:38:39
     */
    ResultVO getStockSparePartByqrCode(UpdateStockQrCodeFormBean formBean);
}
