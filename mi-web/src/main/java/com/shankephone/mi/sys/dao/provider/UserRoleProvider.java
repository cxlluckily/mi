package com.shankephone.mi.sys.dao.provider;

import com.shankephone.mi.common.enumeration.StatusEnum;

public class UserRoleProvider {

    public String findUserRoles(String userName){
        return "select r.roleId from sys_user u join sys_user_role ur on u.userId = ur.userId join sys_role r on ur.roleId = r.roleId   where r.status='"+StatusEnum.START_USING.getValue()+"'  and  u.userName = #{userName} ";
    }

	

}
