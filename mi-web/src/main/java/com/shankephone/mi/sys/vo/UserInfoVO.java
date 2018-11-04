package com.shankephone.mi.sys.vo;

import com.shankephone.mi.model.SysUserEntity;
import lombok.Data;

/**
 * 用户业务实体
 *
 * @author 司徒彬
 * @date 2018/7/2 16:00
 */
@Data
public class UserInfoVO extends SysUserEntity {
    private String newPassword;
    private String oldPassword;
    private String fullPhotoUrl;
    private String phone;
    private  String code;
    private String orgName;

}
