package com.rolldata.web.system.service.impl;

import com.rolldata.core.util.*;
import com.rolldata.web.system.Constants;
import com.rolldata.web.system.dao.SysConfigDao;
import com.rolldata.web.system.entity.*;
import com.rolldata.web.system.pojo.*;
import com.rolldata.web.system.service.SystemService;
import com.rolldata.web.system.util.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import java.util.List;

import static com.rolldata.web.system.entity.SysConfig.JSP_UPDATE_VERSION;

@Service("systemService")
@Transactional
public class SystemServiceImpl implements SystemService {

	private Logger log = LogManager.getLogger(SystemServiceImpl.class);

	@Autowired
    private SysConfigDao sysConfigDao;
	
	//记录数据库最大字符长度
	private static final int WIRTE_DB_MAX_LENGTH = 1500;


	private EntityManagerFactory emf;

	// 使用这个标记来注入EntityManagerFactory
	@PersistenceUnit
	public void setEntityManagerFactory(EntityManagerFactory emf) {
		this.emf = emf;
	}

	/**
	 * 添加日志
	 */
	@Override
	public void addLog(String logTitle,String logContent, Short logLevel) throws Exception {
		// 记录登录日志
		LogUtils.saveLog(ContextHolderUtils.getRequest(),logTitle, logContent,logLevel);
	}
	
	/**
	 * 添加异常日志
	 */
	@Override
	public void addErrorLog(Throwable ex) throws Exception {
		
		String exceptionMessage = "错误异常: "+ex.getClass().getSimpleName()+",错误描述："+ex.getMessage();
		if(oConvertUtils.isNotEmpty(exceptionMessage)){
			if(exceptionMessage.length() > WIRTE_DB_MAX_LENGTH){
				exceptionMessage = exceptionMessage.substring(0,WIRTE_DB_MAX_LENGTH);
			}
		}
		// 记录登录日志
		LogUtils.saveLog(ContextHolderUtils.getRequest(),"系统异常", exceptionMessage,SysLog.LEVEL1);
	}
	
	/**
	 * 查询系统配置信息
	 * @throws Exception
	 */
	@Override
	public void querySysConfigInfo() throws Exception {
		MailManagePojo mailManagePojo = new MailManagePojo();
		PasswordSecurityPojo passwordSecurityPojo = new PasswordSecurityPojo();
		SysConfigPojo sysConfigPojo = new SysConfigPojo();
		AppearanceConfigPojo appearanceConfigPojo = new AppearanceConfigPojo();
		try {
			List<SysConfig> sysConfigList = sysConfigDao.findAll();
			for (int i = 0; i < sysConfigList.size(); i++) {
				SysConfig sysConfig = sysConfigList.get(i);
				String value = sysConfig.getValue();
				switch (sysConfig.getName()) {
				case SysConfig.PASSWORD_LENGTH:
					passwordSecurityPojo.setPsdLength(value);
					break;
				case SysConfig.PASSWORD_SETTING:
					passwordSecurityPojo.setPsdSetting(value);
					break;
				case SysConfig.PASSWORD_PERIOD:
					passwordSecurityPojo.setPsdPeriod(value);
					break;
				case SysConfig.PASSWORD_REQUEST:
					passwordSecurityPojo.setPsdRequest(value);
					break;
				case SysConfig.PASSWORD_ERRNUMBER:
					passwordSecurityPojo.setPsdErrNumber(value);
					break;
				case SysConfig.PASSWORD_PERIOD_IS:
					passwordSecurityPojo.setPsdPeriodIs(value);
					break;
				case SysConfig.PASSWORD_ERRNUMBER_IS:
					passwordSecurityPojo.setPsdErrNumberIs(value);
					break;
				case SysConfig.PASSWORD_VALIDATE_IS:
					passwordSecurityPojo.setPsdValidateIs(value);
					break;
				case SysConfig.PASSWORD_INIT_IS:
					passwordSecurityPojo.setPsdInitIs(value);
					break;	
				case SysConfig.LOGIN_LIMIT:
					passwordSecurityPojo.setIsLimitLogin(value);
					break;
				case SysConfig.BROWSE_PASSWORD_IS:
					passwordSecurityPojo.setIsBrowsePassword(value);
					break;
				case SysConfig.BROWSE_PASSWORD:
					passwordSecurityPojo.setBrowsePassword(value);
					break;
				case SysConfig.LOGINNUM_PASSWORD_IS:
					passwordSecurityPojo.setIsLoginNum(value);
					break;
				case SysConfig.LOGINNUM_PASSWORD:
					passwordSecurityPojo.setLoginNum(value);
					break;
				case SysConfig.MAIL_SMTPSERVER:
					mailManagePojo.setSMTPServer(value);
					break;
				case SysConfig.MAIL_SERVERPORT:
					mailManagePojo.setServerPort(value);
					break;
				case SysConfig.MAIL_SENDERADDRESS:
					mailManagePojo.setSenderAddress(value);
					break;
				case SysConfig.MAIL_PASSWORD:
					mailManagePojo.setPassword(value);
					break;
				case SysConfig.MAIL_DISPLAYNAME:
					mailManagePojo.setDisplayName(value);
					break;
				case SysConfig.MAIL_SSLIS:
					mailManagePojo.setSslIs(value);
					break;
				case SysConfig.MAIL_VERIFY:
					mailManagePojo.setVerify(value);
					break;
				case SysConfig.UPLOAD_FILESIZE:
					sysConfigPojo.setFileSize(value);
					break;
				case SysConfig.UPLOAD_IMGSIZE:
					sysConfigPojo.setImgSize(value);
					break;
//				case SysConfig.UPLOAD_IMGMAXHIGHT:
//					sysConfigPojo.setImgMaxHight(value);
//					break;
//				case SysConfig.UPLOAD_IMGMINHIGHT:
//					sysConfigPojo.setImgMinHight(value);
//					break;
//				case SysConfig.UPLOAD_IMGMAXWIDTH:
//					sysConfigPojo.setImgMaxWidth(value);
//					break;
//				case SysConfig.UPLOAD_IMGMINWIDTH:
//					sysConfigPojo.setImgMinWidth(value);
//					break;
				case SysConfig.SYSTEM_VERSION:
					sysConfigPojo.setVersionNum(value);
					break;
				case SysConfig.SYSTEM_RELEASETIME:
					sysConfigPojo.setReleaseTime(value);
					break;
				case SysConfig.SYSTEM_LAST_UPDATE_TIME:
					sysConfigPojo.setLastUpdateTime(value);
					break;
				case SysConfig.SYSTEM_CRON_FILLTASK:
					sysConfigPojo.setFillTaskCron(value);
					break;
				case SysConfig.SYSTEM_CRON_DPTASK:
					sysConfigPojo.setDpTaskCron(value);
					break;
				case SysConfig.SYSTEM_CRON_WARNTASK:
					sysConfigPojo.setWarnTaskCron(value);
					break;
				case SysConfig.SYSTEM_CRON_SENDMESTASK:
					sysConfigPojo.setSendMesTaskCron(value);
					break;
				case SysConfig.SYSTEM_CRON_SYNACCOUNTDATA:
					sysConfigPojo.setSynAccountDataCron(value);
					break;
				case SysConfig.SYSTEM_CRON_THIRDPARTYINTERFACEDATA:
					sysConfigPojo.setThirdpartyInterfaceDataCron(value);
					break;
				case SysConfig.SYSTEM_CRON_CALCULATEITEMDISPATCH:
					sysConfigPojo.setCalculateItemDispatchCron(value);
					break;
				case SysConfig.APPEARANCE_PORTAL_LOGINTITLE:
					appearanceConfigPojo.setLoginTitle(value);
					break;
				case SysConfig.APPEARANCE_PORTAL_LOGINLOGO:
					appearanceConfigPojo.setLoginLogo(value);
					break;
				case SysConfig.APPEARANCE_PORTAL_LOGINLOGO_ISINIT:
					appearanceConfigPojo.setIsInitLoginLogo(value);
					break;
				case SysConfig.APPEARANCE_PORTAL_TITLE:
					appearanceConfigPojo.setTitle(value);
					break;
				case SysConfig.APPEARANCE_PORTAL_LOGO:
					appearanceConfigPojo.setLogo(value);
					break;
				case SysConfig.APPEARANCE_PORTAL_LOGO_ISINIT:
					appearanceConfigPojo.setIsInitLogo(value);
					break;
				case SysConfig.APPEARANCE_PORTAL_ISSHOW:
					appearanceConfigPojo.setIsShow(value);
					break;
				case SysConfig.APPEARANCE_PORTAL_LOGINBACKGROUND:
					appearanceConfigPojo.setLoginBackground(value);
					break;
				case SysConfig.APPEARANCE_PORTAL_LOGINBACKGROUND_ISINIT:
					appearanceConfigPojo.setIsInitLoginBackground(value);
					break;
				case SysConfig.APPEARANCE_PORTAL_TYPE:
					appearanceConfigPojo.setPortalType(value);
					break;
				case SysConfig.APPEARANCE_PORTAL_STYLE:
					appearanceConfigPojo.setProtalStyle(value);
					break;
				case SysConfig.APPEARANCE_PORTAL_PAGE_LAYOUT:
					appearanceConfigPojo.setPageLayout(value);
					break;
				case SysConfig.APPEARANCE_PORTAL_THEME_COLOR:
					appearanceConfigPojo.setThemeColor(value);
					break;
				case SysConfig.APPEARANCE_PORTAL_TOPBARUSRTHEMECOLOR:
					appearanceConfigPojo.setTopBarUsrThemeColor(value);
					break;
				case SysConfig.APPEARANCE_PORTAL_TOPBARTHEMECOLORBANNER:
					appearanceConfigPojo.setTopBarThemeColorBanner(value);
					break;
				case SysConfig.APPEARANCE_PORTAL_ISOPENRESOURCEWATERMARK:
					appearanceConfigPojo.setIsOpenResourceWatermark(value);
					break;
				default:
					break;
				}
			}
			//查询后放置内存中的
			Constants.property.put(Constants.mailManageInfo,mailManagePojo);
			Constants.property.put(Constants.appearanceConfig,appearanceConfigPojo);
		} catch (Exception e) {
			throw e;
		}finally {
			//密码安全设置的有初始值
			Constants.property.put(Constants.passwordSecurity, passwordSecurityPojo);
			//系统配置有初始值
			Constants.property.put(Constants.sysConfigInfo, sysConfigPojo);
		}
	}

	/**
	 * 查询外观配置详细
	 * @return
	 * @throws Exception
	 */
	@Override
	public AppearanceConfigPojo queryAppearanceConfig() throws Exception {
		//如果内存中有值不进行查库
		AppearanceConfigPojo appearanceConfig = (AppearanceConfigPojo) Constants.property.get(Constants.appearanceConfig);
		if(StringUtil.isNotEmpty(appearanceConfig)) {
			return appearanceConfig;
		}else {
			List<SysConfig> sysConfigList = sysConfigDao.querySysConfigByType(SysConfig.TYPE_APPEARANCE);
			AppearanceConfigPojo appearanceConfigPojo = new AppearanceConfigPojo();
			for (int i = 0; i < sysConfigList.size(); i++) {
				SysConfig sysConfig = sysConfigList.get(i);
				String value = sysConfig.getValue();
				switch (sysConfig.getName()) {
					case SysConfig.APPEARANCE_PORTAL_LOGINTITLE:
						appearanceConfigPojo.setLoginTitle(value);
						break;
					case SysConfig.APPEARANCE_PORTAL_LOGINLOGO:
						appearanceConfigPojo.setLoginLogo(value);
						break;
					case SysConfig.APPEARANCE_PORTAL_LOGINLOGO_ISINIT:
						appearanceConfigPojo.setIsInitLoginLogo(value);
						break;
					case SysConfig.APPEARANCE_PORTAL_TITLE:
						appearanceConfigPojo.setTitle(value);
						break;
					case SysConfig.APPEARANCE_PORTAL_LOGO:
						appearanceConfigPojo.setLogo(value);
						break;
					case SysConfig.APPEARANCE_PORTAL_LOGO_ISINIT:
						appearanceConfigPojo.setIsInitLogo(value);
						break;
					case SysConfig.APPEARANCE_PORTAL_ISSHOW:
						appearanceConfigPojo.setIsShow(value);
						break;
					case SysConfig.APPEARANCE_PORTAL_LOGINBACKGROUND:
						appearanceConfigPojo.setLoginBackground(value);
						break;
					case SysConfig.APPEARANCE_PORTAL_LOGINBACKGROUND_ISINIT:
						appearanceConfigPojo.setIsInitLoginBackground(value);
						break;
					case SysConfig.APPEARANCE_PORTAL_TYPE:
						appearanceConfigPojo.setPortalType(value);
						break;
					case SysConfig.APPEARANCE_PORTAL_STYLE:
						appearanceConfigPojo.setProtalStyle(value);
						break;
					case SysConfig.APPEARANCE_PORTAL_PAGE_LAYOUT:
						appearanceConfigPojo.setPageLayout(value);
						break;
					case SysConfig.APPEARANCE_PORTAL_THEME_COLOR:
						appearanceConfigPojo.setThemeColor(value);
						break;
					case SysConfig.APPEARANCE_PORTAL_TOPBARUSRTHEMECOLOR:
						appearanceConfigPojo.setTopBarUsrThemeColor(value);
						break;
					case SysConfig.APPEARANCE_PORTAL_TOPBARTHEMECOLORBANNER:
						appearanceConfigPojo.setTopBarThemeColorBanner(value);
						break;
					case SysConfig.APPEARANCE_PORTAL_ISOPENRESOURCEWATERMARK:
						appearanceConfigPojo.setIsOpenResourceWatermark(value);
						break;
					default:
						break;
				}
			}
			//查询后放置内存中的
			Constants.property.put(Constants.appearanceConfig, appearanceConfigPojo);
			return appearanceConfigPojo;
		}
	}

    /**
     * 更新 jsp 引入文件版本
     *
     * @param value
     * @throws Exception
     */
    @Override
    public void updateJspVersion(String value) throws Exception {

        sysConfigDao.updateValueByName(JSP_UPDATE_VERSION, value);
        CacheUtils.put(JSP_UPDATE_VERSION, value);
    }

}
