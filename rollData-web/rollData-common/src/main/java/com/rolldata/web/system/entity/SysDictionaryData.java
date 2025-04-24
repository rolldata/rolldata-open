package com.rolldata.web.system.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.Date;

/**
 * 数据字典内容表
 * @author shenshilong
 * @createDate 2018-6-8
 */

@Entity
@Table(name = "wd_sys_dict_content")
@DynamicInsert(true)
@DynamicUpdate(true)
public class SysDictionaryData implements java.io.Serializable{
    
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1696167685959513262L;

	/**
     * 最顶级
     */
    public static String ROOT = "0";
    
    private String id;
    
    /*字典类型ID(数据字典表主键)*/
    private String dictTypeId;
    
    /*字典代码*/
    private String dictCde;
    
    /*字典名称*/
    private String dictName;
    
    /*父节点ID*/
    private String parentId;
    
    /*层级*/
    private String level;
    
    /*行数*/
    private String cIndex;
    
    /*备用字段1*/
    private String ext1;
    
    /*备用字段2*/
    private String ext2;
    
    /*备用字段3*/
    private String ext3;
    
    /*备用字段4*/
    private String ext4;

    /*备用字段*/
    private String ext5;

    /*备用字段*/
    private String ext6;

    /*备用字段*/
    private String ext7;

    /*备用字段*/
    private String ext8;

    /*备用字段*/
    private String ext9;

    /*备用字段*/
    private String ext10;

    /*备用字段*/
    private String ext11;

    /*备用字段*/
    private String ext12;

    /*备用字段*/
    private String ext13;

    /*备用字段*/
    private String ext14;

    /*备用字段*/
    private String ext15;

    /*备用字段*/
    private String ext16;

    /*备用字段*/
    private String ext17;

    /*备用字段*/
    private String ext18;

    /*备用字段*/
    private String ext19;

    /*备用字段*/
    private String ext20;

    /**
     * 主表propertyCount
     */
    private String propertyCount;
    
    /**
     * propertyName
     */
    private String propertyName;
    
    /*创建用户ID*/
    private String createUser;
    
    /*创建时间*/
    private Date createTime;
    
    /*修改用户ID*/
    private String updateUser;
    
    /*修改时间*/
    private Date updateTime;

    /**外部安装模型的id*/
    private String wdModelId;

    @Id
    @Column(name ="id",nullable=false,length=32)
    public String getId(){
        return this.id;
    }
    
    public void setId(String id){
        this.id = id;
    }
    
    public SysDictionaryData() {
        super();
    }
    
    public SysDictionaryData (String id, String dictTypeId, String dictCde, String dictName,
        String parentId, String level, String cIndex, String ext1, String ext2, String ext3,
        String ext4, String ext5, String ext6, String ext7, String ext8, String ext9, String ext10,
        String ext11, String ext12, String ext13, String ext14,String ext15,String ext16,String ext17,
        String ext18,String ext19,String ext20, String propertyCount,
        String propertyName) {
        this.id = id;
        this.dictTypeId = dictTypeId;
        this.dictCde = dictCde;
        this.dictName = dictName;
        this.parentId = parentId;
        this.level = level;
        this.cIndex = cIndex;
        this.ext1 = ext1;
        this.ext2 = ext2;
        this.ext3 = ext3;
        this.ext4 = ext4;
        this.ext5 = ext5;
        this.ext6 = ext6;
        this.ext7 = ext7;
        this.ext8 = ext8;
        this.ext9 = ext9;
        this.ext10 = ext10;
        this.ext11 = ext11;
        this.ext12 = ext12;
        this.ext13 = ext13;
        this.ext14 = ext14;
        this.ext15 = ext15;
        this.ext16 = ext16;
        this.ext17 = ext17;
        this.ext18 = ext18;
        this.ext19 = ext19;
        this.ext20 = ext20;
        this.propertyCount = propertyCount;
        this.propertyName = propertyName;
    }
    
    @Column(name = "dict_type_id", nullable = true, length = 32)
    public String getDictTypeId() {
        return dictTypeId;
    }

    public void setDictTypeId(String dictTypeId) {
        this.dictTypeId = dictTypeId;
    }

    @Column(name = "dict_cde", nullable = true, length = 20)
    public String getDictCde() {
        return dictCde;
    }

    public void setDictCde(String dictCde) {
        this.dictCde = dictCde;
    }

    @Column(name = "dict_name", nullable = true, length = 50)
    public String getDictName() {
        return dictName;
    }

    public void setDictName(String dictName) {
        this.dictName = dictName;
    }

    @Column(name = "parent_id", nullable = true, length = 32)
    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    @Column(name = "c_level", nullable = true, length = 50)
    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    @Column(name = "c_index", nullable = true, length = 50)
    public String getcIndex() {
        return cIndex;
    }

    public void setcIndex(String cIndex) {
        this.cIndex = cIndex;
    }

    @Column(name = "ext1", nullable = true, length = 200)
    public String getExt1() {
        return ext1;
    }

    public void setExt1(String ext1) {
        this.ext1 = ext1;
    }

    @Column(name = "ext2", nullable = true, length = 200)
    public String getExt2() {
        return ext2;
    }

    public void setExt2(String ext2) {
        this.ext2 = ext2;
    }

    @Column(name = "ext3", nullable = true, length = 200)
    public String getExt3() {
        return ext3;
    }

    public void setExt3(String ext3) {
        this.ext3 = ext3;
    }

    @Column(name = "ext4", nullable = true, length = 200)
    public String getExt4() {
        return ext4;
    }

    public void setExt4(String ext4) {
        this.ext4 = ext4;
    }
    
    @Column(name = "create_user", nullable = true, length = 32)
    public String getCreateUser() {
        return createUser;
    }
    
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }
    
    @Column(name = "create_time", nullable = true)
    public Date getCreateTime() {
        return createTime;
    }
    
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    
    @Column(name = "update_user", nullable = true, length = 32)
    public String getUpdateUser() {
        return updateUser;
    }
    
    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }
    
    @Column(name = "update_time", nullable = true)
    public Date getUpdateTime() {
        return updateTime;
    }
    
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 获取 备用字段
     *
     * @return ext5 备用字段
     */
    @Column(name = "ext5", nullable = true, length = 200)
    public String getExt5() {
        return this.ext5;
    }

    /**
     * 设置 备用字段
     *
     * @param ext5 备用字段
     */
    public void setExt5(String ext5) {
        this.ext5 = ext5;
    }

    /**
     * 获取 备用字段
     *
     * @return ext6 备用字段
     */
    @Column(name = "ext6", nullable = true, length = 200)
    public String getExt6() {
        return this.ext6;
    }

    /**
     * 设置 备用字段
     *
     * @param ext6 备用字段
     */
    public void setExt6(String ext6) {
        this.ext6 = ext6;
    }

    /**
     * 获取 备用字段
     *
     * @return ext7 备用字段
     */
    @Column(name = "ext7", nullable = true, length = 200)
    public String getExt7() {
        return this.ext7;
    }

    /**
     * 设置 备用字段
     *
     * @param ext7 备用字段
     */
    public void setExt7(String ext7) {
        this.ext7 = ext7;
    }

    /**
     * 获取 备用字段
     *
     * @return ext8 备用字段
     */
    @Column(name = "ext8", nullable = true, length = 200)
    public String getExt8() {
        return this.ext8;
    }

    /**
     * 设置 备用字段
     *
     * @param ext8 备用字段
     */
    public void setExt8(String ext8) {
        this.ext8 = ext8;
    }

    /**
     * 获取 备用字段
     *
     * @return ext9 备用字段
     */
    @Column(name = "ext9", nullable = true, length = 200)
    public String getExt9() {
        return this.ext9;
    }

    /**
     * 设置 备用字段
     *
     * @param ext9 备用字段
     */
    public void setExt9(String ext9) {
        this.ext9 = ext9;
    }

    /**
     * 获取 备用字段
     *
     * @return ext10 备用字段
     */
    @Column(name = "ext10", nullable = true, length = 200)
    public String getExt10() {
        return this.ext10;
    }

    /**
     * 设置 备用字段
     *
     * @param ext10 备用字段
     */
    public void setExt10(String ext10) {
        this.ext10 = ext10;
    }

    /**
     * 获取 备用字段
     *
     * @return ext11 备用字段
     */
    @Column(name = "ext11", nullable = true, length = 200)
    public String getExt11() {
        return this.ext11;
    }

    /**
     * 设置 备用字段
     *
     * @param ext11 备用字段
     */
    public void setExt11(String ext11) {
        this.ext11 = ext11;
    }

    /**
     * 获取 备用字段
     *
     * @return ext12 备用字段
     */
    @Column(name = "ext12", nullable = true, length = 200)
    public String getExt12() {
        return this.ext12;
    }

    /**
     * 设置 备用字段
     *
     * @param ext12 备用字段
     */
    public void setExt12(String ext12) {
        this.ext12 = ext12;
    }

    /**
     * 获取 备用字段
     *
     * @return ext13 备用字段
     */
    @Column(name = "ext13", nullable = true, length = 200)
    public String getExt13() {
        return this.ext13;
    }

    /**
     * 设置 备用字段
     *
     * @param ext13 备用字段
     */
    public void setExt13(String ext13) {
        this.ext13 = ext13;
    }

    /**
     * 获取 备用字段
     *
     * @return ext14 备用字段
     */
    @Column(name = "ext14", nullable = true, length = 200)
    public String getExt14() {
        return this.ext14;
    }

    /**
     * 设置 备用字段
     *
     * @param ext14 备用字段
     */
    public void setExt14(String ext14) {
        this.ext14 = ext14;
    }
    
    /**
     * 获取 主表propertyCount
     *
     * @return propertyCount 主表propertyCount
     */
    @Transient
    public String getPropertyCount () {
        return this.propertyCount;
    }
    
    /**
     * 设置 主表propertyCount
     *
     * @param propertyCount 主表propertyCount
     */
    public void setPropertyCount (String propertyCount) {
        this.propertyCount = propertyCount;
    }
    
    /**
     * 获取 propertyName
     *
     * @return propertyName propertyName
     */
    @Transient
    public String getPropertyName () {
        return this.propertyName;
    }
    
    /**
     * 设置 propertyName
     *
     * @param propertyName propertyName
     */
    public void setPropertyName (String propertyName) {
        this.propertyName = propertyName;
    }

    /**
     * 获取 外部安装模型的id
     *
     * @return wdModelId 外部安装模型的id
     */
    @Column(name = "wd_model_id", length = 32)
    public String getWdModelId() {
        return this.wdModelId;
    }

    /**
     * 设置 外部安装模型的id
     *
     * @param wdModelId 外部安装模型的id
     */
    public void setWdModelId(String wdModelId) {
        this.wdModelId = wdModelId;
    }

    /**
     * 获取 备用字段
     *
     * @return ext15 备用字段
     */
    @Column(name = "ext15", nullable = true, length = 200)
    public String getExt15() {
        return this.ext15;
    }

    /**
     * 设置 备用字段
     *
     * @param ext15 备用字段
     */
    public void setExt15(String ext15) {
        this.ext15 = ext15;
    }

    /**
     * 获取 备用字段
     *
     * @return ext16 备用字段
     */
    @Column(name = "ext16", nullable = true, length = 200)
    public String getExt16() {
        return this.ext16;
    }

    /**
     * 设置 备用字段
     *
     * @param ext16 备用字段
     */
    public void setExt16(String ext16) {
        this.ext16 = ext16;
    }

    /**
     * 获取 备用字段
     *
     * @return ext17 备用字段
     */
    @Column(name = "ext17", nullable = true, length = 200)
    public String getExt17() {
        return this.ext17;
    }

    /**
     * 设置 备用字段
     *
     * @param ext17 备用字段
     */
    public void setExt17(String ext17) {
        this.ext17 = ext17;
    }

    /**
     * 获取 备用字段
     *
     * @return ext18 备用字段
     */
    @Column(name = "ext18", nullable = true, length = 200)
    public String getExt18() {
        return this.ext18;
    }

    /**
     * 设置 备用字段
     *
     * @param ext18 备用字段
     */
    public void setExt18(String ext18) {
        this.ext18 = ext18;
    }

    /**
     * 获取 备用字段
     *
     * @return ext19 备用字段
     */
    @Column(name = "ext19", nullable = true, length = 200)
    public String getExt19() {
        return this.ext19;
    }

    /**
     * 设置 备用字段
     *
     * @param ext19 备用字段
     */
    public void setExt19(String ext19) {
        this.ext19 = ext19;
    }

    /**
     * 获取 备用字段
     *
     * @return ext20 备用字段
     */
    @Column(name = "ext20", nullable = true, length = 200)
    public String getExt20() {
        return this.ext20;
    }

    /**
     * 设置 备用字段
     *
     * @param ext20 备用字段
     */
    public void setExt20(String ext20) {
        this.ext20 = ext20;
    }
}
