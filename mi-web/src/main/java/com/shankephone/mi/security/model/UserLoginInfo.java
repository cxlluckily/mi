package com.shankephone.mi.security.model;

import com.shankephone.mi.common.enumeration.UserTypeEnum;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 赵亮
 * @date 2018-06-21 9:21
 */
@Data
public class UserLoginInfo
{

    private Long userId;
    private String userName;
    private String password;
    private Long operationSubjectId;
    private String realName;

    private List<String> permissions;
    private List<String> roles;

    private UserTypeEnum userType;

    private List<Long> warehouses = new ArrayList<>();
    private List<Long> workSections = new ArrayList<>();
    private List<String> sectionNames = new ArrayList<>();

    {
        warehouses.add(0l);
        workSections.add(0l);
    }

}
