package com.rolldata.web.common.enums;

/** 
 * @Title: TreeNodeType
 * @Description: 树类型
 * @Company:www.wrenchdata.com
 * @author shenshilong
 * @date 2018/7/16
 * @version V1.0  
 */
public enum TreeNodeType {
    
    /** 菜单类型 */
    FUNC,
    
    /**角色类型*/
    ROLE,
    
    /**按钮*/
    OPER,
    
    /**文件夹*/
    FOLDER,
    
    /**用户*/
    USER,

    /**公司*/
    COMPANY,

    /**
     * 部门
     */
    DEPARTMENT,

    /**
     * 职务
     */
    POST,

    /**top节点*/
    ROOT,
    
    /**树形*/
    TREE,
    
    /**列表*/
    TABLE,
    
    /**档案*/
    DICT,
    /**
     * 表单
     */
    FORM,
    
    /**
     * 指标
     */
    ITEM,
    
    /**
     * 报表
     */
    ENTERPRISEREPORT,
    
    /**
     * 普通指标
     */
    ORDINARYITEM,
    
    /**
     * 计算指标
     */
    CALCULATIONITEM,
    
    /**
     * 流程
     */
    PROCESS,
    
    /**
     * 汇总方案
     */
    SUMMARYPLAN,

    /**
     * 数据报表
     */
    DATAREPORT,
    
    /**数据转换任务*/
    DATATRANSFERTASK,
    
    /**作业流程*/
    DATAWORKFLOW,

    /**调度计划*/
    DATASCHEDULEPLAN,
    
    /**维度*/
    DATADIMENSION,
    
    /**维度的字段*/
    DATADIMENSIONFIELD,
    
    /**指标*/
    DATAITEM,

    /**
     * 模型
     */
    MODEL,

    /**
     * 数据源
     */
    DATASOURCE,
    
    /**项目*/
    PRODUCT,
    
    /**账套*/
    ACCOUNT,
    
    /**人员*/
    PERSION,
    
    /**科目*/
    U8ITEM,

    /**规则*/
    U8Rule,

    /**预警推送任务*/
    WARNTASK,

    /**指标管理-报表*/
    ITEMREPORT,

    /**api接口*/
    API;

}
