package com.yangxiaochen.example.jdbc;

import com.mysql.jdbc.*;

import java.sql.SQLException;
import java.util.Properties;

/**
 * @author yangxiaochen
 * @date 2017/9/22 12:30
 */
public class MyStatmentInterceptor implements StatementInterceptorV2 {
    @Override
    public void init(Connection conn, Properties props) throws SQLException {

    }

    @Override
    public ResultSetInternalMethods preProcess(String sql, Statement interceptedStatement, Connection connection) throws SQLException {
        if (interceptedStatement instanceof PreparedStatement) {
            System.out.println("-----------------------------");
            System.out.println(((PreparedStatement) interceptedStatement).asSql());
            System.out.println("-----------------------------");
        }
        return null;
    }

    @Override
    public boolean executeTopLevelOnly() {
        return false;
    }

    @Override
    public void destroy() {

    }

    @Override
    public ResultSetInternalMethods postProcess(String sql, Statement interceptedStatement, ResultSetInternalMethods originalResultSet, Connection connection, int warningCount, boolean noIndexUsed, boolean noGoodIndexUsed, SQLException statementException) throws SQLException {
        return null;
    }
}
