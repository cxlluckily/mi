package com.shankephone.mi.stock.service;

import com.shankephone.mi.common.model.Pager;
import com.shankephone.mi.common.vo.ResultVO;
import com.shankephone.mi.stock.formbean.QRCodeFindEntity;
import com.shankephone.mi.stock.formbean.QRCodeFormBean;

import java.util.List;
import java.util.Map;

/**
 * @author 赵亮
 * @date 2018-07-24 10:09
 */
public interface QRCodeService
{
    /**
     *批量添加二维码
     *@author：赵亮
     *@date：2018-07-24 10:16
    */
    String batchInsert(QRCodeFormBean entity);

    /**
     *查询二维码
     *@author：赵亮
     *@date：2018-07-24 10:25
    */
    Pager<Map<String, Object>> getQRCodeList(QRCodeFindEntity findEntity);

    /**
     *批量删除
     *@author：赵亮
     *@date：2018-07-24 10:58
    */
    Integer batchDelete(QRCodeFindEntity findEntity);

    List<Map<String, Object>> getAllqrCodeListMap(QRCodeFindEntity findEntity);

    /**
     *查询 二维码是否有效
     *@author：赵亮
     *@date：2018-07-24 10:16
     */
     ResultVO getQrCodeByQrCode(String qrCode);

     ResultVO getQrCodeByQrCodeEntity(QRCodeFindEntity findEntity);
}
