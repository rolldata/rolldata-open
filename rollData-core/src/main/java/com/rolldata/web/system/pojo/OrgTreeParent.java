package com.rolldata.web.system.pojo;

import com.rolldata.web.common.pojo.BasePageInfo;

import java.util.List;

/**
 * 组织管理树外层对象
 * @author shenshilong
 * @createDate 2018-7-31
 */

public class OrgTreeParent extends BasePageInfo{    

    private static final long serialVersionUID = 3442436695296476255L;
    
    private List<OrgTree> treeNodes;

    public List<OrgTree> getTreeNodes() {
        return treeNodes;
    }

    public void setTreeNodes(List<OrgTree> treeNodes) {
        this.treeNodes = treeNodes;
    }    
    
}
