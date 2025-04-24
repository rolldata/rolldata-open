package com.rolldata.web.system.entity;

import com.rolldata.core.common.entity.IdEntity;
import javax.persistence.Transient;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * @Title: WdSysDictLevel
 * @Description: 字典层级表
 * @Company:
 * @author shenshilong
 * @date
 * @version V1.0
 */
@Entity
@Table(name = "wd_sys_dict_level")
@DynamicInsert(true)
@DynamicUpdate(true)
public class WdSysDictLevel extends IdEntity implements java.io.Serializable {

  /**
	 * 
	 */
	private static final long serialVersionUID = 9160714661499271924L;

/**
   * 字典类型id
   */
  private String dictTypeId;

  /**
   * 层级序列
   */
  private String sequence;

  /**
   * 位数
   */
  private String digit;

  /**
   * 层级名称
   */
  private String levelName;

  /**
   * 创建用户
   */
  private String createUser;

  /**
   * 创建时间
   */
  private Date createTime;

  /**
   * 修改用户
   */
  private String updateUser;

  /**
   * 修改时间
   */
  private Date updateTime;
  
  /**
   * 编码层级
   */
  private String codeLevel;

  /**外部安装模型的id*/
  private String wdModelId;

  public WdSysDictLevel () {
  }
  
  /**
   * 修改基础档案时,比对原数据
   * @param codeLevel
   * @param sequence
   * @param digit
   * @param levelName
   */
  public WdSysDictLevel (String codeLevel, String sequence, String digit, String levelName) {
    this.codeLevel = codeLevel;
    this.sequence = sequence;
    this.digit = digit;
    this.levelName = levelName;
  }
  
  /**
   * 获取 字典类型id
   *
   * @return dictTypeId 字典类型id
   */
  @Column(name = "dict_type_id", length = 32)
  public String getDictTypeId () {
    return this.dictTypeId;
  }

  /**
   * 设置 字典类型id
   *
   * @param dictTypeId 字典类型id
   */
  public void setDictTypeId (String dictTypeId) {
    this.dictTypeId = dictTypeId;
  }

  /**
   * 获取 层级序列
   *
   * @return sequence 层级序列
   */
  @Column(name = "c_sequence", length = 1)
  public String getSequence () {
    return this.sequence;
  }

  /**
   * 设置 层级序列
   *
   * @param sequence 层级序列
   */
  public void setSequence (String sequence) {
    this.sequence = sequence;
  }

  /**
   * 获取 位数
   *
   * @return digit 位数
   */
  @Column(name = "digit", length = 50)
  public String getDigit () {
    return this.digit;
  }

  /**
   * 设置 位数
   *
   * @param digit 位数
   */
  public void setDigit (String digit) {
    this.digit = digit;
  }

  /**
   * 获取 层级名称
   *
   * @return levelName 层级名称
   */
  @Column(name = "level_name", length = 50)
  public String getLevelName () {
    return this.levelName;
  }

  /**
   * 设置 层级名称
   *
   * @param levelName 层级名称
   */
  public void setLevelName (String levelName) {
    this.levelName = levelName;
  }

  /**
   * 获取 创建用户
   *
   * @return createUser 创建用户
   */
  @Column(name = "create_user", length = 32)
  public String getCreateUser () {
    return this.createUser;
  }

  /**
   * 设置 创建用户
   *
   * @param createUser 创建用户
   */
  public void setCreateUser (String createUser) {
    this.createUser = createUser;
  }

  /**
   * 获取 创建时间
   *
   * @return createTime 创建时间
   */
  @Column(name = "create_time")
  public Date getCreateTime () {
    return this.createTime;
  }

  /**
   * 设置 创建时间
   *
   * @param createTime 创建时间
   */
  public void setCreateTime (Date createTime) {
    this.createTime = createTime;
  }

  /**
   * 获取 修改用户
   *
   * @return updateUser 修改用户
   */
  @Column(name = "update_user", length = 32)
  public String getUpdateUser () {
    return this.updateUser;
  }

  /**
   * 设置 修改用户
   *
   * @param updateUser 修改用户
   */
  public void setUpdateUser (String updateUser) {
    this.updateUser = updateUser;
  }

  /**
   * 获取 修改时间
   *
   * @return updateTime 修改时间
   */
  @Column(name = "update_time")
  public Date getUpdateTime () {
    return this.updateTime;
  }

  /**
   * 设置 修改时间
   *
   * @param updateTime 修改时间
   */
  public void setUpdateTime (Date updateTime) {
    this.updateTime = updateTime;
  }
  
  /**
   * 获取 编码层级
   *
   * @return codeLevel 编码层级
   */
  @Transient
  public String getCodeLevel () {
    return this.codeLevel;
  }
  
  /**
   * 设置 编码层级
   *
   * @param codeLevel 编码层级
   */
  public void setCodeLevel (String codeLevel) {
    this.codeLevel = codeLevel;
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
}