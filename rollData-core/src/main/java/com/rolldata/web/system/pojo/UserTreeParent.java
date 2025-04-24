package com.rolldata.web.system.pojo;

import com.rolldata.web.common.pojo.BasePageInfo;

import java.util.List;

/** 
 * @Title: UserTreeParent
 * @Description: 用户管理树外层对象
 * @Company:www.wrenchdata.com
 * @author hb
 * @date 2018/7/25
 * @version V1.0  
 */
public class UserTreeParent extends BasePageInfo{	

	private static final long serialVersionUID = 5659924247760584598L;
	
	private List<UserTree> treeNodes;
	
	public List<UserTree> getTreeNodes() {
		return treeNodes;
	}

	public void setTreeNodes(List<UserTree> treeNodes) {
		this.treeNodes = treeNodes;
	}
	
}
