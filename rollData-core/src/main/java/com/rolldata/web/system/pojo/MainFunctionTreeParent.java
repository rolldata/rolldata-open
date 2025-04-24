package com.rolldata.web.system.pojo;

import com.rolldata.web.common.pojo.BasePageInfo;

import java.util.List;

/** 
 * @Title: MainFunctionTreeParent
 * @Description: 菜单外层对象
 * @Company:www.wrenchdata.com
 * @author shenshilong
 * @date 2018/7/13
 * @version V1.0  
 */
public class MainFunctionTreeParent extends BasePageInfo {
    
    private static final long serialVersionUID = 519225917180749209L;
    
    private List<MainFunctionTree> treeNodes;
    
    public List<MainFunctionTree> getTreeNodes() {
        return this.treeNodes;
    }
    
    public MainFunctionTreeParent setTreeNodes(List<MainFunctionTree> treeNodes) {
        this.treeNodes = treeNodes;
        return this;
    }
}
