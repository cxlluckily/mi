package com.shankephone.mi.stock.service.impl;

import com.shankephone.mi.common.enumeration.StatusEnum;
import com.shankephone.mi.common.model.Pager;
import com.shankephone.mi.common.vo.ResultVO;
import com.shankephone.mi.model.StockQrCodeEntity;
import com.shankephone.mi.stock.dao.QRCodeDao;
import com.shankephone.mi.stock.formbean.QRCodeFindEntity;
import com.shankephone.mi.stock.formbean.QRCodeFormBean;
import com.shankephone.mi.stock.service.QRCodeService;
import com.shankephone.mi.util.GeneratorId;
import com.shankephone.mi.util.ResultVOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author 赵亮
 * @date 2018-07-24 10:09
 */
@Service
public class QRCodeServiceImpl implements QRCodeService
{
    @Autowired
    private QRCodeDao qrCodeDao;

    /**
     * 批量添加二维码
     *
     * @param entity
     * @author：赵亮
     * @date：2018-07-24 10:16
     */
    @Override
    public String batchInsert(QRCodeFormBean entity)
    {
        try
        {
            for (int i = 0; i < entity.getCount(); i++)
            {
                String code = GeneratorId.nextId() + "";
                StockQrCodeEntity stockQrCodeEntity = new StockQrCodeEntity();
                stockQrCodeEntity.setUserKey(entity.getUserKey());
                stockQrCodeEntity.setQrCode(code);
                stockQrCodeEntity.setStatus(StatusEnum.START_USING.getValue());
                stockQrCodeEntity.setOperationSubjectId(stockQrCodeEntity.getUserLoginInfo().getOperationSubjectId());
                qrCodeDao.inserOne(stockQrCodeEntity);
            }
            return "success";
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    /**
     * 查询二维码
     *
     * @param findEntity
     * @author：赵亮
     * @date：2018-07-24 10:25
     */
    @Override
    public Pager<Map<String, Object>> getQRCodeList(QRCodeFindEntity findEntity)
    {
        try
        {
            try
            {
                Integer total = qrCodeDao.getQRCodeListCount(findEntity);
                List<Map<String, Object>> sysUserEntities = qrCodeDao.getQRCodeList(findEntity);
                Pager pager = new Pager<>(total, sysUserEntities);
                return pager;
            }
            catch (Exception ex)
            {
                throw ex;
            }
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    /**
     * 批量删除
     *
     * @param findEntity
     * @author：赵亮
     * @date：2018-07-24 10:58
     */
    @Override
    public Integer batchDelete(QRCodeFindEntity findEntity)
    {
        try
        {
            return qrCodeDao.batchDelete(findEntity);
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    @Override
    public List<Map<String, Object>> getAllqrCodeListMap(QRCodeFindEntity findEntity)
    {
        //UserFindEntity userFindEntity=new UserFindEntity();
        // UserLoginInfo uli = SessionMap.getUserInfo(userKey);
        findEntity.setStart(0);
        findEntity.setLimit(Integer.MAX_VALUE);
        return qrCodeDao.getQRCodeList(findEntity);
    }

    @Override
    public ResultVO getQrCodeByQrCode(String qrCode)
    {
        try
        {
            List<StockQrCodeEntity> entities = qrCodeDao.getQrCodeByQrCode(qrCode);
            if (entities.size() > 0)
            {
                if (entities.get(0).getStockId() != null||StatusEnum.STOP_USING.getValue().equals(entities.get(0).getStatus()))
                {
                    return ResultVOUtil.error(201, " 二维码已经使用过！请您更换！");
                }
            }
            else
            {
                return ResultVOUtil.error(201, "二维码无效！");
            }
            return ResultVOUtil.success(qrCode);
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }


    /**
     * 判断二维码是否有效
     *
     * @param findEntity
     * @author：赵亮
     * @date：2018-07-24 10:58
     */
    @Override
    public ResultVO getQrCodeByQrCodeEntity(QRCodeFindEntity findEntity)
    {
        try
        {
            List<StockQrCodeEntity> entities = qrCodeDao.getQrCodeByQrCodeEntity(findEntity);
            if (entities.size() > 0)
            {
                if (entities.get(0).getStockId() != null||StatusEnum.STOP_USING.getValue().equals(entities.get(0).getStatus()))
                {
                    return ResultVOUtil.error(201, " 二维码已经使用过！请您更换！");
                }
            }
            else
            {
                return ResultVOUtil.error(201, "二维码无效！");
            }
            return ResultVOUtil.success(findEntity.getQrCode());
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }
}
