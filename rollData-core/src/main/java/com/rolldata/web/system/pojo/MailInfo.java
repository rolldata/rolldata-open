package com.rolldata.web.system.pojo;

import org.apache.commons.mail.EmailAttachment;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @Title: MailInfo
 * @Description: 邮件交互实体类
 * @Company:www.wrenchdata.com
 * @author zhaibx
 * @date 2020年6月19日
 * @version V1.0
 */
public class MailInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 352299401202360621L;
	/** 多个收件人*/
    private List<String> toAddresses = null;
    /** 多个抄送人地址*/
    private List<String> ccAddresses = null;
    /** 多个密送人*/
    private List<String> bccAddresses = null;
    /** 多个附件信息*/
    private List<EmailAttachment> attachments = null;
    /** 邮件主题*/
    private String subject;
    /** 邮件的文本内容*/
    private String content;
    
    /** 收件人对象集合*/
    private List<MailUserInfo> toAddressObjs = null;
    
    /** 抄送人地址对象集合*/
    private List<MailUserInfo> ccAddressObjs = null;
    
    /** 密送人对象集合*/
    private List<MailUserInfo> bccAddressObjs = null;
    
    /** 单个收件人*/
    private String toAddress;
    
    /** 收件人对象*/
    private MailUserInfo toAddressObj;
    
    /** 抄送人地址对象*/
    private MailUserInfo ccAddressObj;
    
    /** 密送人对象*/
    private MailUserInfo bccAddressObj;
    
    /** 单个抄送人地址*/
    private String ccAddress;
    
    /** 单个密送人*/
    private String bccAddress;
    
    /**编码格式,默认UTF-8*/
    private String charset="UTF-8";
    
    /**
     * 单个附件
     */
    private EmailAttachment attachment;
    
    /**
     * 添加收件人
     * @param toAddresses
     */
    public void addToAddresses(List<String> toAddresses) {
    	if (null != toAddresses && toAddresses.size() > 0)
    		this.toAddresses.addAll(toAddresses);
    }

    /**
     * 添加抄送人
     * @param ccAddress
     */
    public void addCcAddresses(List<String> ccAddresses) {
        if (null != ccAddresses && ccAddresses.size() > 0)
            this.ccAddresses.addAll(ccAddresses);
    }

    /**
     * 获取附件
     * @return
     */
    public List<EmailAttachment> getAttachments() {
        return attachments;
    }

    /**
     * 设置附件
     * @param attachments
     */
    public void setAttachments(List<EmailAttachment> attachments) {
        this.attachments = attachments;
    }

    /**
     * 获取密送人
     * @return
     */
    public List<String> getBccAddresses() {
        return bccAddresses;
    }

    /**
     * 设置密送人
     * @param bccAddress
     */
    public void setBccAddresses(List<String> bccAddresses) {
        this.bccAddresses = bccAddresses;
    }

    /**
     * 获取邮件主题
     * @return
     */
    public String getSubject() {
        return subject;
    }

    /**
     * 设置邮件主题
     * @param subject
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * 获取邮件的文本内容
     * @return
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置邮件的文本内容
     * @param content
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * 设置收件人
     * @param toAddresses
     */
    public void setToAddresses(List<String> toAddresses) {
        this.toAddresses = toAddresses;
    }

    /**
     * 获取抄送人
     * @return
     */
    public List<String> getCcAddresses() {
        return ccAddresses;
    }

    /**
     * 设置抄送人
     * @param ccAddress
     */
    public void setCcAddresses(List<String> ccAddresses) {
        this.ccAddresses = ccAddresses;
    }

	/**  
	 * 获取收件人对象集合  
	 * @return toAddressObj 收件人对象集合  
	 */
	public List<MailUserInfo> getToAddressObjs() {
		return toAddressObjs;
	}

	/**  
	 * 设置收件人对象集合  
	 * @param toAddressObjs 收件人对象集合  
	 */
	public void setToAddressObjs(List<MailUserInfo> toAddressObjs) {
		this.toAddressObjs = toAddressObjs;
	}
	
	/**  
	 * 获取抄送人地址对象集合  
	 * @return ccAddressObj 抄送人地址对象集合  
	 */
	public List<MailUserInfo> getCcAddressObjs() {
		return ccAddressObjs;
	}
	
	/**  
	 * 设置抄送人地址对象集合  
	 * @param ccAddressObj 抄送人地址对象集合  
	 */
	public void setCcAddressObjs(List<MailUserInfo> ccAddressObjs) {
		this.ccAddressObjs = ccAddressObjs;
	}
	
	/**  
	 * 获取密送人对象集合  
	 * @return bccAddressObj 密送人对象集合  
	 */
	public List<MailUserInfo> getBccAddressObjs() {
		return bccAddressObjs;
	}

	/**  
	 * 设置密送人对象集合  
	 * @param bccAddressObj 密送人对象集合  
	 */
	public void setBccAddressObjs(List<MailUserInfo> bccAddressObjs) {
		this.bccAddressObjs = bccAddressObjs;
	}
	
	/**  
	 * 获取收件人  
	 * @return toAddress 收件人  
	 */
	public List<String> getToAddresses() {
		return toAddresses;
	}

	/**  
	 * 获取单个收件人  
	 * @return toAddress 单个收件人  
	 */
	public String getToAddress() {
		return toAddress;
	}

	/**  
	 * 设置单个收件人  
	 * @param toAddress 单个收件人  
	 */
	public void setToAddress(String toAddress) {
		this.toAddress = toAddress;
	}
	
	/**  
	 * 获取单个抄送人地址  
	 * @return ccAddress 单个抄送人地址  
	 */
	public String getCcAddress() {
		return ccAddress;
	}
	
	/**  
	 * 设置单个抄送人地址  
	 * @param ccAddress 单个抄送人地址  
	 */
	public void setCcAddress(String ccAddress) {
		this.ccAddress = ccAddress;
	}
	
	/**  
	 * 获取单个密送人  
	 * @return bccAddress 单个密送人  
	 */
	public String getBccAddress() {
		return bccAddress;
	}

	/**  
	 * 设置单个密送人  
	 * @param bccAddress 单个密送人  
	 */
	public void setBccAddress(String bccAddress) {
		this.bccAddress = bccAddress;
	}
	
	/**  
	 * 获取单个附件  
	 * @return attachment 单个附件  
	 */
	public EmailAttachment getAttachment() {
		return attachment;
	}

	/**  
	 * 设置单个附件  
	 * @param attachment 单个附件  
	 */
	public void setAttachment(EmailAttachment attachment) {
		this.attachment = attachment;
	}

	/**  
	 * 获取收件人对象  
	 * @return toAddressObj 收件人对象  
	 */
	public MailUserInfo getToAddressObj() {
		return toAddressObj;
	}

	/**  
	 * 设置收件人对象  
	 * @param toAddressObj 收件人对象  
	 */
	public void setToAddressObj(MailUserInfo toAddressObj) {
		this.toAddressObj = toAddressObj;
	}

	/**  
	 * 获取抄送人地址对象  
	 * @return ccAddressObj 抄送人地址对象  
	 */
	public MailUserInfo getCcAddressObj() {
		return ccAddressObj;
	}
	
	/**  
	 * 设置抄送人地址对象  
	 * @param ccAddressObj 抄送人地址对象  
	 */
	public void setCcAddressObj(MailUserInfo ccAddressObj) {
		this.ccAddressObj = ccAddressObj;
	}
	
	/**  
	 * 获取密送人对象  
	 * @return bccAddressObj 密送人对象  
	 */
	public MailUserInfo getBccAddressObj() {
		return bccAddressObj;
	}

	/**  
	 * 设置密送人对象  
	 * @param bccAddressObj 密送人对象  
	 */
	public void setBccAddressObj(MailUserInfo bccAddressObj) {
		this.bccAddressObj = bccAddressObj;
	}

	/**  
	 * 获取编码格式  
	 * @return charset 编码格式  
	 */
	public String getCharset() {
		return charset;
	}
	

	/**  
	 * 设置编码格式  
	 * @param charset 编码格式  
	 */
	public void setCharset(String charset) {
		this.charset = charset;
	}
	
	
}
