package com.rolldata.web.system.entity;

import com.rolldata.core.common.entity.IdEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/** 
 * @Title: SysFunctionOper
 * @Description: 功能明细表
 * @Company:www.wrenchdata.com
 * @author shenshilong
 * @date 2018/6/4
 * @version V1.0  
 */
@Entity
@Table(name = "wd_sys_function_oper")
@DynamicUpdate(true)
@DynamicInsert(true)
public class SysFunctionOper extends IdEntity implements java.io.Serializable {
    
	/**
	 * 
	 */
	private static final long serialVersionUID = -7622265965181855528L;

	/**功能id*/
	private String funcId;
	
	/**功能详细名称*/
	private String operName;
	
	/**权限标识*/
	private String powerFlag;
	
	/*创建时间*/
	private Date createTime;
    
    @Column(name = "func_id", length = 32)
    public String getFuncId() {
        return funcId;
    }
    
    public SysFunctionOper setFuncId(String funcId) {
        this.funcId = funcId;
        return this;
    }
    
    @Column(name = "oper_name", length = 20)
    public String getOperName() {
        return operName;
    }
    
    public SysFunctionOper setOperName(String operName) {
        this.operName = operName;
        return this;
    }
    
    @Column(name = "power_flag", length = 100)
    public String getPowerFlag() {
        return powerFlag;
    }
    
    public SysFunctionOper setPowerFlag(String powerFlag) {
        this.powerFlag = powerFlag;
        return this;
    }
    
    @Column(name = "create_time")
    public Date getCreateTime() {
        return createTime;
    }
    
    public SysFunctionOper setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }
}
