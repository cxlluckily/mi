package com.shankephone.mi.wechat.formbean;

import com.shankephone.mi.common.model.BaseModel;
import lombok.Data;

/**
 * @author 赵亮
 * @date 2018-09-11 9:39
 */
@Data
public class BindWechatOpenIdFormBean extends BaseModel
{
    private String userName;
    private String password;
    private String openId;
    private String phone;
    private String code;
}
