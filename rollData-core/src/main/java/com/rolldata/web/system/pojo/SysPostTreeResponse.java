package com.rolldata.web.system.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * @Title: SysPostTreeResponse
 * @Description: 保存后返回前台的tree
 * @Company:www.wrenchdata.com
 * @author:shenshilong
 * @date: 2019-11-30
 * @version:V1.0
 */
public class SysPostTreeResponse implements Serializable {

    private static final long serialVersionUID = 1324843129729660297L;

    private List<SysPostTree> treeNodes;
    
    /**
     * 详细信息
     */
    private SysPostDetailedInfo info;


    /**
     * 获取
     *
     * @return treeNodes
     */
    public List<SysPostTree> getTreeNodes() {
        return this.treeNodes;
    }

    /**
     * 设置
     *
     * @param treeNodes
     */
    public void setTreeNodes(List<SysPostTree> treeNodes) {
        this.treeNodes = treeNodes;
    }

    /**
     * 获取 详细信息
     *
     * @return info 详细信息
     */
    public SysPostDetailedInfo getInfo() {
        return this.info;
    }

    /**
     * 设置 详细信息
     *
     * @param info 详细信息
     */
    public void setInfo(SysPostDetailedInfo info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return "SysPostTreeResponse{" + "treeNodes=" + treeNodes + ", info=" + info + '}';
    }
}
