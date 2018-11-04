package com.shankephone.mi.sys.formbean;

import com.shankephone.mi.common.model.BaseFindEntity;
import lombok.Data;

/**
 * ${description}
 *
 * @author 司徒彬
 * @date 2018-06-21 17:41
 */
@Data
public class RoleInfoFindEntity extends BaseFindEntity {

    private Long roleId;
    private String roleName = "";
    private String status;
    
}
