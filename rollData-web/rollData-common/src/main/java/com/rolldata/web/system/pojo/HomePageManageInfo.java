package com.rolldata.web.system.pojo;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.List;

public class HomePageManageInfo implements Serializable{
	private static final long serialVersionUID = -4588393358695498373L;
	/**
	 * 系统默认首页，html静态页
	 */
	private List<String> defaultPage;
	
	/**
	 * 系统带跳转链接首页
	 */
	private List<String> systemPage;
	
	/**
	 * DS首页
	 */
	private List<String> dsPage;
	
	/**
	 * BI首页
	 */
	private List<String> biPage;

    /**
     * 资源首页
     */
	private HomeResource homeResource;

    /**
     * 简易首页,菜单都在左侧
     */
	private List<String> simplePage;

	/**外部安装模型的id*/
	private String wdModelId;

	/**外观配置*/
	private AppearanceConfigPojo appearanceConfig;

    /**
     * 获取 资源首页
     *
     * @return homeResource 资源首页
     */
    public HomeResource getHomeResource() {
        return this.homeResource;
    }

    /**
     * 设置 资源首页
     *
     * @param homeResource 资源首页
     */
    public void setHomeResource(HomeResource homeResource) {
        this.homeResource = homeResource;
    }

    /**
     * 获取 简易首页菜单都在左侧
     *
     * @return simplePage 简易首页菜单都在左侧
     */
    public List<String> getSimplePage() {
        return this.simplePage;
    }

    /**
     * 设置 简易首页菜单都在左侧
     *
     * @param simplePage 简易首页菜单都在左侧
     */
    public void setSimplePage(List<String> simplePage) {
        this.simplePage = simplePage;
    }

	public static class HomeResource implements Serializable {

        private static final long serialVersionUID = 2108773612920248742L;

        /**
         * 角色id集合
         */
        private List<String> roleId;

        /**
         * 资源id
         */
        private String resourceId;

        /**
         * 主页类型
         */
        private String homeType;

        /**
         * 资源类型
         */
        private String resourceType;

        /**
         * 获取 角色id集合
         *
         * @return roleId 角色id集合
         */
        public List<String> getRoleId() {
            return this.roleId;
        }

        /**
         * 设置 角色id集合
         *
         * @param roleId 角色id集合
         */
        public void setRoleId(List<String> roleId) {
            this.roleId = roleId;
        }

        /**
         * 获取 资源id
         *
         * @return resourceId 资源id
         */
        public String getResourceId() {
            return this.resourceId;
        }

        /**
         * 设置 资源id
         *
         * @param resourceId 资源id
         */
        public void setResourceId(String resourceId) {
            this.resourceId = resourceId;
        }

        /**
         * 获取 主页类型
         *
         * @return homeType 主页类型
         */
        public String getHomeType() {
            return this.homeType;
        }

        /**
         * 设置 主页类型
         *
         * @param homeType 主页类型
         */
        public void setHomeType(String homeType) {
            this.homeType = homeType;
        }

        /**
         * 获取 资源类型
         *
         * @return resourceType 资源类型
         */
        public String getResourceType() {
            return this.resourceType;
        }

        /**
         * 设置 资源类型
         *
         * @param resourceType 资源类型
         */
        public void setResourceType(String resourceType) {
            this.resourceType = resourceType;
        }
    }

	/**  
	 * 获取系统默认首页，html静态页  
	 * @return defaultPage 系统默认首页，html静态页  
	 */
	public List<String> getDefaultPage() {
		return defaultPage;
	}
	
	/**  
	 * 设置系统默认首页，html静态页  
	 * @param defaultPage 系统默认首页，html静态页  
	 */
	public void setDefaultPage(List<String> defaultPage) {
		this.defaultPage = defaultPage;
	}

	/**  
	 * 获取系统带跳转链接首页  
	 * @return systemPage 系统带跳转链接首页  
	 */
	public List<String> getSystemPage() {
		return systemPage;
	}
	
	/**  
	 * 设置系统带跳转链接首页  
	 * @param systemPage 系统带跳转链接首页  
	 */
	public void setSystemPage(List<String> systemPage) {
		this.systemPage = systemPage;
	}
	
	/**  
	 * 获取DS首页  
	 * @return dsPage DS首页  
	 */
	public List<String> getDsPage() {
		return dsPage;
	}
	
	/**  
	 * 设置DS首页  
	 * @param dsPage DS首页  
	 */
	public void setDsPage(List<String> dsPage) {
		this.dsPage = dsPage;
	}

	/**  
	 * 获取BI首页  
	 * @return biPage BI首页  
	 */
	public List<String> getBiPage() {
		return biPage;
	}

	/**  
	 * 设置BI首页  
	 * @param biPage BI首页  
	 */
	public void setBiPage(List<String> biPage) {
		this.biPage = biPage;
	}

	/**
	 * 获取 外部安装模型的id
	 *
	 * @return wdModelId 外部安装模型的id
	 */
	public String getWdModelId() {
		return this.wdModelId;
	}

	/**
	 * 设置 外部安装模型的id
	 *
	 * @param wdModelId 外部安装模型的id
	 */
	public void setWdModelId(String wdModelId) {
		this.wdModelId = wdModelId;
	}

	/**
	 * 获取 外观配置
	 *
	 * @return appearanceConfig 外观配置
	 */
	public AppearanceConfigPojo getAppearanceConfig() {
		return this.appearanceConfig;
	}

	/**
	 * 设置 外观配置
	 *
	 * @param appearanceConfig 外观配置
	 */
	public void setAppearanceConfig(AppearanceConfigPojo appearanceConfig) {
		this.appearanceConfig = appearanceConfig;
	}
}
