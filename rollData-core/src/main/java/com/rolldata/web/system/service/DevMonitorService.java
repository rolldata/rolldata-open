package com.rolldata.web.system.service;

import com.rolldata.web.system.pojo.DevMonitorServerResult;

/**
 * @Title: DevMonitorService
 * @Description: 监控Service接口
 * @Company: www.wrenchdata.com
 * @author: zhaibx
 * @date: 2025-02-06
 * @version: V1.0
 */
public interface DevMonitorService {
    /**
     * 查询服务器监控信息
     * @return
     */
    DevMonitorServerResult queryServerInfo();

    /**
     * 查询服务器网络情况
     * @return
     */
    DevMonitorServerResult queryNetworkInfo();
}
