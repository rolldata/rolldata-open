package com.rolldata.web.system.pojo;

import com.rolldata.web.common.pojo.TreeNode;

import java.util.List;
import java.util.Map;

/**
 * 基础档案内容树节点信息
 * @author shenshilong
 * @createDate 2018-7-31
 */

public class DictTree extends TreeNode {

    private static final long serialVersionUID = -7247379768641953315L;

    private String dictTypeId;
    
    private String dictId;
    
    private String dictName;
    
    private String dictCde;
    
    private Boolean isParent;
    
    /**
     * 层级
     */
    private String level;
    
    /**
     * 层级名称
     */
    private String levelName;
    
    /**
     * 父节点的cde
     */
    private String parentDictCde;
    
    /**
     * 档案属性1
     */
    private String ext1;
    
    /**
     * 档案属性2
     */
    private String ext2;
    
    /**
     * 档案属性3
     */
    private String ext3;
    
    /**
     * 档案属性4
     */
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
     * 子节点
     */
    private List<DictTree> children;

    /**
     * 当前节点另一种形式
     */
    private Map<String, String> selfMap;

    /**
     * 过滤条件配置
     */
    private ModelFilterConf filterConf;

    public String getDictTypeId() {
        return dictTypeId;
    }

    public void setDictTypeId(String dictTypeId) {
        this.dictTypeId = dictTypeId;
    }

    public String getDictId() {
        return dictId;
    }

    public void setDictId(String dictId) {
        this.dictId = dictId;
    }

    public String getDictName() {
        return dictName;
    }

    public void setDictName(String dictName) {
        this.dictName = dictName;
    }

    public String getDictCde() {
        return dictCde;
    }

    public void setDictCde(String dictCde) {
        this.dictCde = dictCde;
    }

    /**  
     * 获取isParent  
     * @return isParent isParent  
     */
    public Boolean getIsParent() {
        return isParent;
    }
    

    /**  
     * 设置isParent  
     * @param isParent isParent  
     */
    public void setIsParent(Boolean isParent) {
        this.isParent = isParent;
    }
    
    
    /**
     * 获取 层级
     *
     * @return level 层级  
     */
    public String getLevel () {
        return this.level;
    }
    
    /**
     * 设置 层级
     *
     * @param level 层级  
     */
    public void setLevel (String level) {
        this.level = level;
    }
    
    /**
     * 获取 父节点的cde
     *
     * @return parentDictCde 父节点的cde  
     */
    public String getParentDictCde () {
        return this.parentDictCde;
    }
    
    /**
     * 设置 父节点的cde
     *
     * @param parentDictCde 父节点的cde  
     */
    public void setParentDictCde (String parentDictCde) {
        this.parentDictCde = parentDictCde;
    }
    
    /**
     * 获取 档案属性1
     *
     * @return ext1 档案属性1  
     */
    public String getExt1 () {
        return this.ext1;
    }
    
    /**
     * 设置 档案属性1
     *
     * @param ext1 档案属性1  
     */
    public void setExt1 (String ext1) {
        this.ext1 = ext1;
    }
    
    /**
     * 获取 档案属性2
     *
     * @return ext2 档案属性2  
     */
    public String getExt2 () {
        return this.ext2;
    }
    
    /**
     * 设置 档案属性2
     *
     * @param ext2 档案属性2  
     */
    public void setExt2 (String ext2) {
        this.ext2 = ext2;
    }
    
    /**
     * 获取 档案属性3
     *
     * @return ext3 档案属性3  
     */
    public String getExt3 () {
        return this.ext3;
    }
    
    /**
     * 设置 档案属性3
     *
     * @param ext3 档案属性3  
     */
    public void setExt3 (String ext3) {
        this.ext3 = ext3;
    }
    
    /**
     * 获取 档案属性4
     *
     * @return ext4 档案属性4  
     */
    public String getExt4 () {
        return this.ext4;
    }
    
    /**
     * 设置 档案属性4
     *
     * @param ext4 档案属性4  
     */
    public void setExt4 (String ext4) {
        this.ext4 = ext4;
    }
    
    /**
     * 获取 层级名称
     *
     * @return levelName 层级名称
     */
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
     * 获取 子节点
     *
     * @return children 子节点
     */
    public List<DictTree> getChildren () {
        return this.children;
    }
    
    /**
     * 设置 子节点
     *
     * @param children 子节点
     */
    public void setChildren (List<DictTree> children) {
        this.children = children;
    }

    /**
     * 获取 当前节点另一种形式
     *
     * @return selfMap 当前节点另一种形式
     */
    public Map<String, String> getSelfMap() {
        return this.selfMap;
    }

    /**
     * 设置 当前节点另一种形式
     *
     * @param selfMap 当前节点另一种形式
     */
    public void setSelfMap(Map<String, String> selfMap) {
        this.selfMap = selfMap;
    }

    /**
     * 获取 过滤条件配置
     *
     * @return filterConf 过滤条件配置
     */
    public ModelFilterConf getFilterConf() {
        return this.filterConf;
    }

    /**
     * 设置 过滤条件配置
     *
     * @param filterConf 过滤条件配置
     */
    public void setFilterConf(ModelFilterConf filterConf) {
        this.filterConf = filterConf;
    }

    /**
     * 获取 备用字段
     *
     * @return ext5 备用字段
     */
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
     * 获取 备用字段
     *
     * @return ext15 备用字段
     */
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
