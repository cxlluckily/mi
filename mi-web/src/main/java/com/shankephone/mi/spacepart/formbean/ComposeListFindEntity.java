package com.shankephone.mi.spacepart.formbean;

import com.shankephone.mi.common.model.BaseFindEntity;
import lombok.Data;

/**
 * @author 赵亮
 * @date 2018-08-13 17:02
 */
@Data
public class ComposeListFindEntity extends BaseFindEntity
{
    private Long operationSubjectId;
    private String partName;
    private String categoryName;
    private String name;
    private String status;

}
