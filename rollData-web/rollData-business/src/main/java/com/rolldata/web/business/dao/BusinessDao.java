package com.rolldata.web.business.dao;

import com.rolldata.web.business.entity.BusinessDemoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @Title: BusinessDao
 * @Description: 业务demo工厂类
 * @Company: www.wrenchdata.com
 * @author: zhaibx
 * @date: 2025-04-18
 * @version: V1.0
 */
public interface BusinessDao extends JpaRepository<BusinessDemoEntity, String> , JpaSpecificationExecutor<BusinessDemoEntity> {

    List<BusinessDemoEntity> queryBusinessDemoEntityByDemoCode(String demoCode);

    @Query(value="select v from BusinessDemoEntity v where v.demoCode like :demoCode order by v.createTime desc ",
            countQuery="select count(v.id) from BusinessDemoEntity v " )
    Page<BusinessDemoEntity> queryBusinessDemoList(@Param("demoCode")String demoCode, Pageable pageable);

    @Modifying(clearAutomatically = true)
    @Query("update BusinessDemoEntity o set o.state=:state where o.id = :id")
    void updateBusinessDemoState(@Param("id")String id, @Param("state")String state);
}
