package com.shankephone.mi.sys.service.impl;

import com.shankephone.fdfs.FdfsClient;
import com.shankephone.mi.common.dao.FieldContentExistsDao;
import com.shankephone.mi.common.enumeration.ResultEnum;
import com.shankephone.mi.common.enumeration.StatusEnum;
import com.shankephone.mi.common.model.BaseFindEntity;
import com.shankephone.mi.common.model.FieldContentExistsFindEntity;
import com.shankephone.mi.common.model.Pager;
import com.shankephone.mi.common.vo.ResultVO;
import com.shankephone.mi.model.OrgOrganizationEntity;
import com.shankephone.mi.model.SysUserEntity;
import com.shankephone.mi.org.dao.OrgOrganizationDao;
import com.shankephone.mi.org.formbean.OrganizationFindEntity;
import com.shankephone.mi.security.context.UserInfoContext;
import com.shankephone.mi.security.controller.LoginController;
import com.shankephone.mi.security.model.UserLoginInfo;
import com.shankephone.mi.security.shiro.CustomShiroRealm;
import com.shankephone.mi.sys.dao.SysUserDao;
import com.shankephone.mi.sys.formbean.GetUserInfoForPhoneVO;
import com.shankephone.mi.sys.formbean.UserFindEntity;
import com.shankephone.mi.sys.service.SysUserService;
import com.shankephone.mi.sys.vo.UserInfoVO;
import com.shankephone.mi.util.*;
import com.shankephone.mi.wechat.formbean.BindWechatOpenIdFormBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author 赵亮
 * @date 2018-06-22 11:08
 */
@Service
public class SysUserServiceImpl implements SysUserService
{
    @Autowired
    private SysUserDao sysUserDao;
    @Autowired
    private OrgOrganizationDao orgOrganizationDao;

    @Autowired
    private FieldContentExistsDao fieldContentExistsDao;

    /**
     * 根据查询条件返回用于信息
     *
     * @author：赵亮
     * @date：2018-06-22 11:07
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Pager<Map<String, Object>> getUserInfo(UserFindEntity findEntity)
    {
        try
        {
            Integer total = sysUserDao.getUserInfoCount(findEntity);
            List<Map<String, Object>> sysUserEntities = sysUserDao.getUserInfo(findEntity);
            Pager pager = new Pager<>(total, sysUserEntities);
            return pager;
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    /**
     * 添加人员
     *
     * @author：赵亮
     * @date：2018-06-22 15:10
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO insertOne(SysUserEntity entity)
    {
        try
        {
            UserLoginInfo userinfo = UserInfoContext.get();
            //验证用户名是否重复
            Integer total = sysUserDao.findCountByUserName(entity.getUserName());
            if (total > 0)
            {
                return ResultVOUtil.error(ResultEnum.USER_NAME_REPEAT.getCode(), ResultEnum.USER_NAME_REPEAT.getMessage());
            }

            FieldContentExistsFindEntity filedentity = new FieldContentExistsFindEntity();
            filedentity.setUserKey(entity.getUserKey());
            filedentity.setTablename("sys_user");
            filedentity.setFieldName("workNumber");
            filedentity.setFieldValue(entity.getWorkNumber());
            if (fieldContentExistsDao.selectFieldExists(filedentity) > 0)
            {
                return ResultVOUtil.error(-1, "工号已存在,请重新填写！");
            }
            filedentity.setUserKey(null);
            filedentity.setOperationSubjectId(null);
            filedentity.setFieldName("phone");
            filedentity.setFieldValue(entity.getPhone());
            if (fieldContentExistsDao.selectFieldExists(filedentity) > 0)
            {
                return ResultVOUtil.error(-1, "手机号已存在,请重新填写！");
            }
            //设定安全的密码
            if (StringUtils.isNotBlank(entity.getPassword()))
            {
                String password = CustomShiroRealm.encrypt(entity.getPassword());
                entity.setPassword(password);
            }
            //entity.setUserName(entity.getPhone());
            entity.setRealNamePinYin(PinYinUtils.getCn2FirstSpell(entity.getRealName()));
            entity.setRealNameAllPinYin(PinYinUtils.getCn2Spell(entity.getRealName()));
            return ResultVOUtil.success(sysUserDao.insertOne(entity));
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    /**
     * 更新人员信息
     *
     * @author：赵亮
     * @date：2018-06-25 10:43
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO updateOne(SysUserEntity entity)
    {
        try
        {
            FieldContentExistsFindEntity filedentity = new FieldContentExistsFindEntity();
            filedentity.setUserKey(entity.getUserKey());
            filedentity.setTablename("sys_user");
            filedentity.setIdName("userId");
            filedentity.setIdValue(entity.getUserId());
            filedentity.setFieldName("workNumber");
            filedentity.setFieldValue(entity.getWorkNumber());
            if (fieldContentExistsDao.selectFieldExists(filedentity) > 0)
            {
                return ResultVOUtil.error(-1, "工号已存在,请重新填写！");
            }
            filedentity.setUserKey(null);
            filedentity.setOperationSubjectId(null);
            filedentity.setFieldName("phone");
            filedentity.setFieldValue(entity.getPhone());
            if (fieldContentExistsDao.selectFieldExists(filedentity) > 0)
            {
                return ResultVOUtil.error(-1, "手机号已存在,请重新填写！");
            }
            //entity.setUserName(entity.getPhone());
            entity.setRealNamePinYin(PinYinUtils.getCn2FirstSpell(entity.getRealName()));
            entity.setRealNameAllPinYin(PinYinUtils.getCn2Spell(entity.getRealName()));

            return ResultVOUtil.success(sysUserDao.updateOne(entity));
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    /**
     * 批量更新人员信息为无效
     *
     * @param findEntity
     * @author：赵亮
     * @date：2018-06-25 10:43
     */
    @Override
    public Integer batchUpdatePersonStopUse(UserFindEntity findEntity)
    {
        try
        {
            findEntity.setStatus(StatusEnum.STOP_USING.getValue());
            return sysUserDao.batchUpdatePersonStopUse(findEntity);
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    @Override
    public Integer updatePassword(UserFindEntity findEntity)
    {
        try
        {
            findEntity.setPassword(CustomShiroRealm.encrypt("123456"));
            return sysUserDao.updatePassword(findEntity);
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }


    @Override
    public SysUserEntity findSysUser(String username)
    {
        return sysUserDao.findSysUser(username);
    }

    @Override
    public SysUserEntity findSysUserbyPhone(String phone)
    {
        return sysUserDao.findSysUserbyPhone(phone);
    }

    @Override
    public Pager<Map<String, Object>> getAllUserInfo(UserFindEntity findEntity)
    {
        try
        {
            Integer total = sysUserDao.getAllUserInfoCount(findEntity);
            List<Map<String, Object>> sysUserEntities = sysUserDao.getAllUserInfo(findEntity);
            Pager pager = new Pager<>(total, sysUserEntities);
            return pager;
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    /**
     * 获取人员基本信息
     *
     * @param entity
     * @author：赵亮
     * @date：2018-08-09 11:11
     */
    @Override
    public Map<String, Object> getOneUserInfo(SysUserEntity entity)
    {
        try
        {
            entity.setUserId(entity.getOperationUserId());
            return sysUserDao.getOneUserInfo(entity);
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    /**
     * 更新个人信息
     *
     * @param entity
     * @author：赵亮
     * @date：2018-08-09 13:22
     */
    @Override
    public Integer updateOnePerson(SysUserEntity entity)
    {
        try
        {
            //entity.setUserName(entity.getPhone());
            entity.setUserId(entity.getOperationUserId());
            return sysUserDao.updateOnePerson(entity);
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    @Override
    public Integer updatedengluIp(SysUserEntity entity)
    {
        try
        {
            return sysUserDao.updatedengluIp(entity);
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO importUserinfoList(String userKey, List<String[]> list)
    {
        try
        {

            List<String[]> userlist = list;
        UserFindEntity userFindEntity = new UserFindEntity();


        OrganizationFindEntity organizationFindEntity = new OrganizationFindEntity();

        organizationFindEntity.setUserKey(userKey);

        List<OrgOrganizationEntity> orgOrganizationEntityList = orgOrganizationDao.listOrganization(organizationFindEntity);

        // UserLoginInfo uli = SessionMap.getUserInfo(userKey);
        userFindEntity.setRoleId(0L);
        userFindEntity.setStatus("all");
        userFindEntity.setStart(0);
        userFindEntity.setLimit(Integer.MAX_VALUE);
        List<Map<String, Object>> usermaplist = sysUserDao.getUserInfo(userFindEntity);
        userFindEntity.setUserKey(userKey);

        // StringBuilder messagesb = new StringBuilder();
        List<Map<String, Object>> messListMap = new ArrayList<>();
        List<SysUserEntity> sysUserEntityList = new ArrayList<SysUserEntity>();
        for (int i = 0; i < userlist.size(); i++)
        {

            SysUserEntity entity = new SysUserEntity();
            String realName = userlist.get(i)[0].trim();
            String sex = userlist.get(i)[1].trim();
            String userName = userlist.get(i)[2].trim();
            String workNumber = userlist.get(i)[3].trim();
            String departmentName = userlist.get(i)[4].trim();
            String position = userlist.get(i)[5].trim();
            String phone = userlist.get(i)[6].trim();
            String email = userlist.get(i)[7].trim();
            String str = realName + sex + userName + workNumber + departmentName + position + phone + email;
            if ("".equals(str))
            {
                break;
            }

            entity.setUserName(userName);
            entity.setWorkNumber(workNumber);
            entity.setRealName(realName);
            entity.setPhone(phone);
            entity.setEmail(email);
            entity.setPosition(position);

            List<OrgOrganizationEntity> orgEntityList = orgOrganizationEntityList.parallelStream().filter(orgentity -> departmentName.equals(orgentity.getOrgName())).collect(Collectors.toList());
            if (orgEntityList != null && orgEntityList.size() > 0)
            {
                entity.setOrgId(orgEntityList.get(0).getOrgId());
            }

            entity.setOperationSubjectId(userFindEntity.getOperationSubjectId());
            entity.setStatus(StatusEnum.START_USING.getValue());
            if (sex.equals("女"))
            {
                sex = "female";
            }
            else
            {
                sex = "male";
            }
            entity.setSex(sex);
            //设定安全的密码
            //if (StringUtils.isNotBlank(entity.getPassword()))
            //{
            String password = CustomShiroRealm.encrypt("123456");
            entity.setPassword(password);
            //}
            entity.setRealNamePinYin(PinYinUtils.getCn2FirstSpell(entity.getRealName()));
            try
            {
                entity.setRealNameAllPinYin(PinYinUtils.getCn2Spell(entity.getRealName()));
            }
            catch (Exception e)
            {


            }
            sysUserEntityList.add(entity);
            StringBuilder messb = new StringBuilder();
            if (ObjectUtils.isEmpty(userName))
            {
                messb.append("用户名不能为空;");
            }
            //            if (ObjectUtils.isEmpty(workNumber))
            //            {
            //                messb.append("工号不能为空;");
            //            }
            if (ObjectUtils.isEmpty(realName))
            {
                messb.append("姓名不能为空;");
            }
            if (ObjectUtils.isEmpty(phone))
            {
                messb.append("手机号不能为空;");
            }
            if (userName.length() > 16)
            {
                messb.append("用户名长度不能超过16个字符;");
            }
            if (realName.length() > 16)
            {
                messb.append("姓名长度不能超过16个字;");
            }
            if (workNumber.length() > 8)
            {
                messb.append("工号长度不能超过8个字符;");
            }
            if (position.length() > 16)
            {
                messb.append("职位长度不能超过16个字;");
            }
            if (email.length() > 32)
            {
                messb.append("邮箱长度不能超过32个字符;");
            }
            if (!StringUtils.isphone(phone))
            {
                messb.append("手机号格式不正确;");
            }
            if (StringUtils.isNotEmpty(email) && email.indexOf("@")<1)
            {
                messb.append("邮箱格式不正确;");
            }

            List<String[]> list1 = list.stream().filter(x -> x[2].trim().equals(userName)).collect(Collectors.toList());
            if (list1 != null && list1.size() > 1)
            {
                messb.append("execl中用户名不能重复;");
            }
            if (ObjectUtils.isNotEmpty(workNumber))
            {
                List<String[]> list2 = list.stream().filter(x -> x[3].trim().equals(workNumber)).collect(Collectors.toList());
                if (list2 != null && list2.size() > 1)
                {
                    messb.append("execl中工号不能重复;");
                }
            }

            if (usermaplist != null && usermaplist.size() > 0)
            {

                List<Map<String, Object>> list3 = usermaplist.stream().filter(x -> x.get("userName").toString().equals(userName)).collect(Collectors.toList());
                if (list3 != null && list3.size() > 0)
                {
                    messb.append("用户名数据已存在;");
                }

                if (ObjectUtils.isNotEmpty(workNumber))
                {
                    List<Map<String, Object>> list4 = usermaplist.stream().filter(x -> x.containsKey("workNumber") && x.containsKey("operationSubjectId") && x.get("workNumber").toString().equals(workNumber) && x.get("operationSubjectId").toString().equals(userFindEntity.getOperationSubjectId().toString())).collect(Collectors.toList());
                    if (list4 != null && list4.size() > 0)
                    {
                        messb.append("工号数据已存在;");
                    }
                }

            }

            if (StringUtils.isNotEmpty(messb.toString()))
            {

                Map<String, Object> mesmap = new HashMap<>();
                mesmap.put("num", (i + 3));
                mesmap.put("message", messb.toString());
                messListMap.add(mesmap);

            }

        }

        if (messListMap != null && messListMap.size() > 0)
        {

            ResultVO resultVO = new ResultVO();
            resultVO.setData(messListMap);
            resultVO.setStatusCode(-1);
            resultVO.setResult("fail");
            resultVO.setMessage("导入失败");
            return resultVO;

        }
        if (sysUserEntityList == null || sysUserEntityList.size() < 1)
        {
            return ResultVOUtil.error(-1, "导入数据不能为空");
        }
        for (int i = 0; i < sysUserEntityList.size(); i++)
        {
            sysUserEntityList.get(i).setUserKey(userKey);
            sysUserDao.insertOne(sysUserEntityList.get(i));
        }
        return ResultVOUtil.success(userlist.size());
    }
        catch (Exception ex)
    {
        throw ex;
    }
    }
@Override
@Transactional(rollbackFor = Exception.class)
public ResultVO importUserList(String userKey, List<String[]> list)
{
    try
    {

    List<String[]> userlist = list;
    UserFindEntity userFindEntity = new UserFindEntity();


    OrganizationFindEntity organizationFindEntity = new OrganizationFindEntity();

    organizationFindEntity.setUserKey(userKey);

    List<OrgOrganizationEntity> orgOrganizationEntityList = orgOrganizationDao.listOrganization(organizationFindEntity);

    userFindEntity.setRoleId(0L);
    userFindEntity.setStatus("all");
    userFindEntity.setStart(0);
    userFindEntity.setLimit(Integer.MAX_VALUE);
    List<Map<String, Object>> usermaplist = sysUserDao.getUserInfo(userFindEntity);
    userFindEntity.setUserKey(userKey);

    // StringBuilder messagesb = new StringBuilder();
    List<Map<String, Object>> messListMap = new ArrayList<>();
    List<SysUserEntity> sysUserEntityList = new ArrayList<SysUserEntity>();
    for (int i = 0; i < userlist.size(); i++)
    {

        SysUserEntity entity = new SysUserEntity();
        String phone = userlist.get(i)[0].trim();
        String realName = userlist.get(i)[1].trim();
        String workNumber = userlist.get(i)[2].trim();
        String sex = userlist.get(i)[3].trim();
        String departmentName = userlist.get(i)[4].trim();
        String position = userlist.get(i)[5].trim();
        String email = userlist.get(i)[6].trim();

        String str = realName + sex  + workNumber + departmentName + position + phone + email;
        if ("".equals(str))
        {
            break;
        }

        entity.setUserName(phone);
        entity.setWorkNumber(workNumber);
        entity.setRealName(realName);
        entity.setPhone(phone);
        entity.setEmail(email);
        entity.setPosition(position);

        List<OrgOrganizationEntity> orgEntityList = orgOrganizationEntityList.parallelStream().filter(orgentity -> departmentName.equals(orgentity.getOrgName())).collect(Collectors.toList());
        if (orgEntityList != null && orgEntityList.size() > 0)
        {
            entity.setOrgId(orgEntityList.get(0).getOrgId());
        }

        entity.setOperationSubjectId(userFindEntity.getOperationSubjectId());
        entity.setStatus(StatusEnum.START_USING.getValue());
        if (sex.equals("女"))
        {
            sex = "female";
        }
        else
        {
            sex = "male";
        }
        entity.setSex(sex);

        entity.setPassword(CustomShiroRealm.encrypt("123456"));

        entity.setRealNamePinYin(PinYinUtils.getCn2FirstSpell(entity.getRealName()));
        try
        {
            entity.setRealNameAllPinYin(PinYinUtils.getCn2Spell(entity.getRealName()));
        }
        catch (Exception e)
        {


        }
        sysUserEntityList.add(entity);
        StringBuilder messb = new StringBuilder();
        if (ObjectUtils.isEmpty(phone))
        {
            messb.append("手机号不能为空;");
        }
        if (!StringUtils.isphone(phone))
        {
            messb.append("手机号格式不正确;");
        }
        if (ObjectUtils.isEmpty(realName))
        {
            messb.append("姓名不能为空;");
        }

        if (realName.length() > 16)
        {
            messb.append("姓名长度不能超过16个字;");
        }
        if (workNumber.length() > 8)
        {
            messb.append("工号长度不能超过8个字符;");
        }
        if (position.length() > 16)
        {
            messb.append("职位长度不能超过16个字;");
        }
        if (email.length() > 32)
        {
            messb.append("邮箱长度不能超过32个字符;");
        }

        if (StringUtils.isNotEmpty(email) && email.indexOf("@")<1)
        {
            messb.append("邮箱格式不正确;");
        }
        List<String[]> list1 = list.stream().filter(x -> x[0].trim().equals(phone)).collect(Collectors.toList());
        if (list1 != null && list1.size() > 1)
        {
            messb.append("execl中手机号不能重复;");
        }
        if (ObjectUtils.isNotEmpty(workNumber))
        {
            List<String[]> list2 = list.stream().filter(x -> x[2].trim().equals(workNumber)).collect(Collectors.toList());
            if (list2 != null && list2.size() > 1)
            {
                messb.append("execl中工号不能重复;");
            }
        }

        if (usermaplist != null && usermaplist.size() > 0)
        {
            List<Map<String, Object>> list3 = usermaplist.stream().filter(x -> x.containsKey("phone") && x.get("phone").toString().equals(phone)).collect(Collectors.toList());
            if (list3 != null && list3.size() > 0)
            {
                messb.append("手机号数据已存在;");
            }
            if (ObjectUtils.isNotEmpty(workNumber))
            {
                List<Map<String, Object>> list4 = usermaplist.stream().filter(x -> x.containsKey("workNumber") && x.containsKey("operationSubjectId") && x.get("workNumber").toString().equals(workNumber) && x.get("operationSubjectId").toString().equals(userFindEntity.getOperationSubjectId().toString())).collect(Collectors.toList());
                if (list4 != null && list4.size() > 0)
                {
                    messb.append("工号数据已存在;");
                }
            }


        }

        if (StringUtils.isNotEmpty(messb.toString()))
        {

            Map<String, Object> mesmap = new HashMap<>();
            mesmap.put("num", (i + 3));
            mesmap.put("message", messb.toString());
            messListMap.add(mesmap);

        }
    }

    if (messListMap != null && messListMap.size() > 0)
    {

        ResultVO resultVO = new ResultVO();
        resultVO.setData(messListMap);
        resultVO.setStatusCode(-1);
        resultVO.setResult("fail");
        resultVO.setMessage("导入失败");
        return resultVO;

    }
    if (sysUserEntityList == null || sysUserEntityList.size() < 1)
    {
        return ResultVOUtil.error(-1, "导入数据不能为空");
    }
    for (int i = 0; i < sysUserEntityList.size(); i++)
    {
        sysUserEntityList.get(i).setUserKey(userKey);
        sysUserDao.insertOne(sysUserEntityList.get(i));
    }
    return ResultVOUtil.success(userlist.size());
    }
    catch (Exception ex)
    {
        throw ex;
    }
}

    @Override
    public List<Map<String, Object>> getAllUserLisMap(UserFindEntity findEntity)
    {
        //UserFindEntity userFindEntity=new UserFindEntity();
        // UserLoginInfo uli = SessionMap.getUserInfo(userKey);
        findEntity.setStart(0);
        findEntity.setLimit(Integer.MAX_VALUE);
        return sysUserDao.getUserInfo(findEntity);
    }

    @Override
    public GetUserInfoForPhoneVO getUserInfoForPhone(BaseFindEntity findEntity)
    {
        try
        {
            UserLoginInfo userLoginInfo = SessionMap.getUserInfo(findEntity.getUserKey());
            GetUserInfoForPhoneVO getUserInfoForPhoneVO = new GetUserInfoForPhoneVO();
            getUserInfoForPhoneVO.setRealName(userLoginInfo.getRealName());
            UserInfoVO SysUserEntity = sysUserDao.findSysUser(userLoginInfo.getUserName());
            getUserInfoForPhoneVO.setSexText("female".equals(SysUserEntity.getSex()) ? "女" : "男");
            getUserInfoForPhoneVO.setSectionNames(userLoginInfo.getSectionNames());
            getUserInfoForPhoneVO.setOrgName(SysUserEntity.getOrgName());
            getUserInfoForPhoneVO.setPosition(SysUserEntity.getPosition());
            getUserInfoForPhoneVO.setPhone(SysUserEntity.getPhone());
            if(StringUtils.isNotEmpty(SysUserEntity.getPhotoUrl()))
            {
                getUserInfoForPhoneVO.setPhotoUrl(FdfsClient.getDownloadServer()+ SysUserEntity.getPhotoUrl());
            }
            else
            {
                getUserInfoForPhoneVO.setPhotoUrl("");
            }

            return getUserInfoForPhoneVO;
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    @Autowired
    private LoginController loginController;

    /**
     * 微信绑定账号
     *
     * @param findEntity
     * @author：赵亮
     * @date：2018-09-11 9:40
     */
    @Override
    public ResultVO bindWechatOpenId(BindWechatOpenIdFormBean findEntity, HttpServletRequest request)
    {
        SysUserEntity userEntity = sysUserDao.findSysUser(findEntity.getUserName());
        String loginPassword = CustomShiroRealm.encrypt(findEntity.getPassword());
        if (userEntity == null)
        {
            return ResultVOUtil.error(0, "您绑定的账号或密码错误，请重试！");
        }
        if (userEntity.getPassword().equals(loginPassword))
        {
            userEntity.setOpenId(findEntity.getOpenId());
            //获取用户角色
            List<String> roles = loginController.getUserRoles(findEntity.getUserName());

            if(StringUtils.isEmpty(roles)||roles.size()<1)
            {
                return ResultVOUtil.error(0, "您绑定的账号无角色，请分配角色后重试！");
            }
            sysUserDao.bindWechatOpenId(userEntity);
            UserLoginInfo userinfo = loginController.getAuthInfo(findEntity.getUserName(), roles, IpUtil.getIpAddr(request));
            String userKey = SessionMap.putUserInfo(userinfo);
            return ResultVOUtil.success(userKey);
        }
        else
        {
            return ResultVOUtil.error(0, "您绑定的账号或密码错误，请重试！");
        }
    }

    /**
     * 微信绑定账号（手机验证码绑定）
     *
     * @param findEntity
     * @author：郝伟州
     * @date：2018年10月10日13:42:10
     */
    @Override
    public ResultVO bindWechatOpenIdByPhone(BindWechatOpenIdFormBean findEntity, HttpServletRequest request)
    {

        String phone=findEntity.getPhone();
        if(!StringUtils.isphone(phone))
        {
            return ResultVOUtil.error(0, "手机号格式不正确!");
        }

        FieldContentExistsFindEntity filedentity = new FieldContentExistsFindEntity();
        filedentity.setTablename("sys_user");
        filedentity.setFieldName("phone");
        filedentity.setFieldValue(phone);
        if (fieldContentExistsDao.selectFieldExists(filedentity) < 1)
        {
            return ResultVOUtil.error(-1, "手机号数据不存在!");
        }
        String code =   DataSwitch.convertObjectToString(SessionMap.get(phone));
        if(!code.equals(findEntity.getCode())||code.equals(""))
        {
            return ResultVOUtil.error(-1, "手机号验证码不正确!");
        }
        SessionMap.remove(phone);
        SysUserEntity userEntity = sysUserDao.findSysUserbyPhone(phone);
        userEntity.setOpenId(findEntity.getOpenId());
        //获取用户角色
        List<String> roles = loginController.getUserRoles(userEntity.getUserName());
        if(StringUtils.isEmpty(roles)||roles.size()<1)
        {
            return ResultVOUtil.error(0, "您绑定的账号无角色，请分配角色后重试！");
        }
        sysUserDao.bindWechatOpenId(userEntity);
        UserLoginInfo userinfo = loginController.getAuthInfo(userEntity.getUserName(), roles, IpUtil.getIpAddr(request));
        String userKey = SessionMap.putUserInfo(userinfo);
        return ResultVOUtil.success(userKey);
    }

}
