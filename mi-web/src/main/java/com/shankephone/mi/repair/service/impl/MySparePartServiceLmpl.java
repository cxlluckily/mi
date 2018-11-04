package com.shankephone.mi.repair.service.impl;

import com.shankephone.fdfs.FdfsClient;
import com.shankephone.mi.common.model.Pager;
import com.shankephone.mi.inventory.service.ApplyInfoService;
import com.shankephone.mi.model.StockBusinessApplyEntity;
import com.shankephone.mi.repair.dao.MySparePartDao;
import com.shankephone.mi.repair.formbean.UpdateMySqparePartCountFormBean;
import com.shankephone.mi.repair.service.MySparePartService;
import com.shankephone.mi.repair.vo.MySparePartFindEntity;
import com.shankephone.mi.util.DataSwitch;
import com.shankephone.mi.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author 赵亮
 * @date 2018-08-06 9:34
 */
@Service
public class MySparePartServiceLmpl implements MySparePartService
{
    @Autowired
    private MySparePartDao mySparePartDao;

    /**
     * 返回我的备件列表数据
     *
     * @param findEntity
     * @author：赵亮
     * @date：2018-08-06 9:37
     */
    @Override
    public Pager<Map<String, Object>> getMySparePartList(MySparePartFindEntity findEntity)
    {
        try
        {
            Integer total = mySparePartDao.getMySparePartListTotal(findEntity);
            List<Map<String, Object>> entitys = mySparePartDao.getMySparePartList(findEntity);

            entitys.stream().forEach(map -> {
                String imageUrl = DataSwitch.convertObjectToString(map.get("imageUrl"));
                String sparePartPicUrl = FdfsClient.getDownloadServer() + imageUrl;
                if(StringUtils.isEmpty(imageUrl))
                {
                    sparePartPicUrl="";
                }
                map.put("imageUrl", sparePartPicUrl);
            });


            return new Pager<>(total, entitys);
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    @Autowired
    private ApplyInfoService applyInfoService;

    /**
     * 返还入库时候更新我的设备表里的数据为零
     *
     * @param applyId
     * @author：赵亮
     * @date：2018-08-15 19:40
     */
    @Override
    public void updateMySqparePartCount(Long applyId)
    {
        StockBusinessApplyEntity stockBusinessApplyEntity = applyInfoService.getApplyInfoByApplyId(applyId);
        UpdateMySqparePartCountFormBean entity = new UpdateMySqparePartCountFormBean();
        entity.setApplyId(applyId);
        entity.setApplyUserId(stockBusinessApplyEntity.getApplyUserId());
        mySparePartDao.updateMySqparePartCount(entity);
    }
}
