package com.rolldata.web.system.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.rolldata.web.system.pojo.BiHomePagePojo;
import com.rolldata.web.system.pojo.HomePageManageInfo;

/**
 * 
 * @Title: HomePageService
 * @Description: 首页管理服务类
 * @Company:www.wrenchdata.com
 * @author zhaibx
 * @date 2019年5月8日
 * @version V1.0
 */
public interface HomePageService {

	/**
	 * 查询首页配置详细
	 * @return
	 * @throws Exception
     * @param terminalType
	 */
	public HomePageManageInfo queryHomepageManageInfo(String terminalType) throws Exception;

	/**
	 * 保存主页配置详细
	 * @param loginLogoImgFile
	 * @param logoImgFile
	 * @param loginBackgroundImgFile
	 * @param homePageManageInfo
	 * @throws Exception
	 */
	public HomePageManageInfo saveHomepageInfo(CommonsMultipartFile loginLogoImgFile,CommonsMultipartFile logoImgFile,CommonsMultipartFile loginBackgroundImgFile,HomePageManageInfo homePageManageInfo) throws Exception;

    /**
     * 保存终端主页配置详细
     *
     * @param homePageManageInfo 配置信息
     * @throws Exception
     */
	public void saveTerminalHomepageInfo(HomePageManageInfo homePageManageInfo) throws Exception;

	/**
	 * 保存BI配置信息
	 * @param uploadImgFile 
	 * @param biHomePagePojo
	 * @throws Exception
	 */
	public void saveBIHomepageInfo(CommonsMultipartFile uploadImgFile, BiHomePagePojo biHomePagePojo) throws Exception;

	/**
	 * 查询BI首页配置信息
	 * @return
	 * @throws Exception
	 */
	public List<BiHomePagePojo> queryBIHomepageList() throws Exception;

	/**
	 * 根据id集合删除
	 * @param biHomePagePojo
	 * @throws Exception
	 */
	public void deleteBIHomepageInfo(BiHomePagePojo biHomePagePojo) throws Exception;

	/**
	 * 修改BI首页配置信息
	 * @param uploadImgFile
	 * @param biHomePagePojo
	 * @throws Exception
	 */
	public void modifyBIHomepageInfo(CommonsMultipartFile uploadImgFile, BiHomePagePojo biHomePagePojo) throws Exception;

	/**
	 * BI首页配置交换顺序
	 * @param biHomePagePojo
	 * @throws Exception
	 */
	public void updateExchangeOrder(BiHomePagePojo biHomePagePojo) throws Exception;

	/**
	 * 根据角色查询主页
	 * @param request
	 * @param portalType
	 * @return
	 * @throws Exception
	 */
	public HomePageManageInfo.HomeResource queryHomepageManageInfoByRoleId(
			HttpServletRequest request,
			String portalType) throws Exception;

}
