package com.shankephone.mi.spacepart.formbean;

import com.shankephone.mi.common.model.BaseFindEntity;
import lombok.Data;

/**
 * @author 赵亮
 * @date 2018-08-02 13:59
 */
@Data
public class SparePartListForBreakdownFindEntity extends BaseFindEntity
{
    private String partName;
    private Long sparePartTypeId;
    private String status;
}
