package com.shankephone.mi.partbreakdowninfo.formbean;

import com.shankephone.mi.common.model.BaseFindEntity;
import lombok.Data;

/**
 * @author 赵亮
 * @date 2018-08-02 13:44
 */
@Data
public class PartBreakdownRepairInfoFindEntity extends BaseFindEntity
{
    private String categoryName;
    private String partName;
    private String breakAbbreviated;
    private String breakdownKey;
    private String cueCode;
    private String reason;
    private String status;
}
