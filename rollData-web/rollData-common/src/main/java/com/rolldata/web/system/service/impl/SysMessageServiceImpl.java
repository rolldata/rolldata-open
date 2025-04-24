package com.rolldata.web.system.service.impl;

import com.rolldata.core.util.StringUtil;
import com.rolldata.web.system.dao.SysMessageDao;
import com.rolldata.web.system.dao.UserDao;
import com.rolldata.web.system.entity.SysMessage;
import com.rolldata.web.system.entity.SysUser;
import com.rolldata.web.system.pojo.MailInfo;
import com.rolldata.web.system.pojo.MessagePojo;
import com.rolldata.web.system.pojo.PageJson;
import com.rolldata.web.system.service.SysMessageService;
import com.rolldata.web.system.service.SystemService;
import com.rolldata.web.system.util.UserUtils;
import com.rolldata.web.system.util.email.EmailResult;
import com.rolldata.web.system.util.email.EmailUtil;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/** 
 * @Title: SysMessageServiceImpl
 * @Description: 系统消息操作 实现层
 * @Company:www.wrenchdata.com
 * @author shenshilong
 * @date 2018/9/19
 * @version V1.0  
 */
@Service("sysMessageService")
@Transactional
public class SysMessageServiceImpl implements SysMessageService {
	
	private Logger log = LogManager.getLogger(SysMessageServiceImpl.class);
			
	@Autowired
    private SysMessageDao sysMessageDao;
	
	@Autowired
	private SystemService systemService;
	
	@Autowired
	private UserDao userDao;
	 
	/**
	 * 查询个人所有消息
	 * @return
	 * @throws Exception
	 */
	@Override
	public PageJson queryPersonalMessage(PageJson pageJson) throws Exception {
		Pageable pageable = PageRequest.of(pageJson.getPageable(), pageJson.getSize());
		Page<SysMessage> page = sysMessageDao.querySysMessageByToUser(UserUtils.getUser().getId(),pageable);
		List<SysMessage> returnList = new ArrayList<SysMessage>();
		for (SysMessage sysMessages : page.getContent()) {
			returnList.add(sysMessages);
		}
		pageJson.setTotalElements(page.getTotalElements());
        pageJson.setTotalPagets(page.getTotalPages());
        pageJson.setResult(returnList);
		return pageJson;
	}

	/**
	 * 发送单条消息
	 * @param messagePojo 中title、type、content、toUser必须参数
	 * @throws Exception
	 */
	@Override
	public void saveOneSysMessage(MessagePojo messagePojo) throws Exception {
		SysMessage sysMessage = changeObj (messagePojo) ;
		sysMessage.setToUser(messagePojo.getToUser());
		//发送消息
		sendSysMessage(messagePojo,sysMessage);
	}

	/**
	 * 保存系统消息
	 * @param sysMessage
	 * @throws Exception
	 */
	@Override
	public void saveSysMessage(SysMessage sysMessage) throws Exception {
		//发送系统内消息
		sysMessageDao.saveAndFlush(sysMessage);
	}

	/**
	 * 给多人发送相同消息
	 * @param messagePojo 中title、type、content、toUsers必须参数
	 * @throws Exception
	 */
	@Override
	public void saveSysMessages(MessagePojo messagePojo) throws Exception {
		
		for(String userId : messagePojo.getToUsers()) {
			SysMessage sysMessage = changeObj (messagePojo) ;
			sysMessage.setToUser(userId);
			//批量发送不能抛出异常
			try {
				sendSysMessage(messagePojo,sysMessage);
			} catch (Exception e) {
				//记录日志
				 log.error("给多人发送相同消息操作失败：" + e.getMessage());
		         systemService.addErrorLog(e);
			}
		}
		
	}

	private SysMessage changeObj(MessagePojo messagePojo) {
		SysMessage sysMessage = new SysMessage();
		sysMessage.setType(messagePojo.getType());
		//默认未读
		sysMessage.setState(SysMessage.STATE_NOREAD);
		sysMessage.setCreateTime(new Date());
		sysMessage.setCreateUser(UserUtils.getUser().getId());
		sysMessage.setUpdateTime(new Date());
		sysMessage.setUpdateUser(UserUtils.getUser().getId());
		return sysMessage;
	}

	private void sendSysMessage(MessagePojo messagePojo, SysMessage sysMessage) throws Exception{
		String sysMessageType = messagePojo.getType();
		//根据类型区分不同消息模板
		if (SysMessage.TYPE_SYS.equals(sysMessageType)) {
			sysMessage.setTitle("【系统消息】" + messagePojo.getTitle());
		} else if (SysMessage.TYPE_APPLY.equals(sysMessageType)) {
			sysMessage.setTitle("【任务填报】"+ messagePojo.getTitle());
		} else if (SysMessage.TYPE_DEAL.equals(sysMessageType)) {
			sysMessage.setTitle("【任务办理】"+ messagePojo.getTitle());
		} else if (SysMessage.TYPE_BACK.equals(sysMessageType)) {
			sysMessage.setTitle("【任务回退】"+ messagePojo.getTitle());
		} else if (SysMessage.TYPE_REFILL.equals(sysMessageType)) {
			sysMessage.setTitle("【任务重报】"+ messagePojo.getTitle());
		} else if (SysMessage.TYPE_DPF.equals(sysMessageType)) {
			sysMessage.setTitle("【数据处理】"+ messagePojo.getTitle());
		} else if (SysMessage.TYPE_REMINDER.equals(sysMessageType)) {
            sysMessage.setTitle("【任务催报】"+ messagePojo.getTitle());
        } else if (SysMessage.TYPE_WITHDRAW.equals(sysMessageType)) {
			sysMessage.setTitle("【撤回提醒】"+ messagePojo.getTitle());
		} else if (SysMessage.TYPE_OTHER.equals(sysMessageType)) {
			sysMessage.setTitle(messagePojo.getTitle());
		}
		sysMessage.setContent(messagePojo.getContent());
		//是否发送系统消息
		if(messagePojo.getIsSendSystem()) {
			//发送系统内消息
			sysMessageDao.saveAndFlush(sysMessage);
		}
		//是否发送邮件
		if(messagePojo.getIsSendEmail()) {
			try {
				MailInfo mailInfo = new MailInfo();
				mailInfo.setSubject(sysMessage.getTitle());
				//根据登录名查询用户，判断邮箱是否存在并一致
				String toAddress = "";
				String userName = "";
				if(StringUtil.isNotEmpty(sysMessage.getToUser())) {
					SysUser user = userDao.querySysUserById(sysMessage.getToUser());
					toAddress = user.getMail();
					userName = user.getUserName()+"，";
				}else if(StringUtil.isNotEmpty(messagePojo.getToAddress())) {
					toAddress = messagePojo.getToAddress();
				}
				String content = "<html lang=\"en\">" +
						"<head>" +
						"    <meta charset=\"UTF-8\">" +
						"    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">" +
						"    <meta http-equiv=\"X-UA-Compatible\" content=\"ie=edge\">" +
						"    <title>"+sysMessage.getTitle()+"</title>" +
						"    <style>" +
						"    </style>" +
						"</head>" +
						"<body style=\"margin: 0;padding:0;width:100%;height:100%;background: #f1f1f1\">" +
						"    <div class=\"big\" style=\"width: 100%;height: 500px;padding: 0;margin: 0;box-sizing: border-box;padding-top: 40px;background: #f1f1f1;padding-bottom: 220px;\">" +
						"        <div class=\"title\" style=\"margin:0 5%;padding:7px 20px;width: 80%;height:25px;line-height:25px;background: #009688;" +
						"        color:#fff;font-family: '黑体';font-weight: bold\"></div>" +
						"        <div style=\"margin:0 5%;padding:0px 20px;width: 80%;height: 210px;background: #ffffff;border: 1px solid #e5e5e5\">     " +
						"            <h3 style=\"font-weight: normal;font-size: 14px;color:sandybrown\">"+userName+"您好：</h3>" +
						"            <div style=\"font-size: 12px;text-indent: 2em;line-height: 20px;padding-bottom: 40px;\">" + sysMessage.getContent() +
						"            </div>" +
						"        </div>" +
						"    </div>" +
						"</body>" +
						"</html>";
				mailInfo.setContent(content);
				if(StringUtil.isNotEmpty(toAddress)) {
					mailInfo.setToAddress(toAddress);
					if(StringUtil.isNotEmpty(messagePojo.getCcAddress())) {
						mailInfo.setCcAddress(messagePojo.getCcAddress());
					}
					EmailUtil email = new EmailUtil();
					EmailResult result = email.sendEmail(mailInfo);
					if(result.isSuccess()) {
						//记录日志
					}else {
						throw new Exception(result.getMsg());
					}
				}
//				else {
//					throw new Exception(MessageUtils.getMessageOrSelf("common.email.notnull",user.getUserCde()));
//				}
			} catch (Exception e) {
				//记录日志
				 log.error("发送消息同时发送邮件失败：" + e.getMessage());
		         systemService.addErrorLog(e);
			}
		}
	}

	/**
	 * 个人清除消息
	 * @throws Exception
	 */
	@Override
	public void deletePersonalMessage() throws Exception {
		sysMessageDao.deleteSysMessageByToUser(UserUtils.getUser().getId());
	}

	/**
	 * 标记已读状态
	 * @param messagePojo
	 * @throws Exception
	 */
	@Override
	public void updateSysMessage(MessagePojo messagePojo) throws Exception {
		
		// 如果是非系统消息,把同样类型的一批数据全更新成已读
		List<SysMessage> sysMessages = sysMessageDao.querySysMessageById(messagePojo.getId(), UserUtils.getUser().getId());
		if (null == sysMessages) {
			return;
		}
		if (sysMessages.size() > 0) {
			List<String> ids = new ArrayList<>();
			sysMessages.forEach(msg -> ids.add(msg.getId()));
			sysMessageDao.updateSysMessageStateByIds(SysMessage.STATE_READ,new Date(),UserUtils.getUser().getId(),ids);
		} else {
			sysMessageDao.updateSysMessageStateById(SysMessage.STATE_READ,new Date(),UserUtils.getUser().getId(),messagePojo.getId());
		}
	}

    /**
     * 查询个人消息数量
     *
     * @return 消息数量
     * @throws Exception
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = false, rollbackFor = Exception.class)
    public int queryPersonalMessageNum () throws Exception {

        return sysMessageDao.queryPersonalMessageNum(UserUtils.getUser().getId());
    }
}
