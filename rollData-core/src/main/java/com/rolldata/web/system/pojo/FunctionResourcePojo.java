package com.rolldata.web.system.pojo;

import java.util.List;

/**
 * @Title: FunctionResourcePojo
 * @Description: FunctionResourcePojo
 * @Company: www.wrenchdata.com
 * @author: zhaibx
 * @date: 2021-04-21
 * @version: V1.0
 */
public class FunctionResourcePojo implements java.io.Serializable{

    private static final long serialVersionUID = -4353528015089730758L;

    /**树id*/
    private String id;

    /**树父id*/
    private String pId;

    /**节点名称*/
    private String name;

    /**图标class名称*/
    private String iconSkin;

    /**类型*/
    private String type;

    /*菜单id*/
    private String funcId;

    /*菜单链接*/
    private String hrefLink;

    /**链接类型，0菜单，1树*/
    private String hrefType;

    /**系统内外(0系统内1系统外)*/
    private String isSystem;

    /**关联的资源id*/
    private String rsid;

    /**子级节点*/
    private List<FunctionResourcePojo> children;

    /**资源链接*/
    private String rsUrl;

    /**
     * 获取 树id
     *
     * @return id 树id
     */
    public String getId() {
        return this.id;
    }

    /**
     * 设置 树id
     *
     * @param id 树id
     */
    public void setId(String id) {
        this.id = id;
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
     * 获取 节点名称
     *
     * @return name 节点名称
     */
    public String getName() {
        return this.name;
    }

    /**
     * 设置 节点名称
     *
     * @param name 节点名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取 图标class名称
     *
     * @return iconSkin 图标class名称
     */
    public String getIconSkin() {
        return this.iconSkin;
    }

    /**
     * 设置 图标class名称
     *
     * @param iconSkin 图标class名称
     */
    public void setIconSkin(String iconSkin) {
        this.iconSkin = iconSkin;
    }

    /**
     * 获取 类型
     *
     * @return type 类型
     */
    public String getType() {
        return this.type;
    }

    /**
     * 设置 类型
     *
     * @param type 类型
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 获取 菜单id
     *
     * @return funcId 菜单id
     */
    public String getFuncId() {
        return this.funcId;
    }

    /**
     * 设置 菜单id
     *
     * @param funcId 菜单id
     */
    public void setFuncId(String funcId) {
        this.funcId = funcId;
    }

    /**
     * 获取 菜单链接
     *
     * @return hrefLink 菜单链接
     */
    public String getHrefLink() {
        return this.hrefLink;
    }

    /**
     * 设置 菜单链接
     *
     * @param hrefLink 菜单链接
     */
    public void setHrefLink(String hrefLink) {
        this.hrefLink = hrefLink;
    }

    /**
     * 获取 链接类型，0菜单，1树
     *
     * @return hrefType 链接类型，0菜单，1树
     */
    public String getHrefType() {
        return this.hrefType;
    }

    /**
     * 设置 链接类型，0菜单，1树
     *
     * @param hrefType 链接类型，0菜单，1树
     */
    public void setHrefType(String hrefType) {
        this.hrefType = hrefType;
    }

    /**
     * 获取 系统内外(0系统内1系统外)
     *
     * @return isSystem 系统内外(0系统内1系统外)
     */
    public String getIsSystem() {
        return this.isSystem;
    }

    /**
     * 设置 系统内外(0系统内1系统外)
     *
     * @param isSystem 系统内外(0系统内1系统外)
     */
    public void setIsSystem(String isSystem) {
        this.isSystem = isSystem;
    }

    /**
     * 获取 关联的资源id
     *
     * @return rsid 关联的资源id
     */
    public String getRsid() {
        return this.rsid;
    }

    /**
     * 设置 关联的资源id
     *
     * @param rsid 关联的资源id
     */
    public void setRsid(String rsid) {
        this.rsid = rsid;
    }

    /**
     * 获取 子级节点
     *
     * @return children 子级节点
     */
    public List<FunctionResourcePojo> getChildren() {
        return this.children;
    }

    /**
     * 设置 子级节点
     *
     * @param children 子级节点
     */
    public void setChildren(List<FunctionResourcePojo> children) {
        this.children = children;
    }

    /**
     * 获取 资源链接
     *
     * @return rsUrl 资源链接
     */
    public String getRsUrl() {
        return this.rsUrl;
    }

    /**
     * 设置 资源链接
     *
     * @param rsUrl 资源链接
     */
    public void setRsUrl(String rsUrl) {
        this.rsUrl = rsUrl;
    }
}
