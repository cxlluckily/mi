package com.shankephone.mi.spacepart.vo;

import com.shankephone.mi.model.PartSparePartEntity;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author 赵亮
 * @date 2018-07-09 13:17
 */
@Data
public class SparePartVO extends PartSparePartEntity
{
    private List<Map<String, Object>> partSparePartImageEntities;
}
