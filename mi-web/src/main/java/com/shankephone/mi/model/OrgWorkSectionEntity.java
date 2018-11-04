package com.shankephone.mi.model;

import com.shankephone.mi.common.model.BaseModel;
import lombok.Data;

import java.sql.Timestamp;


/**
 * org_work_section  Bean
 *
 * @author 系统生成
 * @date 2018-06-20 19:05:23
 */
@Data
public class OrgWorkSectionEntity  extends BaseModel
{
	
    /**
     * 工区ID
    **/
    private Long workSectionId;
    /**
     * 运营主体ID
    **/
    //private Long operationSubjectId;
    /**
     * 工区编码
    **/
    private String sectionCode;
    /**
     * 工区名称
    **/
    private String sectionName;
    /**
     * 负责人
    **/
    private String headPerson;
    /**
     * 联系电话
    **/
    private String phone;
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

