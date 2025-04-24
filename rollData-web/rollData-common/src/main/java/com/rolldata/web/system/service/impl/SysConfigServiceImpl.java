package com.rolldata.web.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rolldata.core.util.StringUtil;
import com.rolldata.web.system.Constants;
import com.rolldata.web.system.dao.SysConfigDao;
import com.rolldata.web.system.entity.SysConfig;
import com.rolldata.web.system.pojo.SysConfigPojo;
import com.rolldata.web.system.service.SysConfigService;

/**
 * 系统配置服务层实现
 * @Title: SysConfigServiceImpl
 * @Description: 系统配置服务层实现
 * @Company:www.wrenchdata.com
 * @author zhaibx
 * @date 2019年11月1日
 * @version V1.0
 */
@Service("sysConfigService")
@Transactional
public class SysConfigServiceImpl implements SysConfigService{
	
	@Autowired
    private SysConfigDao sysConfigDao;
	
	/**
	 * 查询系统相关配置信息
	 * @return
	 * @throws Exception
	 */
	@Override
	public SysConfigPojo querySysConfig() throws Exception {
		//如果内存中有值不进行查库
		SysConfigPojo sysConfigMap = (SysConfigPojo) Constants.property.get(Constants.sysConfigInfo);
		if(StringUtil.isNotEmpty(sysConfigMap)) {
			return sysConfigMap;
		}else {
			List<SysConfig> sysConfigList = sysConfigDao.querySysConfigByType(SysConfig.TYPE_SECURITY);
			SysConfigPojo sysConfigPojo = new SysConfigPojo();
			for (int i = 0; i < sysConfigList.size(); i++) {
				SysConfig sysConfig = sysConfigList.get(i);
				String value = sysConfig.getValue();
				switch (sysConfig.getName()) {
				case SysConfig.UPLOAD_FILESIZE:
					sysConfigPojo.setFileSize(value);
					break;
				case SysConfig.UPLOAD_IMGSIZE:
					sysConfigPojo.setImgSize(value);
					break;
				case SysConfig.SYSTEM_VERSION:
					sysConfigPojo.setVersionNum(value);
					break;
				case SysConfig.SYSTEM_RELEASETIME:
					sysConfigPojo.setReleaseTime(value);
					break;
				case SysConfig.SYSTEM_LAST_UPDATE_TIME:
					sysConfigPojo.setLastUpdateTime(value);
					break;
				default:
					break;
				}
			}
			//查询后放置内存中的
			Constants.property.put(Constants.sysConfigInfo, sysConfigPojo);
			return sysConfigPojo;
		}
	}

	/**
	 * 修改系统相关配置信息
	 * @param sysConfigPojo
	 * @return
	 * @throws Exception
	 */
	@Override
	public SysConfigPojo updateSysConfig(SysConfigPojo sysConfigPojo) throws Exception {
		sysConfigDao.updateValueByName(SysConfig.UPLOAD_FILESIZE, sysConfigPojo.getFileSize());
		sysConfigDao.updateValueByName(SysConfig.UPLOAD_IMGSIZE, sysConfigPojo.getImgSize());
		SysConfigPojo sysConfigPojoOld = querySysConfig();
		sysConfigPojoOld.setFileSize(sysConfigPojo.getFileSize());
		sysConfigPojoOld.setImgSize(sysConfigPojo.getImgSize());
		//修改同时修改内存中的
		Constants.property.put(Constants.sysConfigInfo, sysConfigPojoOld);
		return sysConfigPojo;
	}

}
