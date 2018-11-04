package com.shankephone.mi.sys.formbean;

import com.shankephone.mi.common.model.BaseFindEntity;
import lombok.Data;

/**
 * 用户授权查询实体
 *
 * @author 司徒彬
 * @date 2018/6/27 10:42
 */
@Data
public class UserPermissionFindEntity extends BaseFindEntity {
    private Long userId;
    private String rangeType;
}
