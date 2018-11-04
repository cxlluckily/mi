package com.shankephone.mi.org.vo;

import lombok.Data;

/**
 * @author 赵亮
 * @date 2018-06-28 18:24
 */
@Data
public class LineAndStationListVO
{
    private Long lineId;
    private String lineCode;
    private String lineName;
    private Long stationId;
    private String stationCode;
    private String stationName;
    private String isCheck;

}
