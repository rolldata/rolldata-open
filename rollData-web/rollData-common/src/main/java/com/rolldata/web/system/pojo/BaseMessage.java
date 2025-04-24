package com.rolldata.web.system.pojo;

/**
 * 
 * @Title: BaseMessage
 * @Description: 消息体基础类
 * @Company:www.wrenchdata.com
 * @author zhaibx
 * @date 2019年6月1日
 * @version V1.0
 */
public class BaseMessage  implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7901809597669066686L;
	/**
	 * 开发者微信号
	 */
	private String ToUserName;
	/**
	 * 发送方帐号（一个OpenID）
	 */
	private String FromUserName;
	/**
	 * 消息创建时间 （整型）
	 */
	private long CreateTime;
	/**
	 * 消息类型，event
	 */
	private String MsgType;
	
	public String getToUserName() {
		return ToUserName;
	}
	public void setToUserName(String toUserName) {
		ToUserName = toUserName;
	}
	public String getFromUserName() {
		return FromUserName;
	}
	public void setFromUserName(String fromUserName) {
		FromUserName = fromUserName;
	}
	public long getCreateTime() {
		return CreateTime;
	}
	public void setCreateTime(long createTime) {
		CreateTime = createTime;
	}
	public String getMsgType() {
		return MsgType;
	}
	public void setMsgType(String msgType) {
		MsgType = msgType;
	}

}
