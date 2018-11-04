package com.shankephone.mi.stock.formbean;

import com.shankephone.mi.common.model.BaseFindEntity;
import com.shankephone.mi.util.StringUtils;
import lombok.Data;

import java.util.List;

/**
 * @author 赵亮
 * @date 2018-07-24 10:13
 */
@Data
public class QRCodeFindEntity extends BaseFindEntity
{
    private String qrCode;
    private String status;
    private String partName;
    private String equipmentNO;
    private String serialNumber;

    //批量删除用
    private List<Long> qrCodeIds;

    public String getQrCodeIds()
    {
        return StringUtils.listToString(qrCodeIds);
    }
}
