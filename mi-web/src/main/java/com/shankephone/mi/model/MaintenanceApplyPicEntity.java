package com.shankephone.mi.model;

import com.shankephone.mi.common.model.BaseModel;
import lombok.Data;

import java.util.Date;

/**
 * 维修申请图片
 *
 * @author 司徒彬
 * @date 2018/8/6 10:21
 */
@Data
public class MaintenanceApplyPicEntity extends BaseModel {
    private Long mainApplyPicId;
    private Long maintenanceApplyId;
    private String picUrl;
    private String type;
    private String createUser;
    private Date createTime;
}
