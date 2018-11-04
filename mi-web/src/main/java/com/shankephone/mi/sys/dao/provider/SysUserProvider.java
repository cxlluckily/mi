package com.shankephone.mi.sys.dao.provider;

import com.shankephone.fdfs.FdfsClient;
import com.shankephone.mi.common.enumeration.StatusEnum;
import com.shankephone.mi.model.SysUserEntity;
import com.shankephone.mi.sys.formbean.AutoLoginFormBean;
import com.shankephone.mi.sys.formbean.UserFindEntity;
import com.shankephone.mi.util.ObjectUtils;
import com.shankephone.mi.util.StringUtils;

/**
 * @author 赵亮
 * @date 2018-06-22 10:33
 */
public class SysUserProvider
{
    public String getAllUserInfoCount(UserFindEntity userFindEntity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT ");
        sbSql.append("   count(*) AS totalCount ");
        sbSql.append(" FROM sys_user ");
        sbSql.append("  LEFT JOIN org_organization ");
        sbSql.append("  ON org_organization.orgId = sys_user.orgId ");
        sbSql.append(" where sys_user.status <> 'stop_using' ");
        if (ObjectUtils.isNotNull(userFindEntity.getOperationSubjectId()))
        {
            sbSql.append(" and sys_user.operationSubjectId = #{operationSubjectId}");
        }
        if (ObjectUtils.isNotEmpty(userFindEntity.getUserName()))
        {
            sbSql.append(" and sys_user.userName LIKE concat('%', #{userName}, '%') ");
        }
        if (ObjectUtils.isNotEmpty(userFindEntity.getPhone()))
        {
            sbSql.append(" and sys_user.phone LIKE concat('%', #{phone}, '%') ");
        }
        if (ObjectUtils.isNotEmpty(userFindEntity.getRealName()))
        {
            sbSql.append(" and sys_user.realName LIKE concat('%', #{realName}, '%') ");
        }
        if (ObjectUtils.isNotEmpty(userFindEntity.getWorkNumber()))
        {
            sbSql.append(" and sys_user.workNumber LIKE concat(#{workNumber}, '%') ");
        }
        if (ObjectUtils.isNotEmpty(userFindEntity.getInternalNumber()))
        {
            sbSql.append(" and org_organization.internalNumber LIKE concat(#{internalNumber}, '%') ");
        }
        if ( userFindEntity.getUserIdsSize()>0)
        {
            sbSql.append(" and sys_user.userId not in ("+ userFindEntity.getUserIds() +")");
        }
        if (ObjectUtils.isNotEmpty(userFindEntity.getPosition()))
        {
            sbSql.append(" and sys_user.position LIKE concat('%', #{position}, '%') ");
        }
        return sbSql.toString();
    }

    public String getAllUserInfo(UserFindEntity userFindEntity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append("  SELECT ");
        sbSql.append("   CASE sys_user.status ");
        sbSql.append("   WHEN '" + StatusEnum.START_USING.getValue() + "' ");
        sbSql.append("     THEN '可用' ");
        sbSql.append("   ELSE '不可用' END AS statusText,");
        sbSql.append("  case sex when  'female' THEN  '女' else '男' end as sexmc, ");
        sbSql.append("      sys_user.*, org_organization.orgName ");
        sbSql.append("  FROM sys_user ");
        sbSql.append("  LEFT JOIN org_organization ");
        sbSql.append("       ON org_organization.orgId = sys_user.orgId ");
        sbSql.append(" where sys_user.status <> 'stop_using' ");
        if (ObjectUtils.isNotNull(userFindEntity.getOperationSubjectId()))
        {
            sbSql.append(" and sys_user.operationSubjectId = #{operationSubjectId}");
        }
        if (ObjectUtils.isNotEmpty(userFindEntity.getUserName()))
        {
            sbSql.append(" and sys_user.userName LIKE concat('%', #{userName}, '%') ");
        }
        if (ObjectUtils.isNotEmpty(userFindEntity.getPhone()))
        {
            sbSql.append(" and sys_user.phone LIKE concat('%', #{phone}, '%') ");
        }
        if (ObjectUtils.isNotEmpty(userFindEntity.getRealName()))
        {
            sbSql.append(" and sys_user.realName LIKE concat('%', #{realName}, '%') ");
        }
        if (ObjectUtils.isNotEmpty(userFindEntity.getWorkNumber()))
        {
            sbSql.append(" and sys_user.workNumber LIKE concat(#{workNumber}, '%') ");
        }
        if (ObjectUtils.isNotEmpty(userFindEntity.getInternalNumber()))
        {
            sbSql.append(" and org_organization.internalNumber LIKE concat(#{internalNumber}, '%') ");
        }
        if ( userFindEntity.getUserIdsSize()>0)
        {
            sbSql.append(" and sys_user.userId not in ("+ userFindEntity.getUserIds() +") ");
        }
        if (ObjectUtils.isNotEmpty(userFindEntity.getPosition()))
        {
            sbSql.append(" and sys_user.position LIKE concat('%', #{position}, '%') ");
        }
        sbSql.append("   LIMIT #{start}, #{limit} ");
        return sbSql.toString();
    }

    /**
     * 根据查询条件返回用于信息
     *
     * @author：赵亮
     * @date：2018-06-22 11:07
     */
    public String getUserInfo(UserFindEntity userFindEntity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append("SELECT tempUser.*," );
        sbSql.append("   CASE tempUser.status ");
        sbSql.append("   WHEN '" + StatusEnum.START_USING.getValue() + "' ");
        sbSql.append("     THEN '可用'    ELSE '不可用' END AS statusText,");
        sbSql.append("   case tempUser.sex when  'female' THEN  '女' else '男' end as sexmc, ");
        sbSql.append(" roleName FROM (");
        sbSql.append("  SELECT ");
        sbSql.append("      sys_user.*,orgName, concat('" + FdfsClient.getDownloadServer() + "',photoUrl) AS fullPhotoUrl ");
        sbSql.append("  FROM sys_user ");
        sbSql.append("  LEFT JOIN org_organization ");
        sbSql.append("       ON org_organization.orgId = sys_user.orgId ");
        sbSql.append(getUserInfoWhereStr(userFindEntity));
        sbSql.append(" )AS tempUser");
        sbSql.append(" LEFT JOIN ( ");
        sbSql.append("   SELECT ");
        sbSql.append("     sys_user.userId, ");
        sbSql.append("     group_concat(roleName) roleName ");
        sbSql.append("   FROM sys_user ");
        sbSql.append("     LEFT JOIN sys_user_role ");
        sbSql.append("       ON sys_user_role.userId = sys_user.userId ");
        sbSql.append("     LEFT JOIN sys_role ");
        sbSql.append("       ON sys_user_role.roleId = sys_role.roleId ");
        sbSql.append(getUserInfoWhereStr(userFindEntity));
        sbSql.append("   GROUP BY  sys_user.userId )AS tempRoleUser ");
        sbSql.append(" ON tempRoleUser.userId = tempUser.userId ");
        sbSql.append("   LIMIT #{start}, #{limit} ");
        return sbSql.toString();
    }

    private String getUserInfoWhereStr(UserFindEntity userFindEntity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append("  WHERE 1=1 ");
        if (StringUtils.isNotEmpty(userFindEntity.getOperationSubjectId()))
        {
            sbSql.append("       AND sys_user.operationSubjectId = #{operationSubjectId} ");
        }
        if (ObjectUtils.isNotEmpty(userFindEntity.getUserName()))
        {
            sbSql.append("       AND ifnull(sys_user.userName,'') LIKE concat('%', #{userName}, '%') ");
        }
        if (ObjectUtils.isNotEmpty(userFindEntity.getRealName()))
        {
            sbSql.append("       AND ifnull(sys_user.realName,'') LIKE concat('%', #{realName}, '%') ");
        }
        if (ObjectUtils.isNotEmpty(userFindEntity.getPhone()))
        {
            sbSql.append("       AND ifnull(sys_user.phone,'') LIKE concat('%', #{phone}, '%') ");
        }
        if (!"all".equals(userFindEntity.getStatus()))
        {
            sbSql.append("       AND sys_user.status = #{status} ");
        }
        if (userFindEntity.getRoleId() != 0)
        {
            sbSql.append("       AND sys_user.userId IN (SELECT userId ");
            sbSql.append("                               FROM sys_user_role ");
            sbSql.append("                               WHERE roleId = #{roleId}) ");
        }
        if (StringUtils.isNotEmpty(userFindEntity.getInternalNumber()))
        {
            sbSql.append(" AND sys_user.orgId IN (SELECT orgId ");
            sbSql.append("                              FROM org_organization ");
            sbSql.append("                              WHERE internalNumber LIKE concat( #{internalNumber},'%')) ");
        }
        return sbSql.toString();
    }

    /**
     * 根据查询条件返回用于信息总数量
     *
     * @author：赵亮
     * @date：2018-06-22 11:07
     */
    public String getUserInfoCount(UserFindEntity userFindEntity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT ");
        sbSql.append("   count(*) AS totalCount ");
        sbSql.append(" FROM sys_user ");
        sbSql.append(getUserInfoWhereStr(userFindEntity));
        return sbSql.toString();
    }

    /**
     * 添加人员信息
     *
     * @author：赵亮
     * @date：2018-06-22 15:05
     */
    public String insertOne(SysUserEntity entity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" INSERT INTO ");
        sbSql.append("    sys_user ");
        sbSql.append("    ( ");
        sbSql.append("       orgId, ");
        sbSql.append("       operationSubjectId, ");
        sbSql.append("       userName, ");
        sbSql.append("       password, ");
        sbSql.append("       photoUrl, ");
        sbSql.append("       email, ");
        sbSql.append("       realName, ");
        sbSql.append("       phone, ");
        sbSql.append("       status, ");
        sbSql.append("       salt, ");
        sbSql.append("       position, ");
        sbSql.append("       workNumber, ");
        sbSql.append("       remark, ");
        sbSql.append("       createUser, ");
        sbSql.append("       sex, ");
        sbSql.append("       realNamePinYin, ");
        sbSql.append("       realNameAllPinYin, ");
        sbSql.append("       modifyUser, ");
        sbSql.append("       wasDefaultManager ");
        sbSql.append("    ) ");
        sbSql.append("  VALUES ");
        sbSql.append("    ( ");
        sbSql.append("       #{orgId}, ");
        sbSql.append("       #{operationSubjectId}, ");
        sbSql.append("       #{userName}, ");
        sbSql.append("       #{password}, ");
        sbSql.append("       #{photoUrl}, ");
        sbSql.append("       #{email}, ");
        sbSql.append("       #{realName}, ");
        sbSql.append("       #{phone}, ");
        sbSql.append("       #{status}, ");
        sbSql.append("       #{salt}, ");
        sbSql.append("       #{position}, ");
        sbSql.append("       #{workNumber}, ");
        sbSql.append("       #{remark}, ");
        sbSql.append("       #{operationUserName}, ");
        sbSql.append("       #{sex}, ");
        sbSql.append("       #{realNamePinYin}, ");
        sbSql.append("       #{realNameAllPinYin}, ");
        sbSql.append("       #{operationUserName}, ");
        sbSql.append("       #{wasDefaultManager} ");
        sbSql.append("    ) ");

        return sbSql.toString();

    }

    /**
     * 根据用户名检索数据库中数量
     *
     * @author：赵亮
     * @date：2018-06-22 16:54
     */
    public String findCountByUserName(String userName)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT count(*)  AS hasCount ");
        sbSql.append(" FROM sys_user ");
        sbSql.append(" WHERE userName = #{userName} ");
        return sbSql.toString();
    }

    /**
     * 根据用户id查看用户信息
     *
     * @author：赵亮
     * @date：2018-06-22 16:54
     */
    public String findUserByuserId(Long userId)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT * ");
        sbSql.append(" FROM sys_user ");
        sbSql.append(" WHERE userId = #{userId} ");
        return sbSql.toString();
    }

    /**
     * 更新人员信息
     *
     * @author：赵亮
     * @date：2018-06-25 10:32
     */
    public String updateOne(SysUserEntity entity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" UPDATE ");
        sbSql.append("    sys_user ");
        sbSql.append("  SET ");
        sbSql.append("    orgId = #{orgId}, ");
        if(StringUtils.isNotEmpty(entity.getUserName()))
        {
            sbSql.append("    userName = #{userName}, ");
        }
        //        sbSql.append("    password = #{password}, ");
        sbSql.append("    photoUrl = #{photoUrl}, ");
        sbSql.append("    email = #{email}, ");
        sbSql.append("    realName = #{realName}, ");
        sbSql.append("    phone = #{phone}, ");
        sbSql.append("    status = #{status}, ");
        sbSql.append("    position = #{position}, ");
        sbSql.append("    workNumber = #{workNumber}, ");
        sbSql.append("    remark = #{remark}, ");
        sbSql.append("    sex = #{sex}, ");
        sbSql.append("    realNamePinYin = #{realNamePinYin}, ");
        sbSql.append("    realNameAllPinYin = #{realNameAllPinYin}, ");
        sbSql.append("    modifyUser = #{operationUserName} ");
        sbSql.append("  WHERE ");
        sbSql.append("    userId = #{userId} ");

        return sbSql.toString();

    }


    public String updatedengluIp(SysUserEntity entity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" UPDATE sys_user ");
        sbSql.append(" SET lastLoginTime = #{lastLoginTime}, ");
        sbSql.append("     lastLoginIp = #{lastLoginIp}, ");
        sbSql.append("     currentLoginTime = #{currentLoginTime}, ");
        sbSql.append("     currentLoginIp = #{currentLoginIp} ");
        sbSql.append("   where  userId = #{userId} ");
        return sbSql.toString();

    }



    public String batchUpdatePersonStopUse(UserFindEntity findEntity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" UPDATE sys_user ");
        sbSql.append(" SET status = #{status}, ");
        sbSql.append("     modifyUser = #{operationUserName} ");
        sbSql.append(" WHERE userId IN (" + findEntity.getUserIds() + ") ");
        return sbSql.toString();

    }

    public String findSysUser(String userName)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" select *,orgName from sys_user ");
        sbSql.append("  LEFT JOIN org_organization ");
        sbSql.append("       ON org_organization.orgId = sys_user.orgId ");
        sbSql.append(" WHERE sys_user.userName = #{userName} AND sys_user.status='" + StatusEnum.START_USING.getValue() + "'");
        sbSql.append("   LIMIT 1 ");
        return sbSql.toString();

    }

    public String findSysUserbyPhone(String phone)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" select *,orgName from sys_user ");
        sbSql.append("  LEFT JOIN org_organization ");
        sbSql.append("       ON org_organization.orgId = sys_user.orgId ");
        sbSql.append(" WHERE sys_user.phone = #{phone} AND sys_user.status='" + StatusEnum.START_USING.getValue() + "'");
        sbSql.append("   LIMIT 1 ");
        return sbSql.toString();

    }

    public String updatePassword(UserFindEntity findEntity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" UPDATE sys_user ");
        sbSql.append(" SET password = #{password}, ");
        sbSql.append("     modifyUser = #{operationUserName}, ");
        sbSql.append("     openId = '' ");
        sbSql.append(" WHERE userId IN (" + findEntity.getUserIds() + ") ");
        return sbSql.toString();
    }

    public String getOneUserInfo(SysUserEntity entity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT * ");
        sbSql.append(" FROM ( ");
        sbSql.append("        SELECT ");
        sbSql.append("          sys_user.userId, ");
        sbSql.append("          sys_user.userName, ");
        sbSql.append("          sys_user.phone, ");
        sbSql.append("          concat('" + FdfsClient.getDownloadServer() + "',photoUrl) AS fullPhotoUrl, ");
        sbSql.append("          sys_user.email, ");
        sbSql.append("          sys_user.realName, ");
        sbSql.append("          sys_user.position, ");
        sbSql.append("          sys_user.workNumber, ");
        sbSql.append("          sys_user.sex, ");
        sbSql.append("          sys_user.wasDefaultManager, ");
        sbSql.append("          DATE_FORMAT(sys_user.lastLoginTime, '%Y-%m-%d') AS lastLoginTime, ");
        sbSql.append("          sys_user.lastLoginIp, ");
        sbSql.append("          sys_user.remark, ");
        sbSql.append("          orgName, ");
        sbSql.append("          subjectName ");
        sbSql.append("        FROM sys_user ");
        sbSql.append("          INNER JOIN sys_operation_subject ");
        sbSql.append("            ON sys_operation_subject.operationSubjectId = sys_user.operationSubjectId ");
        sbSql.append("          LEFT JOIN org_organization ");
        sbSql.append("            ON sys_user.orgId = org_organization.orgId ");
        sbSql.append("        WHERE sys_user.userId = #{userId} ");
        sbSql.append("      ) AS tempUser ");
        sbSql.append("   LEFT JOIN ( ");
        sbSql.append("               SELECT ");
        sbSql.append("                 sys_user.userId, ");
        sbSql.append("                 group_concat(roleName) roleName ");
        sbSql.append("               FROM sys_user ");
        sbSql.append("                 LEFT JOIN sys_user_role ");
        sbSql.append("                   ON sys_user_role.userId = sys_user.userId ");
        sbSql.append("                 LEFT JOIN sys_role ");
        sbSql.append("                   ON sys_user_role.roleId = sys_role.roleId ");
        sbSql.append("               WHERE sys_user.userId = #{userId} ");
        sbSql.append("               GROUP BY sys_user.userId) AS tempRoleUser ");
        sbSql.append("     ON tempUser.userId = tempRoleUser.userId ");

        return sbSql.toString();
    }

    public String updateOnePerson(SysUserEntity entity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" UPDATE ");
        sbSql.append("    sys_user ");
        sbSql.append("  SET ");
        //if(StringUtils.isNotEmpty(entity.getUserName()))
        if(entity.getUserName()!=null)
        {
            sbSql.append("    userName = #{userName}, ");
        }
        sbSql.append("    photoUrl = #{photoUrl}, ");

        sbSql.append("    email = #{email}, ");
       // if(StringUtils.isNotEmpty(entity.getPhone()))
        if(entity.getPhone()!=null)
        {
            sbSql.append("    phone = #{phone}, ");
        }
        //if(StringUtils.isNotEmpty(entity.getPosition()))
        if(entity.getPosition()!=null)
        {
            sbSql.append("    position = #{position}, ");
        }
        //if(StringUtils.isNotEmpty(entity.getWorkNumber()))
        if(entity.getWorkNumber()!=null)
        {
            sbSql.append("    workNumber = #{workNumber}, ");
        }
        sbSql.append("    remark = #{remark}, ");
        sbSql.append("    sex = #{sex}, ");
        sbSql.append("    modifyUser = #{operationUserName} ");
        sbSql.append("  WHERE ");
        sbSql.append("    userId = #{userId} ");

        return sbSql.toString();
    }

    public String wechatLoginOut(Long userId)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" UPDATE sys_user ");
        sbSql.append(" SET openId = '' ");
        sbSql.append(" WHERE userId = #{userId} ");

        return sbSql.toString();

    }

    public String bindWechatOpenId(SysUserEntity entity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" UPDATE sys_user ");
        sbSql.append(" SET openId =#{openId} ");
        sbSql.append(" WHERE userId = #{userId} ");

        return sbSql.toString();

    }

    public String findSysUserByOpenId(String openId)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" select * from sys_user ");
        sbSql.append(" WHERE openId = #{openId} AND status='" + StatusEnum.START_USING.getValue() + "'");
        return sbSql.toString();

    }

    public String autoLogin(AutoLoginFormBean entity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT * FROM sys_user WHERE md5(concat(userId,#{userType},#{key})) =#{userKey} ");
        return sbSql.toString();
    }

    public String udpateLoginIpForWechat(SysUserEntity entity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" UPDATE sys_user ");
        sbSql.append(" SET lastLoginTime = #{lastLoginTime}, ");
        sbSql.append("     lastLoginIp = #{lastLoginIp}, ");
        sbSql.append("     currentLoginTime = #{currentLoginTime}, ");
        sbSql.append("     currentLoginIp = #{currentLoginIp} ");
        sbSql.append(" WHERE openId = #{openId} ");

        return sbSql.toString();
    }
}
