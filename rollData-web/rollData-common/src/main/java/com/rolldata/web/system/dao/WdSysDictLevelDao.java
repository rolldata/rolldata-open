package com.rolldata.web.system.dao;

import com.rolldata.web.system.entity.WdSysDictLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @Title: WdSysDictLevelDao
 * @Description: 
 * @author shenshilong
 * @date
 * @version V1.0
 */
public interface WdSysDictLevelDao extends JpaRepository<WdSysDictLevel, String> {
    
    /**
     * 删除主表关联的数据
     * @param dictTypeId
     * @throws Exception
     */
    @Modifying(clearAutomatically = true)
    @Query("delete from WdSysDictLevel s where s.dictTypeId = :dictTypeId")
    public void deleteAllByDictTypeId(@Param("dictTypeId")String dictTypeId) throws Exception;
    
    /**
     * 根据字典数据表id和层级查实体类
     * @param dictTypeId 字典数据表id
     * @param sequence 层级
     * @return
     * @throws Exception
     */
    public WdSysDictLevel queryWdSysDictLevelByDictTypeIdAndSequence(String dictTypeId, String sequence) throws Exception;

    /**
     * 根据字典数据表id和层级名称查实体类
     *
     * @param dictTypeId 字典数据表id
     * @param levelName  层级名称
     * @return
     * @throws Exception
     */
    WdSysDictLevel queryWdSysDictLevelByDictTypeIdAndLevelName(String dictTypeId, String levelName) throws Exception;
    
    /**
     *
     * @param dictTypeId
     * @return
     * @throws Exception
     */
    @Query("select new com.rolldata.web.system.entity.WdSysDictLevel(dict.codeLevel as codeLevel, s.sequence, s.digit, s.levelName) from WdSysDictLevel s, SysDictionary dict where s.dictTypeId = dict.id and s.dictTypeId = :dictTypeId order by CAST(s.sequence AS integer) ASC")
    public List<WdSysDictLevel> queryWdSysDictLevelsByDictTypeId(@Param("dictTypeId")String dictTypeId) throws Exception;

    /**
     * 排序表里全部数据
     * @return
     * @throws Exception
     */
    @Query("select s from WdSysDictLevel s order by s.dictTypeId, CAST(s.sequence AS integer) ASC")
    public List<WdSysDictLevel> queryEntitysOrderByDictTypeIdAndSequence () throws Exception;

    /**
     * 根据字典主表id集合查询全部层级数据
     * @return
     * @throws Exception
     */
    @Query("select s from WdSysDictLevel s where s.dictTypeId in (:dictTypeIds)")
    public List<WdSysDictLevel> queryEntitysByDictTypeIds (@Param("dictTypeIds")List<String> dictTypeId) throws Exception;

}
