package com.shankephone.mi.common.model;

import com.shankephone.mi.security.model.UserLoginInfo;
import com.shankephone.mi.util.ObjectUtils;
import com.shankephone.mi.util.SessionMap;
import com.shankephone.mi.util.StringUtils;
import lombok.Data;
import org.apache.shiro.authz.AuthorizationException;

import java.util.List;

/**
 * The type Base find entity.
 *
 * @author 赵亮
 * @date 2018 -06-21 10:16
 */
@Data
public class BaseFindEntity {
    private Long operationUserId;
    private String operationUserName;
    private String userKey;
    private Long operationSubjectId;
    private int start;
    private int limit;
    private String sortField;
    private String sortType;
    private String beginTime;
    private String endTime;
    private String searchContent = "";

    private List<Long> wareHouseRange;
    private List<Long> workSectionRange;

    public List<Long> getWareHouseRange() {
        UserLoginInfo userLoginInfo = getUserLoginInfo();
        return userLoginInfo.getWarehouses();
    }

    public List<Long> getWorkSectionRange() {
        UserLoginInfo userLoginInfo = getUserLoginInfo();
        return userLoginInfo.getWorkSections();
    }

    public Long getOperationSubjectId() {
        UserLoginInfo userLoginInfo = getUserLoginInfo();
        operationSubjectId = ObjectUtils.isNotNull(userLoginInfo) ? userLoginInfo.getOperationSubjectId() : null;
        return operationSubjectId;
    }

    /**
     * Gets user login info.
     *
     * @return the user login info
     */
    public UserLoginInfo getUserLoginInfo() {
        return SessionMap.getUserInfo(userKey);
    }

    /**
     * 获取用户Id
     *
     * @return user id
     */
    public Long getOperationUserId() {
        UserLoginInfo loginInfo = this.getUserLoginInfo();
        operationUserId = ObjectUtils.isNotNull(loginInfo) ? loginInfo.getUserId() : null;
        return operationUserId;
    }


    /**
     * Gets user name.
     *
     * @return the user name
     */
    public String getOperationUserName() {
        UserLoginInfo loginInfo = this.getUserLoginInfo();
        operationUserName = ObjectUtils.isNotNull(loginInfo) ? (ObjectUtils.isNull(loginInfo.getRealName()) ? loginInfo.getUserName() : loginInfo.getRealName()) : "";
        return operationUserName;
    }

    public void validateUserKey()
    {
        if(StringUtils.isEmpty(this.userKey) || !SessionMap.checkUserKeyIsHave(this.userKey))
        {
            throw new AuthorizationException();
        }
    }
}
