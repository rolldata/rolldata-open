package com.rolldata.core.common.model.json;

import com.alibaba.fastjson2.JSONObject;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 
 * @Title:AjaxJson
 * @Description:ajax调用返回类
 * @Company:www.wrenchdata.com
 * @author zhaibx
 * @date 2018-6-4
 * @version V1.0
 */
public class AjaxJson {

	/**
	 * 是否成功
	 */
	private boolean success = true;
	
	/**
	 * 提示信息
	 */
	private String msg = "操作成功";
	/**
	 * 其他信息
	 */
	private Object obj = null;
	/**
	 * 扩展数据
	 */
	private LinkedHashMap<String, Object> extend = new LinkedHashMap<String, Object>();
	/**
	 * 其他参数
	 */
	private Map<String, Object> attributes;
	
	/**
	 * 设置扩展数据
	 * @param key
	 * @param value
	 */
	public void put(String key, Object value) {
		extend.put(key, value);
	}

	/**
	 * 删除扩展数据
	 * @param key
	 */
	public void remove(String key) {
		extend.remove(key);
	}

	/**
	 * 获取扩展数据
	 * @return
	 */
	public LinkedHashMap<String, Object> getExtend() {
		return extend;
	}

	/**
	 * 设置扩展数据
	 * @param extend
	 */
	public void setExtend(LinkedHashMap<String, Object> extend) {
		this.extend = extend;
	}
	
	/**
	 * 获取其他参数
	 * @return
	 */
	public Map<String, Object> getAttributes() {
		return attributes;
	}

	/**
	 * 设置其他参数
	 * @param attributes
	 */
	public void setAttributes(Map<String, Object> attributes) {
		this.attributes = attributes;
	}

	/**
	 * 获取提示信息
	 * @return
	 */
	public String getMsg() {
		return msg;
	}

	/**
	 * 设置提示信息
	 * @param msg
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}

	/**
	 * 获取其他信息
	 * @return
	 */
	public Object getObj() {
		return obj;
	}

	/**
	 * 设置其他信息
	 * @param obj
	 */
	public void setObj(Object obj) {
		this.obj = obj;
	}

	/**
	 * 获取是否成功
	 * @return
	 */
	public boolean isSuccess() {
		return success;
	}

	/**
	 * 设置是否成功
	 * @param success
	 */
	public void setSuccess(boolean success) {
		this.success = success;
	}
	
	/**
	 * 设置是否成功和提示信息
	 * @param success
	 * @param msg
	 */
	public void setSuccessAndMsg(boolean success,String msg){
		this.success = success;
		this.msg = msg;
	}
	
	/**
	 * 获取json串
	 * @return
	 */
	public String getJsonStr(){
		JSONObject obj = new JSONObject();
		obj.put("success", this.isSuccess());
		obj.put("msg", this.getMsg());
		obj.put("obj", this.obj);
		obj.put("attributes", this.attributes);
		return obj.toJSONString();
	}
}
