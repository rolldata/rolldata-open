package com.rolldata.web.system.pojo;

/**
 * @Title: PageJson
 * @Description: 分页
 * @Company:www.wrenchdata.com
 * @author shenshilong
 * @date
 * @version V1.0
 */
public class PageJson extends Result {
    
    /**
     * 0=最近一周
     */
    public static final String SEARCH_WEEK = "0";
    
    /**
     * 1=最近一个月
     */
    public static final String SEARCH_MONTH = "1";
    
    /**
     * 2=最近1年
     */
    public static final String SEARCH_YEAR = "2";

    /**
     * 3-全部
     */
    public static final String SEARCH_ALL = "3";

    /*第几页*/
    private int page = 1;
    
    /*页面条数*/
    private int size = 10;
    
    /*总页数*/
    private int totalPagets;
    
    /*pId*/
    private String parentId;
    
    /*类型*/
    private String type;
    
    /*搜索输入的内容*/
    private String search = "";
    
    /**
     * 总条数
     */
    private long totalElements;
    
    public int getPage() {
        return page;
    }
    
    public int getPageable() {
        return page - 1;
    }
    
    public PageJson setPage(int page) {
        this.page = page;
        return this;
    }
    
    public int getSize() {
        return size;
    }
    
    public PageJson setSize(int size) {
        this.size = size;
        return this;
    }
    
    public int getTotalPagets() {
        return totalPagets;
    }
    
    public PageJson setTotalPagets(int totalPagets) {
        this.totalPagets = totalPagets;
        return this;
    }
    
    public String getParentId() {
        return this.parentId;
    }
    
    public PageJson setParentId(String parentId) {
        this.parentId = parentId;
        return this;
    }
    
    public String getType() {
        return this.type;
    }
    
    public PageJson setType(String type) {
        this.type = type;
        return this;
    }

	/**  
	 * 获取search  
	 * @return search search  
	 */
	public String getSearch() {
		return search;
	}

	/**  
	 * 设置search  
	 * @param search search  
	 */
	public void setSearch(String search) {
		this.search = search;
	}
    
    
    /**
     * 获取 总条数
     */
    public long getTotalElements() {
        return this.totalElements;
    }
    
    /**
     * 设置 总条数
     */
    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }
}
