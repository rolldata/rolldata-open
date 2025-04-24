package com.rolldata.web.system.pojo;

import java.util.List;

/** 
 * @Title: Result
 * @Description: 结果集
 * @Company:www.wrenchdata.com
 * @author shenshilong
 * @date 2018/6/4
 * @version V1.0  
 */
public class Result {
    
    /*结果集*/
    private List result;
    
    private Object resultObj;
    
    public Result() {
    }
    
    public Result(List result) {
    	super();
        this.result = result;
    }
    
    public Result(Object resultObj) {
		super();
		this.resultObj = resultObj;
	}

	public List getResult() {
        return result;
    }
    
    public Result setResult(List result) {
        this.result = result;
        return this;
    }

	/**  
	 * 获取resultObj  
	 * @return resultObj resultObj  
	 */
	public Object getResultObj() {
		return resultObj;
	}
	

	/**  
	 * 设置resultObj  
	 * @param resultObj resultObj  
	 */
	public Result setResultObj(Object resultObj) {
		this.resultObj = resultObj;
		return this;
	}
	
    
    
}
