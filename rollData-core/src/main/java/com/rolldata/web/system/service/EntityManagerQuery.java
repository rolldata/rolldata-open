package com.rolldata.web.system.service;

import javax.persistence.Query;

/**
 * @Title:EntityManagerQuery
 * @Description:EntityManagerQuery
 * @Company:www.wrenchdata.com
 * @author:shenshilong
 * @date:2021-3-01
 * @version:V1.0
 */
public interface EntityManagerQuery {

    Object qeury(Query query);
}
