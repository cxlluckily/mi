package com.shankephone.mi.inventory.dao;

import com.shankephone.mi.common.model.BaseFindEntity;
import com.shankephone.mi.inventory.dao.provider.BaseDataProvider;
import com.shankephone.mi.inventory.formbean.BaseDataFindEntity;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * ${description}
 *
 * @author 司徒彬
 * @date 2018 /7/11 15:00
 */
@Repository
public interface BaseDataDao {
    /**
     * Gets spare part list.
     *
     * @param findEntity the find entity
     * @return the spare part list
     */
    @SelectProvider(type = BaseDataProvider.class, method = "getSparePartList")
    List<Map<String,Object>> getSparePartList(BaseFindEntity findEntity);

    /**
     * Gets spare part in warehouse.
     *
     * @param findEntity the find entity
     * @return the spare part in warehouse
     */
    @SelectProvider(type = BaseDataProvider.class, method = "getSparePartInWarehouse")
    List<Map<String,Object>> getSparePartInWarehouse(BaseDataFindEntity findEntity);
}
