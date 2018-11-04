package com.shankephone.mi.common.model;

import com.shankephone.mi.common.enumeration.UserTypeEnum;
import com.shankephone.mi.security.model.UserLoginInfo;
import com.shankephone.mi.util.ObjectUtils;
import com.shankephone.mi.util.SessionMap;
import com.shankephone.mi.util.StringUtils;
import lombok.Data;
import org.apache.shiro.authz.AuthorizationException;

import java.io.Serializable;

@Data
public class BaseModel implements Serializable
{

    private static final long serialVersionUID = 1L;

    private String userKey;

    private String operationUserName;

    private Long operationUserId;

    private Long operationSubjectId;

    public void setOperationSubjectId(Long operationSubjectId)
    {
        this.operationSubjectId = operationSubjectId;
    }

    public Long getLoginOperationSubjectId()
    {
        return operationSubjectId;
    }

    public UserLoginInfo getUserLoginInfo()
    {
        return SessionMap.getUserInfo(userKey);
    }

    public String getOperationUserName()
    {
        try
        {
            UserLoginInfo userLoginInfo = getUserLoginInfo();
            if(ObjectUtils.isNotEmpty(userLoginInfo.getRealName()))
            {
                operationUserName = userLoginInfo.getRealName();
            }
            else if(ObjectUtils.isNotEmpty(userLoginInfo.getUserName()))
            {
                operationUserName = userLoginInfo.getUserName();
            }
            else
            {
                operationUserName = "";
            }
            return operationUserName;
        }
        catch(Exception ex)
        {
            return "";
        }

    }

    public Long getOperationUserId()
    {
        UserLoginInfo userLoginInfo = getUserLoginInfo();
        operationUserId = ObjectUtils.isNotNull(userLoginInfo) ? userLoginInfo.getUserId() : null;
        return operationUserId;
    }

    public Long getOperationSubjectId()
    {
        UserLoginInfo userLoginInfo = getUserLoginInfo();
        Long innerId = ObjectUtils.isNotNull(userLoginInfo) ? userLoginInfo.getOperationSubjectId() : null;
        if (null != userLoginInfo && userLoginInfo.getUserType().equals(UserTypeEnum.USER))
        {
            return innerId;
        }
        else
        {
            return operationSubjectId;
        }
    }

    public void validateUserKey()
    {
        if(StringUtils.isEmpty(this.userKey) || !SessionMap.checkUserKeyIsHave(this.userKey))
        {
            throw new AuthorizationException();
        }
    }

}
