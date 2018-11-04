package com.shankephone.mi.model;

import lombok.Data;

import java.util.Date;


/**
 * operations_equipment  Bean
 *
 * @author 系统生成
 * @date 2018-10-17 10:30:18
 */
@Data
public class OperationsEquipmentEntity
{
	
    /**
     * 运营设备ID
    **/
    private Long equipmentId;
    /**
     * 运营主体ID
    **/
    private Long operationSubjectId;
    /**
     * 所属工区
    **/
    private Long workSectionId;
    /**
     * 线路ID
    **/
    private Long lineId;
    /**
     * 所属站点
    **/
    private Long stationId;
    /**
     * 备件ID
    **/
    private Long sparePartId;
    /**
     * 设备编号
    **/
    private String equipmentNo;
    /**
     * 序列号
    **/
    private String serialNumber;
    /**
     * 唯一标识
    **/
    private String uniquelyIdentifies;
    /**
     * 设备状态
    **/
    private String status;
    /**
     * 所在位置
    **/
    private String location;
    /**
     * 备注
    **/
    private String remark;
    /**
     * 添加人
    **/
    private String createUser;
    /**
     * 创建时间
    **/
    private Date createTime;
    /**
     * 修改人
    **/
    private String modifyUser;
    /**
     * 修改时间
    **/
    private Date modifyTime;
    /**
     * 二维码
    **/
    private String qrCode;
    /**
     * 设备唯一ID(注册)
    **/
    private String deviceuId;
    /**
     * 设备编号(注册)
    **/
    private String deviceCode;
    /**
     * 设备所在车站代码(注册)
    **/
    private String stationCode;
    /**
     * 文件服务器ip
    **/
    private String fileServerIP;
    /**
     * 文件服务器用户名
    **/
    private String fileServerUserName;
    /**
     * 文件服务器密码
    **/
    private String fileServerPassword;
    /**
     * 服务器返回的认证Token
    **/
    private String token;

    /**
     *设备运行状态
     *@author：赵亮
     *@date：2018-10-23 17:08
    */
    private String deviceStatus;
   
}

