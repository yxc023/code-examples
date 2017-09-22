package com.yangxiaochen.example.jdbc;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import jodd.db.DbQuery;

import java.sql.SQLException;

/**
 * @author yangxiaochen
 * @date 2017/9/22 12:19
 */
public class Main {
    public static void main(String[] args) throws SQLException {
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setUrl("jdbc:mysql://localhost:3306/test?statementInterceptors=com.yangxiaochen.example.jdbc.MyStatmentInterceptor");
        dataSource.setUser("root");
        DbQuery query = new DbQuery(dataSource.getConnection(),
                "select * from test where id=:id");
        query.setInteger("id",1);
        query.execute();
        System.out.println(query);
    }
}
