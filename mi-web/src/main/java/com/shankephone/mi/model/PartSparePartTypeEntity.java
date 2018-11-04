package com.shankephone.mi.model;

import com.shankephone.mi.common.model.BaseModel;
import lombok.Data;

import java.sql.Date;


/**
 * part_spare_part_type  Bean
 *
 * @author 系统生成
 * @date 2018-06-20 19:05:24
 */
@Data
public class PartSparePartTypeEntity  extends BaseModel
{
	
    /**
     * 备件类型ID
    **/
    private Long sparePartTypeId;
    /**
     * 备件类型IDs
     **/
    private String sparePartTypeIds;
//    /**
//     * 运营主体ID
//    **/
//    private Long operationSubjectId;
    /**
     * 分类名称
    **/
    private String categoryName;
    /**
     * 父级ID
    **/
    private Long parentPartId;
    /**
     * 排序位置
    **/
    private Integer sort;
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
    private Date createTime;
    /**
     * 修改人
    **/
    private String modifyUser;
    /**
     * 修改时间
    **/
    private Date modifyTime;
    /**
     * 拼音缩写
     **/
    private String partTypePinYin;

   
}

