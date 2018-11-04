package com.shankephone.mi.model;

import com.shankephone.mi.common.model.BaseModel;
import lombok.Data;

import java.sql.Timestamp;


/**
 * sys_function_tree  Bean
 *
 * @author 系统生成
 * @date 2018-06-20 19:05:26
 */
@Data
public class SysFunctionTreeEntity extends BaseModel
{
	
    /**
     * 功能树ID
    **/
    private Long functionTreeId;
    /**
     * 父节点ID
    **/
    private Long parentTreeId;
    /**
     * 功能节点名称
    **/
    private String treeName;
    /**
     * 路由路径
    **/
    private String routeUrl;
    /**
     * 状态
    **/
    private String status;
    /**
     * 权限代码
    **/
    private String permissionCode;
    /**
     * 排序位置
    **/
    private Integer sort;
    /**
     * 备注
    **/
    private String remark;
    /**
     * 唯一标识名
     */
    private String identity;
    /**
     * 引用前端类名
     */
    private String xclass;
    /**
     * 图标
     */
    private String iconCls;
    /**
     * 添加人
    **/
    private String createUser;
    /**
     * 创建时间
    **/
    private Timestamp createTime;
    /**
     * 修改人
    **/
    private String modifyUser;
    /**
     * 修改时间
    **/
    private Timestamp modifyTime;

    /**
     *前端tab页签用到的id
     *@author：赵亮
     *@date：2018-07-26 11:35
    */
   private String id;
    /**
     * 功能树分类
     **/
   private String treeType;
}

