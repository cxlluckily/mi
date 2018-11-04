package com.shankephone.mi.sys.dao;

import com.shankephone.mi.model.SysUserEntity;
import com.shankephone.mi.sys.dao.provider.SysUserProvider;
import com.shankephone.mi.sys.formbean.AutoLoginFormBean;
import com.shankephone.mi.sys.formbean.UserFindEntity;
import com.shankephone.mi.sys.vo.UserInfoVO;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 用户管理
 *
 * @author 赵亮
 * @date 2018-06-22 10:54
 */
@Repository
public interface SysUserDao
{
    /**
     * 根据查询条件返回用于信息
     *
     * @author：赵亮
     * @date：2018-06-22 11:07
     */
    @SelectProvider(type = SysUserProvider.class, method = "getUserInfo")
    List<Map<String, Object>> getUserInfo(UserFindEntity userFindEntity);

    /**
     * 根据查询条件返回用于信息总数量
     *
     * @author：赵亮
     * @date：2018-06-22 11:07
     */
    @SelectProvider(type = SysUserProvider.class, method = "getUserInfoCount")
    Integer getUserInfoCount(UserFindEntity userFindEntity);

    /**
     * 添加人员信息
     *
     * @author：赵亮
     * @date：2018-06-21 15:24
     */
    @InsertProvider(type = SysUserProvider.class, method = "insertOne")
    @Options(useGeneratedKeys = true, keyProperty = "userId")
    Long insertOne(SysUserEntity entity);

    /**
     * 根据用户名检索数据库中数量
     *
     * @author：赵亮
     * @date：2018-06-22 16:52
     */
    @SelectProvider(type = SysUserProvider.class, method = "findCountByUserName")
    Integer findCountByUserName(String userName);

    /**
     * 更新人员信息
     *
     * @author：赵亮
     * @date：2018-06-22 16:58
     */
    @UpdateProvider(type = SysUserProvider.class, method = "updateOne")
    Integer updateOne(SysUserEntity entity);

    /**
     * 更新人员登录时间和登录ip
     *
     * @author：郝伟州
     * @date：2018年8月15日14:31:59
     */
    @UpdateProvider(type = SysUserProvider.class, method = "updatedengluIp")
    Integer updatedengluIp(SysUserEntity entity);

    /**
     * 批量更新人员信息为无效
     *
     * @param findEntity
     * @author：赵亮
     * @date：2018-06-25 10:43
     */
    @UpdateProvider(type = SysUserProvider.class, method = "batchUpdatePersonStopUse")
    Integer batchUpdatePersonStopUse(UserFindEntity findEntity);

    @UpdateProvider(type = SysUserProvider.class, method = "updatePassword")
    Integer updatePassword(UserFindEntity findEntity);

    @SelectProvider(type = SysUserProvider.class, method = "findSysUser")
    UserInfoVO findSysUser(String username);

    @SelectProvider(type = SysUserProvider.class, method = "getAllUserInfo")
    List<Map<String, Object>> getAllUserInfo(UserFindEntity findEntity);

    @SelectProvider(type = SysUserProvider.class, method = "getAllUserInfoCount")
    Integer getAllUserInfoCount(UserFindEntity findEntity);

    @SelectProvider(type = SysUserProvider.class, method = "getOneUserInfo")
    Map<String, Object> getOneUserInfo(SysUserEntity entity);

    @UpdateProvider(type = SysUserProvider.class, method = "updateOnePerson")
    Integer updateOnePerson(SysUserEntity entity);


    @UpdateProvider(type = SysUserProvider.class, method = "wechatLoginOut")
    void wechatLoginOut(Long userId);

    @UpdateProvider(type = SysUserProvider.class, method = "bindWechatOpenId")
    void bindWechatOpenId(SysUserEntity entity);

    @SelectProvider(type = SysUserProvider.class, method = "findSysUserByOpenId")
    SysUserEntity findSysUserByOpenId(String openId);

    @SelectProvider(type = SysUserProvider.class, method = "autoLogin")
    SysUserEntity autoLogin(AutoLoginFormBean entity);

    @UpdateProvider(type = SysUserProvider.class, method = "udpateLoginIpForWechat")
    Integer udpateLoginIpForWechat(SysUserEntity entity);

    @SelectProvider(type = SysUserProvider.class, method = "findUserByuserId")
    SysUserEntity findUserByuserId(Long userId);

    @SelectProvider(type = SysUserProvider.class, method = "findSysUserbyPhone")
    UserInfoVO findSysUserbyPhone(String phone);

}
