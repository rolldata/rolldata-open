package com.rolldata.web.system.service;

import java.util.List;

/**
 * 统一开关实体管理器,每次调用都复制command里的方法冗余
 *
 * @Title:EntityManagerCommand
 * @Description:EntityManagerCommand
 * @Company:www.wrenchdata.com
 * @author:shenshilong
 * @date:2021-3-01
 * @version:V1.0
 */
public interface EntityManagerCommand {

    /**
     * 打开关闭EntityManager实体管理器
     *
     * @param entityManagerQuery 执行查询
     * @param sql                执行的SQL语句
     * @param pValues            预编译值
     */
    Object command(EntityManagerQuery entityManagerQuery, String sql, List<String> pValues);
}
