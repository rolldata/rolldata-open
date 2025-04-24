package com.rolldata.web.system.pojo;

import com.rolldata.web.common.pojo.BasePageInfo;

import java.io.Serializable;
import java.util.List;


/**
 * 基础档案档案目录返回值
 *
 * @author shenshilong
 * @createDate 2018-8-6
 */
public class ResponseDictJson extends BasePageInfo implements Serializable {

    private static final long serialVersionUID = -3363507757442119647L;

    private List<DictTree> treeNodes;

    public List<DictTree> getTreeNodes() {
        return treeNodes;
    }

    public void setTreeNodes(List<DictTree> treeNodes) {
        this.treeNodes = treeNodes;
    }
}
