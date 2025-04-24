package com.rolldata.web.common.pojo;

import java.io.Serializable;

/** 
 * @Title: BasePageInfo
 * @Description: 外层json基本信息
 * @Company:www.wrenchdata.com
 * @author shenshilong
 * @date 2018/7/10
 * @version V1.0  
 */
public class BasePageInfo implements Serializable{
    
    private static final long serialVersionUID = -1936831275011239427L;
    
    /*是否支持拖拽*/
    private boolean drag = false;
    
    /*是否支持复制*/
    private boolean copy = false;
    
    /*是否有复选框选项*/
    private boolean check = false;
    
    /*是否支持编辑*/
    private boolean edit = false;
    
    public boolean isCheck() {
        return this.check;
    }
    
    public BasePageInfo setCheck(boolean check) {
        this.check = check;
        return this;
    }
    
    public boolean isDrag() {
        return this.drag;
    }
    
    public BasePageInfo setDrag(boolean drag) {
        this.drag = drag;
        return this;
    }
    
    public boolean isCopy() {
        return this.copy;
    }
    
    public BasePageInfo setCopy(boolean copy) {
        this.copy = copy;
        return this;
    }
    
    public boolean isEdit() {
        return this.edit;
    }
    
    public BasePageInfo setEdit(boolean edit) {
        this.edit = edit;
        return this;
    }
    
    public void setAll(boolean drag,boolean copy,boolean check,boolean edit){
    	this.drag = drag;
    	this.copy = copy;
    	this.check = check;
    	this.edit = edit;
    }
}
