package com.shankephone.mi.supersys.formbean;

import com.shankephone.mi.common.model.BaseModel;
import lombok.Data;

import java.util.List;

/**
 * @author 赵亮
 * @date 2018/8/14 19:52
 */
@Data
public class DeleteAdminFormBean extends BaseModel
{
    private List<Integer> adminIdarr;
}
