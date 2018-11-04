package com.shankephone.mi.inventory.service.impl;

import com.shankephone.fdfs.FdfsClient;
import com.shankephone.mi.common.model.BaseFindEntity;
import com.shankephone.mi.inventory.dao.BaseDataDao;
import com.shankephone.mi.inventory.formbean.BaseDataFindEntity;
import com.shankephone.mi.inventory.service.BaseDataService;
import com.shankephone.mi.util.DataSwitch;
import com.shankephone.mi.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * ${description}
 *
 * @author 司徒彬
 * @date 2018 /7/11 14:59
 */
@Service
public class BaseDataServiceImpl implements BaseDataService
{

    @Autowired
    private BaseDataDao baseDataDao;

    @Override
    public List<Map<String, Object>> getSparePartList(BaseFindEntity findEntity)
    {
        try
        {
            List<Map<String, Object>> data = baseDataDao.getSparePartList(findEntity);
            data.stream().forEach(oneData -> {
                        if (!oneData.get("imageUrl").toString().equals("noImage"))
                        {
                            oneData.put("imageUrl", FdfsClient.getDownloadServer() + oneData.get("imageUrl").toString());
                        }
                    }
            );
            return data;
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    @Override
    public List<Map<String, Object>> getSparePartInWarehouse(BaseDataFindEntity findEntity)
    {
        try
        {
            List<Map<String, Object>> data = baseDataDao.getSparePartInWarehouse(findEntity);

            data.stream().forEach(map -> {
                String imageUrl = DataSwitch.convertObjectToString(map.get("imageUrl"));
                String sparePartPicUrl = FdfsClient.getDownloadServer() + imageUrl;
                if(StringUtils.isEmpty(imageUrl))
                {
                    sparePartPicUrl="noImage";
                }
                map.put("imageUrl", sparePartPicUrl);
            });
            return data;
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }
}
