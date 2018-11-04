package com.shankephone.mi.common.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;


/**
 * 
 * @author fengql
 * @date 2018年6月25日 下午4:22:36
 */
@Data
public class DataTreeEntity extends BaseModel {
	
	private static final long serialVersionUID = 1L;
	private Long dataNodeId;
	private String code;
    private String text;
    private boolean expanded;
    private boolean leaf;
    private Long dataParentNodeId;
    private List<DataTreeEntity> children = new ArrayList<DataTreeEntity>();

}
