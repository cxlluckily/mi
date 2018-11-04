package com.shankephone.mi.inventory.formbean;

import com.shankephone.mi.common.model.Pager;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author 赵亮
 * @date 2018/8/31 17:39
 */
@Data
public class GetPagerDetailInfoVO
{
    private Pager pager;
    private List<Map<String,Object>> partSparePartImageEntities;
    private Map<String,Object> partSparePartEntity;
}
