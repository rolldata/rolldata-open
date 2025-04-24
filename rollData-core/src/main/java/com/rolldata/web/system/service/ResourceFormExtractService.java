package com.rolldata.web.system.service;

import java.util.List;

/**
 *
 * @Title:ResourceFormExtractService
 * @Description:资源表单调用服务层
 * @Company:www.wrenchdata.com
 * @author shenshilong
 * @date 2020-6-23
 * @version V1.0
 */
public interface ResourceFormExtractService {

    /**
     * 查询表单及任务都是启用的
     *
     * @param formIds 表单id集合
     * @return
     * @throws Exception
     */
    List<String> queryFormState(List<String> formIds) throws Exception;

    /**
     * 所有启用中的表单id集合
     *
     * @return
     * @throws Exception
     */
    List<String> queryFormState() throws Exception;

    /**
     * 所有启用并且是当前登录人上报的表单 id 集合
     *
     * @return 表单 id 集合
     * @throws Exception
     */
    List<String> queryFormStateAndFillUser() throws Exception;

    /**
     * 查询能看到的上报表单
     *
     * @param formIds 表单集合
     * @return
     * @throws Exception
     */
//    List<String> queryFillForm(List<String> formIds) throws Exception;

}
