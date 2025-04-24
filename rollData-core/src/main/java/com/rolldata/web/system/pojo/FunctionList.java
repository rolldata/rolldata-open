package com.rolldata.web.system.pojo;

import java.util.List;

/** 
 * @Title: FunctionList
 * @Description: 菜单列表 给前台的json
 * @Company:www.wrenchdata.com
 * @author shenshilong
 * @date 2018/6/1
 * @version V1.0  
 */
public class FunctionList {
    
    /*ID*/
    private String id;
    
    /**功能名称*/
    private String funcName;

    /**功能链接地址*/
    private String hrefLink;

    /**状态0停用1启用*/
    private String functionState;

    /*权限按钮标志*/
    private Boolean haveButton = false;

    /*菜单下的按钮*/
    private List<ButtonPower> buttonChildren;

    /*是否选中*/
    private Boolean isChecked = false;

    /*菜单子节点*/
    private List<FunctionList> children;

    /*系统内外(0系统内1系统外)*/
    private String isSystem;

    /*0目录,1填报表单,2财务报表,3外部链接*/
    private String type;

    private String relationId;
    
    public String getId() {
        return id;
    }
    
    public FunctionList setId(String id) {
        this.id = id;
        return this;
    }
    
    public String getFuncName() {
        return funcName;
    }
    
    public FunctionList setFuncName(String funcName) {
        this.funcName = funcName;
        return this;
    }
    
    public String getHrefLink() {
        return hrefLink;
    }
    
    public FunctionList setHrefLink(String hrefLink) {
        this.hrefLink = hrefLink;
        return this;
    }
    
    public String getFunctionState() {
        return functionState;
    }
    
    public FunctionList setFunctionState(String functionState) {
        this.functionState = functionState;
        return this;
    }
    
    public List<FunctionList> getChildren() {
        return children;
    }
    
    public FunctionList setChildren(List<FunctionList> children) {
        this.children = children;
        return this;
    }
    
    public Boolean getChecked() {
        return isChecked;
    }
    
    public FunctionList setChecked(Boolean checked) {
        isChecked = checked;
        return this;
    }
    
    public List<ButtonPower> getButtonChildren() {
        return buttonChildren;
    }
    
    public FunctionList setButtonChildren(List<ButtonPower> buttonChildren) {
        this.buttonChildren = buttonChildren;
        return this;
    }
    
    public Boolean getHaveButton() {
        return haveButton;
    }
    
    public FunctionList setHaveButton(Boolean haveButton) {
        this.haveButton = haveButton;
        return this;
    }
    
    public String getIsSystem() {
        return isSystem;
    }
    
    public FunctionList setIsSystem(String isSystem) {
        this.isSystem = isSystem;
        return this;
    }
    
    public String getType() {
        return type;
    }
    
    public FunctionList setType(String type) {
        this.type = type;
        return this;
    }
    
    public String getRelationId() {
        return relationId;
    }
    
    public FunctionList setRelationId(String relationId) {
        this.relationId = relationId;
        return this;
    }
}
