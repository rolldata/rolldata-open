package com.rolldata.web.system.util.email;

import com.rolldata.core.util.MessageUtils;
import com.rolldata.core.util.StringUtil;
import com.rolldata.web.system.Constants;
import com.rolldata.web.system.pojo.EmailAttachmentPojo;
import com.rolldata.web.system.pojo.MailInfo;
import com.rolldata.web.system.pojo.MailManagePojo;
import com.rolldata.web.system.pojo.MailUserInfo;
import com.rolldata.web.system.util.PasswordUtil;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class EmailUtil extends HtmlEmail {
	public static Logger log = LogManager.getLogger(EmailUtil.class);

	public static EmailUtil newEmail() {
		return new EmailUtil();
	}

	public void initConfig() throws EmailException {
		// 初始化配置
		MailManagePojo mailManageMap = (MailManagePojo) Constants.property.get(Constants.mailManageInfo);
		if(StringUtil.isEmpty(mailManageMap.getVerify()) || mailManageMap.getVerify().equals("0")) {
			throw new EmailException(MessageUtils.getMessage("common.email.send.test.fail"));
		}
		setHostName(mailManageMap.getSMTPServer());
		setSmtpPort(Integer.parseInt(mailManageMap.getServerPort()));
		//解密密码
		byte[] salt = PasswordUtil.getStaticSalt();
		setAuthenticator(new DefaultAuthenticator(mailManageMap.getSenderAddress(),
				PasswordUtil.decrypt(mailManageMap.getPassword(), mailManageMap.getSenderAddress(),salt)));
		String ssl = mailManageMap.getSslIs();
        if (ssl.equals("1")) {
            setSSLOnConnect(true);
        }
        // 设置主题的字符集为UTF-8
        setCharset("UTF-8");
		setFrom(mailManageMap.getSenderAddress(), mailManageMap.getDisplayName());
	}
	
	public EmailResult testSend(MailManagePojo mailManageMap,String toEmail, String subject, String content) {
		try {
			setHostName(mailManageMap.getSMTPServer());
			setSmtpPort(Integer.parseInt(mailManageMap.getServerPort()));
			if(mailManageMap.getVerify().equals("1")) {
				//解密密码
				byte[] salt = PasswordUtil.getStaticSalt();
				setAuthenticator(new DefaultAuthenticator(mailManageMap.getSenderAddress(),
						PasswordUtil.decrypt(mailManageMap.getPassword(), mailManageMap.getSenderAddress(),salt)));
			}else {
				setAuthenticator(new DefaultAuthenticator(mailManageMap.getSenderAddress(),mailManageMap.getPassword()));
			}
			String ssl = mailManageMap.getSslIs();
	        if (ssl.equals("1")) {
	            setSSLOnConnect(true);
	        }
	        // 设置主题的字符集为UTF-8
	        setCharset("UTF-8");
			setFrom(mailManageMap.getSenderAddress(), mailManageMap.getDisplayName());
			addTo(toEmail);
			if (!StringUtil.isEmpty(subject)) {
				setSubject(subject);
			}
			setHtmlMsg(content);
			send();
			return EmailResult.success("发送成功");
		} catch (EmailException e) {
			e.printStackTrace();
			String message ="";
			if(e.getMessage().indexOf("Invalid Addresses") != -1){
				message = "请检查邮箱地址格式";
			}else if(e.getMessage().indexOf("Connection timed out") != -1){
				message = "连接超时,请检查服务器配置与您的网络";
			}else if(e.getMessage().indexOf("Unknown SMTP host") != -1){
				message = "请检查服务器配置与您的网络";
			}else if(e.getMessage().indexOf("Could not connect to SMTP host") != -1){
				message = "连接服务器失败,请检查服务器配置与您的网络";
//			}else if(e.getNextException().toString().indexOf("FileNotFoundException") != -1){
//				message = "系统找不到指定的文件";
			}else{
				message = "发送失败："+e.getMessage();
			}
			return EmailResult.fail(message);
		}
	}

	public EmailResult send(String toEmail, String subject, String content) {
		try {
			initConfig();
			addTo(toEmail);
			if (!StringUtil.isEmpty(subject)) {
				setSubject(subject);
			}
			setHtmlMsg(content);
			send();
			return EmailResult.success("发送成功");
		} catch (Exception e) {
			e.printStackTrace();
			log.error("邮件发送失败：" + e.getMessage(),e);
			return EmailResult.fail("发送失败");
		}
	}
	
	public EmailResult send(String toEmail, String userName, String subject, String content, EmailAttachmentPojo emailAttachmentPojo) {
		try {
			initConfig();
			addTo(toEmail,userName);
			if (!StringUtil.isEmpty(subject)) {
				setSubject(subject);
			}

			addAttachement(emailAttachmentPojo);
			setHtmlMsg(content);
			send();

			return EmailResult.success("发送成功");
		} catch (Exception e) {
			e.printStackTrace();
			return EmailResult.fail("发送失败");
		}
	}

	private void addAttachement(EmailAttachmentPojo emailAttachmentPojo) throws EmailException {

		if (null == emailAttachmentPojo) {
			return;
		}

		// 创建一个EmailAttachment对象，并设置附件的相关属性
		EmailAttachment attachment = new EmailAttachment();
		attachment.setPath(emailAttachmentPojo.getFilePath());
		attachment.setDisposition(EmailAttachment.ATTACHMENT);
		attachment.setDescription("Description of attachment");
		attachment.setName(emailAttachmentPojo.getFileName());

		// 将附件添加到邮件中
		attach(attachment);
	}

	/**
     * 发送 邮件方法 (Html格式，支持附件)
     * 
     * @return void
     */
    public EmailResult sendEmails(MailInfo mailInfo) {
    	try {
    		// 配置信息
    		initConfig();
    		setCharset(mailInfo.getCharset());
    		if (!StringUtil.isEmpty(mailInfo.getSubject())) {
				setSubject(mailInfo.getSubject());
			}
    		setHtmlMsg(mailInfo.getContent());

            // 添加附件
            List<EmailAttachment> attachments = mailInfo.getAttachments();
            if (null != attachments && attachments.size() > 0) {
                for (int i = 0; i < attachments.size(); i++) {
                    attach(attachments.get(i));
                }
            }
            
            // 收件人
            List<String> toAddress = mailInfo.getToAddresses();
            if (null != toAddress && toAddress.size() > 0) {
                for (int i = 0; i < toAddress.size(); i++) {
                        addTo(toAddress.get(i));
                }
            }
            // 抄送人
            List<String> ccAddress = mailInfo.getCcAddresses();
            if (null != ccAddress && ccAddress.size() > 0) {
                for (int i = 0; i < ccAddress.size(); i++) {
                        addCc(ccAddress.get(i));
                }
            }
            //邮件模板 密送人
            List<String> bccAddress = mailInfo.getBccAddresses();
            if (null != bccAddress && bccAddress.size() > 0) {
                for (int i = 0; i < bccAddress.size(); i++) {
                    addBcc(ccAddress.get(i));
                }
            }
            send();
            return EmailResult.success("发送成功");
        } catch (EmailException e) {
            e.printStackTrace();
            return EmailResult.fail("发送失败");
        } 
    }
    /**
     * 发送 邮件方法 (Html格式，支持附件)
     * 
     * @return void
     */
    public EmailResult sendEmailObjs(MailInfo mailInfo) {
    	try {
    		// 配置信息
    		initConfig();
    		setCharset(mailInfo.getCharset());
    		if (!StringUtil.isEmpty(mailInfo.getSubject())) {
				setSubject(mailInfo.getSubject());
			}
    		setHtmlMsg(mailInfo.getContent());

            // 添加附件
            List<EmailAttachment> attachments = mailInfo.getAttachments();
            if (null != attachments && attachments.size() > 0) {
                for (int i = 0; i < attachments.size(); i++) {
                    attach(attachments.get(i));
                }
            }
            
            // 收件人
            List<MailUserInfo> toAddress = mailInfo.getToAddressObjs();
            if (null != toAddress && toAddress.size() > 0) {
                for (int i = 0; i < toAddress.size(); i++) {
                        addTo(toAddress.get(i).getToAddress(),toAddress.get(i).getDisplayName());
                }
            }
            // 抄送人
            List<MailUserInfo> ccAddress = mailInfo.getCcAddressObjs();
            if (null != ccAddress && ccAddress.size() > 0) {
                for (int i = 0; i < ccAddress.size(); i++) {
                        addCc(ccAddress.get(i).getToAddress(),ccAddress.get(i).getDisplayName());
                }
            }
            //邮件模板 密送人
            List<MailUserInfo> bccAddress = mailInfo.getBccAddressObjs();
            if (null != bccAddress && bccAddress.size() > 0) {
                for (int i = 0; i < bccAddress.size(); i++) {
                    addBcc(ccAddress.get(i).getToAddress(),ccAddress.get(i).getDisplayName());
                }
            }
            send();
            return EmailResult.success("发送成功");
        } catch (EmailException e) {
            e.printStackTrace();
            return EmailResult.fail("发送失败");
        } 
    }
    /**
     * 发送 邮件方法 (Html格式，支持附件)
     * 
     * @return void
     */
    public EmailResult sendEmail(MailInfo mailInfo) {
    	try {
    		// 配置信息
    		initConfig();
    		setCharset(mailInfo.getCharset());
    		if (StringUtil.isNotEmpty(mailInfo.getSubject())) {
				setSubject(mailInfo.getSubject());
			}
    		setHtmlMsg(mailInfo.getContent());

            // 添加附件
    		if(StringUtil.isNotEmpty(mailInfo.getAttachment())) {
    			attach(mailInfo.getAttachment());
    		}
            
            // 收件人
            addTo(mailInfo.getToAddress());
            // 抄送人
            if(StringUtil.isNotEmpty(mailInfo.getCcAddress())) {
            	addCc(mailInfo.getCcAddress());
            }
            //邮件模板 密送人
            if(StringUtil.isNotEmpty(mailInfo.getBccAddress())){
                addBcc(mailInfo.getBccAddress());
    		}
            send();
            return EmailResult.success("发送成功");
        } catch (EmailException e) {
            e.printStackTrace();
            return EmailResult.fail(e.getMessage());
        } 
    }
    
    /**
     * 发送 邮件方法 (Html格式，支持附件)
     * 
     * @return void
     */
    public EmailResult sendEmailObj(MailInfo mailInfo) {
    	try {
    		// 配置信息
    		initConfig();
    		setCharset(mailInfo.getCharset());
    		if (StringUtil.isNotEmpty(mailInfo.getSubject())) {
				setSubject(mailInfo.getSubject());
			}
    		setHtmlMsg(mailInfo.getContent());

            // 添加附件
    		if(StringUtil.isNotEmpty(mailInfo.getAttachment())) {
    			attach(mailInfo.getAttachment());
    		}
            
            // 收件人
    		if(StringUtil.isNotEmpty(mailInfo.getToAddressObj())) {
    			if(StringUtil.isNotEmpty(mailInfo.getToAddressObj().getDisplayName())) {
    				addTo(mailInfo.getToAddressObj().getToAddress(),mailInfo.getToAddressObj().getDisplayName());
    			}else {
        			addTo(mailInfo.getToAddressObj().getToAddress());
        		}
    		}else {
    			return EmailResult.fail("发送失败");
    		}
            // 抄送人
            if(StringUtil.isNotEmpty(mailInfo.getCcAddressObj())) {
            	if(StringUtil.isNotEmpty(mailInfo.getCcAddressObj().getDisplayName())) {
            		addCc(mailInfo.getCcAddressObj().getToAddress(),mailInfo.getCcAddressObj().getDisplayName());
            	}else {
            		addCc(mailInfo.getCcAddressObj().getToAddress());
            	}
            }
            
            //邮件模板 密送人
            if(StringUtil.isNotEmpty(mailInfo.getBccAddressObj())){
            	if(StringUtil.isNotEmpty(mailInfo.getBccAddressObj().getDisplayName())) {
            		addBcc(mailInfo.getBccAddressObj().getToAddress(),mailInfo.getBccAddressObj().getDisplayName());
            	}else {
            		addBcc(mailInfo.getBccAddressObj().getToAddress());
            	}
    		}
            send();
            return EmailResult.success("发送成功");
        } catch (EmailException e) {
            e.printStackTrace();
            return EmailResult.fail("发送失败");
        } 
    }
}
