package com.rolldata.web.system.entity;

import com.rolldata.core.common.entity.IdEntity;
import com.rolldata.core.util.StringUtil;
import com.rolldata.web.system.util.SpecialSymbolConstants;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * 数据字典类型表
 * @author shenshilong
 * @createDate 2018-6-8
 */
@Entity
@Table(name="wd_sys_dict_type")
@DynamicInsert(true)
@DynamicUpdate(true)
public class SysDictionary extends IdEntity implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3501370725577237928L;

	/*展现形式：1列表  2树形*/
	public static final String SHOWTIME_TABLE = "1";
	
	public static final String SHOWTIME_TREE = "2";
	
	/*创建类型： 1系统 2普通用户创建*/
	public static final String DICTTYPE_SYS = "1";
	
	public static final String DICTTYPE_ORD = "2";

    /**
     * 字典类型:0基础档案
     */
	public static final String TYPE0 = "0";

    /**
     * 字典类型:1数据档案
     */
	public static final String TYPE1 = "1";

    /**
     * 0无
     */
    public static final String RELY_TYPE0 = "0";

    /**
     * 1依赖父节点构建
     */
	public static final String RELY_TYPE1 = "1";

    /**
     * 2依赖长度构建
     */
	public static final String RELY_TYPE2 = "2";


	/*字典类型代码*/
	private String dictTypeCde;
	
	/*字典类型名称*/
	private String dictTypeName;
	
	/*展现形式：1列表  2树形*/
	private String showType;
	
	/*创建类型： 1系统 2普通用户创建*/
	private String dictType;

	/**
	 * 属性名称(逗号拼接)
	 */
	private String propertyName;

	/**
	 * 编码层级
	 */
	private String codeLevel;

	/**
	 * 属性个数
	 */
	private String propertyCount;

	/**
	 * 字典类型:0基础档案1数据档案
	 */
	private String cType;

	/**
	 * 构建类型:0无1依赖父节点构建2依赖长度构建
	 */
	private String relyType;

	/**
	 * 库表
	 */
	private String tableName;

	/**
	 * 显示值字段
	 */
	private String showValue;

	/**
	 * 实际值字段
	 */
	private String realValue;

	/**
	 * 父节点字段
	 */
	private String parentValue;

	/**
	 * 属性字段
	 */
	private String propertyColumn;

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

    public String assembleColumns() {

        StringBuilder columns = new StringBuilder();
        columns.append(SpecialSymbolConstants.bundleColumn(this.realValue));

        // 实际值,显示值 不是一个字段时 拼接
        if (!this.showValue.equalsIgnoreCase(this.realValue)) {
            columns.append(SpecialSymbolConstants.COMMA)
                   .append(SpecialSymbolConstants.bundleColumn(this.showValue));
        }
        if (StringUtil.isNotEmpty(this.parentValue) &&
            !this.parentValue.equalsIgnoreCase(this.realValue)  &&
            !this.parentValue.equalsIgnoreCase(this.showValue)) {
            columns.append(SpecialSymbolConstants.COMMA)
                   .append(SpecialSymbolConstants.bundleColumn(this.parentValue));
        }
        if (StringUtil.isNotEmpty(this.propertyName) &&
            StringUtil.isNotEmpty(this.propertyColumn)) {
            String[] propertyTables = this.propertyName.split(",");
            String[] propertyColumns = this.propertyColumn.split(",");
            for (int i = 0; i < propertyTables.length; i++) {
                String columnName = propertyTables[i];
                String column = propertyColumns[i];

                // 判断重复字段
                if (column.equalsIgnoreCase(this.showValue) ||
                    column.equalsIgnoreCase(this.realValue) ||
                    column.equalsIgnoreCase(this.parentValue)) {
                    continue;
                }
                columns.append(SpecialSymbolConstants.COMMA)
                       .append(column).append(SpecialSymbolConstants.SQL_AS)
                       .append(SpecialSymbolConstants.bundleColumn(columnName));
            }
        }
        return columns.toString();
    }

	@Column(name = "dict_type_cde", nullable = true, length = 20)
	public String getDictTypeCde() {
		return dictTypeCde;
	}

	public void setDictTypeCde(String dictTypeCde) {
		this.dictTypeCde = dictTypeCde;
	}

	@Column(name = "dict_type_name", nullable = true, length = 50)
	public String getDictTypeName() {
		return dictTypeName;
	}

	public void setDictTypeName(String dictTypeName) {
		this.dictTypeName = dictTypeName;
	}

	@Column(name = "show_type", nullable = true, length = 1)
	public String getShowType() {
		return showType;
	}

	public void setShowType(String showType) {
		this.showType = showType;
	}
	
	@Column(name = "dict_type", nullable= true, length = 1)
	public String getDictType() {
		return dictType;
	}

	public void setDictType(String dictType) {
		this.dictType = dictType;
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
	 * 获取 属性名称(逗号拼接)
	 *
	 * @return propertyName 属性名称(逗号拼接)
	 */
	@Column(name = "property_name", length = 50)
	public String getPropertyName () {
		return this.propertyName == null ? "" : this.propertyName;
	}

	/**
	 * 设置 属性名称(逗号拼接)
	 *
	 * @param propertyName 属性名称(逗号拼接)
	 */
	public void setPropertyName (String propertyName) {
		this.propertyName = propertyName;
	}

	/**
	 * 获取 编码层级
	 *
	 * @return codeLevel 编码层级
	 */
	@Column(name = "code_level", length = 2)
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
	 * 获取 属性个数
	 *
	 * @return propertyCount 属性个数
	 */
	@Column(name = "property_count", length = 2)
	public String getPropertyCount () {
		return this.propertyCount;
	}

	/**
	 * 设置 属性个数
	 *
	 * @param propertyCount 属性个数
	 */
	public void setPropertyCount (String propertyCount) {
		this.propertyCount = propertyCount;
	}


    /**
     * 获取 字典类型:0基础档案1数据档案
     *
     * @return cType 字典类型:0基础档案1数据档案
     */
    @Column(name = "c_type", length = 1)
    public String getCType() {
        if (StringUtil.isEmpty(this.cType)) {
            this.cType = SysDictionary.TYPE0;
        }
        return this.cType;
    }

    /**
     * 设置 字典类型:0基础档案1数据档案
     *
     * @param cType 字典类型:0基础档案1数据档案
     */
    public void setCType(String cType) {
        this.cType = cType;
    }

	/**
	 * 获取 构建类型:0无1依赖父节点构建2依赖长度构建
	 *
	 * @return relyType 构建类型:0无1依赖父节点构建2依赖长度构建
	 */
	@Column(name = "rely_type", length = 1)
	public String getRelyType() {
		return this.relyType;
	}

	/**
	 * 设置 构建类型:0无1依赖父节点构建2依赖长度构建
	 *
	 * @param relyType 构建类型:0无1依赖父节点构建2依赖长度构建
	 */
	public void setRelyType(String relyType) {
		this.relyType = relyType;
	}

	/**
	 * 获取 库表
	 *
	 * @return tableName 库表
	 */
	@Column(name = "c_table_name", length = 30)
	public String getTableName() {
		return this.tableName;
	}

	/**
	 * 设置 库表
	 *
	 * @param tableName 库表
	 */
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	/**
	 * 获取 显示值字段
	 *
	 * @return showValue 显示值字段
	 */
	@Column(name = "show_value", length = 50)
	public String getShowValue() {
		return this.showValue;
	}

	/**
	 * 设置 显示值字段
	 *
	 * @param showValue 显示值字段
	 */
	public void setShowValue(String showValue) {
		this.showValue = showValue;
	}

	/**
	 * 获取 实际值字段
	 *
	 * @return realValue 实际值字段
	 */
	@Column(name = "real_value", length = 50)
	public String getRealValue() {
		return this.realValue;
	}

	/**
	 * 设置 实际值字段
	 *
	 * @param realValue 实际值字段
	 */
	public void setRealValue(String realValue) {
		this.realValue = realValue;
	}

	/**
	 * 获取 父节点字段
	 *
	 * @return parentValue 父节点字段
	 */
	@Column(name = "parent_value", length = 50)
	public String getParentValue() {
		return this.parentValue;
	}

	/**
	 * 设置 父节点字段
	 *
	 * @param parentValue 父节点字段
	 */
	public void setParentValue(String parentValue) {
		this.parentValue = parentValue;
	}

	/**
	 * 获取 属性字段
	 *
	 * @return propertyColumn 属性字段
	 */
	@Column(name = "property_column", length = 50)
	public String getPropertyColumn() {
		return this.propertyColumn;
	}

	/**
	 * 设置 属性字段
	 *
	 * @param propertyColumn 属性字段
	 */
	public void setPropertyColumn(String propertyColumn) {
		this.propertyColumn = propertyColumn;
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
