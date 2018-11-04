package com.shankephone.mi.spacepart.formbean;

import com.shankephone.mi.common.model.BaseFindEntity;
import lombok.Data;

/**
 * @author 赵亮
 * @date 2018-06-29 13:41
 */
@Data
public class SparePartFindEntity extends BaseFindEntity
{
    private Long sparePartTypeId;
    private String status;
    private String partName;
    private String brand;
    private String materiaCoding;
    private String specificationModel;

    private Long sparePartId;
}
