package com.shankephone.mi.spacepart.formbean;

import com.shankephone.mi.common.model.BaseFindEntity;
import lombok.Data;

/**
 * @author 赵亮
 * @date 2018-08-13 11:09
 */
@Data
public class PartDeviceComposeFindEntity extends BaseFindEntity
{
    private Long parentPartId;
    private String status;
}
