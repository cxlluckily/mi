package com.shankephone.mi.org.vo;

import lombok.Data;

/**
 * @author 赵亮
 * @date 2018-06-28 18:17
 */
@Data
public class StationVO
{
    private Long stationId;
    private String stationName;
    private String isCheck;

    public StationVO(Long stationId, String stationName, String isCheck)
    {
        this.stationId = stationId;
        this.stationName = stationName;
        this.isCheck = isCheck;
    }
}
