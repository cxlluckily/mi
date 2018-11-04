package com.shankephone.mi.batchcommand.formbean;

import com.shankephone.mi.common.model.BaseFindEntity;
import lombok.Data;

/**
 * @author 赵亮
 * @date 2018/10/31 14:38
 */
@Data
public class DeviceNoRegistFindEntity  extends BaseFindEntity
{
    /**
     * 设备唯一ID
     **/
    private String deviceuId;
    /**
     * 设备编号
     **/
    private String deviceCode;
    /**
     * 设备所在车站代码
     **/
    private String stationCode;
}
