package com.shankephone.mi.security.vo;

import com.shankephone.mi.model.SysUserEntity;
import lombok.Data;

/**
 * 用户业务实体
 *
 * @author 司徒彬
 * @date 2018/7/2 16:00
 */
@Data
public class UserLoginInfoVO extends SysUserEntity {
    private String newPassword;
    private String oldPassword;
    private String phone;
    private String code;

}
