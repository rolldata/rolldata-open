package com.rolldata.web.system.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @Title: ResponseOrgJson
 * @Description: 组织树交互实体类
 * @Company:www.wrenchdata.com
 * @author zhaibx
 * @date 2020年6月19日
 * @version V1.0
 */
public class ResponseOrgJson implements Serializable{

	private static final long serialVersionUID = 4969208070249592842L;

	private List<OrgTree> treeNodes;
	
	private OrgDetailedJson info;

	public List<OrgTree> getTreeNodes() {
		return treeNodes;
	}

	public void setTreeNodes(List<OrgTree> treeNodes) {
		this.treeNodes = treeNodes;
	}

	public OrgDetailedJson getInfo() {
		return info;
	}

	public void setInfo(OrgDetailedJson info) {
		this.info = info;
	}

}
