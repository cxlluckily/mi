package com.shankephone.mi.model;

import com.shankephone.mi.common.model.BaseModel;
import lombok.Data;


/**
 * org_work_section_station  Bean
 *
 * @author 系统生成
 * @date 2018-06-20 19:05:23
 */
@Data
public class OrgWorkSectionStationEntity  extends BaseModel
{
	
    /**
     * 工区站点ID
    **/
    private Long workSectionStationId;
    /**
     * 车站Id
    **/
    private Long stationId;
    /**
     * 工区ID
    **/
    private Long workSectionId;

   
}

