package com.shankephone.mi.task.vo;

import com.shankephone.mi.model.StockWarehouseEntity;
import lombok.Data;

import java.util.List;

/**
 * @author 赵亮
 * @date 2018/10/15 19:05
 */
@Data
public class StockWarehouseEntityVO extends StockWarehouseEntity
{

    private List<String> permissionCodes;
    private  Long permissionId;
}
