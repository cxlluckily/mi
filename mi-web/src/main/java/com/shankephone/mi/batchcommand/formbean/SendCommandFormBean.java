package com.shankephone.mi.batchcommand.formbean;

import com.shankephone.mi.common.model.BaseModel;
import lombok.Data;

import java.util.List;

/**
 * @author 赵亮
 * @date 2018-10-24 11:47
 */
@Data
public class SendCommandFormBean extends BaseModel
{
    private List<SendCommandDeviceFormBean> deviceFormBeans;
    private String commandType;
    private String commandCategory;
    private String commandContent;
}
