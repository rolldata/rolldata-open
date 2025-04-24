package com.rolldata.web.system.pojo;

import com.rolldata.web.system.entity.SysFunctionOper;

import java.io.Serializable;
import java.util.List;

/**
 * @Title: SysFuncButtons
 * @Description: SysFuncButtons
 * @Company: www.wrenchdata.com
 * @author: shenshilong
 * @date: 2020-06-21
 * @version: V1.0
 */
public class SysFuncButtons implements Serializable {

    private static final long serialVersionUID = 5565781906260193481L;

    private String funcId;

    private List<SysFunctionOper> buttons;

    public String getFuncId() { return funcId;}

    public void setFuncId(String funcId) { this.funcId = funcId;}

    public List<SysFunctionOper> getButtons() { return buttons;}

    public void setButtons(List<SysFunctionOper> buttons) { this.buttons = buttons;}

}
