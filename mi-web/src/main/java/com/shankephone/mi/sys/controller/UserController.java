package com.shankephone.mi.sys.controller;

import com.shankephone.fdfs.FdfsClient;
import com.shankephone.mi.common.enumeration.TreeTypeEnum;
import com.shankephone.mi.common.model.BaseFindEntity;
import com.shankephone.mi.common.vo.ResultVO;
import com.shankephone.mi.model.SysUserEntity;
import com.shankephone.mi.sys.formbean.UserFindEntity;
import com.shankephone.mi.sys.service.SysFunctionTreeService;
import com.shankephone.mi.sys.service.SysUserService;
import com.shankephone.mi.sys.vo.UserTreeFindEntity;
import com.shankephone.mi.util.*;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * 用户管理
 *
 * @author：赵亮
 * @date：2018-08-01 9:41
 */
@Controller
@RequestMapping("/user")
public class UserController
{

    @Autowired
    private SysFunctionTreeService sysFunctionTreeService;
    @Autowired
    private SysUserService sysUserService;

    /***
     * 根据用户表的id返回对应用户的功能树节点
     * @return
     */
    //	//@RequiresPermissions("gnsjd")
    @RequestMapping(value = "/getUserTree")
    @ResponseBody
    public ResultVO<Map<String, String>> getUserTree(@RequestBody UserTreeFindEntity findEntity)
    {
        findEntity.validateUserKey();
        findEntity.setTreeType(TreeTypeEnum.MANAGE.getCode());
        return ResultVOUtil.success(sysFunctionTreeService.getUserTree(findEntity));
    }

    /**
     * 返回手机端的功能树
     *
     * @author：赵亮
     * @date：2018-08-01 15:45
     */
    @RequestMapping(value = "/getPhoneTree")
    @ResponseBody
    public ResultVO<Map<String, String>> getPhoneTree(@RequestBody UserTreeFindEntity findEntity)
    {
        findEntity.validateUserKey();
        findEntity.setUserId(findEntity.getOperationUserId());
        findEntity.setTreeType(TreeTypeEnum.PHONE.getCode());
        return ResultVOUtil.success(sysFunctionTreeService.getUserTree(findEntity));
    }

    /***
     * 根据查询条件返回用户信息
     * @return
     */
    @RequestMapping(value = "/all")
    @ResponseBody
    public ResultVO<Map<String, String>> getAllUserInfo(@RequestBody UserFindEntity findEntity)
    {
        try
        {
            findEntity.validateUserKey();
            return ResultVOUtil.success(sysUserService.getAllUserInfo(findEntity));
        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex);
        }
    }

    /***
     * 根据查询条件返回用于信息
     * @return
     */
    //@RequiresPermissions("yhgl")
    @RequestMapping(value = "/getUserInfo")
    @ResponseBody
    public ResultVO<Map<String, String>> getUserInfo(@RequestBody UserFindEntity findEntity)
    {
        try
        {
            findEntity.validateUserKey();
            return ResultVOUtil.success(sysUserService.getUserInfo(findEntity));
        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex);
        }
    }

    /**
     * 添加用户
     * @return
     * @author：赵亮
     * @date：2018-06-21 15:59
     */
    //@RequiresPermissions("yhgl")
    @RequestMapping(value = "/insertOne")
    @ResponseBody
    public ResultVO insertOne(@RequestBody SysUserEntity entity)
    {

        try
        {
            entity.validateUserKey();
            String userName = entity.getUserName();
            String password = entity.getPassword();
            String photoUrl = entity.getPhotoUrl();
            String email = entity.getEmail();
            String realName = entity.getRealName();
            String phone = entity.getPhone();
            String status = entity.getStatus();
            String position = entity.getPosition();
            String workNumber = entity.getWorkNumber();
            String remark = entity.getRemark();
            Long operationSubjectId = entity.getOperationSubjectId();
            if (ObjectUtils.isEmpty(userName))
            {
                return ResultVOUtil.paramError("userName");
            }
            else if (ObjectUtils.isEmpty(operationSubjectId))
            {
                return ResultVOUtil.paramError("operationSubjectId");
            }
            else if (ObjectUtils.isEmpty(password))
            {
                return ResultVOUtil.paramError("password");
            }
            else if (photoUrl == null)
            {
                return ResultVOUtil.paramError("photoUrl");
            }
            else if (email == null)
            {
                return ResultVOUtil.paramError("email");
            }
            else if (ObjectUtils.isEmpty(realName))
            {
                return ResultVOUtil.paramError("realName");
            }
            else if (phone == null)
            {
                return ResultVOUtil.paramError("phone");
            }
            else if (ObjectUtils.isEmpty(status))
            {
                return ResultVOUtil.paramError("status");
            }
            else if (position == null)
            {
                return ResultVOUtil.paramError("position");
            }
            else if (ObjectUtils.isEmpty(workNumber))
            {
                return ResultVOUtil.paramError("workNumber");
            }
            else if (remark == null)
            {
                return ResultVOUtil.paramError("remark");
            }
            else if (ObjectUtils.isEmpty(entity.getUserKey()))
            {
                return ResultVOUtil.paramError("userKey");
            }
            else if (ObjectUtils.isEmpty(entity.getSex()))
            {
                return ResultVOUtil.paramError("sex");
            }
            else
            {
                return sysUserService.insertOne(entity);
            }
        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex);
        }
    }

    /**
     * 更新人员信息
     *
     * @param entity the findEntity
     * @return the ResultVO
     * @author：赵亮
     * @date：2018-06-22
     */
    //@RequiresPermissions("yhgl")
    @ResponseBody
    @RequestMapping(value = "/updateOne", method = RequestMethod.POST)
    public ResultVO updateOne(@RequestBody SysUserEntity entity)
    {
        try
        {
            entity.validateUserKey();
            if (ObjectUtils.isNull(entity.getEmail()))
            {
                return ResultVOUtil.paramError("email");
            }
            if (ObjectUtils.isEmpty(entity.getUserId()))
            {
                return ResultVOUtil.paramError("userId");
            }
            if (ObjectUtils.isEmpty(entity.getOrgId()))
            {
                return ResultVOUtil.paramError("orgId");
            }
            if (ObjectUtils.isNull(entity.getPhone()))
            {
                return ResultVOUtil.paramError("phone");
            }
            if (ObjectUtils.isNull(entity.getPhotoUrl()))
            {
                return ResultVOUtil.paramError("photoUrl");
            }
            if (ObjectUtils.isNull(entity.getPosition()))
            {
                return ResultVOUtil.paramError("position");
            }
            if (ObjectUtils.isEmpty(entity.getRealName()))
            {
                return ResultVOUtil.paramError("realName");
            }
            if (ObjectUtils.isNull(entity.getRemark()))
            {
                return ResultVOUtil.paramError("remark");
            }
            if (ObjectUtils.isEmpty(entity.getStatus()))
            {
                return ResultVOUtil.paramError("status");
            }
            if (ObjectUtils.isEmpty(entity.getWorkNumber()))
            {
                return ResultVOUtil.paramError("workNumber");
            }
            if (ObjectUtils.isEmpty(entity.getUserKey()))
            {
                return ResultVOUtil.paramError("userKey");
            }
            if (ObjectUtils.isEmpty(entity.getSex()))
            {
                return ResultVOUtil.paramError("sex");
            }
            return sysUserService.updateOne(entity);
        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex, "更新人员信息出现异常");
        }
    }

    /**
     * 批量跟新人员为无效
     *
     * @param findEntity the findEntity
     * @return the ResultVO
     * @author：赵亮
     * @date：2018-06-25
     */
    //@RequiresPermissions("yhgl")
    @ResponseBody
    @RequestMapping(value = "/batchUpdatePersonStopUse", method = RequestMethod.POST)
    public ResultVO batchUpdatePersonStopUse(@RequestBody UserFindEntity findEntity)
    {
        try
        {
            findEntity.validateUserKey();
            if (ObjectUtils.isEmpty(findEntity.getUserIds()))
            {
                return ResultVOUtil.paramError("userIds");
            }
            return ResultVOUtil.success(sysUserService.batchUpdatePersonStopUse(findEntity));
        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex, "批量跟新人员为无效出现异常");
        }
    }

    /**
     * 批量初始化用户密码
     *
     * @param findEntity the findEntity
     * @return the ResultVO
     * @author：赵亮
     * @date：2018-06-25
     */
    //@RequiresPermissions("yhgl")
    @ResponseBody
    @RequestMapping(value = "/updatePassword", method = RequestMethod.POST)
    public ResultVO updatePassword(@RequestBody UserFindEntity findEntity)
    {
        try
        {
            findEntity.validateUserKey();
            if (ObjectUtils.isEmpty(findEntity.getUserIds()))
            {
                return ResultVOUtil.paramError("userIds");
            }
            return ResultVOUtil.success(sysUserService.updatePassword(findEntity));
        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex, "批量初始化用户密码出现异常");
        }
    }




    /**
     * 人员添加或修改
     *
     * @param request  the request
     * @param response the response
     * @return the json object
     * public ResultVO inserOrUpdateOne(@RequestParam(name = "photoUrl", required = false) CommonsMultipartFile photoUrl
     */
    //@RequiresPermissions("yhgl")
    @ResponseBody
    @RequestMapping(value = "/inserOrUpdateOne", method = RequestMethod.POST)
    public ResultVO inserOrUpdateOne(HttpServletRequest request, HttpServletResponse response)
    {
        try
        {
            CommonsMultipartFile photoUrl = FileUtil.getRequestOneFile(request, "photoUrl");
            SysUserEntity sysUserEntity = DataSwitch.convertHttpServletRequestObjToEntity(SysUserEntity.class, request);
            sysUserEntity.validateUserKey();
            if (ObjectUtils.isEmpty(sysUserEntity.getUserName()))
            {
                return ResultVOUtil.paramError("userName");
            }
            if (ObjectUtils.isEmpty(sysUserEntity.getOperationSubjectId()))
            {
                return ResultVOUtil.paramError("operationSubjectId");
            }

            else if (sysUserEntity.getEmail() == null)
            {
                return ResultVOUtil.paramError("email");
            }
            if (ObjectUtils.isEmpty(sysUserEntity.getRealName()))
            {
                return ResultVOUtil.paramError("realName");
            }
            if (sysUserEntity.getPhone() == null)
            {
                return ResultVOUtil.paramError("phone");
            }
            if (ObjectUtils.isEmpty(sysUserEntity.getStatus()))
            {
                return ResultVOUtil.paramError("status");
            }
            if (sysUserEntity.getPosition() == null)
            {
                return ResultVOUtil.paramError("position");
            }
            if (ObjectUtils.isEmpty(sysUserEntity.getWorkNumber()))
            {
                return ResultVOUtil.paramError("workNumber");
            }
            if (sysUserEntity.getRemark() == null)
            {
                return ResultVOUtil.paramError("remark");
            }
            if (ObjectUtils.isEmpty(sysUserEntity.getUserKey()))
            {
                return ResultVOUtil.paramError("userKey");
            }
            if (ObjectUtils.isEmpty(sysUserEntity.getSex()))
            {
                return ResultVOUtil.paramError("sex");
            }
            if (sysUserEntity.isHavePic() == true && photoUrl != null && photoUrl.getFileItem() != null && photoUrl.getFileItem().getSize() > 0)
            {
                String name = photoUrl.getFileItem().getName();
                String extName = name.substring(name.lastIndexOf(".") + 1);
                byte[] bytes;
                bytes = photoUrl.getBytes(); //将文件转换成字节流形式
                String fileId = FdfsClient.upload(bytes, extName, null);
                System.out.println(FdfsClient.getDownloadServer() + fileId);
                sysUserEntity.setPhotoUrl(fileId);
            }
            response.setStatus(HttpServletResponse.SC_OK);
            if (sysUserEntity.getUserId() == 0)
            {
                return sysUserService.insertOne(sysUserEntity);
            }
            else
            {
                return sysUserService.updateOne(sysUserEntity);
            }
        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex, "上传头像出现异常");
        }
    }

    /**
     * 获取人员基本信息
     *
     * @param entity the findEntity
     * @return the ResultVO
     * @author：赵亮
     * @date：2018-08-09
     */
    @ResponseBody
    @RequestMapping(value = "/getOneUserInfo", method = RequestMethod.POST)
    public ResultVO getOneUserInfo(@RequestBody SysUserEntity entity)
    {
        try
        {
            entity.validateUserKey();
            return ResultVOUtil.success(sysUserService.getOneUserInfo(entity));
        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex, "获取人员基本信息出现异常");
        }
    }

    /**
     * 更新个人信息
     *
     * @param
     * @return the ResultVO
     * @author：赵亮
     * @date：2018-08-09
     */
    @ResponseBody
    @RequestMapping(value = "/updateOnePerson", method = RequestMethod.POST)
    public ResultVO updateOnePerson(HttpServletRequest request, HttpServletResponse response)
    {
        try
        {
            CommonsMultipartFile photoUrl = FileUtil.getRequestOneFile(request, "photoUrl");
            SysUserEntity entity = DataSwitch.convertHttpServletRequestObjToEntity(SysUserEntity.class, request);
            entity.validateUserKey();
            //如果有图片就上传图片
            if (entity.isHavePic() == true && photoUrl.getFileItem() != null && photoUrl.getFileItem().getSize() > 0)
            {
                String name = photoUrl.getFileItem().getName();
                String extName = name.substring(name.lastIndexOf(".") + 1);
                byte[] bytes = null;
                bytes = photoUrl.getBytes(); //将文件转换成字节流形式
                String fileId = FdfsClient.upload(bytes, extName, null);
                System.out.println(FdfsClient.getDownloadServer() + fileId);
                entity.setPhotoUrl(fileId);
            }
            response.setStatus(HttpServletResponse.SC_OK);
            return ResultVOUtil.success(sysUserService.updateOnePerson(entity));
        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex, "更新个人信息出现异常");
        }
    }


    /***
     * 导入用户列表信息 无用户名的导入
     * @return
     */
    ////@RequiresPermissions("yhgl")
    @ResponseBody
    @RequestMapping(value = "/importUserList", method = RequestMethod.POST)
    public ResultVO importUserList(HttpServletRequest request)
    {
        try
        {

            UserFindEntity findEntity= DataSwitch.convertRequestToEntity(UserFindEntity.class,request);
            findEntity.validateUserKey();
            if(ObjectUtils.isEmpty(findEntity.getUserKey())){
                return ResultVOUtil.paramError("userKey");
            }
            CommonsMultipartFile importfileUrl = FileUtil.getRequestOneFile(request, "importfileUrl");
            if (importfileUrl.getFileItem() != null && importfileUrl.getFileItem().getSize() > 0)
            {
                String savePath = FilePath.getImportExcelPath();
                String name = importfileUrl.getFileItem().getName();
                //检查文件后缀格式
                String fileType = FileUtil.getFileType(name);
                String uuid = DataSwitch.getUUID();
                String saveFileName = uuid + fileType;
                savePath = savePath + saveFileName;
                FileUtil.createDirectory(savePath);
                FileUtil.copyInputStreamToFile(importfileUrl.getInputStream(), new File(savePath));
                try
                {
                    List<String[]> userlist = ExcelUtil.readExcel(savePath, 1, 0);

                    StringBuilder messagesb = new StringBuilder();
                    messagesb.append(userlist.get(0)[0].trim());
                    messagesb.append(userlist.get(0)[1].trim());
                    messagesb.append(userlist.get(0)[2].trim());
                    messagesb.append(userlist.get(0)[3].trim());
                    messagesb.append(userlist.get(0)[4].trim());
                    messagesb.append(userlist.get(0)[5].trim());
                    messagesb.append(userlist.get(0)[6].trim());

                    if (!"手机号姓名工号性别部门职位邮箱".equals(messagesb.toString()))
                    {
                        return ResultVOUtil.error(-1, "用户导入模板出现错误");

                    }
                    userlist.remove(0);
                    if (userlist == null || userlist.size() < 1)
                    {
                        return ResultVOUtil.error(-1, "导入数据不能为空");
                    }
                    return sysUserService.importUserList(findEntity.getUserKey(), userlist);
                }
                catch (Exception ex)
                {
                    return ResultVOUtil.error(-1, "用户导入模板出现错误");
                }
            }

            return ResultVOUtil.success(1);
        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex, "导入用户列表信息出现异常");
        }
    }


    /***
     * 导入用户列表信息  有用户名导入
     * @return
     */
    ////@RequiresPermissions("yhgl")
    @ResponseBody
    @RequestMapping(value = "/importUserinfoList", method = RequestMethod.POST)
    public ResultVO importUserinfoList(HttpServletRequest request)
    {
        try
        {

            UserFindEntity findEntity= DataSwitch.convertRequestToEntity(UserFindEntity.class,request);
            findEntity.validateUserKey();
            if(ObjectUtils.isEmpty(findEntity.getUserKey())){
                return ResultVOUtil.paramError("userKey");
            }
            CommonsMultipartFile importfileUrl = FileUtil.getRequestOneFile(request, "importfileUrl");
            if (importfileUrl.getFileItem() != null && importfileUrl.getFileItem().getSize() > 0)
            {
                String savePath = FilePath.getImportExcelPath();
                String name = importfileUrl.getFileItem().getName();
                //检查文件后缀格式
                String fileType = FileUtil.getFileType(name);
                String uuid = DataSwitch.getUUID();
                String saveFileName = uuid + fileType;
                savePath = savePath + saveFileName;
                FileUtil.createDirectory(savePath);
                FileUtil.copyInputStreamToFile(importfileUrl.getInputStream(), new File(savePath));
                try
                {
                    List<String[]> userlist = ExcelUtil.readExcel(savePath, 1, 0);

                    StringBuilder messagesb = new StringBuilder();
                    messagesb.append(userlist.get(0)[0].trim());
                    messagesb.append(userlist.get(0)[1].trim());
                    messagesb.append(userlist.get(0)[2].trim());
                    messagesb.append(userlist.get(0)[3].trim());
                    messagesb.append(userlist.get(0)[4].trim());
                    messagesb.append(userlist.get(0)[5].trim());
                    messagesb.append(userlist.get(0)[6].trim());
                    messagesb.append(userlist.get(0)[7].trim());
                    if (!"姓名性别用户名工号部门职位手机号邮箱".equals(messagesb.toString()))
                    {
                        return ResultVOUtil.error(-1, "用户导入模板出现错误");

                    }
                    userlist.remove(0);
                    if (userlist == null || userlist.size() < 1)
                    {
                        return ResultVOUtil.error(-1, "导入数据不能为空");
                    }
                    return sysUserService.importUserinfoList(findEntity.getUserKey(), userlist);
                }
                catch (Exception ex)
                {
                    return ResultVOUtil.error(-1, "用户导入模板出现错误");
                }
            }

            return ResultVOUtil.success(1);
        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex, "导入用户列表信息出现异常");
        }
    }

    /***
     * 根据查询条件导出用户信息
     * @return
     */
    @RequestMapping(value = "/userExport")
    @ResponseBody
    public ResultVO userExport(HttpServletRequest request, HttpServletResponse response)
    {
        try
        {
            UserFindEntity findEntity = DataSwitch.convertRequestToEntity(UserFindEntity.class, request);
            findEntity.validateUserKey();
                List<Map<String, Object>> listmap = sysUserService.getAllUserLisMap(findEntity);
                String path = ExcelUtil.exportExcel("userListExport", listmap);
                return FileDownLoad.download(response, path, "用户导出.xls");
        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex);
        }
    }

    /**
     * 手机端获取用户信息
     *
     * @param findEntity the findEntity
     * @return the ResultVO
     * @author：赵亮
     * @date：2018-09-10
     */
    @ResponseBody
    @RequestMapping(value = "/getUserInfoForPhone", method = RequestMethod.POST)
    public ResultVO getUserInfoForPhone(@RequestBody BaseFindEntity findEntity)
    {
        try
        {
            findEntity.validateUserKey();
            return ResultVOUtil.success(sysUserService.getUserInfoForPhone(findEntity));
        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex, "手机端获取用户信息出现异常");
        }
    }


}
