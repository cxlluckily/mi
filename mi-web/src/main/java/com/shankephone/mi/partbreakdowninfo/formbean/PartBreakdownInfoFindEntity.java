package com.shankephone.mi.partbreakdowninfo.formbean;

import com.shankephone.mi.common.model.BaseFindEntity;
import lombok.Data;

/**
 * @author 赵亮
 * @date 2018-08-02 10:08
 */
@Data
public class PartBreakdownInfoFindEntity extends BaseFindEntity
{
    private String categoryName;
    private String partName;
    private String breakAbbreviated;
    private String breakdownKey;
    private String status;

}
