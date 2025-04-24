package com.rolldata.web.business.service.impl;

import com.rolldata.web.business.dao.BusinessDao;
import com.rolldata.web.business.entity.BusinessDemoEntity;
import com.rolldata.web.business.pojo.BusinessDemoPojo;
import com.rolldata.web.business.service.BusinessService;
import com.rolldata.web.system.pojo.PageJson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * @Title: BusinessServiceImpl
 * @Description: 业务逻辑实现类
 * @Company: www.wrenchdata.com
 * @author: zhaibx
 * @date: 2025-04-18
 * @version: V1.0
 */
@Service("businessService")
@Transactional
public class BusinessServiceImpl implements BusinessService {

    private static final Logger log = LogManager.getLogger(BusinessServiceImpl.class);

    @Autowired
    private BusinessDao businessDao;

    /**
     * 查询集合，带分页
     * @param pageJson
     * @return
     */
    @Override
    public PageJson queryBusinessList(PageJson pageJson) {
        //比如查询所有，可直接使用，无需写sql
        //return businessDao.findAll();
        //比如根据编码查询，根据JPA规范，无需写sql
        //return businessDao.queryBusinessDemoEntityByDemoCode(pageJson.getSearch());
        Pageable pageable = PageRequest.of(pageJson.getPageable(), pageJson.getSize());
        String search = "%" + pageJson.getSearch() + "%";
        //查询集合，带分页，可自定义条件
        Page<BusinessDemoEntity> page = businessDao.queryBusinessDemoList(search,pageable);
        pageJson.setTotalElements(page.getTotalElements());
        pageJson.setTotalPagets(page.getTotalPages());
        pageJson.setResult(page.getContent());
        return pageJson;
    }

    /**
     * 更新状态
     * @param businessDemoPojo
     */
    @Override
    public void updateBusinessState(BusinessDemoPojo businessDemoPojo) {

        List<String> ids = businessDemoPojo.getIds();
        for (String id : ids) {
            BusinessDemoEntity businessDemoEntity = this.businessDao.findById(id).orElse(null);
            String state = "";
            if(businessDemoEntity.getState().equals("1")){
                state = "0";
            } else {
                state = "1";
            }
            //更新状态，整个实体自动更新保存
//        BusinessDemoEntity businessDemoEntity1 = this.businessDao.findById(businessDemoEntity.getId()).orElse(null);
//        businessDemoEntity1.setState(state);
//        businessDao.saveAndFlush(businessDemoEntity1);
            //自定义sql更新
            businessDao.updateBusinessDemoState(id,state);
        }
    }
}
