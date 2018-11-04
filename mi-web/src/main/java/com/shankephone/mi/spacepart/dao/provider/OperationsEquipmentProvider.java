package com.shankephone.mi.spacepart.dao.provider;

import com.shankephone.mi.common.enumeration.StatusEnum;
import com.shankephone.mi.model.OperationsEquipmentEntity;
import com.shankephone.mi.spacepart.formbean.OperationsEquipmentFindEntity;
import com.shankephone.mi.util.ObjectUtils;
import com.shankephone.mi.util.StringUtils;

/**
 * 营运设备Provider
 *
 * @author 司徒彬
 * @date 2018 /8/20 15:21
 */
public class OperationsEquipmentProvider {

    /**
     * Get station list string.
     *
     * @param findEntity the find entity
     * @return the string
     */
    public String getStationList(OperationsEquipmentFindEntity findEntity) {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT ");
        sbSql.append("   station.stationId, ");
        sbSql.append("   stationName ");
        sbSql.append(" FROM org_station station ");
        sbSql.append("        INNER JOIN org_work_section_station sectionStation ");
        sbSql.append("          ON station.stationId = sectionStation.stationId ");
        sbSql.append(" WHERE lineId IN (#{lineId}) ");
        sbSql.append("   AND workSectionId IN (#{workSectionId}) ");

        return sbSql.toString();
    }

    /**
     * Get pager info string.
     *
     * @param findEntity the find entity
     * @return the string
     */
    public String getPagerInfo(OperationsEquipmentFindEntity findEntity) {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT ");
        sbSql.append("   equipmentId, ");
        sbSql.append(" (select  imageUrl from part_spare_part_image where sparePartId= part.sparePartId LIMIT  1) imageUrl, ");
        sbSql.append("   partName, ");
        sbSql.append("   equipment.serialNumber, ");
        sbSql.append("   materiaCoding, ");
        sbSql.append("   categoryName, ");
        sbSql.append("   equipmentName, ");
        sbSql.append("   equipmentNo, ");
        sbSql.append("   brand, ");
        sbSql.append("   specificationModel, ");
        sbSql.append("   lineName, ");
         sbSql.append("   station.stationName, ");
         sbSql.append("   equipment.location, ");
         sbSql.append("   equipment.status, ");
        sbSql.append("   equipment.qrCode, ");
        sbSql.append("   CASE equipment.status ");
        sbSql.append("   WHEN '" + StatusEnum.START_USING.getValue() + "' ");
        sbSql.append("     THEN '可用' ");
        sbSql.append("   ELSE '不可用' END AS statusText,");
       // sbSql.append("   sectionName, ");
       // sbSql.append("   stationName, ");
        sbSql.append("   part.sparePartTypeId, ");
        sbSql.append("   station.stationId ");
        sbSql.append(" FROM operations_equipment equipment ");
        sbSql.append("        INNER JOIN  part_spare_part   part ");
        sbSql.append("          ON part.sparePartId = equipment.sparePartId ");
        sbSql.append("        INNER JOIN part_spare_part_type type ");
        sbSql.append("          ON type.sparePartTypeId = part.sparePartTypeId ");
//        sbSql.append("        INNER JOIN org_work_section section ");
//        sbSql.append("          ON section.workSectionId = equipment.workSectionId ");
        sbSql.append("        INNER JOIN org_line line ");
        sbSql.append("          ON line.lineId = equipment.lineId ");
        sbSql.append("        INNER JOIN org_station station ");
        sbSql.append("          ON station.stationId = equipment.stationId ");

        sbSql.append(" WHERE line.operationSubjectId=#{operationSubjectId}   ");

        if (!"all".equals(findEntity.getStationId())&&StringUtils.isNotEmpty(findEntity.getStationId()))  {
            sbSql.append("  AND station.stationId IN (#{stationId}) ");
        }
        if(!"all".equals(findEntity.getStationCode())&&StringUtils.isNotEmpty(findEntity.getStationCode()))
        {
            sbSql.append("  AND station.stationCode IN (#{stationCode}) ");
        }
        if (!"all".equals(findEntity.getSparePartTypeId())) {
            sbSql.append("   AND part.sparePartTypeId IN (#{sparePartTypeId}) ");
        }
        if (ObjectUtils.isNotEmpty(findEntity.getEquipmentNo())) {
            sbSql.append("   AND equipmentNo LIKE concat('%', #{equipmentNo}, '%') ");
        }
        if (ObjectUtils.isNotEmpty(findEntity.getEquipmentName())) {
            sbSql.append("   AND equipmentName LIKE concat('%', #{equipmentName}, '%') ");
        }
        if (ObjectUtils.isNotEmpty(findEntity.getPartName())) {
            sbSql.append("   AND partName LIKE concat('%', #{partName}, '%') ");
        }
        if (ObjectUtils.isNotEmpty(findEntity.getMateriaCoding())) {
            sbSql.append("   AND materiaCoding LIKE concat('%', #{materiaCoding}, '%') ");
        }
        if (ObjectUtils.isNotEmpty(findEntity.getSpecificationModel())) {
            sbSql.append("   AND specificationModel LIKE concat('%', #{specificationModel}, '%') ");
        }
        if (ObjectUtils.isNotEmpty(findEntity.getBrand())) {
            sbSql.append("   AND brand LIKE concat('%', #{brand}, '%') ");
        }
        sbSql.append(" LIMIT #{start}, #{limit} ");
        return sbSql.toString();

    }

    /**
     * Get pager info total string.
     *
     * @param findEntity the find entity
     * @return the string
     */
    public String getPagerInfoTotal(OperationsEquipmentFindEntity findEntity) {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT ");
        sbSql.append("   count(1) ");
        sbSql.append(" FROM operations_equipment equipment ");
        sbSql.append("        INNER JOIN part_spare_part  part ");
        sbSql.append("          ON part.sparePartId = equipment.sparePartId ");
        sbSql.append("        INNER JOIN part_spare_part_type type ");
        sbSql.append("          ON type.sparePartTypeId = part.sparePartTypeId ");
//        sbSql.append("        INNER JOIN org_work_section section ");
//        sbSql.append("          ON section.workSectionId = equipment.workSectionId ");
        sbSql.append("        INNER JOIN org_line line ");
        sbSql.append("          ON line.lineId = equipment.lineId ");
        sbSql.append("        INNER JOIN org_station station ");
        sbSql.append("          ON station.stationId = equipment.stationId ");

        sbSql.append(" WHERE line.operationSubjectId=#{operationSubjectId}   ");

        if (!"all".equals(findEntity.getStationId())&&StringUtils.isNotEmpty(findEntity.getStationId())) {
            sbSql.append("  AND station.stationId IN (#{stationId}) ");
        }
        if(!"all".equals(findEntity.getStationCode())&&StringUtils.isNotEmpty(findEntity.getStationCode()))
        {
            sbSql.append("  AND station.stationCode IN (#{stationCode}) ");
        }
        if (!"all".equals(findEntity.getSparePartTypeId())) {
            sbSql.append("   AND part.sparePartTypeId IN (#{sparePartTypeId}) ");
        }
        if (ObjectUtils.isNotEmpty(findEntity.getEquipmentNo())) {
            sbSql.append("   AND equipmentNo LIKE concat('%', #{equipmentNo}, '%') ");
        }
        if (ObjectUtils.isNotEmpty(findEntity.getEquipmentName())) {
            sbSql.append("   AND equipmentName LIKE concat('%', #{equipmentName}, '%') ");
        }
        if (ObjectUtils.isNotEmpty(findEntity.getPartName())) {
            sbSql.append("   AND partName LIKE concat('%', #{partName}, '%') ");
        }
        if (ObjectUtils.isNotEmpty(findEntity.getMateriaCoding())) {
            sbSql.append("   AND materiaCoding LIKE concat('%', #{materiaCoding}, '%') ");
        }
        if (ObjectUtils.isNotEmpty(findEntity.getSpecificationModel())) {
            sbSql.append("   AND specificationModel LIKE concat('%', #{specificationModel}, '%') ");
        }
        if (ObjectUtils.isNotEmpty(findEntity.getBrand())) {
            sbSql.append("   AND brand LIKE concat('%', #{brand}, '%') ");
        }
        return sbSql.toString();
    }

    /**
     * Add equipment string.
     *
     * @param equipmentEntity the equipment entity
     * @return the string
     */
    public String addEquipment(OperationsEquipmentEntity equipmentEntity) {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" INSERT INTO ");
        sbSql.append("   operations_equipment ");
        sbSql.append("   ( ");
        sbSql.append("      operationSubjectId, ");
       // sbSql.append("      workSectionId, ");
        sbSql.append("      lineId, ");
        sbSql.append("      stationId, ");
        sbSql.append("      sparePartId, ");
        sbSql.append("      qrCode, ");
        sbSql.append("      equipmentName, ");
        sbSql.append("      equipmentNo, ");
        sbSql.append("      serialNumber, ");
        sbSql.append("      uniquelyIdentifies, ");
        sbSql.append("      status, ");
        sbSql.append("      location, ");
        sbSql.append("      remark, ");
        sbSql.append("      createUser, ");
        sbSql.append("      modifyUser ");
        sbSql.append("   ) ");
        sbSql.append(" VALUES ");
        sbSql.append("   ( ");
        sbSql.append("      #{operationSubjectId}, ");
        // sbSql.append("   (SELECT  workSectionId FROM org_work_section_station WHERE stationId=#{stationId} LIMIT  1), ");
       /// sbSql.append("      #{workSectionId}, ");
        sbSql.append("   (SELECT  lineId FROM org_station WHERE stationId=#{stationId} LIMIT  1), ");
       // sbSql.append("      #{lineId}, ");
        sbSql.append("      #{stationId}, ");
        sbSql.append("      #{sparePartId}, ");
        sbSql.append("      #{qrCode}, ");
        sbSql.append("      #{equipmentName}, ");
        sbSql.append("      #{equipmentNo}, ");
        sbSql.append("      #{serialNumber}, ");
        sbSql.append("      #{uniquelyIdentifies}, ");
        sbSql.append("      #{status}, ");
        sbSql.append("      #{location}, ");
        sbSql.append("      #{remark}, ");
        sbSql.append("      #{operationUserName}, ");
        sbSql.append("      #{operationUserName} ");
        sbSql.append("   ) ");
        return sbSql.toString();
    }

    public String updateEquipment(OperationsEquipmentEntity equipmentEntity){
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" UPDATE ");
        sbSql.append("   operations_equipment ");
        sbSql.append(" SET ");
        sbSql.append("   operationSubjectId = #{operationSubjectId}, ");
       // sbSql.append("   workSectionId = (SELECT  workSectionId FROM org_work_section_station WHERE stationId=#{stationId} LIMIT  1), ");
        sbSql.append("   lineId =  (SELECT  lineId FROM org_station WHERE stationId=#{stationId} LIMIT  1), ");
        sbSql.append("   stationId = #{stationId}, ");
        sbSql.append("   qrCode = #{qrCode}, ");
        sbSql.append("   sparePartId = #{sparePartId}, ");
        sbSql.append("   equipmentName = #{equipmentName}, ");
        sbSql.append("   equipmentNo = #{equipmentNo}, ");
        sbSql.append("   serialNumber = #{serialNumber}, ");
        sbSql.append("   uniquelyIdentifies = #{uniquelyIdentifies}, ");
        sbSql.append("   status = #{status}, ");
        sbSql.append("   location = #{location}, ");
        sbSql.append("   remark = #{remark}, ");
        sbSql.append("   modifyUser = #{modifyUser} ");
        sbSql.append(" WHERE ");
        sbSql.append("   equipmentId = #{equipmentId} ");
        return sbSql.toString();
    }

    /**
     * Delete equipment string.
     *
     * @param findEntity the find entity
     * @return the string
     */
    public String deleteEquipment(OperationsEquipmentFindEntity findEntity) {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" UPDATE operations_equipment ");
        sbSql.append(" SET ");
        sbSql.append("   status = 'stop_using', ");
        sbSql.append("   modifyUser = #{operationUserName} ");
        sbSql.append(" WHERE equipmentId = #{equipmentId} ");

        return sbSql.toString();
    }

    /**
     * Gets equipment.
     *
     * @param equipmentId the equipment id
     * @return the equipment
     */
    public String getEquipment(Long equipmentId) {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT ");
        sbSql.append("   equipment.*, ");
        sbSql.append("   partName, ");
        sbSql.append("   brand, ");
        sbSql.append("   materiaCoding, ");
        sbSql.append("   specificationModel, ");
        sbSql.append("   size, ");
        sbSql.append("   material, ");
        sbSql.append("   units, ");
        sbSql.append("   upperLimit, ");
        sbSql.append("   lowerLimit, ");
        sbSql.append("   partName, ");
        sbSql.append("   lineName, ");
        sbSql.append("   station.stationName, ");
        sbSql.append("   equipment.location, ");
        sbSql.append("   equipment.status, ");
        sbSql.append("   type.sparePartTypeId, ");
        sbSql.append("   type.categoryName, ");
       // sbSql.append("   stationName, ");
       // sbSql.append("   sectionName, ");
        sbSql.append("   categoryName ");
        sbSql.append(" FROM operations_equipment equipment ");
        sbSql.append("        INNER JOIN part_spare_part part ");
        sbSql.append("          ON part.sparePartId = equipment.sparePartId ");
        sbSql.append("        INNER JOIN org_line line ");
        sbSql.append("          ON line.lineId = equipment.lineId ");
      //  sbSql.append("        INNER JOIN org_work_section section ");
       // sbSql.append("          ON section.workSectionId = equipment.workSectionId ");
        sbSql.append("        INNER JOIN org_station station ");
        sbSql.append("          ON station.stationId = equipment.stationId ");
        sbSql.append("        INNER JOIN part_spare_part_type type ");
        sbSql.append("          ON type.sparePartTypeId = part.sparePartTypeId ");
        sbSql.append(" WHERE   equipment.equipmentId = #{equipmentId} ");

        return sbSql.toString();
    }


    /**
     * Gets equipment.
     *
     * @param findEntity the equipment id
     * @return the equipment
     */
    public String getEquipmentEntity(OperationsEquipmentFindEntity findEntity) {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT ");
        sbSql.append("   equipment.*, ");
        sbSql.append("   partName, ");
        sbSql.append("   brand, ");
        sbSql.append("   materiaCoding, ");
        sbSql.append("   specificationModel, ");
        sbSql.append("   size, ");
        sbSql.append("   material, ");
        sbSql.append("   units, ");
        sbSql.append("   upperLimit, ");
        sbSql.append("   lowerLimit, ");
        sbSql.append("   partName, ");
        sbSql.append("   lineName, ");
        sbSql.append("   station.stationName, ");
        sbSql.append("   type.sparePartTypeId, ");
        sbSql.append("   type.categoryName  ");
        sbSql.append(" FROM operations_equipment equipment ");
        sbSql.append("        INNER JOIN part_spare_part part ");
        sbSql.append("          ON part.sparePartId = equipment.sparePartId ");
        sbSql.append("        INNER JOIN org_line line ");
        sbSql.append("          ON line.lineId = equipment.lineId ");
        //  sbSql.append("        INNER JOIN org_work_section section ");
        // sbSql.append("          ON section.workSectionId = equipment.workSectionId ");
        sbSql.append("        INNER JOIN org_station station ");
        sbSql.append("          ON station.stationId = equipment.stationId ");
        sbSql.append("        INNER JOIN part_spare_part_type type ");
        sbSql.append("          ON type.sparePartTypeId = part.sparePartTypeId ");
        sbSql.append(" WHERE 1=1   ");

        if(StringUtils.isNotEmpty(findEntity.getEquipmentId()))
        {
            sbSql.append("  AND equipment.equipmentId = #{equipmentId} ");
        }
        if(StringUtils.isNotEmpty(findEntity.getQrCode()))
        {
            sbSql.append("  AND equipment.qrCode = #{qrCode} ");
        }
        if(StringUtils.isNotEmpty(findEntity.getEquipmentNo()))
        {
            sbSql.append("  AND equipment.equipmentNo = #{equipmentNo} ");
        }
        sbSql.append(" LiMIT 1  ");

        return sbSql.toString();
    }

    /**
     * Gets equipment images.
     *
     * @param equipmentId the equipment id
     * @return the equipment images
     */
    public String getEquipmentImages(Long equipmentId) {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT * ");
        sbSql.append(" FROM part_spare_part_image image ");
        sbSql.append("        INNER JOIN operations_equipment equipment ");
        sbSql.append("          ON equipment.sparePartId = image.sparePartId ");
        sbSql.append(" WHERE equipmentId = #{equipmentId} ");

        return sbSql.toString();
    }

    /**
     * Gets spare parts info.
     *
     * @param equipmentId the equipment id
     * @return the spare parts info
     */
    public String getSparePartsInfo(Long equipmentId) {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT ");
        sbSql.append("  (SELECT imageUrl FROM part_spare_part_image WHERE sparePartId = part.sparePartId  LIMIT 1 )  AS imageUrl,");
        sbSql.append("   part.*, ");
        sbSql.append("   categoryName, ");
        sbSql.append("   part.sparePartTypeId ");
        sbSql.append(" FROM operations_equipment_parts equipmentPart ");
        sbSql.append("        INNER JOIN  part_spare_part part    ON part.sparePartId = equipmentPart.sparePartId ");
        sbSql.append("        INNER JOIN part_spare_part_type type ");
        sbSql.append("          ON part.sparePartTypeId = type.sparePartTypeId ");
        sbSql.append(" WHERE equipmentId = #{equipmentId} ");

        return sbSql.toString();
    }


    /**
     * Get station list string.
     *
     * @param findEntity the find entity
     * @return the string
     */
    public String getLineStationList(OperationsEquipmentFindEntity findEntity) {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT  ");
        sbSql.append(" org_line.lineId, ");
        sbSql.append(" org_line.lineCode,");
        sbSql.append(" org_line.lineName,");
        sbSql.append(" station.stationId, ");
        sbSql.append(" station.stationCode, ");
        sbSql.append(" stationName ");
        sbSql.append(" FROM org_station station ");
        sbSql.append(" INNER JOIN org_line ON org_line.lineId=station.lineId ");
        //  sbSql.append(" INNER JOIN org_work_section_station sectionStation ON station.stationId = sectionStation.stationId ");
         sbSql.append(" WHERE   org_line.operationSubjectId = #{operationSubjectId} ");

       // sbSql.append(" WHERE   workSectionId IN ("+StringUtils.listToString(findEntity.getWorkSectionRange()) +") ");

        return sbSql.toString();
    }


    /**
     * Get station list string.
     *
     * @param findEntity the find entity
     * @return the string
     */
    public String getOperationPartList(OperationsEquipmentFindEntity findEntity) {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT  ");
        sbSql.append(" part_spare_part.sparePartId, ");
        sbSql.append(" part_spare_part.partName,");
        sbSql.append(" part_spare_part_type.categoryName");
        sbSql.append(" FROM part_spare_part ");
        sbSql.append(" INNER JOIN part_spare_part_type ON part_spare_part.sparePartTypeId=part_spare_part_type.sparePartTypeId ");
        sbSql.append(" WHERE   part_spare_part_type.operationSubjectId = #{operationSubjectId} ");
        sbSql.append("   AND part_spare_part_type.parentPartId = -1 ");
        sbSql.append("   AND part_spare_part_type.status = '" + StatusEnum.START_USING.getValue() + "' ");
        return sbSql.toString();
    }

    /**
     * Get station list string.
     *
     * @param findEntity the find entity
     * @return the string
     */
    public String getOperationPartListForInventory(OperationsEquipmentFindEntity findEntity) {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT  ");
        sbSql.append(" part_spare_part.sparePartId, ");
        sbSql.append(" part_spare_part.partName,");
        sbSql.append(" part_spare_part_type.categoryName");
        sbSql.append(" FROM part_spare_part ");
        sbSql.append(" INNER JOIN part_spare_part_type ON part_spare_part.sparePartTypeId=part_spare_part_type.sparePartTypeId ");
        sbSql.append(" WHERE   part_spare_part_type.operationSubjectId = #{operationSubjectId} ");
        sbSql.append("   AND part_spare_part_type.status = '" + StatusEnum.START_USING.getValue() + "' ");
        return sbSql.toString();
    }

}
