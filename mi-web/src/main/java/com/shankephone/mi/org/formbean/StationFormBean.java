package com.shankephone.mi.org.formbean;

import com.shankephone.mi.common.model.BaseModel;
import com.shankephone.mi.model.OrgStationEntity;
import lombok.Data;

import java.util.List;

/**
 * @author 赵亮
 * @date 2018-06-28 10:22
 */
@Data
public class StationFormBean extends BaseModel
{
    List<OrgStationEntity> stationList;
}
