package com.shankephone.mi.common.model;


import lombok.Data;

/**
 * ${description}
 *
 * @author 司徒彬
 * @date 2017-11-24 11:56
 */
@Data
public class SessionEntity {
    private Long timeStamp;
    private Object value;
    private Boolean deleteWhenExpire = true;
    private Long overTime;

    public static SessionEntity getInstance(Object value, boolean deleteWhenExpire) {
        SessionEntity sessionEntity = new SessionEntity();
        sessionEntity.setTimeStamp(System.currentTimeMillis());
        sessionEntity.setValue(value);
        sessionEntity.setDeleteWhenExpire(deleteWhenExpire);
        return sessionEntity;
    }

    public static SessionEntity getInstance(Object value, Long overTime, boolean deleteWhenExpire) {
        SessionEntity sessionEntity = new SessionEntity();
        sessionEntity.setTimeStamp(System.currentTimeMillis());
        sessionEntity.setValue(value);
        sessionEntity.setDeleteWhenExpire(deleteWhenExpire);
        sessionEntity.setOverTime(overTime);
        return sessionEntity;
    }

}
