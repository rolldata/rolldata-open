package com.rolldata.web.system.pojo;

import com.rolldata.core.util.DateUtils;
import com.rolldata.core.util.StringUtil;

import java.util.Date;

/**
 * 区间查询
 *
 * @Title: RegionPageJson
 * @Description: RegionPageJson
 * @Company: www.wrenchdata.com
 * @author: shenshilong
 * @date: 2021-04-01
 * @version: V1.0
 */
public class RegionPageJson extends PageJson {

    /**
     * 查询条件-开始时间
     */
    private String startTime;

    /**
     * 查询条件-结束时间
     */
    private String endTime;

    /**
     * 开始时间格式化
     *
     * @return 日期
     */
    public Date getStartDate() {

        if (StringUtil.isEmpty(this.startTime)) {
            return null;
        }
        return DateUtils.str2Date(this.startTime + " 00:00:00", DateUtils.PATTERN_YYYY_MM_DD_HH_MM_SS);
    }

    /**
     * 结束时间格式化
     *
     * @return 日期
     */
    public Date getEndDate() {

        if (StringUtil.isEmpty(this.endTime)) {
            return null;
        }
        return DateUtils.str2Date(this.endTime + " 23:59:59", DateUtils.PATTERN_YYYY_MM_DD_HH_MM_SS);
    }

    /**
     * 获取 查询条件-开始时间
     *
     * @return startTime 查询条件-开始时间
     */
    public String getStartTime() {
        return this.startTime;
    }

    /**
     * 设置 查询条件-开始时间
     *
     * @param startTime 查询条件-开始时间
     */
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    /**
     * 获取 查询条件-结束时间
     *
     * @return endTime 查询条件-结束时间
     */
    public String getEndTime() {
        return this.endTime;
    }

    /**
     * 设置 查询条件-结束时间
     *
     * @param endTime 查询条件-结束时间
     */
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
