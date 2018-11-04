package com.shankephone.mi.task.dao;

import com.shankephone.mi.model.TaskPendingTaskEntity;
import com.shankephone.mi.task.dao.provider.PendingTaskProvider;
import com.shankephone.mi.task.formbean.PendingTaskFindEntity;
import com.shankephone.mi.task.vo.PendingTaskListVO;
import com.shankephone.mi.task.vo.StockWarehouseEntityVO;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author 赵亮
 * @date 2018-08-06 10:28
 */
@Repository
public interface PendingTaskDao
{
    @InsertProvider(type = PendingTaskProvider.class, method = "insertOne")
    @Options(useGeneratedKeys = true, keyProperty = "pendingTaskId")
    Integer insertOne(TaskPendingTaskEntity entity);

    @UpdateProvider(type = PendingTaskProvider.class, method = "updateOne")
    Integer updateOne(TaskPendingTaskEntity entity);

    @SelectProvider(type = PendingTaskProvider.class, method = "getPendingTaskList")
    List<PendingTaskListVO> getPendingTaskList(PendingTaskFindEntity findEntity);

    @SelectProvider(type = PendingTaskProvider.class, method = "getPendingTaskListCount")
    Integer getPendingTaskListCount(PendingTaskFindEntity findEntity);

    @SelectProvider(type = PendingTaskProvider.class, method = "getReceivePersonList")
    List<Long> getReceivePersonList(StockWarehouseEntityVO entity);

    @SelectProvider(type = PendingTaskProvider.class, method = "getPersonPermissionCode")
    List<Long> getPersonPermissionCode(StockWarehouseEntityVO entity);

//    @SelectProvider(type = PendingTaskProvider.class, method = "getApplyReceivePersonList")
//    List<Long> getApplyReceivePersonList(StockWarehouseEntity entity);

    @DeleteProvider(type = PendingTaskProvider.class, method = "deleteTask")
    Integer deleteTask(TaskPendingTaskEntity entity);

    @UpdateProvider(type = PendingTaskProvider.class, method = "updateTaskOver")
    Integer updateTaskOver(TaskPendingTaskEntity entity);

    @UpdateProvider(type = PendingTaskProvider.class, method = "updateTaskSatuts")
    Integer updateTaskSatuts(TaskPendingTaskEntity entity);


    @SelectProvider(type = PendingTaskProvider.class, method = "getPaiDuanRenYuanList")
    List<Map<String, Object>> getPaiDuanRenYuanList(PendingTaskFindEntity findEntity);
}
