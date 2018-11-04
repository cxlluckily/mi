package com.shankephone.mi.supersys.formbean;

import com.shankephone.mi.common.model.BaseFindEntity;
import com.shankephone.mi.util.StringUtils;
import lombok.Data;

import java.util.List;

/**
 * @author 赵亮
 * @date 2018-07-23 9:31
 */
@Data
public class SuperAdminFindEntity extends BaseFindEntity
{
    private String userName;
    private String realName;
    private String phone;
    private String status;

    /**
     *初始化密码时候用
     *@author：赵亮
     *@date：2018-07-23 9:40
    */
    private List<Long> adminIds;
    private String password;

    public String getAdminIds()
    {
        return StringUtils.listToString(adminIds);
    }
}
