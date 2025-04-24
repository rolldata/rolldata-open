package com.rolldata.web.system.service.impl;

import com.rolldata.web.system.pojo.SysTableInfo;
import com.rolldata.web.system.service.QueryVOs;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;

import javax.persistence.Query;
import java.util.List;

/**
 * @Title: QueryVOImpl
 * @Description:
 * @Company: www.wrenchdata.com
 * @author: shenshilong
 * @date: 2020-11-19
 * @version: V1.0
 */
public class QueryVOImpl implements QueryVOs<SysTableInfo> {

    @Override
    public List<SysTableInfo> queryList(Query query) {

        query.unwrap(SQLQuery.class).setResultTransformer(Transformers.aliasToBean(SysTableInfo.class));
        return query.getResultList();
    }
}
