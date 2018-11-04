package com.shankephone.mi.model;

import com.shankephone.mi.common.model.BaseModel;
import lombok.Data;

import java.sql.Timestamp;


/**
 * sys_data_dictionary  Bean
 *
 * @author 系统生成
 * @date 2018-06-26 09:25:25
 */
@Data
public class SysDataDictionaryEntity extends BaseModel
{
	
    /**
     * 数据字典ID
    **/
    private Long dataDictionaryId;

    /**
     * 运营主体ID
     **/
    private Long operationSubjectId;
    /**
     * 标签
    **/
    private String dataLabel;
    /**
     * 类型编码
    **/
    private String code;
    /**
     * 类型名称
    **/
    private String name;
    /**
     * 排序
    **/
    private Integer sort;
    /**
     * 描述
    **/
    private String remark;
    /**
     * 状态
     */
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

