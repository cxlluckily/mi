package com.shankephone.mi.onlineequipment.service.impl;


import com.shankephone.mi.common.enumeration.StatusEnum;
import com.shankephone.mi.common.vo.ResultVO;
import com.shankephone.mi.model.OperationsEquipmentEntity;
import com.shankephone.mi.model.StockQrCodeEntity;
import com.shankephone.mi.onlineequipment.dao.OnlinequipmentDao;
import com.shankephone.mi.onlineequipment.service.OnlineequipmentService;
import com.shankephone.mi.stock.dao.OutStockApplyDao;
import com.shankephone.mi.stock.dao.QRCodeDao;
import com.shankephone.mi.stock.formbean.UpdateStockQrCodeFormBean;
import com.shankephone.mi.util.ResultVOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author 郝伟州
 * @date 2018/8/23 10:31
 */
@Service
public class OnlineequipmentServiceImpl implements OnlineequipmentService
{

    @Autowired
    private OnlinequipmentDao onlinequipmentDao;

    @Override
    public   List<Map<String, Object>> getOnlinequipmentList(OperationsEquipmentEntity entity)
    {
        try
        {
            return onlinequipmentDao.getOnlinequipmentList(entity);
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    @Autowired
    private QRCodeDao qrCodeDao;
    @Autowired
    private OutStockApplyDao outStockApplyDao;
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO updateOnlineequipment(OperationsEquipmentEntity entity)
    {
        try
        {
            //验证二维码是否存在
            List<StockQrCodeEntity> entities = qrCodeDao.getQrCodeByQrCode(entity.getQrCode());

            if (entities.size() > 0)
            {
                if (entities.get(0).getStockId() != null||StatusEnum.STOP_USING.getValue().equals(entities.get(0).getStatus()))
                {
                    return ResultVOUtil.error(201, " 二维码已被用，请更换！");
                }
            }
            else
            {
                return ResultVOUtil.error(201, "无效的二维码！");
            }

            //更新二维码状态为不可用
            OperationsEquipmentEntity entity1 = onlinequipmentDao.getOnlinequipmentDetail(entity);
            entity1.setQrCode(entity.getQrCode());
            UpdateStockQrCodeFormBean stockQrCodeFormBean = new UpdateStockQrCodeFormBean();
            stockQrCodeFormBean.setQrCode(entity1.getQrCode());
            stockQrCodeFormBean.setStockId(0l);
            stockQrCodeFormBean.setSparePartId(entity1.getSparePartId());
            outStockApplyDao.updateQrCode(stockQrCodeFormBean);
            //更新设备的二维码
            onlinequipmentDao.updateOnlineequipment(entity1);
            return ResultVOUtil.success();
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

}

