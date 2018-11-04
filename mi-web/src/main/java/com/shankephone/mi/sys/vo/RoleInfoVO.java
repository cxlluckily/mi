package com.shankephone.mi.sys.vo;

import com.shankephone.mi.model.SysRoleEntity;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * ${description}
 *
 * @author 司徒彬
 * @date 2018-06-22 15:02
 */
@Data
//@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
//@JsonInclude(JsonInclude.Include.NON_NULL)  //空值不返回在json中
public class RoleInfoVO extends SysRoleEntity {
    List<Long> functionTreeIds;
    List<Map<String, Object>> treeInfoList;
    List<Map<String, Object>> appTreeInfoList;
}
