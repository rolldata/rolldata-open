package com.rolldata.web.system.pojo;

import java.io.Serializable;
import java.util.List;

/** 
 * @Title: MainRoleTree
 * @Description: 新建角色响应信息
 * @Company:www.wrenchdata.com
 * @author shenshilong
 * @date 2018/7/14
 * @version V1.0  
 */
public class MainRoleTreeResponse implements Serializable {
    
    private static final long serialVersionUID = -4656323167986757985L;
    
    private List<MainRoleTree> treeNodes;
    
    /**
     * 详细信息
     */
    private RoleDetailedInfo info;
    
    /**
     * 获取 详细信息
     */
    public RoleDetailedInfo getInfo() {
        return this.info;
    }
    
    /**
     * 设置 详细信息
     */
    public void setInfo(RoleDetailedInfo info) {
        this.info = info;
    }
    
    public List<MainRoleTree> getTreeNodes() {
        return this.treeNodes;
    }
    
    public void setTreeNodes(List<MainRoleTree> treeNodes) {
        this.treeNodes = treeNodes;
    }
}
