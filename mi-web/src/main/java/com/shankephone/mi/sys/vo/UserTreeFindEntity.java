package com.shankephone.mi.sys.vo;

import com.shankephone.mi.common.model.BaseFindEntity;
import lombok.Data;

/**
 * @author 赵亮
 * @date 2018-08-01 9:56
 */
@Data
public class UserTreeFindEntity extends BaseFindEntity
{
    private Long userId;
    private String treeType;
}
