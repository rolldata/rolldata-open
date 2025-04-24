package com.rolldata.web.system.pojo;

import com.rolldata.web.system.pojo.datahandle.Breadcrumb;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SysResourcePage extends PageJson {

	public static final Map<String, List<String>> TYPE_CENTRE = new HashMap<>(2);

	/**
	 * 看板中心
	 */
	public static final String KANBAN_CENTER = "1";

	public static final String REPORT_CENTER = "2";

	static {

		// 看板中心
		TYPE_CENTRE.put(KANBAN_CENTER, Arrays.asList(
		));

		// 报表中心
		TYPE_CENTRE.put(REPORT_CENTER, Arrays.asList(
		));
	}

	/**
	 * 是否公开，0个人，1公开
	 */
	private String isPublic;

	/**
	 * 面包屑导航
	 */
	private List<Breadcrumb> breadcrumbs;

	/**
	 * 获取是否公开，0个人，1公开
	 * @return isPublic 是否公开，0个人，1公开
	 */
	public String getIsPublic() {
		return isPublic;
	}


	/**
	 * 设置是否公开，0个人，1公开
	 * @param isPublic 是否公开，0个人，1公开
	 */
	public void setIsPublic(String isPublic) {
		this.isPublic = isPublic;
	}

	/**
	 * 获取 面包屑导航
	 *
	 * @return breadcrumbs 面包屑导航
	 */
	public List<Breadcrumb> getBreadcrumbs() {
		return this.breadcrumbs;
	}

	/**
	 * 设置 面包屑导航
	 *
	 * @param breadcrumbs 面包屑导航
	 */
	public void setBreadcrumbs(List<Breadcrumb> breadcrumbs) {
		this.breadcrumbs = breadcrumbs;
	}
}
