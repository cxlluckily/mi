package com.shankephone.mi.model;

import com.shankephone.mi.common.model.BaseModel;
import lombok.Data;

import java.sql.Timestamp;


/**
 * org_line  Bean
 *
 * @author 系统生成
 * @date 2018-06-20 19:05:23
 */
@Data
public class OrgLineEntity  extends BaseModel
{
	
    /**
     * 线路ID
    **/
    private Long lineId;
    /**
     * 运营主体ID
    **/
    //private Long operationSubjectId;
    /**
     * 线路名称
    **/
    private String lineName;
    /**
     * 线路编码
    **/
    private String lineCode;
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

