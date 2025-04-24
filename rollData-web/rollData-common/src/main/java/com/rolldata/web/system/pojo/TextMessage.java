package com.rolldata.web.system.pojo;

/**
 * 
 * @Title: TextMessage
 * @Description: 文本消息类
 * @Company:www.wrenchdata.com
 * @author zhaibx
 * @date 2019年6月1日
 * @version V1.0
 */
public class TextMessage extends BaseMessage{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4617544685319793310L;
	
	private String Content;
	
	public String getContent() {
		return Content;
	}
	public void setContent(String content) {
		Content = content;
	}

}
