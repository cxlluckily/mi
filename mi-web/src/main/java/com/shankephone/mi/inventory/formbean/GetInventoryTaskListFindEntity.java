package com.shankephone.mi.inventory.formbean;

import com.shankephone.mi.common.model.BaseFindEntity;
import lombok.Data;

/**
 * @author 赵亮
 * @date 2018-08-16 15:15
 */
@Data
public class GetInventoryTaskListFindEntity extends BaseFindEntity
{
    private String headPerson;
    private String warehouseName;
    private String status;
    private String searchType="";
}
