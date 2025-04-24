package com.rolldata.web.system.service;

import com.rolldata.web.system.entity.SysMessage;
import com.rolldata.web.system.pojo.MessagePojo;
import com.rolldata.web.system.pojo.PageJson;

/** 
 * @Title: SysMessageService
 * @Description: 系统消息操作接口
 * @Company:www.wrenchdata.com
 * @author shenshilong
 * @date 2018/9/19
 * @version V1.0  
 */
public interface SysMessageService {

	/**
	 * 查询个人所有消息
	 * @param pageJson 
	 * @return
	 * @throws Exception
	 */
	public PageJson queryPersonalMessage(PageJson pageJson) throws Exception;
    
	/**
	 * 发送单条消息
	 * @param messagePojo 中title、type、content、toUser必须参数
	 * @throws Exception
	 */
	public void saveOneSysMessage(MessagePojo messagePojo) throws Exception;

	/**
	 * 保存系统消息
	 * @param sysMessage
	 * @throws Exception
	 */
	public void saveSysMessage(SysMessage sysMessage) throws Exception;

	/**
	 * 给多人发送相同消息
	 * @param messagePojo 中title、type、content、toUser必须参数
	 * @throws Exception
	 */
	public void saveSysMessages(MessagePojo messagePojo) throws Exception;

	/**
	 * 个人清除消息
	 * @throws Exception
	 */
	public void deletePersonalMessage() throws Exception;

	/**
	 * 标记已读状态
	 * @param messagePojo
	 * @throws Exception
	 */
	public void updateSysMessage(MessagePojo messagePojo) throws Exception;

	/**
	 * 查询个人消息数量
     *
	 * @return 消息数量
	 * @throws Exception
	 */
    int queryPersonalMessageNum () throws Exception;
}
