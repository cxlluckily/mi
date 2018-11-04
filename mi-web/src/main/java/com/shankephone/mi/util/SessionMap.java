package com.shankephone.mi.util;


import com.shankephone.mi.common.model.SessionEntity;
import com.shankephone.mi.security.model.UserLoginInfo;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledExecutorService;

/**
 * 内存数据存储Map
 *
 * @author 司徒彬
 * @date 2017 -04-11 22:00
 */
@Component
public class SessionMap
{
    private static final Map<String, SessionEntity> paraMap = new ConcurrentHashMap<>();

    private static final long overtime = 1800000L;
    private ScheduledExecutorService scheduledExecutorService;

    /*static
    {
        UserLoginInfo userLoginInfo = new UserLoginInfo();
        userLoginInfo.setUserId(1L);
        userLoginInfo.setPassword("1");
        userLoginInfo.setUserName("修改人");
        userLoginInfo.setOperationSubjectId(1L);
        userLoginInfo.setUserType(UserTypeEnum.USER);
        //测试的 userKey 是 05dfac17756ce7c02dc1d355caed2eb5
        System.out.println("==========================================测试的userKey是:" + SessionMap.putUserInfo(userLoginInfo));
        UserLoginInfo entity = new UserLoginInfo();
        entity.setUserName("赵亮");
        entity.setUserId(1l);
        entity.setUserType(UserTypeEnum.SUPER_ADMIN);
        System.out.println("==========================================测试的userKey是222:" + SessionMap.putUserInfo(entity));
    }*/


    /**
     * Gets keys.
     *
     * @return the keys
     */
    public static Set<String> getKeys()
    {
        return paraMap.keySet();
    }

    /**
     * Get object.
     *
     * @param key the key
     * @return the object
     * @throws Exception the exception
     */
    public static Object get(String key)
    {
        SessionEntity sessionEntity = getSessionEntity(key);
        if (sessionEntity != null)
        {
            Object object = sessionEntity.getValue();
            Boolean deleteWhenExpire = sessionEntity.getDeleteWhenExpire();
            Long overTime = sessionEntity.getOverTime();
            put(key, object, overTime, deleteWhenExpire);
            Object result = BeanUtils.deepCopy(object);
            return result;
        }
        else
        {
            return null;
        }
    }

    /**
     * Gets session entity.
     *
     * @param key the key
     * @return the session entity
     */
    private static SessionEntity getSessionEntity(String key)
    {
        SessionMap.clearOverTimeSession();
        if (containsKey(key))
        {
            SessionEntity sessionEntity = paraMap.get(key);
            sessionEntity.setTimeStamp(System.currentTimeMillis());
            return sessionEntity;
        }
        else
        {
            return null;
        }
    }

    private static final String USER_KEY = "USER_";

    /**
     * Gets user info.
     *
     * @param userKey the user key 登陆时，生成的对应改用户的UUID
     * @return the user info
     */
    public static UserLoginInfo getUserInfo(String userKey)
    {
        return SessionMap.getValue(USER_KEY + userKey);
    }

    /**
     * Gets user info.
     *
     * @param userId   the user id
     * @param userType the user type
     * @return the user info
     */
    public static UserLoginInfo getUserInfo(Long userId, String userType)
    {
        String userKey = getUserKey(userId, userType);
        return getUserInfo(userKey);
    }


    /**
     * Put user info.
     *
     * @param userLoginInfo the user login info
     * @return the string
     */
    public static String putUserInfo(UserLoginInfo userLoginInfo, Long overtime)
    {
        String userKey = getUserKey(userLoginInfo.getUserId(), userLoginInfo.getUserType().getValue());
        SessionMap.put(USER_KEY + userKey, userLoginInfo, overtime);
        return userKey;
    }

    public static String putUserInfo(UserLoginInfo userLoginInfo)
    {
        String userKey = getUserKey(userLoginInfo.getUserId(), userLoginInfo.getUserType().getValue());
        SessionMap.put(USER_KEY + userKey, userLoginInfo, false);
        return userKey;
    }

    //    public static String putSuperAdminUserInfo(SysAdminEntity entity)
    //    {
    //        String userKey = getUserKey(entity.getAdminId(), "sysAdmin");
    //        SessionMap.put(USER_KEY + userKey, entity, false);
    //        return userKey;
    //    }

    private final static String PRIVATE_KEY = PropertyAccessor.getProperty("privateKey");

    private static String getUserKey(Long userId, String userType)
    {
        String userKey = MessageDigestUtils.md5(userId + userType + PRIVATE_KEY);
        return userKey;
    }

    /**
     * Remove user info.
     *
     * @param userKey the user key
     */
    public static void removeUserInfo(String userKey)
    {
        SessionMap.remove(USER_KEY + userKey);
    }


    /**
     * Gets string.
     *
     * @param key the key
     * @return the string
     * @throws Exception the exception
     */
    public static String getString(String key)
    {
        Object value = get(key);
        return DataSwitch.convertObjectToString(value);
    }


    /**
     * Gets value.
     *
     * @param <T> the type parameter
     * @param key the key
     * @return the value
     * @throws Exception the exception
     */
    public static <T> T getValue(String key)
    {
        Object value = get(key);
        return value == null ? null : (T) value;
    }


    /**
     * Put.
     *
     * @param key   the key
     * @param value the value
     */
    public static void put(String key, Object value)
    {
        put(key, value, true);

    }

    /**
     * Put.
     *
     * @param key      the key
     * @param value    the value
     * @param overtime the overtime
     */
    public static void put(String key, Object value, Long overtime)
    {
        put(key, value, overtime, true);

    }

    /**
     * Put.
     *
     * @param key              the key
     * @param value            the value
     * @param deleteWhenExpire the delete when expire
     */
    public static void put(String key, Object value, boolean deleteWhenExpire)
    {
        put(key, value, overtime, deleteWhenExpire);
    }

    /**
     * Put.
     *
     * @param key              the key
     * @param value            the value
     * @param overtime         the overtime
     * @param deleteWhenExpire the delete when expire
     */
    private static void put(String key, Object value, Long overtime, boolean deleteWhenExpire)
    {
        SessionMap.clearOverTimeSession();
        Object result = BeanUtils.deepCopy(value);
        if (key != null && value != null)
        {
            SessionEntity sessionEntity = SessionEntity.getInstance(result, overtime, deleteWhenExpire);
            paraMap.put(key, sessionEntity);
        }
        else
        {
            paraMap.remove(key);
        }
    }

    /**
     * Contains key boolean.
     *
     * @param key the key
     * @return the boolean
     */
    public static boolean containsKey(String key)
    {
        return paraMap.keySet().stream().filter(str -> str.equalsIgnoreCase(key)).count() > 0;
    }

    public static boolean checkUserKeyIsHave(String key)
    {
        return paraMap.keySet().stream().filter(str -> str.equalsIgnoreCase(USER_KEY + key)).count() > 0;
    }

    /**
     * Remove.
     *
     * @param key the key
     */
    public static void remove(String key)
    {
        paraMap.remove(key);
    }


    /**
     * Gets overtime keys.
     *
     * @return the overtime keys
     */
    public static void clearOverTimeSession()
    {
        long now = System.currentTimeMillis();
        paraMap.entrySet().stream().forEach(entry -> {
            String key = entry.getKey();
            SessionEntity sessionEntity = entry.getValue();
            boolean isNull = sessionEntity == null;
            if (!isNull)
            {
                boolean isOverTime = now - sessionEntity.getTimeStamp() > sessionEntity.getOverTime();
                boolean deleteWhenExpire = sessionEntity.getDeleteWhenExpire();
                if (isOverTime && deleteWhenExpire)
                {
                    paraMap.remove(key);
                }
            }

        });
    }
}


