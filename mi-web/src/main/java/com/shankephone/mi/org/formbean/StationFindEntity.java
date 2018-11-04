package com.shankephone.mi.org.formbean;

import com.shankephone.mi.common.model.BaseFindEntity;
import lombok.Data;

/**
 * 车站
 * @author 赵亮
 * @date 2018-06-25 17:21
 */
@Data
public class StationFindEntity extends BaseFindEntity
{
    private String stationCode;
    private String stationName;
    private String headPerson;
    private String status;
    private Long lineId;
    //isExprot=1 是导出
    private Long isExprot;

    /**
     *工区ID，获取线路和车站的信息
     *@author：赵亮
     *@date：2018-06-28 18:40
    */
    private Long workSectionId;
}
