package com.shankephone.mi.model;

import com.shankephone.mi.common.model.BaseModel;
import lombok.Data;

import java.sql.Timestamp;
import java.util.Date;


/**
 * sys_admin  Bean
 *
 * @author 系统生成
 * @date 2018-06-20 19:16:43
 */
@Data
public class SysAdminEntity  extends BaseModel
{
	
    /**
     * 超级管理员ID
    **/
    private Long adminId;
    /**
     * 用户名
    **/
    private String userName;
    /**
     * 密码
    **/
    private String password;
    /**
     * 姓名
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
    private Date modifyTime;

   
}

