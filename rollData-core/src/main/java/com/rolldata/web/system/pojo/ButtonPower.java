package com.rolldata.web.system.pojo;

import java.io.Serializable;

/**
 * @Title: ButtonPower
 * @Description: 菜单下的按钮
 * @Company:www.wrenchdata.com
 * @author shenshilong
 * @date 2018/6/4
 * @version V1.0  
 */
public class ButtonPower implements Serializable {

    private static final long serialVersionUID = -9195478774258048569L;

    /*按钮名称*/
    private String name;

    /*角色权限表的id*/
    private String buttonPowerId;

    /*是否选中*/
    private Boolean isChecked = false;
    
    public String getName() {
        return name;
    }
    
    public ButtonPower setName(String name) {
        this.name = name;
        return this;
    }
    
    public String getButtonPowerId() {
        return buttonPowerId;
    }
    
    public ButtonPower setButtonPowerId(String buttonPowerId) {
        this.buttonPowerId = buttonPowerId;
        return this;
    }
    
    public Boolean getChecked() {
        return isChecked;
    }
    
    public ButtonPower setChecked(Boolean checked) {
        isChecked = checked;
        return this;
    }
}
