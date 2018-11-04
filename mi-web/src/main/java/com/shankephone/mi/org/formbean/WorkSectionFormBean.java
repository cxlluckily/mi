package com.shankephone.mi.org.formbean;

import com.shankephone.mi.common.model.BaseModel;
import com.shankephone.mi.model.OrgWorkSectionEntity;
import com.shankephone.mi.model.OrgWorkSectionStationEntity;
import lombok.Data;

import java.util.List;

/**
 * @author 赵亮
 * @date 2018-06-28 10:56
 */
@Data
public class WorkSectionFormBean extends BaseModel
{
    OrgWorkSectionEntity orgWorkSectionEntity;
    List<OrgWorkSectionStationEntity> stationEntityList;
}
