package com.shankephone.mi.model;

import com.shankephone.mi.common.model.BaseModel;
import lombok.Data;

import java.sql.Timestamp;


/**
 * sys_user  Bean
 *
 * @author 系统生成
 * @date 2018-06-20 19:05:27
 */
@Data
public class SysUserEntity extends BaseModel
{
	
    /**
     * 用户Id
    **/
    private Long userId;
    /**
     * 组织结构Id
    **/
    private Long orgId;
    /**
     * 运营主体ID
    **/
    //private Long operationSubjectId;
    /**
     * 用户名
    **/
    private String userName;
    /**
     * 密码
    **/
    private String password;
    /**
     * 用户头像Url
    **/
    private String photoUrl;
    /**
     * 邮箱
    **/
    private String email;
    /**
     * 真实姓名
    **/
    private String realName;
    /**
     * 手机号
    **/
    private String phone;
    /**
     * 用户状态
    **/
    private String status;
    /**
     * 加密盐值
    **/
    private String salt;
    /**
     * 职位
    **/
    private String position;
    /**
     * 工号
    **/
    private String workNumber;
    /**
     * 最后登录时间
    **/
    private Timestamp lastLoginTime;
    /**
     * 最后登录IP地址
    **/
    private String lastLoginIp;

    /**
     * 当前登录时间
     **/
    private Timestamp currentLoginTime;
    /**
     * 当前登录IP地址
     **/
    private String currentLoginIp;

    /**
     * 备注
    **/
    private String remark;
    /**
     * 添加人
    **/
    private String createUser;
    /**
     * 创建时间
    **/
    private Timestamp createTime;
    /**
     * 修改人
    **/
    private String modifyUser;
    /**
     * 修改时间
    **/
    private Timestamp modifyTime;

    /**
     * 性别
     **/
    private String sex;

    private String realNamePinYin;
    private String realNameAllPinYin;
    private String wasDefaultManager;

    private boolean isHavePic;

    private String openId;
}

