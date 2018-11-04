package com.shankephone.mi.stock.dao;

import com.shankephone.mi.model.StockOutStockApplyDetailEntity;
import com.shankephone.mi.model.StockOutStockApplyEntity;
import com.shankephone.mi.model.StockOutStockDetailEntity;
import com.shankephone.mi.stock.dao.provider.OutStockApplyProvider;
import com.shankephone.mi.stock.formbean.BindQrCodeFindEntity;
import com.shankephone.mi.stock.formbean.OutStockApplyFindEntity;
import com.shankephone.mi.stock.formbean.StockOutStockApplyBusiEntity;
import com.shankephone.mi.stock.formbean.UpdateStockQrCodeFormBean;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author 赵亮
 * @date 2018-07-11 10:06
 */
@Repository
public interface OutStockApplyDao
{
    @SelectProvider(type = OutStockApplyProvider.class, method = "getOutStockApplyInfo")
    List<Map<String, Object>> getOutStockApplyInfo(OutStockApplyFindEntity findEntity);

    @SelectProvider(type = OutStockApplyProvider.class, method = "getOutStockApplyInfoCount")
    Integer getOutStockApplyInfoCount(OutStockApplyFindEntity findEntity);

    @SelectProvider(type = OutStockApplyProvider.class, method = "getApplyDetailInfoByoutStockApplyId")
    List<Map<String, Object>> getApplyDetailInfoByoutStockApplyId(Long outStockApplyId);

    @SelectProvider(type = OutStockApplyProvider.class, method = "getOutStockDetailByoutStockApplyId")
    List<Map<String, Object>> getOutStockDetailByoutStockApplyId(Long outStockApplyId);

    @SelectProvider(type = OutStockApplyProvider.class, method = "getCanSendGoodsInfo")
    List<Map<String, Object>> getCanSendGoodsInfo(OutStockApplyFindEntity findEntity);

    @UpdateProvider(type = OutStockApplyProvider.class, method = "outStockUpdateOutApply")
    Integer outStockUpdateOutApply(StockOutStockApplyEntity applyEntity);

    @UpdateProvider(type = OutStockApplyProvider.class, method = "outStockUpdateApplyDetail")
    Integer outStockUpdateApplyDetail(StockOutStockApplyDetailEntity applyDetailEntity);

    @SelectProvider(type = OutStockApplyProvider.class, method = "getCanOutCountBystockId")
    Integer getCanOutCountBystockId(Long stockId);

    @UpdateProvider(type = OutStockApplyProvider.class, method = "outStockUpdateStock")
    Integer outStockUpdateStock(StockOutStockDetailEntity entity);

    @InsertProvider(type = OutStockApplyProvider.class, method = "insertStockOutStockDetail")
    @Options(useGeneratedKeys = true, keyProperty = "outStockDetailId")
    Integer insertStockOutStockDetail(StockOutStockDetailEntity entity);

    @InsertProvider(type = OutStockApplyProvider.class, method = "insertOutStockApply")
    @Options(useGeneratedKeys = true, keyProperty = "outStockApplyId")
    Integer insertOutStockApply(StockOutStockApplyEntity entity);

    @InsertProvider(type = OutStockApplyProvider.class, method = "insertOutStockApplyDetail")
    @Options(useGeneratedKeys = true, keyProperty = "outStockApplyDetailId")
    Integer insertOutStockApplyDetail(StockOutStockApplyDetailEntity entity);

    @SelectProvider(type = OutStockApplyProvider.class, method = "getBindqrCodeStockInfo")
    List<Map<String,Object>> getBindqrCodeStockInfo(BindQrCodeFindEntity findEntity);

    @UpdateProvider(type = OutStockApplyProvider.class, method = "updateStockQrCode")
    Integer updateStockQrCode(UpdateStockQrCodeFormBean formBean);

    @SelectProvider(type = OutStockApplyProvider.class, method = "getStockOutStockApplyEntity")
    StockOutStockApplyBusiEntity getStockOutStockApplyEntity(Long outStockApplyId);

    @UpdateProvider(type = OutStockApplyProvider.class, method = "updateQrCode")
    Integer updateQrCode(UpdateStockQrCodeFormBean entity);

    /**
     * 根据qrcode获取库存备件信息
     * @param formBean
     * @return the spare parts in bag for repaired
     */
    @SelectProvider(type = OutStockApplyProvider.class, method = "getStockSparePartByqrCode")
    Map<String, Object> getStockSparePartByqrCode(UpdateStockQrCodeFormBean formBean);
}
