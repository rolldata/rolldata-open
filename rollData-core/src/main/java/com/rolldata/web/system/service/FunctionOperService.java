package com.rolldata.web.system.service;

import com.rolldata.web.system.entity.SysFunctionOper;

import java.util.List;

/** 
 * @Title: FunctionOperService
 * @Description: 功能明细
 * @Company:www.wrenchdata.com
 * @author shenshilong
 * @date 2018/6/5
 * @version V1.0  
 */
public interface FunctionOperService {
    
    /**
     * 获取当前用户授权按钮
     * @param userId
     * @return
     * @throws Exception
     */
    public List<SysFunctionOper> findFuncnOperByUserId(String userId) throws Exception;
}
