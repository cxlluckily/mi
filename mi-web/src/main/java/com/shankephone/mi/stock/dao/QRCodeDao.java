package com.shankephone.mi.stock.dao;

import com.shankephone.mi.model.StockQrCodeEntity;
import com.shankephone.mi.stock.dao.provider.QRCodeProvider;
import com.shankephone.mi.stock.formbean.QRCodeFindEntity;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author 赵亮
 * @date 2018-07-24 10:10
 */
@Repository
public interface QRCodeDao
{
    @InsertProvider(type = QRCodeProvider.class, method = "inserOne")
    @Options(useGeneratedKeys = true, keyProperty = "qrCodeId")
    Integer inserOne(StockQrCodeEntity entity);

    @SelectProvider(type = QRCodeProvider.class, method = "getQRCodeList")
    List<Map<String, Object>> getQRCodeList(QRCodeFindEntity findEntity);

    @SelectProvider(type = QRCodeProvider.class, method = "getQRCodeListCount")
    Integer getQRCodeListCount(QRCodeFindEntity findEntity);

    @DeleteProvider(type = QRCodeProvider.class, method = "batchDelete")
    Integer batchDelete(QRCodeFindEntity findEntity);

    @SelectProvider(type = QRCodeProvider.class, method = "getQrCodeByQrCode")
    List<StockQrCodeEntity> getQrCodeByQrCode(String qrCode);

    @SelectProvider(type = QRCodeProvider.class, method = "getQrCodeByQrCodeEntity")
    List<StockQrCodeEntity> getQrCodeByQrCodeEntity(QRCodeFindEntity findEntity);

    @UpdateProvider(type = QRCodeProvider.class, method = "updateByQrCode")
    void updateByQrCode(StockQrCodeEntity entity);
}
