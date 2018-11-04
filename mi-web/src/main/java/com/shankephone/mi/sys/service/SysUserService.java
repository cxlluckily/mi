package com.shankephone.mi.sys.service;

import com.shankephone.mi.common.model.BaseFindEntity;
import com.shankephone.mi.common.model.Pager;
import com.shankephone.mi.common.vo.ResultVO;
import com.shankephone.mi.model.SysUserEntity;
import com.shankephone.mi.sys.formbean.GetUserInfoForPhoneVO;
import com.shankephone.mi.sys.formbean.UserFindEntity;
import com.shankephone.mi.wechat.formbean.BindWechatOpenIdFormBean;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author 赵亮
 * @date 2018-06-22 11:06
 */
public interface SysUserService
{
    /**
     *根据查询条件返回用于信息
     *@author：赵亮
     *@date：2018-06-22 11:08
    */
    Pager<Map<String,Object>> getUserInfo(UserFindEntity findEntity);

    /**
     *添加人员
     *@author：赵亮
     *@date：2018-06-21 15:24
     */
    ResultVO insertOne(SysUserEntity entity);


    /**
     *根据主键更新组织机构
     *@author：赵亮
     *@date：2018-06-21 19:36
     */
    ResultVO updateOne(SysUserEntity entity);



    /**
     *批量更新人员信息为无效
     *@author：赵亮
     *@date：2018-06-25 10:44
     * @param findEntity
    */
    Integer batchUpdatePersonStopUse(UserFindEntity findEntity);

    Integer updatePassword(UserFindEntity findEntity);

    /**
     * 根据用户名查询用户
     * @param username
     * @return
     */
    SysUserEntity findSysUser(String username);

    /**
     * 根据手机号查询用户
     * @param phone
     * @return
     */
    SysUserEntity findSysUserbyPhone(String phone);

    /**
     * 获取运营主体下所有用户
     * @param findEntity
     * @return
     */
    Pager<Map<String, Object>> getAllUserInfo(UserFindEntity findEntity);

    /**
     *获取人员基本信息
     *@author：赵亮
     *@date：2018-08-09 11:12
    */
    Map<String, Object> getOneUserInfo(SysUserEntity entity);

    /**
     *更新个人信息
     *@author：赵亮
     *@date：2018-08-09 13:22
    */
    Integer updateOnePerson(SysUserEntity entity);


    /**
     *根据主键更新组织机构
     *@author：郝伟州
     *@date：2018年8月15日14:33:14
     */
    Integer updatedengluIp(SysUserEntity entity);


    /**
     *导入人员信息列表
     *@author：郝伟州
     *@date：2018年8月15日14:33:14
     */
    ResultVO importUserList(String userKey ,List<String[]> list);

    /**
     *导入人员信息列表
     *@author：郝伟州
     *@date：2018年8月15日14:33:14
     */
    ResultVO importUserinfoList(String userKey ,List<String[]> list);


    /**
     * 获取运营主体下所有用户listMap
     * @param findEntity
     * @return
     */
    List<Map<String, Object>> getAllUserLisMap(UserFindEntity findEntity);

    GetUserInfoForPhoneVO getUserInfoForPhone(BaseFindEntity findEntity);

    /**
     *微信绑定账号
     *@author：赵亮
     *@date：2018-09-11 9:40
    */
    ResultVO bindWechatOpenId(BindWechatOpenIdFormBean findEntity, HttpServletRequest request);

    /**
     *微信绑定账号（手机验证码绑定）
     *@author：郝伟州
     *@date：2018年10月10日13:41:20
     */
    ResultVO bindWechatOpenIdByPhone(BindWechatOpenIdFormBean findEntity, HttpServletRequest request);
}
