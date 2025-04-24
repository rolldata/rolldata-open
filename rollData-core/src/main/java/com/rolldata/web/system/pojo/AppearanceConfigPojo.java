package com.rolldata.web.system.pojo;

/**
 * @Title: AppearanceConfigPojo
 * @Description: 外观配置交互类
 * @Company: www.wrenchdata.com
 * @author: zhaibx
 * @date: 2022-11-29
 * @version: V1.0
 */
public class AppearanceConfigPojo implements java.io.Serializable{
    private static final long serialVersionUID = -6907615317934833235L;
    /**门户类型，1传统门户*/
    public static final String PORTALTYPE1 = "1";

    /**门户类型，2经典门户*/
    public static final String PORTALTYPE2 = "2";

    /**
     * 门户类型,3奇迹门户
     */
    public static final String PORTALTYPE3 = "3";

    /**
     * 门户类型,4新版小诺门户
     */
    public static final String PORTALTYPE4 = "4";

    /**登陆标题*/
    private String loginTitle = "企业数据平台";

    /**登陆logo*/
    private String loginLogo;

    /**是否初始登陆logo，0否，1是*/
    private String isInitLoginLogo = "1";

    /**平台标题*/
    private String title = "企业数据平台";

    /**平台logo*/
    private String logo;

    /**是否初始平台logo，0否，1是*/
    private String isInitLogo = "1";

    /**是否显示品牌信息，0否，1是*/
    private String isShow = "1";

    /**登陆背景图*/
    private String loginBackground;

    /**是否初始登陆背景图，0否，1是*/
    private String isInitLoginBackground = "1";

    /**门户类型，1传统门户，2经典门户*/
    private String portalType = "2";

    /**整体风格，1暗色主题风格，2亮色主题风格，3暗黑主题风格*/
    private String protalStyle = "1";

    /**页面布局，1经典，2双排菜单，3顶部菜单*/
    private String pageLayout = "2";

    /**主题色*/
    private String themeColor;

    /**顶栏应用主题色,0否1是*/
    private String topBarUsrThemeColor = "0";

    /**顶栏主题色通栏,0否1是*/
    private String topBarThemeColorBanner = "0";

    /**水印内容*/
    private String watermarkContent = "企业数据平台-开源版";

    /**是否开启资源水印，0否1是*/
    private String isOpenResourceWatermark = "1";

    /**
     * 获取 登陆标题
     *
     * @return loginTitle 登陆标题
     */
    public String getLoginTitle() {
        return this.loginTitle;
    }

    /**
     * 设置 登陆标题
     *
     * @param loginTitle 登陆标题
     */
    public void setLoginTitle(String loginTitle) {
        this.loginTitle = loginTitle;
    }

    /**
     * 获取 登陆logo
     *
     * @return loginLogo 登陆logo
     */
    public String getLoginLogo() {
        return this.loginLogo;
    }

    /**
     * 设置 登陆logo
     *
     * @param loginLogo 登陆logo
     */
    public void setLoginLogo(String loginLogo) {
        this.loginLogo = loginLogo;
    }

    /**
     * 获取 平台标题
     *
     * @return title 平台标题
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * 设置 平台标题
     *
     * @param title 平台标题
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 获取 平台logo
     *
     * @return logo 平台logo
     */
    public String getLogo() {
        return this.logo;
    }

    /**
     * 设置 平台logo
     *
     * @param logo 平台logo
     */
    public void setLogo(String logo) {
        this.logo = logo;
    }

    /**
     * 获取 是否显示品牌信息，0否，1是
     *
     * @return isShow 是否显示品牌信息，0否，1是
     */
    public String getIsShow() {
        return this.isShow;
    }

    /**
     * 设置 是否显示品牌信息，0否，1是
     *
     * @param isShow 是否显示品牌信息，0否，1是
     */
    public void setIsShow(String isShow) {
        this.isShow = isShow;
    }

    /**
     * 获取 登陆背景图
     *
     * @return loginBackground 登陆背景图
     */
    public String getLoginBackground() {
        return this.loginBackground;
    }

    /**
     * 设置 登陆背景图
     *
     * @param loginBackground 登陆背景图
     */
    public void setLoginBackground(String loginBackground) {
        this.loginBackground = loginBackground;
    }

    /**
     * 获取 门户类型，1传统门户，2日常门户
     *
     * @return portalType 门户类型，1传统门户，2日常门户
     */
    public String getPortalType() {
        return this.portalType;
    }

    /**
     * 设置 门户类型，1传统门户，2日常门户
     *
     * @param portalType 门户类型，1传统门户，2日常门户
     */
    public void setPortalType(String portalType) {
        this.portalType = portalType;
    }

    /**
     * 获取 是否初始登陆logo，0否，1是
     *
     * @return isInitLoginLogo 是否初始登陆logo，0否，1是
     */
    public String getIsInitLoginLogo() {
        return this.isInitLoginLogo;
    }

    /**
     * 设置 是否初始登陆logo，0否，1是
     *
     * @param isInitLoginLogo 是否初始登陆logo，0否，1是
     */
    public void setIsInitLoginLogo(String isInitLoginLogo) {
        this.isInitLoginLogo = isInitLoginLogo;
    }

    /**
     * 获取 是否初始平台logo，0否，1是
     *
     * @return isInitLogo 是否初始平台logo，0否，1是
     */
    public String getIsInitLogo() {
        return this.isInitLogo;
    }

    /**
     * 设置 是否初始平台logo，0否，1是
     *
     * @param isInitLogo 是否初始平台logo，0否，1是
     */
    public void setIsInitLogo(String isInitLogo) {
        this.isInitLogo = isInitLogo;
    }

    /**
     * 获取 是否初始登陆背景图，0否，1是
     *
     * @return isInitLoginBackground 是否初始登陆背景图，0否，1是
     */
    public String getIsInitLoginBackground() {
        return this.isInitLoginBackground;
    }

    /**
     * 设置 是否初始登陆背景图，0否，1是
     *
     * @param isInitLoginBackground 是否初始登陆背景图，0否，1是
     */
    public void setIsInitLoginBackground(String isInitLoginBackground) {
        this.isInitLoginBackground = isInitLoginBackground;
    }

    /**
     * 获取 整体风格，1暗色主题风格，2亮色主题风格，3暗黑主题风格
     *
     * @return protalStyle 整体风格，1暗色主题风格，2亮色主题风格，3暗黑主题风格
     */
    public String getProtalStyle() {
        return this.protalStyle;
    }

    /**
     * 设置 整体风格，1暗色主题风格，2亮色主题风格，3暗黑主题风格
     *
     * @param protalStyle 整体风格，1暗色主题风格，2亮色主题风格，3暗黑主题风格
     */
    public void setProtalStyle(String protalStyle) {
        this.protalStyle = protalStyle;
    }

    /**
     * 获取 页面布局，1经典，2双排菜单，3顶部菜单
     *
     * @return pageLayout 页面布局，1经典，2双排菜单，3顶部菜单
     */
    public String getPageLayout() {
        return this.pageLayout;
    }

    /**
     * 设置 页面布局，1经典，2双排菜单，3顶部菜单
     *
     * @param pageLayout 页面布局，1经典，2双排菜单，3顶部菜单
     */
    public void setPageLayout(String pageLayout) {
        this.pageLayout = pageLayout;
    }

    /**
     * 获取 主题色
     *
     * @return themeColor 主题色
     */
    public String getThemeColor() {
        return this.themeColor;
    }

    /**
     * 设置 主题色
     *
     * @param themeColor 主题色
     */
    public void setThemeColor(String themeColor) {
        this.themeColor = themeColor;
    }

    /**
     * 获取 顶栏应用主题色0否1是
     *
     * @return topBarUsrThemeColor 顶栏应用主题色0否1是
     */
    public String getTopBarUsrThemeColor() {
        return this.topBarUsrThemeColor;
    }

    /**
     * 设置 顶栏应用主题色0否1是
     *
     * @param topBarUsrThemeColor 顶栏应用主题色0否1是
     */
    public void setTopBarUsrThemeColor(String topBarUsrThemeColor) {
        this.topBarUsrThemeColor = topBarUsrThemeColor;
    }

    /**
     * 获取 顶栏主题色通栏0否1是
     *
     * @return topBarThemeColorBanner 顶栏主题色通栏0否1是
     */
    public String getTopBarThemeColorBanner() {
        return this.topBarThemeColorBanner;
    }

    /**
     * 设置 顶栏主题色通栏0否1是
     *
     * @param topBarThemeColorBanner 顶栏主题色通栏0否1是
     */
    public void setTopBarThemeColorBanner(String topBarThemeColorBanner) {
        this.topBarThemeColorBanner = topBarThemeColorBanner;
    }

    /**
     * 获取 水印内容
     *
     * @return watermarkContent 水印内容
     */
    public String getWatermarkContent() {
        return this.watermarkContent;
    }

    /**
     * 设置 水印内容
     *
     * @param watermarkContent 水印内容
     */
    public void setWatermarkContent(String watermarkContent) {
        this.watermarkContent = watermarkContent;
    }

    /**
     * 获取 是否开启资源水印，0否1是
     *
     * @return isOpenResourceWatermark 是否开启资源水印，0否1是
     */
    public String getIsOpenResourceWatermark() {
        return this.isOpenResourceWatermark;
    }

    /**
     * 设置 是否开启资源水印，0否1是
     *
     * @param isOpenResourceWatermark 是否开启资源水印，0否1是
     */
    public void setIsOpenResourceWatermark(String isOpenResourceWatermark) {
        this.isOpenResourceWatermark = isOpenResourceWatermark;
    }
}
