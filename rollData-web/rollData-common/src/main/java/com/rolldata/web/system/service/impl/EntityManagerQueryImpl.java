package com.rolldata.web.system.service.impl;

import com.rolldata.web.system.service.EntityManagerQuery;
import javax.persistence.Query;

/**
 * @Title: EntityManagerQueryImpl
 * @Description: EntityManagerQueryImpl
 * @Company: www.wrenchdata.com
 * @author: shenshilong
 * @date: 2021-03-01
 * @version: V1.0
 */
public class EntityManagerQueryImpl implements EntityManagerQuery {

    @Override
    public Object qeury(Query query) {
        query.executeUpdate();
        return "asdf";
    }
}
