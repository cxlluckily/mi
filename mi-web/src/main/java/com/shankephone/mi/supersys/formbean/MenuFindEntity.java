package com.shankephone.mi.supersys.formbean;

import com.shankephone.mi.common.model.BaseFindEntity;
import lombok.Data;

/**
 * @author 赵亮
 * @date 2018-07-24 9:38
 */
@Data
public class MenuFindEntity extends BaseFindEntity
{
    private String treeType;
    private String treeName;
    private String permissionCode;
    private String status;
}
