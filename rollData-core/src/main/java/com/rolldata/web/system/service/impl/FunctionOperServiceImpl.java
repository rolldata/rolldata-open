package com.rolldata.web.system.service.impl;

import com.rolldata.core.util.AdministratorUtils;
import com.rolldata.web.system.dao.SysFunctionOperDao;
import com.rolldata.web.system.entity.SysFunctionOper;
import com.rolldata.web.system.service.FunctionOperService;
import com.rolldata.web.system.util.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * @Title: FunctionOperServiceImpl
 * @Description: 功能明细 实现
 * @Company:
 * @author shenshilong
 * @date 2018/6/5
 * @version V1.0
 */
@Service("functionOperService")
public class FunctionOperServiceImpl implements FunctionOperService {

    @Autowired
    private SysFunctionOperDao sysFunctionOperDao;


    /**
     * 获取当前用户授权按钮
     *
     * @param userId
     * @return
     * @throws Exception
     */
    @Override
    public List<SysFunctionOper> findFuncnOperByUserId(String userId) throws Exception {

        List<SysFunctionOper> roleFunctioinList = null;
        List<SysFunctionOper> postFunctioinList = null;
        List<SysFunctionOper> result = new LinkedList<>();
        if (AdministratorUtils.getAdministratorName().equals(UserUtils.getUser().getUserCde())) {

            // 所有按钮
            result = sysFunctionOperDao.findFuncnOper();
        } else {
            roleFunctioinList = sysFunctionOperDao.findFuncnOperByUserId(userId);
            postFunctioinList = sysFunctionOperDao.findPostFuncnOperByUserId(userId);
            roleFunctioinList.addAll(postFunctioinList);
            Set<String> ids = new HashSet<>();
            for (SysFunctionOper sysFunctionOper : roleFunctioinList) {
                if (!ids.contains(sysFunctionOper.getId())) {
                    result.add(sysFunctionOper);
                }
                ids.add(sysFunctionOper.getId());
            }
        }
        return result;
    }
}
