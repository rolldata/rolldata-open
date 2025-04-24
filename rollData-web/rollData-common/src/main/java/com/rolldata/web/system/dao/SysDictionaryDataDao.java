package com.rolldata.web.system.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rolldata.web.system.entity.SysDictionaryData;

/**
 * 数据字典类型表数据层
 * @author shenshilong
 * @createDate 2018-6-11
 */
public interface SysDictionaryDataDao extends JpaRepository<SysDictionaryData, String>{
    
    /**
     * 根据数据字典表主键查询结果集
     * @param dictTypeId
     * @return
     * @throws Exception
     * @createDate 2018-6-12
     */
    @Query("from SysDictionaryData s where s.dictTypeId = :dictTypeId order by CAST(s.cIndex AS integer) ASC")
    public List<SysDictionaryData> findByDictTypeId(@Param("dictTypeId")String dictTypeId) throws Exception;
    
    /**
     * 根据父id查询
     * @param parentId
     * @return
     * @createDate 2018-6-11
     */
    public List<SysDictionaryData> findByParentId(@Param("parentId")String parentId) throws Exception;
    
    /**
     * 根据数据字典主表删除所有
     * @param dictTypeId
     * @throws Exception
     * @createDate 2018-6-12
     */
    @Modifying(clearAutomatically = true)
    @Query("delete from SysDictionaryData s where s.dictTypeId = :dictTypeId")
    public void delAllByDictTypeId(@Param("dictTypeId")String dictTypeId) throws Exception;
    
    @Modifying(clearAutomatically = true)
    @Query("delete from SysDictionaryData s where s.id = :id")
    public void delteSysDictionaryDataById(@Param("id")String id) throws Exception;
    
    /**
     * 根据字典类型id和父节点id查询
     * @param dictTypeId
     * @param parentId
     * @return
     * @throws Exception
     * @createDate 2018-6-11
     */
    public List<SysDictionaryData> findByDictTypeIdAndParentId(@Param("dictTypeId")String dictTypeId, @Param("parentId")String parentId) throws Exception;
    
    /**
     * 更新
     * @param id
     * @param dictName
     * @param ext1
     * @param ext2
     * @param ext3
     * @param ext4
     * @throws Exception
     * @createDate 2018-6-11
     */
    @Modifying(clearAutomatically = true)
    @Query("update SysDictionaryData s set s.dictName = :dictName, s.ext1 = :ext1, s.ext2 = :ext2, s.ext3 = :ext3, s.ext4 = :ext4, s.ext5 = :ext5, s.ext6 = :ext6, s.ext7 = :ext7, s.ext8 = :ext8, s.ext9 = :ext9, s.ext10 = :ext10, s.ext11 = :ext11, s.ext12 = :ext12, s.ext13 = :ext13, s.ext14 = :ext14,s.ext15 = :ext15,s.ext16 = :ext16,s.ext17 = :ext17,s.ext18 = :ext18,s.ext19 = :ext19,s.ext20 = :ext20, s.updateUser = :updateUser, s.updateTime = :updateTime where s.id = :id")
    public void update(
        @Param("id")String id,
        @Param("dictName")String dictName,
        @Param("ext1")String ext1,
        @Param("ext2")String ext2,
        @Param("ext3")String ext3,
        @Param("ext4")String ext4,
        @Param("ext5")String ext5,
        @Param("ext6")String ext6,
        @Param("ext7")String ext7,
        @Param("ext8")String ext8,
        @Param("ext9")String ext9,
        @Param("ext10")String ext10,
        @Param("ext11")String ext11,
        @Param("ext12")String ext12,
        @Param("ext13")String ext13,
        @Param("ext14")String ext14,
        @Param("ext15")String ext15,
        @Param("ext16")String ext16,
        @Param("ext17")String ext17,
        @Param("ext18")String ext18,
        @Param("ext19")String ext19,
        @Param("ext20")String ext20,
        @Param("updateUser")String updateUser,
        @Param("updateTime") Date updateTime
    ) throws Exception;

    /**
     * 通过dictName和DictTypeId查询对象,名称是否重复
     *
     * @author shenshilong
     * @param pId
     * @param dictName
     * @param dictTypeId
     * @return
     * @throws Exception
     * @createDate 2018-8-7
     */
    @Query("from SysDictionaryData s where s.dictName = :dictName and s.dictTypeId = :dictTypeId"
        + " and s.parentId = :pId")
    public SysDictionaryData getBydictNameAndDictTypeId(
        @Param("pId")String pId,
        @Param("dictName")String dictName,
        @Param("dictTypeId")String dictTypeId
    ) throws Exception;
    
    /**
     * 通过dictCde和DictTypeId查询对象
     * @author shenshilong
     * @param dictCde
     * @param dictTypeId
     * @return
     * @throws Exception
     * @createDate 2018-8-7
     */
    @Query("from SysDictionaryData s where s.dictCde = :dictCde and s.dictTypeId = :dictTypeId")
    public SysDictionaryData getBydictCdeAndDictTypeId(@Param("dictCde")String dictCde, @Param("dictTypeId")String dictTypeId) throws Exception;


    @Modifying(clearAutomatically = true)
    @Query("update SysDictionaryData s set s.parentId = :parentId where s.id = :id")
    public void updateByIdAndParentId(@Param("id")String id, @Param("parentId")String parentId) throws Exception;

    /**
     * 通过dictCde和DictTypeId、dictId查询对象
     * @param dictCde
     * @param dictTypeId
     * @throws Exception
     */
    @Query("from SysDictionaryData s where s.dictCde = :dictCde and s.dictTypeId = :dictTypeId and s.id <> :dictId")
    public SysDictionaryData getBydictCdeAndDictTypeId(@Param("dictCde")String dictCde,
            @Param("dictTypeId")String dictTypeId, @Param("dictId")String dictId) throws Exception;

    /**
     * 名称重复查询
     *
     * @param pId        父节点id
     * @param dictName   名称
     * @param dictTypeId 档案主表id
     * @param dictId     档案内容id
     * @return
     * @throws Exception
     */
    @Query("from SysDictionaryData s where s.parentId = :pId and s.dictName = :dictName and"
        + " s.dictTypeId = :dictTypeId and s.id <> :dictId")
    public SysDictionaryData getBydictNameAndDictTypeId(
        @Param("pId")String pId,
        @Param("dictName")String dictName,
        @Param("dictTypeId")String dictTypeId,
        @Param("dictId") String dictId
    ) throws Exception;
    
    /**
     * 根据id 查对象
     * @param id
     * @return
     * @throws Exception
     */
    public SysDictionaryData queryDistinctById(String id) throws Exception;
    
    /**
     * 去掉父级dictCde,结尾最大的数字
     *
     * @param startNum 截取起始位置
     * @param dictTypeId 字典主表id
     * @param parentId 父id
     * @return
     * @throws Exception
     */
    @Query("select max(substring(sdd.dictCde, :startNum, length(sdd.dictCde))) from SysDictionaryData sdd where sdd.dictTypeId = :dictTypeId and sdd.parentId = :parentId")
    public String queryMaxMantissa(
        @Param("startNum")int startNum,
        @Param("dictTypeId")String dictTypeId,
        @Param("parentId")String parentId
    ) throws Exception;

    /**
     * 根据档案代码查SysDictionaryData集合
     * @param dictCde  档案主表代码
     * @return
     * @throws Exception
     */
    @Query("select new com.rolldata.web.system.entity.SysDictionaryData(fsdc.id, fsdc.dictTypeId, fsdc.dictCde, fsdc.dictName, fsdc.parentId, fsdc.level, fsdc.cIndex, fsdc.ext1, fsdc.ext2, fsdc.ext3, fsdc.ext4, fsdc.ext5, fsdc.ext6, fsdc.ext7, fsdc.ext8, fsdc.ext9, fsdc.ext10, fsdc.ext11, fsdc.ext12, fsdc.ext13, fsdc.ext14,fsdc.ext15,fsdc.ext16,fsdc.ext17,fsdc.ext18,fsdc.ext19,fsdc.ext20, fsdt.propertyCount as propertyCount, fsdt.propertyName as propertyName) from SysDictionary fsdt, SysDictionaryData fsdc where fsdt.id = fsdc.dictTypeId and fsdt.dictTypeCde = :dictCde order by CAST(fsdc.cIndex AS integer) ASC")
    public List<SysDictionaryData> queryAllByMainCde(@Param("dictCde")String dictCde) throws Exception;
    
    /**
     * 根据档案id查SysDictionaryData集合
     * @param dictId  档案主表id
     * @return
     * @throws Exception
     */
    @Query("select new com.rolldata.web.system.entity.SysDictionaryData(fsdc.id, fsdc.dictTypeId, fsdc.dictCde, fsdc.dictName, fsdc.parentId, fsdc.level, fsdc.cIndex, fsdc.ext1, fsdc.ext2, fsdc.ext3, fsdc.ext4, fsdc.ext5, fsdc.ext6, fsdc.ext7, fsdc.ext8, fsdc.ext9, fsdc.ext10, fsdc.ext11, fsdc.ext12, fsdc.ext13, fsdc.ext14,fsdc.ext15,fsdc.ext16,fsdc.ext17,fsdc.ext18,fsdc.ext19,fsdc.ext20, fsdt.propertyCount as propertyCount, fsdt.propertyName as propertyName) from SysDictionary fsdt, SysDictionaryData fsdc where fsdt.id = fsdc.dictTypeId and fsdt.id = :dictId order by CAST(fsdc.cIndex AS integer) ASC")
    public List<SysDictionaryData> queryAllByDictId(@Param("dictId")String dictId) throws Exception;
    
    /**
     * 修改基础数据编码
     * @param id
     * @param dictCde
     */
    @Modifying(clearAutomatically = true)
    @Query("update SysDictionaryData s set s.dictCde = :dictCde where s.id = :id")
    public void updateDictCde (@Param("id")String id, @Param("dictCde")String dictCde);
    
    /**
     * 基础的档案相应层级数据
     * @param dictTypeId 档案主表id
     * @param level 层级
     * @return
     * @throws Exception
     */
    @Query("select new com.rolldata.web.system.entity.SysDictionaryData(sdd.id, sdd.dictTypeId, sdd.dictCde, sdd.dictName, sdd.parentId, sdd.level, sdd.cIndex, sdd.ext1, sdd.ext2, sdd.ext3, sdd.ext4, sdd.ext5, sdd.ext6, sdd.ext7, sdd.ext8, sdd.ext9, sdd.ext10, sdd.ext11, sdd.ext12, sdd.ext13, sdd.ext14,sdd.ext15,sdd.ext16,sdd.ext17,sdd.ext18,sdd.ext19,sdd.ext20, dict.propertyCount as propertyCount, dict.propertyName as propertyName) from SysDictionaryData sdd, SysDictionary dict where sdd.dictTypeId = dict.id and sdd.dictTypeId = :dictTypeId and sdd.level = :level")
    public List<SysDictionaryData> querySysDictionaryDataByLevel(@Param("dictTypeId")String dictTypeId, @Param("level")String level) throws Exception;
    
    /**
     * 基础的档案相应层级数据
     * @param dictTypeId 档案主表id
     * @param levelName 层级名称
     * @return
     * @throws Exception
     */
    @Query("select new com.rolldata.web.system.entity.SysDictionaryData(sdd.id, sdd.dictTypeId, sdd.dictCde, sdd.dictName, sdd.parentId, sdd.level, sdd.cIndex, sdd.ext1, sdd.ext2, sdd.ext3, sdd.ext4, sdd.ext5, sdd.ext6, sdd.ext7, sdd.ext8, sdd.ext9, sdd.ext10, sdd.ext11, sdd.ext12, sdd.ext13, sdd.ext14,sdd.ext15,sdd.ext16,sdd.ext17,sdd.ext18,sdd.ext19,sdd.ext20, dict.propertyCount as propertyCount, dict.propertyName as propertyName) from SysDictionaryData sdd, SysDictionary dict where sdd.dictTypeId = dict.id and sdd.dictTypeId = :dictTypeId and sdd.level = (select wdl.sequence from WdSysDictLevel wdl where wdl.dictTypeId = (select redict.id from SysDictionary redict where redict.id = :dictTypeId) and wdl.levelName = :levelName)")
    public List<SysDictionaryData> querySysDictionaryDataByLevelName(@Param("dictTypeId")String dictTypeId, @Param("levelName")String levelName) throws Exception;
    
    
    /**
     * 删除基础数据相应层级所有数据
     * @param dictTypeId 基础档案id
     * @param codeLevel 层级
     * @throws Exception
     */
    @Modifying(clearAutomatically = true)
    @Query("delete from SysDictionaryData s where s.dictTypeId = :dictTypeId and s.level = :codeLevel")
    public void deleteUnnecessaryDigit (@Param("dictTypeId")String dictTypeId, @Param("codeLevel")String codeLevel) throws Exception;
    
    /**
     * 级联结果集
     * @param dictId 档案主表id
     * @param parentDictCde 父cde
     * @return
     * @throws Exception
     */
    @Query("select new com.rolldata.web.system.entity.SysDictionaryData(sdd.id, sdd.dictTypeId, sdd.dictCde, sdd.dictName, sdd.parentId, sdd.level, sdd.cIndex, sdd.ext1, sdd.ext2, sdd.ext3, sdd.ext4, sdd.ext5, sdd.ext6, sdd.ext7, sdd.ext8, sdd.ext9, sdd.ext10, sdd.ext11, sdd.ext12, sdd.ext13, sdd.ext14,sdd.ext15,sdd.ext16,sdd.ext17,sdd.ext18,sdd.ext19,sdd.ext20, dict.propertyCount as propertyCount, dict.propertyName as propertyName) " +
                              "from SysDictionaryData sdd, SysDictionary dict where sdd.dictTypeId = dict.id " +
                              "and sdd.parentId = (select sysdd.id from SysDictionary sysd, SysDictionaryData sysdd where sysd.id = sysdd.dictTypeId and sysd.id = :dictId and sysdd.dictCde = :parentDictCde)")
    public List<SysDictionaryData> queryCascadeDistinctByDictId (@Param("dictId")String dictId, @Param("parentDictCde")String parentDictCde) throws Exception;
    
    /**
     * 下拉框提供数据
     * @param dictId 档案主表id
     * @param levelName 层级名称
     * @return
     * @throws Exception
     */
    @Query("select new com.rolldata.web.system.entity.SysDictionaryData(sdd.id, sdd.dictTypeId, sdd.dictCde, sdd" +
           ".dictName, sdd.parentId, sdd.level, sdd.cIndex, sdd.ext1, sdd.ext2, sdd.ext3, sdd.ext4,"
        + " sdd.ext5, sdd.ext6, sdd.ext7, sdd.ext8, sdd.ext9, sdd.ext10, sdd.ext11, sdd.ext12, sdd.ext13, sdd.ext14," +
            "sdd.ext15,sdd.ext16,sdd.ext17,sdd.ext18,sdd.ext19,sdd.ext20, " +
           "dict.propertyCount as propertyCount, dict.propertyName as propertyName) from SysDictionaryData sdd, " +
           "SysDictionary dict where sdd.dictTypeId = dict.id and dict.id = :dictId and sdd.level = (select wdl" +
           ".sequence from WdSysDictLevel wdl where wdl.dictTypeId = :dictId and wdl.levelName = :levelName)")
    public List<SysDictionaryData> queryDistinctBySelectDictId (@Param("dictId")String dictId, @Param("levelName")String levelName) throws Exception;

    /**
     * c_index最大的
     * @param dictTypeId 主表字典id
     * @return
     * @throws Exception
     */
    @Query("select MAX(cast(s.cIndex as int)) from SysDictionaryData s where s.dictTypeId = :dictTypeId")
    public String queryMaxCindex (@Param("dictTypeId") String dictTypeId) throws Exception;

    /**
     * 根据代码查数据
     *
     * @param code       字典内容代码
     * @param dictTypeId 主表id
     * @return
     * @throws Exception
     */
    public SysDictionaryData querySysDictionaryDataByDictCdeAndDictTypeId(String code, String dictTypeId) throws Exception;

    /**
     * 根据数据字典表主键集合查询结果集
     * @param dictTypeIds
     * @return
     * @throws Exception
     * @createDate 2018-6-12
     */
    @Query("from SysDictionaryData s where s.dictTypeId in (:dictTypeIds) ")
    public List<SysDictionaryData> findByDictTypeIds(@Param("dictTypeIds")List<String> dictTypeIds) throws Exception;

    /**
     * 根据id修改排序序号
     * @param id
     * @param index
     * @param updateUser
     * @param updateTime
     * @throws Exception
     */
    @Modifying(clearAutomatically = true)
    @Query("update SysDictionaryData s set s.cIndex = :index,s.updateTime=:updateTime,s.updateUser =:updateUser where s.id = :id")
    public void updateSortByIdAndToId(@Param("id")String id, @Param("index")String index, @Param("updateUser") String updateUser, @Param("updateTime") Date updateTime) throws Exception;

}
