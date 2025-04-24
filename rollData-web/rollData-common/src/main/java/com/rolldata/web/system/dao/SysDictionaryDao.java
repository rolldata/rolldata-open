package com.rolldata.web.system.dao;

import com.rolldata.web.system.entity.SysDictionary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

/**
 * 数据字典表数据层
 * @author shenshilong
 * @createDate 2018-6-11
 */
public interface SysDictionaryDao extends JpaRepository<SysDictionary, String>{

    /**
     * 更新
     *
     * @param id
     * @param dictTypeCde
     * @param dictTypeName
     * @param codeLevel
     * @param propertyCount
     * @param propertyName
     * @param CType
     * @param relyType
     * @param tableName
     * @param showValue
     * @param realValue
     * @param parentValue
     * @param propertyColumn
     * @param updateUser
     * @param updateTime
     * @throws Exception
     */
    @Modifying(clearAutomatically = true)
    @Query("update SysDictionary s set s.dictTypeCde = :dictTypeCde, s.dictTypeName = :dictTypeName," +
           " s.codeLevel = :codeLevel, s.propertyCount = :propertyCount, s.propertyName = :propertyName," +
           " s.CType = :CType, s.relyType = :relyType, s.tableName = :tableName, s.showValue = :showValue, " +
           " s.realValue = :realValue, s.parentValue = :parentValue, s.propertyColumn = :propertyColumn," +
           " s.updateUser = :updateUser, s.updateTime = :updateTime where s.id = " + ":id")
    public void update(@Param("id") String id, @Param("dictTypeCde") String dictTypeCde,
        @Param("dictTypeName") String dictTypeName, @Param("codeLevel") String codeLevel,
        @Param("propertyCount") String propertyCount, @Param("propertyName") String propertyName,
        @Param("CType") String CType, @Param("relyType") String relyType, @Param("tableName") String tableName,
        @Param("showValue") String showValue, @Param("realValue") String realValue, @Param("parentValue") String parentValue,
        @Param("propertyColumn") String propertyColumn, @Param("updateUser") String updateUser,
        @Param("updateTime") Date updateTime) throws Exception;

    /**
     * 通过dictTypeCde查询对象
     *
     * @param dictTypeCde
     * @return
     * @throws Exception
     * @author shenshilong
     * @createDate 2018-8-6
     */
    @Query("from SysDictionary s where s.dictTypeCde = :dictTypeCde")
    public SysDictionary getByDictTypeCde(@Param("dictTypeCde")String dictTypeCde) throws Exception;

    /**
     * 通过dictTypeName查询对象
     *
     * @param dictTypeName
     * @return
     * @throws Exception
     * @author shenshilong
     * @createDate 2018-8-6
     */
    @Query("from SysDictionary s where s.dictTypeName = :dictTypeName")
    public SysDictionary getByDictTypeName(@Param("dictTypeName")String dictTypeName) throws Exception;

    /**
     * 根据id删除目录
     *
     * @param id
     */
    @Modifying(clearAutomatically = true)
    @Query("delete from SysDictionary s where s.id = :id")
    public void deleteSysDictionaryById(@Param("id")String id) throws Exception;

    /**
     * 通过dictTypeCde查询对象
     *
     * @param dictTypeCde
     * @param dictTypeId
     * @throws Exception
     */
    @Query("from SysDictionary s where s.dictTypeCde = :dictTypeCde and s.id <> :dictTypeId")
    public SysDictionary getByDictTypeCde(@Param("dictTypeCde")String dictTypeCde, @Param("dictTypeId")String dictTypeId) throws Exception;

    /**
     * 通过dictTypeName查询对象
     *
     * @param dictTypeName
     * @param dictTypeId
     * @throws Exception
     */
    @Query("from SysDictionary s where s.dictTypeName = :dictTypeName and s.id <> :dictTypeId")
    public SysDictionary getByDictTypeName(@Param("dictTypeName")String dictTypeName,
            @Param("dictTypeId")String dictTypeId) throws Exception;

    /**
     * 根据id查唯一实体类
     *
     * @param id
     * @return
     * @throws Exception
     */
    public SysDictionary querySysDictionaryById(String id) throws Exception;

    /**
     * 查询全部字典数据
     *
     * @param userIdPermissionList 可管理的用户id集合
     * @return
     * @throws Exception
     */
    @Query("from SysDictionary s where s.createUser in (:userIdPermissionList) order by s.createTime asc ")
    public List<SysDictionary> querySysDictionaries (@Param("userIdPermissionList") List<String> userIdPermissionList) throws Exception;

    /**
     * 查询全部字典数据
     *
     * @return
     * @throws Exception
     */
    @Query("from SysDictionary s order by s.createTime asc ")
    public List<SysDictionary> querySysDictionaries () throws Exception;

    /**
     * 根据id集合查询字典
     * @param dictIds
     * @return
     * @throws Exception
     */
    @Query("from SysDictionary s where s.id in (:ids)")
    public List<SysDictionary> querySysDictionaryByIds(@Param("ids")List<String> dictIds) throws Exception;

    /**
     * 根据类型查询字典数据
     *
     * @return
     * @throws Exception
     */
    @Query("from SysDictionary s where s.CType = :cType order by s.createTime asc ")
    List<SysDictionary> querySysDictionariesByCType(@Param("cType")String cType);
}
