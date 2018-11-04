package com.shankephone.mi.sys.vo;

import com.shankephone.mi.common.model.BaseModel;
import lombok.Data;

import java.util.List;

/**
 * 用户授权
 *
 * @author 司徒彬
 * @date 2018/6/27 15:55
 */
@Data
public class UserPermissionVo extends BaseModel {
    private Long userId;
    private List<Long>  userIds;
    private List<Long> warehouseIds;
    private List<Long> roleIds;
    private List<Long> workSectionIds;
    private List<String> permissionCodes;
    private String warehouseId;
}
