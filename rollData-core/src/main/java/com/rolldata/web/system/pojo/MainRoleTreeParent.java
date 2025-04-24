package com.rolldata.web.system.pojo;

import com.rolldata.web.common.pojo.BasePageInfo;

import java.util.List;

/** 
 * @Title: MainRoleTreeParent
 * @Description: 角色列表外层对象
 * @Company:www.wrenchdata.com
 * @author shenshilong
 * @date 2018/7/14
 * @version V1.0  
 */
public class MainRoleTreeParent extends BasePageInfo {
    
    
    private static final long serialVersionUID = -2077355122339841670L;
    
    private List<MainRoleTree> treeNodes;
    
    public List<MainRoleTree> getTreeNodes() {
        return this.treeNodes;
    }
    
    public MainRoleTreeParent setTreeNodes(List<MainRoleTree> treeNodes) {
        this.treeNodes = treeNodes;
        return this;
    }
}
