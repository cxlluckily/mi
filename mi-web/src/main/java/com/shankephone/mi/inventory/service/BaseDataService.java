package com.shankephone.mi.inventory.service;

import com.shankephone.mi.common.model.BaseFindEntity;
import com.shankephone.mi.inventory.formbean.BaseDataFindEntity;

import java.util.List;
import java.util.Map;

/**
 * ${description}
 *
 * @author 司徒彬
 * @date 2018 /7/11 14:57
 */
public interface BaseDataService {
    /**
     * Gets spare part list.
     *
     * @param searchContent the search content
     * @return the spare part list
     */
    List<Map<String,Object>> getSparePartList(BaseFindEntity searchContent);

    /**
     * Gets spare part in warehouse.
     *
     * @param findEntity the find entity
     * @return the spare part in warehouse
     */
    List<Map<String,Object>> getSparePartInWarehouse(BaseDataFindEntity findEntity);
}
