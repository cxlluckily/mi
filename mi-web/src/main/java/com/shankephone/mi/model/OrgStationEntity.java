package com.shankephone.mi.model;

import com.shankephone.mi.common.model.BaseModel;
import lombok.Data;
import java.sql.Timestamp;


/**
 * org_station  Bean
 *
 * @author 系统生成
 * @date 2018-06-20 19:05:23
 */
@Data
public class OrgStationEntity extends BaseModel
{
	
    /**
     * 车站Id
    **/
    private Long stationId;
    /**
     * 线路ID
    **/
    private Long lineId;
    /**
     * 站点编码
    **/
    private String stationCode;
    /**
     * 站点名称
    **/
    private String stationName;
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

    /**
     * 站点拼音缩写
     **/
    private String pinyin;
    /**
     * 是否起始站
     **/
    private Boolean wasBegin;
    /**
     * 是否终点站
     **/
    private Boolean wasEnd;
}

