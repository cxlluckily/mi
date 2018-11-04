package com.shankephone.mi.inventory.dao;

import com.shankephone.mi.inventory.dao.provider.InventoryTaskProvider;
import com.shankephone.mi.inventory.formbean.GetInventoryTaskListFindEntity;
import com.shankephone.mi.inventory.formbean.InventoryFindEntity;
import com.shankephone.mi.model.StockInventoryDetailEntity;
import com.shankephone.mi.model.StockInventoryTaskEntity;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 盘库
 *
 * @author 赵亮
 * @date 2018-08-16 14:13
 */
@Repository
public interface InventoryTaskDao
{
    @InsertProvider(type = InventoryTaskProvider.class, method = "insertOne")
    @Options(useGeneratedKeys = true, keyProperty = "inventoryTaskId")
    Long insertOne(StockInventoryTaskEntity entity);

    @UpdateProvider(type = InventoryTaskProvider.class, method = "updateOne")
    void updateOne(StockInventoryTaskEntity entity);

    @InsertProvider(type = InventoryTaskProvider.class, method = "insertInventoryDetail")
    @Options(useGeneratedKeys = true, keyProperty = "inventoryDetailId")
    Long insertInventoryDetail(StockInventoryDetailEntity entity);

    @SelectProvider(type = InventoryTaskProvider.class, method = "getInsertDetailList")
    List<Map<String, Object>> getInsertDetailList(Long warehouseId);

    @SelectProvider(type = InventoryTaskProvider.class, method = "getInventoryTaskList")
    List<Map<String, Object>> getInventoryTaskList(GetInventoryTaskListFindEntity findEntity);

    @SelectProvider(type = InventoryTaskProvider.class, method = "getInventoryTaskListCount")
    Integer getInventoryTaskListCount(GetInventoryTaskListFindEntity findEntity);

    @SelectProvider(type = InventoryTaskProvider.class, method = "getInventoryDetailEntity")
    List<Map<String,Object>> getInventoryDetailEntity(InventoryFindEntity entity);



    /**
     *更新盘库任务表状态
     *@author：赵亮
     *@date：2018-08-16 16:42
     */
    @UpdateProvider(type = InventoryTaskProvider.class, method = "updateInventoryTaskStatus")
    Integer updateInventoryTaskStatus(StockInventoryTaskEntity entity);

    /**
     *更新盘库任务的盘库数量
     *@author：赵亮
     *@date：2018-08-16 16:44
    */
    @UpdateProvider(type = InventoryTaskProvider.class, method = "updateInventoryDetail")
    Integer updateInventoryDetail(StockInventoryDetailEntity entity);


    /**
     *获取盘库任务的详情
     *@author：郝伟州
     *@date：2018年8月29日14:03:51
     */
    @SelectProvider(type = InventoryTaskProvider.class, method = "selectInventoryTaskentity")
    List<Map<String, Object>> selectInventoryTaskentity(InventoryFindEntity entity);



}
