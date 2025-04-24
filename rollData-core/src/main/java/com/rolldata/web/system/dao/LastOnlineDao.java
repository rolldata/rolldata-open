package com.rolldata.web.system.dao;

import com.rolldata.web.system.entity.SysLastOnline;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Title: LastOnlineDao
 * @Description: 最后在线用户表Dao层 接口
 * @Company:www.wrenchdata.com
 * @author shenshilong
 * @date 2018-7-25
 * @version V1.0
 */

public interface LastOnlineDao extends JpaRepository<SysLastOnline, String> {
    
    /**
     * id取实体
     * @param id
     * @return
     * @throws Exception
     */
    public SysLastOnline querySysLastOnlineById(String id) throws Exception;
    
    /**
     * userId取实体
     * @param userId
     * @return
     * @throws Exception
     */
    public SysLastOnline querySysLastOnlineByUserId(String userId) throws Exception;
}
