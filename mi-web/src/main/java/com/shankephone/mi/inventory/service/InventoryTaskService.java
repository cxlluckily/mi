package com.shankephone.mi.inventory.service;

import com.shankephone.mi.common.model.Pager;
import com.shankephone.mi.common.vo.ResultVO;
import com.shankephone.mi.inventory.formbean.GetInventoryTaskListFindEntity;
import com.shankephone.mi.inventory.formbean.InventoryFindEntity;
import com.shankephone.mi.inventory.formbean.UpdateInventoryTaskFormBean;
import com.shankephone.mi.model.StockInventoryTaskEntity;

import java.util.List;
import java.util.Map;

/**
 * @author 赵亮
 * @date 2018-08-16 14:16
 */
public interface InventoryTaskService
{
    /**
     *新增盘库信息
     *@author：赵亮
     *@date：2018-08-16 14:43
    */
    ResultVO insertOne(StockInventoryTaskEntity entity) throws Exception;

    /**
     *修改盘库信息
     *@author：赵亮
     *@date：2018-08-16 14:43
     */
    ResultVO updateOne(StockInventoryTaskEntity entity) throws Exception;

    /**
     *返回盘库分页数据
     *@author：赵亮
     *@date：2018-08-16 16:01
    */
    Pager<Map<String, Object>> getInventoryTaskList(GetInventoryTaskListFindEntity findEntity);

    /**
     *根据任务Id返回盘库信息
     *@author：赵亮
     *@date：2018-08-16 16:26
    */
    List<Map<String,Object>> getInventoryDetailEntity(InventoryFindEntity entity);

    /**
     *更新盘库信息
     *@author：赵亮
     *@date：2018-08-16 16:32
    */
    String updateInventoryTask(UpdateInventoryTaskFormBean entity) throws Exception;


    /**
     *获取盘库任务的详情
     *@author：郝伟州
     *@date：2018年8月29日14:03:51
     */
    List<Map<String,Object>> selectInventoryTaskentity(InventoryFindEntity entity);
}
