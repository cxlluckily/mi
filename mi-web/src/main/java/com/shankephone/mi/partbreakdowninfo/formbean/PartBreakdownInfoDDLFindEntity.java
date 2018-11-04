package com.shankephone.mi.partbreakdowninfo.formbean;

import com.shankephone.mi.common.model.BaseFindEntity;
import lombok.Data;

/**
 * @author 赵亮
 * @date 2018-08-02 14:44
 */
@Data
public class PartBreakdownInfoDDLFindEntity extends BaseFindEntity
{
    private Long sparePartId;
    private String breakdownKey;
    private String breakAbbreviated;
    private String status;
}
