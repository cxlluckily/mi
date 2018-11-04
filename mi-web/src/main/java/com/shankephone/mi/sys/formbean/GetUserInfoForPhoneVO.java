package com.shankephone.mi.sys.formbean;

import lombok.Data;

import java.util.List;

/**
 * @author 赵亮
 * @date 2018-09-10 17:22
 */
@Data
public class GetUserInfoForPhoneVO
{
    private String realName;
    private  String sexText;
    private List<String> sectionNames;
    //职位
    private  String position;
    //部门
    private  String orgName;

    private  String photoUrl;

    private String phone;
}
