package com.rolldata.web.system.service;

import javax.persistence.Query;
import java.util.List;

/**
 * 根据sql返回泛型集合
 *
 * @Title: QueryVO
 * @Description: 根据sql返回泛型集合
 * @Company: www.wrenchdata.com
 * @author: shenshilong
 * @date: 2020-11-19
 * @version: V1.0
 */
@FunctionalInterface
public interface QueryVOs<T> {

    List<T> queryList(Query query);
}
