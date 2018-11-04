package com.shankephone.mi.model;

import com.shankephone.mi.common.model.BaseModel;
import lombok.Data;
import java.sql.Timestamp;


/**
 * org_organization  Bean
 *
 * @author 系统生成
 * @date 2018-06-20 19:05:23
 */
@Data
public class OrgOrganizationEntity extends BaseModel
{
	
    /**
     * 组织结构Id
    **/
    private Long orgId;
    /**
     * 运营主体ID
    **/
    //private Long operationSubjectId;
    /**
     * 父机构ID
    **/
    private Long parentOrgId;
    /**
     * 机构名称
    **/
    private String orgName;
    /**
     * 内部编号
    **/
    private String internalNumber;
    /**
     * 负责人
    **/
    private String headPerson;
    /**
     * 联系电话
    **/
    private String contactNumber;
    /**
     * 机构描述
    **/
    private String description;
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
     * 是否可以删除
     **/
    private Boolean wasCanDelete;
    /**
     * 状态
     **/
    private String status;
}

