package com.shankephone.mi.sys.formbean;

import com.shankephone.mi.common.model.BaseFindEntity;
import com.shankephone.mi.util.StringUtils;
import lombok.Data;

import java.util.List;

/**
 * @author 赵亮
 * @date 2018-06-22 10:28
 */
@Data
public class UserFindEntity extends BaseFindEntity
{
    private String internalNumber;
    private Long roleId;
    private String userName;
    private String realName;
    private String phone;
    private String status;
    private String password;
    private String workNumber;
    private String position;
    /**
     * 批量删除时候用
     *
     * @author：赵亮
     * @date：2018-06-25 11:02
     */
    private List<Long> userIds;

    public String getUserIds()
    {
        return StringUtils.listToString(userIds);
    }

    public int getUserIdsSize()
    {
        return userIds==null?0:this.userIds.size();
    }

}
