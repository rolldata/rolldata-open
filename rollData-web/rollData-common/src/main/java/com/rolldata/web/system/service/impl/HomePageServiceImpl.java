package com.rolldata.web.system.service.impl;

import com.rolldata.core.common.exception.WdFileNotFoundException;
import com.rolldata.core.common.pojo.UploadFile;
import com.rolldata.core.util.AdministratorUtils;
import com.rolldata.core.util.BrowserUtils;
import com.rolldata.core.util.FileUtil;
import com.rolldata.core.util.MessageUtils;
import com.rolldata.core.util.ResourceUtil;
import com.rolldata.core.util.StringUtil;
import com.rolldata.core.util.UUIDGenerator;
import com.rolldata.web.system.Constants;
import com.rolldata.web.system.dao.BiHomeConfigDao;
import com.rolldata.web.system.dao.SysConfigDao;
import com.rolldata.web.system.dao.SysRoleHomePageDao;
import com.rolldata.web.system.entity.BiHomeConfig;
import com.rolldata.web.system.entity.SysConfig;
import com.rolldata.web.system.entity.SysRoleHomePage;
import com.rolldata.web.system.pojo.AppearanceConfigPojo;
import com.rolldata.web.system.pojo.BiHomePagePojo;
import com.rolldata.web.system.pojo.HomePageManageInfo;
import com.rolldata.web.system.service.HomePageService;
import com.rolldata.web.system.service.SystemService;
import com.rolldata.web.system.util.UserUtils;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
 * 
 * @Title: HomePageServiceImpl
 * @Description: 首页管理服务类实现
 * @Company:www.wrenchdata.com
 * @author zhaibx
 * @date 2019年5月8日
 * @version V1.0
 */
@Service("homePageService")
@Transactional
public class HomePageServiceImpl implements HomePageService{
	
	@Autowired
    private SysRoleHomePageDao sysRoleHomePageDao;

	@Autowired
	private BiHomeConfigDao biHomeConfigDao;

    @Autowired
    private SysConfigDao sysConfigDao;

    @Autowired
    private SystemService systemService;

	/**
	 * 查询首页配置详细
	 * @return
	 * @throws Exception
     * @param terminalType
	 */
    @Override
	public HomePageManageInfo queryHomepageManageInfo(String terminalType) throws Exception {
		HomePageManageInfo homePageManageInfo = new HomePageManageInfo();
		List<SysRoleHomePage> list = sysRoleHomePageDao.queryEntitiesByTerminalType(terminalType);
        setHomepageInfo(homePageManageInfo, list);
        if(terminalType.equals("1")){
            AppearanceConfigPojo appearanceConfigPojo = systemService.queryAppearanceConfig();
            homePageManageInfo.setAppearanceConfig(appearanceConfigPojo);
        }
        return homePageManageInfo;
	}

    private void setHomepageInfo(HomePageManageInfo homePageManageInfo, List<SysRoleHomePage> list) throws Exception {
        List<String> defaultPage = new ArrayList<>();
        List<String> systemPage = new ArrayList<>();
        List<String> simplePage = new ArrayList<>();
        List<String> dsPage = new ArrayList<>();
        List<String> biPage = new ArrayList<>();
        HomePageManageInfo.HomeResource homeResource = new HomePageManageInfo.HomeResource();
        List<String> resourceRoles = new ArrayList<>();
        homeResource.setRoleId(resourceRoles);
        for (int i = 0; i < list.size(); i++) {
            SysRoleHomePage homePageObj = list.get(i);
            String type = homePageObj.getType();
            String roleId = homePageObj.getRoleId();

            //1默认主页，2BI，3DS，4系统菜单
            switch (type) {
                case "1":
                    defaultPage.add(roleId);
                    break;
                case "2":
                    biPage.add(roleId);
                    break;
                case "3":
                    dsPage.add(roleId);
                    break;
                case "4":
                    systemPage.add(roleId);
                    break;
                case "5":
                    resourceRoles.add(roleId);
                    //List<SysResource> resources = resourceDao.querySysResourceByRelationId(homePageObj.getResourceId());
                    //if (!resources.isEmpty()) {
                    //    homeResource.setResourceType(resources.get(0).getType());
                    //    homeResource.setResourceId(resources.get(0).getRelationId());
                    //}
                    homeResource.setResourceId(homePageObj.getResourceId());
                    break;
                case "6":
                    simplePage.add(roleId);
                    break;
                default:
                    defaultPage.add(roleId);
                    break;
            }
        }
        homePageManageInfo.setDefaultPage(defaultPage);
        homePageManageInfo.setBiPage(biPage);
        homePageManageInfo.setDsPage(dsPage);
        homePageManageInfo.setSystemPage(systemPage);
        homePageManageInfo.setHomeResource(homeResource);
        homePageManageInfo.setSimplePage(simplePage);
    }

    /**
	 * 保存主页配置详细
	 * @param homePageManageInfo
	 * @throws Exception
	 */
    @Override
	public HomePageManageInfo saveHomepageInfo(CommonsMultipartFile loginLogoImgFile,CommonsMultipartFile logoImgFile,CommonsMultipartFile loginBackgroundImgFile,
                                 HomePageManageInfo homePageManageInfo) throws Exception {
		List<String> defaultPage = homePageManageInfo.getDefaultPage();
		List<String> systemPage = homePageManageInfo.getSystemPage();
		List<String> dsPage = homePageManageInfo.getDsPage();
		List<String> biPage = homePageManageInfo.getBiPage();
		List<String> simplePage = homePageManageInfo.getSimplePage();
        HomePageManageInfo.HomeResource homeResource = homePageManageInfo.getHomeResource();
        //1默认主页，2BI，3DS，4系统菜单
		saveHomepageInfo(defaultPage,"1", "1");
		saveHomepageInfo(biPage,"2", "1");
		saveHomepageInfo(dsPage,"3", "1");
		saveHomepageInfo(systemPage,"4", "1");
		saveHomepageInfo(homeResource,"5", "1",homePageManageInfo.getWdModelId());
		saveHomepageInfo(simplePage,"6", "1");
        AppearanceConfigPojo appearanceConfig = homePageManageInfo.getAppearanceConfig();
		if(StringUtil.isNotEmpty(appearanceConfig)){
            //保存上传图片
            if(appearanceConfig.getIsInitLoginLogo().equals("0") && loginLogoImgFile.getSize() > 0){
                String loginLogoImgFileName = this.uploadImgFile(loginLogoImgFile);
                appearanceConfig.setLoginLogo(loginLogoImgFileName);
            }
            if(appearanceConfig.getIsInitLogo().equals("0") && logoImgFile.getSize() > 0){
                String logoImgFileName = this.uploadImgFile(logoImgFile);
                appearanceConfig.setLogo(logoImgFileName);
            }
            if(appearanceConfig.getIsInitLoginBackground().equals("0") && loginBackgroundImgFile.getSize() > 0){
                String loginBackgroundImgFileName = this.uploadImgFile(loginBackgroundImgFile);
                appearanceConfig.setLoginBackground(loginBackgroundImgFileName);
            }
            //保存外观配置信息
            sysConfigDao.updateValueByName(SysConfig.APPEARANCE_PORTAL_LOGINTITLE, appearanceConfig.getLoginTitle());
            sysConfigDao.updateValueByName(SysConfig.APPEARANCE_PORTAL_LOGINLOGO, appearanceConfig.getLoginLogo());
            sysConfigDao.updateValueByName(SysConfig.APPEARANCE_PORTAL_LOGINLOGO_ISINIT, appearanceConfig.getIsInitLoginLogo());
            sysConfigDao.updateValueByName(SysConfig.APPEARANCE_PORTAL_TITLE, appearanceConfig.getTitle());
            sysConfigDao.updateValueByName(SysConfig.APPEARANCE_PORTAL_LOGO, appearanceConfig.getLogo());
            sysConfigDao.updateValueByName(SysConfig.APPEARANCE_PORTAL_LOGO_ISINIT, appearanceConfig.getIsInitLogo());
            sysConfigDao.updateValueByName(SysConfig.APPEARANCE_PORTAL_ISSHOW,appearanceConfig.getIsShow());
            sysConfigDao.updateValueByName(SysConfig.APPEARANCE_PORTAL_LOGINBACKGROUND, appearanceConfig.getLoginBackground());
            sysConfigDao.updateValueByName(SysConfig.APPEARANCE_PORTAL_LOGINBACKGROUND_ISINIT, appearanceConfig.getIsInitLoginBackground());
            sysConfigDao.updateValueByName(SysConfig.APPEARANCE_PORTAL_TYPE, appearanceConfig.getPortalType());
            sysConfigDao.updateValueByName(SysConfig.APPEARANCE_PORTAL_STYLE, appearanceConfig.getProtalStyle());
            sysConfigDao.updateValueByName(SysConfig.APPEARANCE_PORTAL_PAGE_LAYOUT, appearanceConfig.getPageLayout());
            sysConfigDao.updateValueByName(SysConfig.APPEARANCE_PORTAL_THEME_COLOR, appearanceConfig.getThemeColor());
            sysConfigDao.updateValueByName(SysConfig.APPEARANCE_PORTAL_TOPBARUSRTHEMECOLOR, appearanceConfig.getTopBarUsrThemeColor());
            sysConfigDao.updateValueByName(SysConfig.APPEARANCE_PORTAL_TOPBARTHEMECOLORBANNER, appearanceConfig.getTopBarThemeColorBanner());
            sysConfigDao.updateValueByName(SysConfig.APPEARANCE_PORTAL_ISOPENRESOURCEWATERMARK, appearanceConfig.getIsOpenResourceWatermark());
            //修改同时修改内存中的
            Constants.property.put(Constants.appearanceConfig, appearanceConfig);
        }
		return homePageManageInfo;
	}

    private String uploadImgFile(CommonsMultipartFile file) throws Exception {
        String originalFilename = file.getOriginalFilename();
        // 当文件超过设置的大小时，则不运行上传
        if (file.getSize() > UploadFile.imgSize) {
            throw new Exception(
                    MessageUtils.getMessageOrSelf("common.sys.file.size.error", UploadFile.sysConfigInfo.getImgSize()));
        }
        int pos = originalFilename.lastIndexOf('.');
        // 获取文件名后缀
        String fileSuffix = originalFilename.substring(pos + 1).toLowerCase();
        String fileName = originalFilename.substring(0, pos);
        String imgName = "";
        // 判断该类型的文件是否在允许上传的文件类型内
        if (!Arrays.asList(UploadFile.TypeMap.get("image").split(",")).contains(fileSuffix)) {
            throw new Exception(MessageUtils.getMessage("common.sys.file.type.error"));
        }
        File tmpFile = null;
        try {
            // 检查上传文件的目录
            String realPath = ResourceUtil.getUploadImgTempPath();
            FileUtil.mkDir(realPath);
            String timeMillis = String.valueOf(System.currentTimeMillis());
            //临时文件名
            String tempfileName = fileName + "_" + timeMillis + "." + fileSuffix;
            //正式保存的文件名
            imgName = fileName + "." + fileSuffix;
            // 新文件名
            String path = realPath + tempfileName;
            tmpFile = new File(path);
            // 通过CommonsMultipartFile的方法直接写文件
            file.transferTo(tmpFile);
            // 符合大小的迁移文件
            FileUtil.copyFile(path, ResourceUtil.getUploadImgPath() + imgName);
        } catch (FileNotFoundException e) {
            throw new WdFileNotFoundException(e);
        } catch (Exception e) {
            throw e;
        } finally {
            // 删除临时文件
            if (null != tmpFile &&
                    tmpFile.exists()) {
                tmpFile.delete();
            }
        }
        return imgName;
    }

    /**
     * 保存终端主页配置详细
     *
     * @param homePageManageInfo 配置信息
     * @throws Exception
     */
    @Override
    public void saveTerminalHomepageInfo(HomePageManageInfo homePageManageInfo) throws Exception {

        List<String> defaultPage = homePageManageInfo.getDefaultPage();
        List<String> systemPage = homePageManageInfo.getSystemPage();
        List<String> dsPage = homePageManageInfo.getDsPage();
        List<String> biPage = homePageManageInfo.getBiPage();
        List<String> simplePage = homePageManageInfo.getSimplePage();

        //1默认主页，2BI，3DS，4系统菜单
        saveHomepageInfo(defaultPage, "1", "3");
        saveHomepageInfo(biPage, "2", "3");
        saveHomepageInfo(dsPage, "3", "3");
        saveHomepageInfo(systemPage, "4", "3");
        saveHomepageInfo(homePageManageInfo.getHomeResource(),"5", "3",homePageManageInfo.getWdModelId());
        saveHomepageInfo(simplePage,"6", "3");
    }

    /**
     * 保存主页信息
     *
     * @param homePageList 页面集合
     * @param type         保存类型
     * @param terminalType 终端类型
     * @throws Exception
     */
    private void saveHomepageInfo(List<String> homePageList, String type, String terminalType) throws Exception{
		if(StringUtil.isNotEmpty(homePageList)) {
			sysRoleHomePageDao.deleteHomePageByType(type, terminalType);
			for (int i = 0; i < homePageList.size(); i++) {
				String roleId = homePageList.get(i);
				SysRoleHomePage sysRoleHomePage = new SysRoleHomePage();
				sysRoleHomePage.setRoleId(roleId);
				sysRoleHomePage.setType(type);
				sysRoleHomePage.setTerminalType(terminalType);
				sysRoleHomePage.setCreateTime(new Date());
				sysRoleHomePage.setCreateUser(UserUtils.getUser().getId());
				sysRoleHomePageDao.saveAndFlush(sysRoleHomePage);
			}
		}
	}

    /**
     * 保存主页信息
     *
     * @param homePageList 页面集合
     * @param type         保存类型
     * @param terminalType 终端类型
     * @throws Exception
     */
    private void saveHomepageInfo(List<String> homePageList, String type, String terminalType,String wdModelId) throws Exception{
        if(StringUtil.isNotEmpty(homePageList)) {
            sysRoleHomePageDao.deleteHomePageByType(type, terminalType);
            for (int i = 0; i < homePageList.size(); i++) {
                String roleId = homePageList.get(i);
                SysRoleHomePage sysRoleHomePage = new SysRoleHomePage();
                sysRoleHomePage.setRoleId(roleId);
                sysRoleHomePage.setType(type);
                sysRoleHomePage.setTerminalType(terminalType);
                sysRoleHomePage.setCreateTime(new Date());
                sysRoleHomePage.setCreateUser(UserUtils.getUser().getId());
                if(StringUtil.isNotEmpty(wdModelId)) {
                    sysRoleHomePage.setWdModelId(wdModelId);
                }
                sysRoleHomePageDao.saveAndFlush(sysRoleHomePage);
            }
        }
    }

    private void saveHomepageInfo(HomePageManageInfo.HomeResource homeResource, String type, String terminalType,String wdModelId) throws Exception{

        if (null == homeResource) {
            return;
        }
        this.sysRoleHomePageDao.deleteHomePageByType(type, terminalType);
        List<String> homePageList = homeResource.getRoleId();
        String resourceId = homeResource.getResourceId();
        for (int i = 0; i < homePageList.size(); i++) {
            String roleId = homePageList.get(i);
            SysRoleHomePage sysRoleHomePage = new SysRoleHomePage();
            sysRoleHomePage.setRoleId(roleId);
            sysRoleHomePage.setType(type);
            sysRoleHomePage.setResourceId(resourceId);
            if(StringUtil.isNotEmpty(wdModelId)) {
                sysRoleHomePage.setWdModelId(wdModelId);
            }
            sysRoleHomePage.setTerminalType(terminalType);
            sysRoleHomePage.setCreateTime(new Date());
            sysRoleHomePage.setCreateUser(UserUtils.getUser().getId());
            this.sysRoleHomePageDao.saveAndFlush(sysRoleHomePage);
        }
    }

    /**
     * 保存BI配置信息
     *
     * @param biHomePagePojo
     * @throws Exception
     */
    @Override
    public void saveBIHomepageInfo(CommonsMultipartFile file,BiHomePagePojo biHomePagePojo) throws Exception {

        String fileName = "";
        String imgUrl = "";
        if (file.isEmpty()) {

                imgUrl = "report.jpg";
        } else {
            String originalFilename = file.getOriginalFilename();

            // 当文件超过设置的大小时，则不运行上传
            if (file.getSize() > UploadFile.imgSize) {
                throw new Exception(
                    MessageUtils.getMessageOrSelf("common.sys.file.size.error", UploadFile.sysConfigInfo.getImgSize()));
            }
            int pos = originalFilename.lastIndexOf('.');

            // 获取文件名后缀
            String fileSuffix = originalFilename.substring(pos + 1).toLowerCase();
            fileName = originalFilename.substring(0, pos);
            imgUrl = UUIDGenerator.generate();

            // 判断该类型的文件是否在允许上传的文件类型内
            if (!Arrays.asList(UploadFile.TypeMap.get("image").split(",")).contains(fileSuffix)) {
                throw new Exception(MessageUtils.getMessage("common.sys.file.type.error"));
            }
            File tmpFile = null;
            try {
                // 检查上传文件的目录
                String realPath = ResourceUtil.getUploadImgTempPath();
                FileUtil.mkDir(realPath);
                String timeMillis = String.valueOf(System.currentTimeMillis());
                //临时文件名
                String tempfileName = imgUrl + "_" + timeMillis + "." + fileSuffix;

                //正式保存的文件名
                fileName = fileName + "." + fileSuffix;
                imgUrl = imgUrl + "." + fileSuffix;
                // 新文件名
                String path = realPath + tempfileName;
                tmpFile = new File(path);
                // 通过CommonsMultipartFile的方法直接写文件
                file.transferTo(tmpFile);

                // 符合大小的迁移文件
                FileUtil.copyFile(path, ResourceUtil.getUploadImgPath() + imgUrl);

            } catch (FileNotFoundException e) {
                throw new WdFileNotFoundException(e);
            } catch (Exception e) {
                throw e;
            } finally {
                // 删除临时文件
                if (null != tmpFile &&
                    tmpFile.exists()) {
                    tmpFile.delete();
                }
            }
        }

        //根据名称查询是否重复
        List<BiHomeConfig> list = biHomeConfigDao.queryBiHomeConfigByName(biHomePagePojo.getName());
        if (list.size() > 0) {

            //重复抛出异常
            throw new Exception(
                MessageUtils.getMessageOrSelf("common.sys.name.repeat", biHomePagePojo.getName()));
        }

        // 查询最大排序号
        String sort = biHomeConfigDao.queryMaxSort();
        if (StringUtil.isEmpty(sort)) {
            sort = "0";
        }
        BiHomeConfig biHomeConfig = new BiHomeConfig();
        biHomeConfig.setName(biHomePagePojo.getName());
        biHomeConfig.setCreateUser(UserUtils.getUser().getId());
        biHomeConfig.setCreateTime(new Date());
        biHomeConfig.setImgName(fileName);
        biHomeConfig.setImgUrl(imgUrl);
        biHomeConfig.setResourceId(biHomePagePojo.getResourceId());
        biHomeConfig.setSort(String.valueOf(Integer.parseInt(sort) + 1));
        biHomeConfigDao.saveAndFlush(biHomeConfig);
    }

	/**
	 * 查询BI首页配置信息
	 * @return
	 * @throws Exception
	 */
	@Override
    public List<BiHomePagePojo> queryBIHomepageList() throws Exception {
		List<BiHomePagePojo> returnList = new ArrayList<>();
		return returnList;
	}

	/**
	 * 根据id集合删除
	 * @param biHomePagePojo
	 * @throws Exception
	 */
    @Override
	public void deleteBIHomepageInfo(BiHomePagePojo biHomePagePojo) throws Exception {
		List<String> deleteIds = biHomePagePojo.getIds();
		List<BiHomeConfig> returnList = biHomeConfigDao.queryBiHomeConfigByIds(deleteIds);
		for (int i = 0; i < returnList.size(); i++) {
			BiHomeConfig biHomeConfig = returnList.get(i);
			FileUtil.delFile(ResourceUtil.getUploadImgPath()+ biHomeConfig.getImgName());
			biHomeConfigDao.deleteBiHomeConfigById(biHomeConfig.getId());
		}
	}

	/**
	 * 修改BI首页配置信息
	 * @param file
	 * @param biHomePagePojo
	 * @throws Exception
	 */
    @Override
	public void modifyBIHomepageInfo(CommonsMultipartFile file, BiHomePagePojo biHomePagePojo)
			throws Exception {
		String isReUpload=biHomePagePojo.getIsReUpload();
		//重新上传文件
		if(isReUpload.equals("1")) {
			if (!file.isEmpty()) {
				String originalFilename = file.getOriginalFilename();
				// 当文件超过设置的大小时，则不运行上传
				if (file.getSize() > UploadFile.imgSize) {
					throw new Exception(MessageUtils.getMessageOrSelf("common.sys.file.size.error",UploadFile.sysConfigInfo.getImgSize()));
				}
				int pos = originalFilename.lastIndexOf(".");
				// 获取文件名后缀
				String fileSuffix = originalFilename.substring(pos + 1).toLowerCase();
				String fileName = originalFilename.substring(0, pos);
				// 判断该类型的文件是否在允许上传的文件类型内
				if (!Arrays.asList(UploadFile.TypeMap.get("image").split(",")).contains(fileSuffix)) {
					throw new Exception(MessageUtils.getMessage("common.sys.file.type.error"));
				}
				File tmpFile = null;
				try {
				    String imgUrl = UUIDGenerator.generate();
					// 检查上传文件的目录
					String realPath = ResourceUtil.getUploadImgTempPath();
					FileUtil.mkDir(realPath);
					String timeMillis = System.currentTimeMillis() + "";
			        //临时文件名
			        String tempfileName = fileName + "_" + timeMillis + "." + fileSuffix;
			        
					//正式保存的文件名
					fileName = fileName + "." + fileSuffix;
                    imgUrl = imgUrl + "." + fileSuffix;
					// 新文件名
					String path = realPath + tempfileName ;
					tmpFile = new File(path);
					// 通过CommonsMultipartFile的方法直接写文件
					file.transferTo(tmpFile);
                    //根据名称查询是否重复
                    List<BiHomeConfig> list = biHomeConfigDao.queryBiHomeConfigByNameAndNotId(biHomePagePojo.getName(),biHomePagePojo.getId());
                    if(list.size()>0) {
                        //重复抛出异常
                        throw new Exception(MessageUtils.getMessageOrSelf("common.sys.name.repeat",biHomePagePojo.getName()));
                    }
                    //查询旧的对象信息
                    BiHomeConfig biHomeConfig = biHomeConfigDao.queryBiHomePagePojoById(biHomePagePojo.getId());

                    //删除旧的图片
                    if (!"report.jpg".equalsIgnoreCase(biHomeConfig.getImgUrl()) &&
                        !"form.jpg".equalsIgnoreCase(biHomeConfig.getImgUrl()) &&
                        !"bi.jpg".equalsIgnoreCase(biHomeConfig.getImgUrl())) {
                        FileUtil.delFile(ResourceUtil.getUploadImgPath()+ biHomeConfig.getImgUrl());
                    }

                    //符合大小的迁移文件
                    FileUtil.copyFile(path, ResourceUtil.getUploadImgPath()+ imgUrl);
                    biHomeConfigDao.updateBiHomeConfigInfo(biHomePagePojo.getId(),biHomePagePojo.getName(),fileName,imgUrl,biHomePagePojo.getResourceId(),UserUtils.getUser().getId(),new Date());
				} catch (FileNotFoundException e) {
					throw new WdFileNotFoundException(e);
				} catch (Exception e) {
					throw e;
				}finally {
					// 删除临时文件
					if (tmpFile.exists()) {
						tmpFile.delete();
					}
				}
			}else {
				throw new Exception(MessageUtils.getMessage("common.sys.file.notfound.error"));
			}
		}else {
			biHomeConfigDao.updateBiHomeConfigInfo(biHomePagePojo.getId(),biHomePagePojo.getName(),biHomePagePojo.getResourceId(),UserUtils.getUser().getId(),new Date());
		}
	}

	/**
	 * BI首页配置交换顺序
	 * @param biHomePagePojo
	 * @throws Exception
	 */
    @Override
	public void updateExchangeOrder(BiHomePagePojo biHomePagePojo) throws Exception {
		BiHomeConfig biHomeConfig = biHomeConfigDao.queryBiHomePagePojoById(biHomePagePojo.getId());
		BiHomeConfig toBiHomeConfig = biHomeConfigDao.queryBiHomePagePojoById(biHomePagePojo.getToId());
		String toBiSort = toBiHomeConfig.getSort();
		String sort = biHomeConfig.getSort();
		biHomeConfigDao.updateBiHomeConfigInfo(biHomeConfig.getId(), toBiHomeConfig.getSort(),UserUtils.getUser().getId(),new Date());
		biHomeConfigDao.updateBiHomeConfigInfo(toBiHomeConfig.getId(), biHomeConfig.getSort(),UserUtils.getUser().getId(),new Date());
	}

	@Override
	public HomePageManageInfo.HomeResource queryHomepageManageInfoByRoleId(
            HttpServletRequest request, String portalType) throws Exception {

        String terminalType = BrowserUtils.terminalType(request);
        HomePageManageInfo.HomeResource homeResource = new HomePageManageInfo.HomeResource();
        String homeType = "";
        if (portalType.equals(AppearanceConfigPojo.PORTALTYPE1)){
            homeType = "1";//传统门户默认静态页
        } else if (portalType.equals(AppearanceConfigPojo.PORTALTYPE2)){
            homeType = "7";//经典门户默认新门户主页，虚拟的
        } else if (portalType.equals(AppearanceConfigPojo.PORTALTYPE3)){

            // 奇迹门户默认新门户主页，虚拟的
            homeType = "8";
        }
        homeResource.setHomeType(homeType);
        List<String> rolesIdList = UserUtils.getRolesIdList();
        if(!rolesIdList.isEmpty()) {
			List<SysRoleHomePage> list = sysRoleHomePageDao.queryHomepageManageInfoByRoleId(
                rolesIdList,
                terminalType
            );
			if(!list.isEmpty()){
                SysRoleHomePage sysRoleHomePage = list.get(0);
                homeResource.setHomeType(sysRoleHomePage.getType());
                homeResource.setResourceId(sysRoleHomePage.getResourceId());
			}else{//无角色也进入
                homeResource.setHomeType(homeType);
            }
		}
		if (AdministratorUtils.getAdministratorName().equals(UserUtils.getUser().getUserCde())) {
            homeResource.setHomeType(homeType);
        }
		return homeResource;
	}
	
	
}
