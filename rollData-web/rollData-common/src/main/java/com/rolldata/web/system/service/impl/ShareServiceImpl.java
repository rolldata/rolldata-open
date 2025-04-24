package com.rolldata.web.system.service.impl;

import com.rolldata.web.common.pojo.tree.ResponseTree;
import com.rolldata.web.system.pojo.DataSourceInfo;
import com.rolldata.web.system.pojo.SQLContainer;
import com.rolldata.web.system.service.BaseService;
import com.rolldata.web.system.service.ShareService;
import com.rolldata.web.system.util.DBConnectionUtil;
import com.rolldata.web.system.util.DBUtil;
import com.rolldata.web.system.util.DynamicDBUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Title: ShareServiceImpl
 * @Description: ShareServiceImpl
 * @Company: www.wrenchdata.com
 * @author: shenshilong
 * @date: 2020-12-15
 * @version: V1.0
 */
@Service("shareService")
@Transactional
public class ShareServiceImpl implements ShareService {

    @Autowired
    private BaseService baseService;

    @Override
    public List<ResponseTree> querySysTables() throws Exception {

        List<ResponseTree> tableAndViewList = new ArrayList<>();
        DataSourceInfo dataSourceInfo = DBUtil.getSystemDataSourceInfo();
        Connection conn = DBUtil.getSystemConn();

        /**查询数据库里的表*/
        String allTableSql = DynamicDBUtil.getCurrentSysTableSQL();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = DBConnectionUtil.getPreparedStatement(conn, allTableSql);
            rs = DBConnectionUtil.findResult(pstmt, null);
            while (rs.next()) {
                ResponseTree tableResponseTree = new ResponseTree(rs.getString(1), rs.getString(1));
                tableResponseTree.setName(rs.getString(1));
                tableResponseTree.setDsType(dataSourceInfo.getDbType());
                tableAndViewList.add(tableResponseTree);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            DBConnectionUtil.closeAll(rs, pstmt, conn);
        }
        return tableAndViewList;
    }


    /**
     * 根据表名查询字段集合
     *
     * @param tableName 表名
     * @return
     * @throws Exception
     */
    @Override
    public List<String> querySysTableColunms(String tableName) throws Exception {

        DataSourceInfo systemDataSourceInfo = DBUtil.getSystemDataSourceInfo();
        SQLContainer sqlContainer = DynamicDBUtil.hhCreateTCSql(tableName, systemDataSourceInfo.getDbType());
        return this.baseService.queryValueObjectsBySQL((query) ->{
            return query.getResultList();
        }, sqlContainer.getSql(), sqlContainer.getPrecompiledValues());
    }
}
