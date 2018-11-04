package com.shankephone.mi.onlineequipment.dao.provider;


import com.shankephone.mi.model.OperationsEquipmentEntity;

/**
 * @author 郝伟州
 * @date 2018/8/23 10:45
 */
public class OnlinequipmentProvider
{

    /**
     * @author 郝伟州
     * @date 2018/8/23 10:45
     */
    public String getOnlinequipmentList(OperationsEquipmentEntity entity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" select equipmentId, ");
        sbSql.append("   IFNULL(qrcode,'') qrCode, ");
        sbSql.append("   equipmentNo, ");
        sbSql.append("   serialNumber, ");
        sbSql.append("   uniquelyIdentifies, ");
        sbSql.append("   equipmentNo  ");
        sbSql.append("  from  operations_equipment ");
        sbSql.append(" WHERE ");
        sbSql.append("   stationId = #{stationId} ");
        return sbSql.toString();
    }


    public String updateOnlineequipment(OperationsEquipmentEntity entity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" UPDATE ");
        sbSql.append("   operations_equipment ");
        sbSql.append(" SET ");
        sbSql.append("   qrCode = #{qrCode} ");

        sbSql.append(" WHERE ");
        sbSql.append("   equipmentId = #{equipmentId} ");
        return sbSql.toString();
    }

    public String getOnlinequipmentDetail(OperationsEquipmentEntity entity)
    {
        return "select * from operations_equipment where equipmentId = #{equipmentId}";
    }


}
