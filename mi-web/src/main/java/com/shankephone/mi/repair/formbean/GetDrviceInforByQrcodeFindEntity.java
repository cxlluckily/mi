package com.shankephone.mi.repair.formbean;

import com.shankephone.mi.common.model.BaseFindEntity;
import lombok.Data;

/**
 * @author 赵亮
 * @date 2018-08-27 11:02
 */
@Data
public class GetDrviceInforByQrcodeFindEntity extends BaseFindEntity
{
    private String qrCode;
}
