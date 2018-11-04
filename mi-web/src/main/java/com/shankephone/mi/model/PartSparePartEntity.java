package com.shankephone.mi.model;

import com.shankephone.mi.common.model.BaseModel;
import lombok.Data;
import java.sql.Timestamp;


/**
 * part_spare_part  Bean
 *
 * @author 系统生成
 * @date 2018-06-29 13:46:50
 */
@Data
public class PartSparePartEntity extends BaseModel
{
	
    /**
     * 备件ID
    **/
    private Long sparePartId;
    /**
     * 备件类型ID
    **/
    private Long sparePartTypeId;
       /**
     * 备件名称
    **/
    private String partName;
    /**
     * 品牌
    **/
    private String brand;
    /**
     * 物资编码
    **/
    private String materiaCoding;
    /**
     * 规格型号
    **/
    private String specificationModel;
    /**
     * 尺寸
    **/
    private String size;
    /**
     * 材质
    **/
    private String material;
    /**
     * 单位
    **/
    private String units;
    /**
     * 库存上限
    **/
    private Integer upperLimit;
    /**
     * 库存下限
    **/
    private Integer lowerLimit;
    /**
     * 备       注
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
    /**
     * 拼音缩写
     **/
    private String partPinYin;

   
}

