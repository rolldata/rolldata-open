package com.rolldata.web.system.pojo;

import java.io.Serializable;
import java.util.List;

public class ResponseUserJson implements Serializable{

	private static final long serialVersionUID = 1398876294002585727L;

	private List<UserTree> treeNodes;
	
	private UserDetailedJson info;

	public List<UserTree> getTreeNodes() {
		return treeNodes;
	}

	public void setTreeNodes(List<UserTree> treeNodes) {
		this.treeNodes = treeNodes;
	}

	public UserDetailedJson getInfo() {
		return info;
	}

	public void setInfo(UserDetailedJson info) {
		this.info = info;
	}
	
	
}
