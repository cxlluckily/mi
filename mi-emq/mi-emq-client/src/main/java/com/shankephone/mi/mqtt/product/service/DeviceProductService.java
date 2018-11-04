package com.shankephone.mi.mqtt.product.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.shankephone.mi.mqtt.enumeration.DeviceStatusEnum;
import com.shankephone.mi.mqtt.enumeration.MessageTypeEnum;
import com.shankephone.mi.mqtt.enumeration.NeedReplyEnum;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author 赵亮
 * @date 2018-10-15 18:09
 */
@Service
public class DeviceProductService
{
    /**
     * 设备注册返回的JSON
     *
     * @author：赵亮
     * @date：2018-10-18 17:21
     */
    public String onLineRegist(String deviceuId)
    {
        JsonObject jsonObject = new JsonObject();

        JsonObject header = new JsonObject();
        header.addProperty("createTime", new Date().getTime());
        header.addProperty("messageType", MessageTypeEnum.REGIST.getCode());
        header.addProperty("wasNeedReply", NeedReplyEnum.YES.getCode());
        jsonObject.add("header", header);

        JsonObject body = new JsonObject();
        JsonObject registMainInfo = new JsonObject();
        registMainInfo.addProperty("deviceUid", deviceuId);
        registMainInfo.addProperty("deviceCode", deviceuId);
        registMainInfo.addProperty("stationCode", "0102stationCode");
        registMainInfo.addProperty("deviceStatus", DeviceStatusEnum.INSERVICE.getCode());
        body.add("registMainInfo", registMainInfo);

        JsonObject deviceDescription = new JsonObject();
        JsonObject identifier = new JsonObject();
        identifier.addProperty("HWID", "FDSFDSFDSFXDFsdrfe2432fessdfds");
        deviceDescription.add("identifier", identifier);
        JsonObject location = new JsonObject();
        location.addProperty("cityCode", "01");
        location.addProperty("lineCode", "01");
        location.addProperty("stationCode", "02");
        location.addProperty("localPosition", "西北角");
        location.addProperty("GPSLocation", "90 63 62 15");
        deviceDescription.add("location", location);
        JsonObject config = new JsonObject();
        config.addProperty("deviceCode", "设备编号");
        config.addProperty("deviceType", "设备类型");
        config.addProperty("deviceSubType", "设备子类型");
        config.add("deviceSettings", null);
        deviceDescription.add("config", config);
        JsonObject HWComponent = new JsonObject();
        HWComponent.addProperty("HWName", "英文缩写名称");
        HWComponent.addProperty("HWID", "硬件编号");
        HWComponent.addProperty("HWStatus", "是否安装，是否正常");
        HWComponent.addProperty("HWStatusCode", "预留");
        HWComponent.addProperty("HWSettings", "预留");
        HWComponent.add("subComponentList", null);
        deviceDescription.add("HWComponent", HWComponent);
        JsonObject SWComponent = new JsonObject();
        SWComponent.addProperty("SWName", "英文缩写名称");
        SWComponent.addProperty("SWID", "组件编号");
        SWComponent.addProperty("SWType", "英文缩写类型");
        SWComponent.addProperty("basePath", "组件安装路径");
        JsonObject componentFile = new JsonObject();
        componentFile.addProperty("fileCategory", "文件类型");
        componentFile.addProperty("filePath", "文件所在路径");
        componentFile.addProperty("fileName", "文件名称");
        componentFile.addProperty("fileFormat", "文件格式");
        componentFile.addProperty("fileVersionNumber", "文件版本号");
        componentFile.addProperty("fileSize", "文件大小(Byte)");
        componentFile.addProperty("fileDigest", "文件摘要(MD5)");
        componentFile.addProperty("fileLastTime", "文件最后更新时间");
        SWComponent.add("componentFile", componentFile);
        JsonArray componentFileList = new JsonArray();
        JsonObject componentOneFile = new JsonObject();
        componentOneFile.addProperty("fileCategory", "文件类型");
        componentOneFile.addProperty("filePath", "文件所在路径");
        componentOneFile.addProperty("fileName", "文件名称");
        componentOneFile.addProperty("fileFormat", "文件格式");
        componentOneFile.addProperty("fileVersionNumber", "文件版本号");
        componentOneFile.addProperty("fileSize", "文件大小(Byte)");
        componentOneFile.addProperty("fileDigest", "文件摘要(MD5)");
        componentOneFile.addProperty("fileLastTime", "文件最后更新时间");
        componentFileList.add(componentOneFile);
        SWComponent.add("componentFileList", componentFileList);
        deviceDescription.add("SWComponent", SWComponent);
        body.add("deviceDescription", deviceDescription);

        jsonObject.add("body", body);
        return jsonObject.toString();
    }

    public String onLineRegist(String deviceuId,String deviceCode,String stationCode,String deviceStatus)
    {
        JsonObject jsonObject = new JsonObject();

        JsonObject header = new JsonObject();
        header.addProperty("createTime", new Date().getTime());
        header.addProperty("messageType", MessageTypeEnum.REGIST.getCode());
        header.addProperty("wasNeedReply", NeedReplyEnum.YES.getCode());
        jsonObject.add("header", header);

        JsonObject body = new JsonObject();
        JsonObject registMainInfo = new JsonObject();
        registMainInfo.addProperty("deviceUid", deviceuId);
        registMainInfo.addProperty("deviceCode", deviceCode);
        registMainInfo.addProperty("stationCode", stationCode);
        registMainInfo.addProperty("deviceStatus", deviceStatus);
        body.add("registMainInfo", registMainInfo);

        JsonObject deviceDescription = new JsonObject();
        JsonObject identifier = new JsonObject();
        identifier.addProperty("HWID", "FDSFDSFDSFXDFsdrfe2432fessdfds");
        deviceDescription.add("identifier", identifier);
        JsonObject location = new JsonObject();
        location.addProperty("cityCode", "01");
        location.addProperty("lineCode", "01");
        location.addProperty("stationCode", "02");
        location.addProperty("localPosition", "西北角");
        location.addProperty("GPSLocation", "90 63 62 15");
        deviceDescription.add("location", location);
        JsonObject config = new JsonObject();
        config.addProperty("deviceCode", "设备编号");
        config.addProperty("deviceType", "设备类型");
        config.addProperty("deviceSubType", "设备子类型");
        config.add("deviceSettings", null);
        deviceDescription.add("config", config);
        JsonObject HWComponent = new JsonObject();
        HWComponent.addProperty("HWName", "英文缩写名称");
        HWComponent.addProperty("HWID", "硬件编号");
        HWComponent.addProperty("HWStatus", "是否安装，是否正常");
        HWComponent.addProperty("HWStatusCode", "预留");
        HWComponent.addProperty("HWSettings", "预留");
        HWComponent.add("subComponentList", null);
        deviceDescription.add("HWComponent", HWComponent);
        JsonObject SWComponent = new JsonObject();
        SWComponent.addProperty("SWName", "英文缩写名称");
        SWComponent.addProperty("SWID", "组件编号");
        SWComponent.addProperty("SWType", "英文缩写类型");
        SWComponent.addProperty("basePath", "组件安装路径");
        JsonObject componentFile = new JsonObject();
        componentFile.addProperty("fileCategory", "文件类型");
        componentFile.addProperty("filePath", "文件所在路径");
        componentFile.addProperty("fileName", "文件名称");
        componentFile.addProperty("fileFormat", "文件格式");
        componentFile.addProperty("fileVersionNumber", "文件版本号");
        componentFile.addProperty("fileSize", "文件大小(Byte)");
        componentFile.addProperty("fileDigest", "文件摘要(MD5)");
        componentFile.addProperty("fileLastTime", "文件最后更新时间");
        SWComponent.add("componentFile", componentFile);
        JsonArray componentFileList = new JsonArray();
        JsonObject componentOneFile = new JsonObject();
        componentOneFile.addProperty("fileCategory", "文件类型");
        componentOneFile.addProperty("filePath", "文件所在路径");
        componentOneFile.addProperty("fileName", "文件名称");
        componentOneFile.addProperty("fileFormat", "文件格式");
        componentOneFile.addProperty("fileVersionNumber", "文件版本号");
        componentOneFile.addProperty("fileSize", "文件大小(Byte)");
        componentOneFile.addProperty("fileDigest", "文件摘要(MD5)");
        componentOneFile.addProperty("fileLastTime", "文件最后更新时间");
        componentFileList.add(componentOneFile);
        SWComponent.add("componentFileList", componentFileList);
        deviceDescription.add("SWComponent", SWComponent);
        body.add("deviceDescription", deviceDescription);

        jsonObject.add("body", body);
        return jsonObject.toString();
    }

    /**
     * 设备状态变更
     *
     * @author：赵亮
     * @date：2018-10-18 17:21
     */
    public String deviceStatusChange(String deviceuId, String deviceStatus)
    {
        JsonObject jsonObject = new JsonObject();

        JsonObject header = new JsonObject();
        header.addProperty("createTime", new Date().getTime());
        header.addProperty("messageType", MessageTypeEnum.STATUS_CHANGE.getCode());
        header.addProperty("wasNeedReply", NeedReplyEnum.NO.getCode());
        jsonObject.add("header", header);
        JsonObject body = new JsonObject();
        body.addProperty("deviceUid", deviceuId);
        body.addProperty("deviceStatus", deviceStatus);
        jsonObject.add("body", body);
        return jsonObject.toString();
    }

    public String deviceCommandExecuteFinished(String messageSessionId)
    {
        JsonObject jsonObject = new JsonObject();

        JsonObject header = new JsonObject();
        header.addProperty("createTime", new Date().getTime());
        header.addProperty("messageType", MessageTypeEnum.EXECUTE_COMMAND_RESULT.getCode());
        header.addProperty("wasNeedReply", NeedReplyEnum.NO.getCode());
        jsonObject.add("header", header);
        JsonObject body = new JsonObject();
        body.addProperty("messageSessionId", messageSessionId);
        JsonObject runResult = new JsonObject();
        runResult.addProperty("commandName", "具备预定义的值");
        runResult.addProperty("commandType", "预留，填写空串");
        JsonObject commandFile = new JsonObject();
        commandFile.addProperty("fileCategory", "文件类型");
        commandFile.addProperty("filePath", "文件所在路径");
        commandFile.addProperty("fileName", "文件名称");
        commandFile.addProperty("fileFormat", "文件格式");
        commandFile.addProperty("fileVersionNumber", "文件版本号");
        commandFile.addProperty("fileSize", "文件大小(Byte)");
        commandFile.addProperty("fileDigest", "文件摘要(MD5)");
        commandFile.addProperty("fileLastTime", "文件最后更新时间");
        runResult.add("commandFile", commandFile);
        runResult.addProperty("executedCommand", "执行脚本的内容");
        runResult.addProperty("commandExecuteCode", "脚本执行返回值");
        body.add("runResult", runResult);
        jsonObject.add("body", body);
        return jsonObject.toString();
    }

    public String deviceCommandExecuteReceive(String messageSessionId)
    {
        JsonObject jsonObject = new JsonObject();

        JsonObject header = new JsonObject();
        header.addProperty("createTime", new Date().getTime());
        header.addProperty("messageType", MessageTypeEnum.COMMAND_SERVER_RECEIVED.getCode());
        header.addProperty("wasNeedReply", NeedReplyEnum.NO.getCode());
        jsonObject.add("header", header);
        JsonObject body = new JsonObject();
        body.addProperty("messageSessionId", messageSessionId);
        jsonObject.add("body", body);
        return jsonObject.toString();
    }
}
