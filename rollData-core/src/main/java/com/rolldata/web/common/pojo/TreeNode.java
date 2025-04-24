package com.rolldata.web.common.pojo;

import com.rolldata.core.util.StringUtil;
import com.rolldata.web.common.enums.TreeNodeType;

import java.io.Serializable;
import java.util.List;

/** 
 * @Title: TreeNode
 * @Description: TreeNode
 * @Company:www.wrenchdata.com
 * @author shenshilong
 * @date 2018/7/10
 * @version V1.0  
 */
public class TreeNode implements Serializable {
    
    private static final long serialVersionUID = 2469042308773714935L;
    
    public static final String ROOT = "0";
    
    /*树id*/
    private String id;
    
    /*树父id*/
    private String pId;

    /*节点编码*/
    private String code;

    /*节点名称*/
    private String name;

    /*节点显示名*/
    private String title;

    /*选中状态*/
    private boolean checked = false;
    
    /*禁用状态*/
    private boolean nocheck = false;
    
    /*是否支持拖拽*/
    private boolean drag = false;
    
    /*是否支持复制*/
    private boolean copy = false;
    
    /*节点图标路径*/
    private String icon;
    
    /*图标class名称*/
    private String iconSkin;
    
    /*是否可放置*/
    private boolean drop = false;
    
    /*节点可放置类型*/
    private List<String> valid_children;
    
    /*类型*/
    private TreeNodeType type;

    /**
     * 原始类型
     */
    private String originalType;

    private List<String> ids;
    
    public String getId() {
        return this.id;
    }
    
    public TreeNode setId(String id) {
        this.id = id;
        return this;
    }
    
    public String getpId() {
        return this.pId;
    }
    
    public TreeNode setpId(String pId) {
        this.pId = pId;
        return this;
    }
    
    public String getName() {
        return this.name;
    }
    
    public TreeNode setName(String name) {
        this.name = name;
        return this;
    }
    
    public boolean isChecked() {
        return this.checked;
    }
    
    public TreeNode setChecked(boolean checked) {
        this.checked = checked;
        return this;
    }
    
    public boolean isNocheck() {
        return this.nocheck;
    }
    
    public TreeNode setNocheck(boolean nocheck) {
        this.nocheck = nocheck;
        return this;
    }
    
    public String getIcon() {
        return this.icon;
    }
    
    public TreeNode setIcon(String icon) {
        this.icon = icon;
        return this;
    }
    
    public boolean isDrag() {
        return this.drag;
    }
    
    public TreeNode setDrag(boolean drag) {
        this.drag = drag;
        return this;
    }
    
    public boolean isCopy() {
        return this.copy;
    }
    
    public TreeNode setCopy(boolean copy) {
        this.copy = copy;
        return this;
    }
    
    public boolean isDrop() {
        return this.drop;
    }
    
    public TreeNode setDrop(boolean drop) {
        this.drop = drop;
        return this;
    }
    
    public List<String> getValid_children() {
        return this.valid_children;
    }
    
    public TreeNode setValid_children(List<String> valid_children) {
        this.valid_children = valid_children;
        return this;
    }
    
    public TreeNodeType getType() {
        return this.type;
    }
    
    public TreeNode setType(TreeNodeType type) {
        this.type = type;
        return this;
    }
    
    public String getIconSkin() {
        return this.iconSkin;
    }
    
    public TreeNode setIconSkin(String iconSkin) {
        this.iconSkin = iconSkin;
        return this;
    }
    
    public void setAll(boolean drag,boolean copy,boolean checked,boolean drop){
        this.drag = drag;
        this.copy = copy;
        this.checked = checked;
        this.drop = drop;
    }

    public TreeNode() {
        super();
    }

    public TreeNode(String id, String pId, String name) {
        super();
        this.id = id;
        this.pId = pId;
        this.name = name;
    }
    
    public TreeNode(String id, String pId, String name, TreeNodeType type) {
        super();
        this.id = id;
        this.pId = pId;
        this.name = name;
        this.type = type;
    }

    public TreeNode(String id, String name) {
        super();
        this.id = id;
        this.name = name;
    }


    /**
     * 获取
     *
     * @return ids
     */
    public List<String> getIds() {
        return this.ids;
    }

    /**
     * 设置
     *
     * @param ids
     */
    public void setIds(List<String> ids) {
        this.ids = ids;
    }

    /**
     * 获取 原始类型
     *
     * @return originalType 原始类型
     */
    public String getOriginalType() {
        return this.originalType;
    }

    /**
     * 设置 原始类型
     *
     * @param originalType 原始类型
     */
    public void setOriginalType(String originalType) {
        this.originalType = originalType;
    }


    /**
     * 获取 树父id
     *
     * @return pId 树父id
     */
    public String getPId() {
        return this.pId;
    }

    /**
     * 设置 树父id
     *
     * @param pId 树父id
     */
    public void setPId(String pId) {
        this.pId = pId;
    }

    /**
     * 获取 节点编码
     *
     * @return code 节点编码
     */
    public String getCode() {
        return this.code;
    }

    /**
     * 设置 节点编码
     *
     * @param code 节点编码
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * 获取 节点显示名
     *
     * @return title 节点显示名
     */
    public String getTitle() {
        if(StringUtil.isNotEmpty(this.title)){
            return this.title;
        }else {
            if(StringUtil.isNotEmpty(this.code)){
                return this.code+"-"+this.name;
            }else{
                return this.name;
            }
        }
    }

    /**
     * 设置 节点显示名
     *
     * @param title 节点显示名
     */
    public void setTitle(String title) {
        this.title = title;
    }
}
