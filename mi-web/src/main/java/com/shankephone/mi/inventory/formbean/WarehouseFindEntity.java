package com.shankephone.mi.inventory.formbean;

import com.shankephone.mi.common.model.BaseFindEntity;
import com.shankephone.mi.util.StringUtils;
import lombok.Data;

import java.util.List;

/**
 * 仓库查询实体
 *
 * @author 司徒彬
 * @date 2018 /7/2 17:13
 */
@Data
public class WarehouseFindEntity extends BaseFindEntity {
    private Long workSectionId;
    private List<Long> workSectionIds;
    private String warehouseName;
    private String headPerson;
    private String contactNumber;
    private String status;
    private Long warehouseId;
    private Long parentWarehouseId;

    public String getWorkSectionIds()
    {
        return StringUtils.listToString(this.workSectionIds);
    }
}
