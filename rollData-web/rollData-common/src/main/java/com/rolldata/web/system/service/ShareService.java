package com.rolldata.web.system.service;

import com.rolldata.web.common.pojo.tree.ResponseTree;
import java.util.List;

/**
 * @Title:
 * @Description:
 * @Company:www.wrenchdata.com
 * @author:shenshilong
 * @date:2020-12-15
 * @version:V1.0
 */
public interface ShareService {

    List<ResponseTree> querySysTables() throws Exception;

    /**
     * 根据表名查询字段集合
     *
     * @param tableName 表名
     * @return
     * @throws Exception
     */
    List<String> querySysTableColunms(String tableName) throws Exception;

}
