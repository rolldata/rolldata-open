package com.rolldata.web.business.service;

import com.rolldata.web.business.entity.BusinessDemoEntity;
import com.rolldata.web.business.pojo.BusinessDemoPojo;
import com.rolldata.web.system.pojo.PageJson;

import java.util.List;

/**
 * @Title: BusinessService
 * @Description: 业务服务接口
 * @Company: www.wrenchdata.com
 * @author: zhaibx
 * @date: 2025-04-18
 * @version: V1.0
 */
public interface BusinessService {
    /**
     * 查询业务列表
     * @param pageJson
     * @return
     */
    PageJson queryBusinessList(PageJson pageJson);

    /**
     * 更新业务状态
     * @param businessDemoPojo
     */
    void updateBusinessState(BusinessDemoPojo businessDemoPojo);
}
