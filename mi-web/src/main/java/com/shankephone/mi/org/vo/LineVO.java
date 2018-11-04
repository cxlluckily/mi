package com.shankephone.mi.org.vo;

import lombok.Data;

import java.util.List;

/**
 * @author 赵亮
 * @date 2018-06-28 18:17
 */
@Data
public class LineVO
{
    private Long lineId;
    private String lineName;
    List<StationVO> stationList;
}
