package com.shankephone.mi.sys.service.impl;

import com.shankephone.fdfs.FdfsClient;
import com.shankephone.mi.model.SysUserEntity;
import com.shankephone.mi.security.shiro.CustomShiroRealm;
import com.shankephone.mi.sys.dao.PersonalInformationDao;
import com.shankephone.mi.sys.service.PersonalInformationService;
import com.shankephone.mi.sys.vo.UserInfoVO;
import com.shankephone.mi.util.DataSwitch;
import com.shankephone.mi.util.PathSetting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.IOException;
import java.util.Map;

/**
 * 个人信息service
 *
 * @author 司徒彬
 * @date 2018 /6/28 10:07
 */
@Service
public class PersonalInformationServiceImpl implements PersonalInformationService {

    @Autowired
    private PersonalInformationDao personalInformationDao;

    @Override
    public Map<String, Object> gerPersonalInfo(Long userId) {
        try {
            Map<String, Object> user = personalInformationDao.gerPersonalInfo(userId);
            String photoUrl = PathSetting.getUserHeadPicUrl(DataSwitch.convertObjectToString(user.get("photoUrl")));
            user.put("photoUrl", photoUrl);
            return user;
        } catch (Exception ex) {
            throw ex;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updatePersonalInfo(CommonsMultipartFile photo, SysUserEntity user) throws IOException {
        try {
        	if (photo.getFileItem() != null && photo.getFileItem().getSize()>0)
            {
            	String name = photo.getFileItem().getName();
            	String extName = name.substring(name.lastIndexOf(".") + 1);
            	byte[] bytes = null;
                bytes = photo.getBytes(); //将文件转换成字节流形式
            	String fileId = FdfsClient.upload(bytes, extName, null);
        		System.out.println(FdfsClient.getDownloadServer() + fileId);
        		user.setPhotoUrl(fileId);
            }
        	
            /*if (photo.getFileItem() != null) {
                String name = photo.getFileItem().getName();
                String fileType = FileUtil.getFileType(name);
                String uuid = DataSwitch.getUUID();
                String saveFileName = uuid + fileType;
                String savePath = PathSetting.getUserHeadPicUrl(saveFileName);
                FileUtil.copyInputStreamToFile(photo.getInputStream(), new File(savePath));
                user.setPhotoUrl(saveFileName);
            }*/
            personalInformationDao.updatePersonalInfo(user);
        } catch (Exception ex) {
            throw ex;
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean modifyPassword(UserInfoVO userEntity) {
        try {
            Long operationUserId = userEntity.getOperationUserId();
            SysUserEntity user = personalInformationDao.getUserInfo(operationUserId);
            if (user.getPassword().equals(CustomShiroRealm.encrypt(userEntity.getOldPassword()))) {
                userEntity.setNewPassword(CustomShiroRealm.encrypt(userEntity.getNewPassword()));
                return personalInformationDao.modifyPassword(userEntity);
            } else {
                return false;
            }
        } catch (Exception ex) {
            throw ex;
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean modifyPasswordbyphone(UserInfoVO userEntity) {
        try {

            userEntity.setNewPassword(CustomShiroRealm.encrypt(userEntity.getNewPassword()));
            return personalInformationDao.modifyPassword(userEntity);

        } catch (Exception ex) {
            throw ex;
        }
    }
}
