package com.shankephone.mi.model;

import com.shankephone.mi.common.model.BaseModel;
import lombok.Data;

import java.sql.Timestamp;


/**
 * sys_operation_subject  Bean
 *
 * @author 系统生成
 * @date 2018-06-20 19:05:26
 */
@Data
public class SysOperationSubjectEntity extends BaseModel
{
	
    /**
     * 运营主体ID
    **/
    //private Long operationSubjectId;
    /**
     * 地区ID
    **/
    private Long regionId;
    /**
     * 编号
    **/
    private String subjectCode;
    /**
     * 登录名
     **/
    private String loginName;
    /**
     * 密码
     **/
    private String password;
    /**
     * 主体名称
    **/
    private String subjectName;
    /**
     * 联系人
    **/
    private String contacts;
    /**
     * 联系电话
    **/
    private String contactPhone;
    /**
     * 备注
    **/
    private String remark;
    /**
     * 状态
    **/
    private String status;
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

   
}

