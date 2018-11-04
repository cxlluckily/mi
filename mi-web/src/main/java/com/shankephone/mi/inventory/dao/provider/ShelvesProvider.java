package com.shankephone.mi.inventory.dao.provider;

import com.shankephone.mi.common.enumeration.StatusEnum;
import com.shankephone.mi.inventory.formbean.ShelvesFindEntity;
import com.shankephone.mi.model.StockGoodsShelvesEntity;
import com.shankephone.mi.util.DataSwitch;
import com.shankephone.mi.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 货架 provider
 *
 * @author 司徒彬
 * @date 2018 /7/5 17:12
 */
public class ShelvesProvider
{
    /**
     * Get pager list string.
     *
     * @param findEntity the find entity
     * @return the string
     */
    public String getPagerList(ShelvesFindEntity findEntity)
    {

        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT ");
        sbSql.append("   shelves.*, ");
        sbSql.append("   warehouse.warehouseName, ");
        sbSql.append("   section.sectionName, ");
        sbSql.append("   subject.subjectName ");
        sbSql.append(" FROM stock_goods_shelves shelves ");
        sbSql.append("   INNER JOIN stock_warehouse warehouse ON warehouse.warehouseId = shelves.warehouseId ");
        sbSql.append("   INNER JOIN org_work_section section ON section.workSectionId = warehouse.workSectionId ");
        sbSql.append("   INNER JOIN sys_operation_subject subject ON subject.operationSubjectId = warehouse.operationSubjectId ");

        sbSql.append(" WHERE 1=1  ");

        //sbSql.append("  AND  warehouse.warehouseId in ("+StringUtils.listToString(findEntity.getWarehouseIdlist())+") ");

        sbSql.append("  AND  warehouse.warehouseId  in ("+findEntity.getWarehouseId()+")");

        if(!"all".equals(findEntity.getStatus()))
        {
            sbSql.append("       AND shelves.status = #{status} ");
        }
        if(!"all".equals(findEntity.getContainerType()))
        {
            sbSql.append("       AND containerType = #{containerType} ");
        }
        sbSql.append("       AND shelfNumber LIKE concat('%', #{shelfNumber}, '%') ");
        sbSql.append("       AND houseNo LIKE concat('%', #{houseNo}, '%') ");
        sbSql.append("   LIMIT #{start}, #{limit} ");
        return sbSql.toString();
    }

    public String getPagerCount(ShelvesFindEntity findEntity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT ");
        sbSql.append("   count(1) ");
        sbSql.append(" FROM stock_goods_shelves shelves ");
        sbSql.append("   INNER JOIN stock_warehouse warehouse ON warehouse.warehouseId = shelves.warehouseId ");
        sbSql.append("   INNER JOIN org_work_section section ON section.workSectionId = warehouse.workSectionId ");
        sbSql.append("   INNER JOIN sys_operation_subject subject ON subject.operationSubjectId = warehouse.operationSubjectId ");
        sbSql.append(" WHERE 1=1  ");
        //sbSql.append("  AND  warehouse.warehouseId in ("+StringUtils.listToString(findEntity.getWarehouseIdlist())+") ");
        sbSql.append("  AND  warehouse.warehouseId  in ("+findEntity.getWarehouseId()+")");

        if(!"all".equals(findEntity.getStatus()))
        {
            sbSql.append("       AND shelves.status = #{status} ");
        }
        if(!"all".equals(findEntity.getContainerType()))
        {
            sbSql.append("       AND containerType = #{containerType} ");
        }
        sbSql.append("       AND shelfNumber LIKE concat('%', #{shelfNumber}, '%') ");
        sbSql.append("       AND houseNo LIKE concat('%', #{houseNo}, '%') ");

        return sbSql.toString();
    }

    /**
     * Get shelves info string.
     *
     * @param goodsShelvesId the goods shelves id
     * @return the string
     */
    public String getShelvesInfo(Long goodsShelvesId)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT ");
        sbSql.append("   shelves.*, ");
        sbSql.append("   warehouse.warehouseName, ");
        sbSql.append("   subject.subjectName ");
        sbSql.append(" FROM stock_goods_shelves shelves ");
        sbSql.append("   INNER JOIN stock_warehouse warehouse ON warehouse.warehouseId = shelves.warehouseId ");
        sbSql.append("   INNER JOIN sys_operation_subject subject ON subject.operationSubjectId = warehouse.operationSubjectId ");
        sbSql.append(" WHERE shelves.goodsShelvesId = #{goodsShelvesId} ");
        return sbSql.toString();
    }

    /**
     * Get pager list string.
     *
     * @param findEntity the find entity
     * @return the string
     */
    public String getShelvesList(ShelvesFindEntity findEntity)
    {

        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT ");
        sbSql.append("   shelves.*  ");

        sbSql.append(" FROM stock_goods_shelves shelves ");
        sbSql.append("   INNER JOIN stock_warehouse warehouse ON warehouse.warehouseId = shelves.warehouseId ");
        sbSql.append(" WHERE 1=1  ");
        sbSql.append("  AND  warehouse.warehouseId  in ("+findEntity.getWarehouseId()+")");
        if(!"all".equals(findEntity.getStatus()))
        {
            sbSql.append("       AND shelves.status = #{status} ");
        }
        if(!"all".equals(findEntity.getContainerType()))
        {
            sbSql.append("       AND containerType = #{containerType} ");
        }

        if(StringUtils.isNotEmpty(findEntity.getHouseNo()))
        {
            sbSql.append(" AND shelves.houseNo = #{houseNo} ");
        }
        sbSql.append(" ORDER BY houseNo,shelfNumber ");
        return sbSql.toString();
    }

    /**
     * Insert shelves info string.
     *
     * @param shelvesEntity the shelves entity
     * @return the string
     */
    public String insertShelvesInfo(StockGoodsShelvesEntity shelvesEntity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" INSERT INTO ");
        sbSql.append("   stock_goods_shelves ");
        sbSql.append("   ( ");
        sbSql.append("      warehouseId, ");
        sbSql.append("      houseNo, ");
        sbSql.append("      containerType, ");
        sbSql.append("      shelfNumber, ");
        sbSql.append("      remark, ");
        sbSql.append("      status, ");
        sbSql.append("      createUser, ");
        sbSql.append("      modifyUser ");
        sbSql.append("   ) ");
        sbSql.append(" VALUES ");
        sbSql.append("   ( ");
        sbSql.append("      #{warehouseId}, ");
        sbSql.append("      #{houseNo}, ");
        sbSql.append("      #{containerType}, ");
        sbSql.append("      #{shelfNumber}, ");
        sbSql.append("      #{remark}, ");
        sbSql.append("      #{status}, ");
        sbSql.append("      #{createUser}, ");
        sbSql.append("      #{modifyUser} ");
        sbSql.append("   ) ");
        return sbSql.toString();
    }

    /**
     * Update shelves info string.
     *
     * @param shelvesEntity the shelves entity
     * @return the string
     */
    public String updateShelvesInfo(StockGoodsShelvesEntity shelvesEntity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" UPDATE ");
        sbSql.append("   stock_goods_shelves ");
        sbSql.append(" SET ");
        sbSql.append("   warehouseId = #{warehouseId}, ");
        sbSql.append("   houseNo = #{houseNo}, ");
        sbSql.append("   containerType = #{containerType}, ");
        sbSql.append("   shelfNumber = #{shelfNumber}, ");
        sbSql.append("   remark = #{remark}, ");
        sbSql.append("   status = #{status}, ");
        sbSql.append("   modifyUser = #{modifyUser} ");
        sbSql.append(" WHERE ");
        sbSql.append("   goodsShelvesId = #{goodsShelvesId} ");
        return sbSql.toString();
    }

    /**
     * Delete shelves info string.
     *
     * @param goodsShelvesId the goods shelves id
     * @return the string
     */
    public String deleteShelvesInfo(Long goodsShelvesId)
    {
        String sql = "UPDATE stock_goods_shelves SET status = '" + StatusEnum.STOP_USING.getValue() + "' where goodsShelvesId = #{goodsShelvesId} ";
        return sql;
    }

    public String getShelvesInfoBySearchContent(ShelvesFindEntity findEntity)
    {
        List<String> contentList = StringUtils.splitToList(findEntity.getSearchContent(), " ");
        List<String> like = new ArrayList<>();
        contentList.stream().forEach(content -> {
            like.add(" shelfNumber LIKE concat('%','" + content + "','%')");
            like.add(" houseNo LIKE concat('%','" + content + "','%')");
        });
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT ");
        sbSql.append("   shelves.goodsShelvesId, ");
        sbSql.append("  concat(houseNo,' ',shelfNumber) shelfNumber");
        sbSql.append(" FROM stock_goods_shelves shelves ");
        sbSql.append(" WHERE warehouseId = #{warehouseId} ");
        if (like.size() != 0)
        {
            sbSql.append("       AND ( ");
            sbSql.append(DataSwitch.getCombineString(" OR ", like));
            sbSql.append("       ) ");
        }

        return sbSql.toString();
    }
}
