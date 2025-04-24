package com.rolldata.web.system.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @Title: JdbcQuery
 * @Description: JdbcQuery
 * @Company: www.wrenchdata.com
 * @author: shenshilong
 * @date: 2021-07-19
 * @version: V1.0
 */
@FunctionalInterface
public interface JdbcQuery {

    List<?> queryList(ResultSet resultSet) throws SQLException;
}
