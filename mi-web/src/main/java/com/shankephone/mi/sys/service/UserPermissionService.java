package com.shankephone.mi.sys.service;

import com.shankephone.mi.sys.formbean.UserPermissionFindEntity;
import com.shankephone.mi.sys.vo.UserPermissionVo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 用户授权服务类
 *
 * @author 司徒彬
 * @date 2018 /6/27 10:38
 */
@Service
public interface UserPermissionService  {


    /**
     * Gets init page info for batch authorization.
     *
     * @param findEntity the find entity
     * @return the init page info for batch authorization
     */
    Map<String,Object> getInitPageInfoForBatchAuthorization(UserPermissionFindEntity findEntity);

    /**
     * Insert batch authorization info.
     *
     * @param userPermissionVo the user permission vo
     */
    void insertBatchAuthorizationInfo(UserPermissionVo userPermissionVo);

    /**
     * Gets user authorization info by user id.
     *
     * @param findEntity the find entity
     * @return the user authorization info by user id
     */
    Map<String,Object> getUserAuthorizationInfoByUserId(UserPermissionFindEntity findEntity);

    /**
     * Authorization by user id.
     *
     * @param userPermissionVo the user permission vo
     */
    void authorizationByUserId(UserPermissionVo userPermissionVo);

	Map<String, List<Long>> getAuthorizationInfoByUserId(Long userId);

	List<Long> getUserWarehouseRangeIds(Long userId);
}