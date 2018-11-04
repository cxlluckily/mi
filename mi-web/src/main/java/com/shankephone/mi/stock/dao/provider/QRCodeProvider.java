package com.shankephone.mi.stock.dao.provider;

import com.shankephone.mi.common.enumeration.StatusEnum;
import com.shankephone.mi.model.StockQrCodeEntity;
import com.shankephone.mi.stock.formbean.QRCodeFindEntity;
import com.shankephone.mi.util.StringUtils;

/**
 * @author 赵亮
 * @date 2018-07-24 10:09
 */
public class QRCodeProvider
{
    public String inserOne(StockQrCodeEntity entity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" INSERT INTO ");
        sbSql.append("   stock_qr_code ");
        sbSql.append("   ( ");
        sbSql.append("      qrCode, ");
        sbSql.append("      status, ");
        sbSql.append("      operationSubjectId, ");
        sbSql.append("      createUser, ");
        sbSql.append("      modifyUser ");
        sbSql.append("   ) ");
        sbSql.append(" VALUES ");
        sbSql.append("   ( ");
        sbSql.append("      #{qrCode}, ");
        sbSql.append("      #{status}, ");
        sbSql.append("      #{operationSubjectId}, ");
        sbSql.append("      #{operationUserName}, ");
        sbSql.append("      #{operationUserName} ");
        sbSql.append("   ) ");
        return sbSql.toString();

    }

    public String getQRCodeList(QRCodeFindEntity findEntity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT ");
        sbSql.append("   stock_qr_code.*, ");
        sbSql.append("   materiaCoding,categoryName, ");
        sbSql.append("   specificationModel, ");
        sbSql.append("   part_spare_part.partName, ");
        sbSql.append("   CASE stock_qr_code.status ");
        sbSql.append("   WHEN '" + StatusEnum.START_USING.getValue() + "' ");
        sbSql.append("     THEN '可用' ");
        sbSql.append("   ELSE '不可用' END AS statusText,  DATE_FORMAT(stock_qr_code.createTime, '%Y-%m-%d') AS insertDate ");
        sbSql.append(" FROM stock_qr_code ");
        sbSql.append("   LEFT JOIN part_spare_part ");
        sbSql.append("     ON part_spare_part.sparePartId = stock_qr_code.sparePartId ");
        sbSql.append("   LEFT JOIN part_spare_part_type ");
        sbSql.append("     ON part_spare_part.sparePartTypeId = part_spare_part_type.sparePartTypeId ");
        sbSql.append(" WHERE 1 = 1 ");
        if (!"all".equals(findEntity.getStatus()) )
        {
            sbSql.append("       AND stock_qr_code.status = #{status} ");
        }
        if(StringUtils.isNotEmpty(findEntity.getBeginTime()))
        {
            sbSql.append("       AND stock_qr_code.createTime BETWEEN #{beginTime} AND #{endTime} ");
        }
        if (StringUtils.isNotEmpty(findEntity.getQrCode()))
        {
            sbSql.append("       AND ifnull(stock_qr_code.qrCode, '') LIKE concat('%', #{qrCode}, '%') ");
        }
        if (StringUtils.isNotEmpty(findEntity.getPartName()))
        {
            sbSql.append("       AND ifnull(part_spare_part.partName, '') LIKE concat('%', #{partName}, '%') ");
        }
        sbSql.append("       AND stock_qr_code.operationSubjectId = #{operationSubjectId} ");
        sbSql.append(" ORDER BY stock_qr_code.status ASC,stock_qr_code.qrCodeId desc ");
        sbSql.append("   LIMIT #{start}, #{limit} ");
        
        System.out.println(findEntity.getStart() + ", " + findEntity.getLimit()); 
        return sbSql.toString();

    }

    public String getQRCodeListCount(QRCodeFindEntity findEntity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT count(*) AS totalCount ");
        sbSql.append(" FROM stock_qr_code ");
        sbSql.append("   LEFT JOIN part_spare_part ");
        sbSql.append("     ON part_spare_part.sparePartId = stock_qr_code.sparePartId ");
        sbSql.append("   LEFT JOIN part_spare_part_type ");
        sbSql.append("     ON part_spare_part.sparePartTypeId = part_spare_part_type.sparePartTypeId ");
        sbSql.append(" WHERE 1 = 1 ");
        if (!"all".equals(findEntity.getStatus()) )
        {
            sbSql.append("       AND stock_qr_code.status = #{status} ");
        }
        if(StringUtils.isNotEmpty(findEntity.getBeginTime()))
        {
            sbSql.append("       AND stock_qr_code.createTime BETWEEN #{beginTime} AND #{endTime} ");
        }
        if (StringUtils.isNotEmpty(findEntity.getQrCode()))
        {
            sbSql.append("       AND ifnull(stock_qr_code.qrCode, '') LIKE concat('%', #{qrCode}, '%') ");
        }
        if (StringUtils.isNotEmpty(findEntity.getPartName()))
        {
            sbSql.append("       AND ifnull(part_spare_part.partName, '') LIKE concat('%', #{partName}, '%') ");
        }
        sbSql.append("       AND stock_qr_code.operationSubjectId = #{operationSubjectId} ");
        return sbSql.toString();

    }

    public String batchDelete(QRCodeFindEntity findEntity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" DELETE FROM ");
        sbSql.append("   stock_qr_code ");
        sbSql.append(" WHERE qrCodeId IN (" + findEntity.getQrCodeIds() + ") ");
        return sbSql.toString();
    }
    public String getQrCodeByQrCodeEntity(QRCodeFindEntity findEntity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT * ");
        sbSql.append(" FROM stock_qr_code ");
        sbSql.append(" WHERE qrCode = #{qrCode} ");
        if(StringUtils.isNotEmpty(findEntity.getOperationUserId()))
        {
            sbSql.append(" AND operationSubjectId = #{operationSubjectId} ");
        }
        if(StringUtils.isNotEmpty(findEntity.getStatus()))
        {
            sbSql.append(" AND status = #{status} ");
        }
        return sbSql.toString();

    }
    public String getQrCodeByQrCode(String qrCode)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT * ");
        sbSql.append(" FROM stock_qr_code ");
        sbSql.append(" WHERE qrCode = #{qrCode} ");

        return sbSql.toString();

    }

    public String updateByQrCode(StockQrCodeEntity entity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" UPDATE stock_qr_code ");
        sbSql.append(" SET status = '"+ StatusEnum.STOP_USING.getValue() +"' ");
        sbSql.append(" ,stockId = #{stockId},sparePartId = #{sparePartId}");
        sbSql.append(" WHERE qrCode = #{qrCode} ");

        return sbSql.toString();

    }
}
