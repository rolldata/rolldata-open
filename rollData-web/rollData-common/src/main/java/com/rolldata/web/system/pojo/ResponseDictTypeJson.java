package com.rolldata.web.system.pojo;

import java.io.Serializable;
import java.util.List;


/**
 * 基础档案档案目录返回值
 * @author shenshilong
 * @createDate 2018-8-6
 */

public class ResponseDictTypeJson implements Serializable{

	private static final long serialVersionUID = 3357850853913246421L;

	private List<DictTypeTree> treeNodes;
	
	public List<DictTypeTree> getTreeNodes() {
		return treeNodes;
	}

	public void setTreeNodes(List<DictTypeTree> treeNodes) {
		this.treeNodes = treeNodes;
	}

}
