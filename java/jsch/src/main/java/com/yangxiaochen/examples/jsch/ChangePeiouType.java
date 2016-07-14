package com.yangxiaochen.examples.jsch;

import com.jcraft.jsch.Session;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import com.pastdev.jsch.DefaultSessionFactory;
import com.yangxiaochen.examples.jsch.config.SshTunnelDbConfig;
import jodd.bean.BeanUtil;
import jodd.db.DbQuery;
import jodd.db.DbSession;
import jodd.db.DbTransactionMode;
import jodd.db.QueryMapper;
import jodd.db.connection.ConnectionProvider;
import jodd.db.connection.DataSourceConnectionProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.core.ColumnMapRowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author yangxiaochen
 * @date 16/7/14 上午10:55
 */
public class ChangePeiouType {
    private static Logger log = LogManager.getLogger(ChangePeiouType.class);

    public static void main(String[] args) throws Exception {


        SshTunnelDbConfig config = SshTunnelDbConfig.createOnlineConfig();
//        SshTunnelDbConfig config = SshTunnelDbConfig.createDevConfig();
//        SshTunnelDbConfig config = SshTunnelDbConfig.createCiConfig();
        String dbuser = config.getDbuser();
        String dbpasswd = config.getDbpasswd();
        DefaultSessionFactory defaultSessionFactory = new DefaultSessionFactory(
                config.getSshusername(), config.getSshhost(), 22);

        defaultSessionFactory.setPassword(config.getSshpasswd());
        defaultSessionFactory.setKnownHosts("~/.ssh/known_hosts");
//        defaultSessionFactory.setConfig("StrictHostKeyChecking", "no");
        Session session = defaultSessionFactory.newSession();

        session.connect();
        session.setPortForwardingL(10240, config.getDbhost(), config.getDbport());

        try {


            MysqlDataSource dataSource = new MysqlDataSource();
            dataSource.setURL("jdbc:mysql://localhost:10240/FN_TE?useUnicode=true&characterEncoding=utf-8&allowMultiQueries=true&zeroDateTimeBehavior=convertToNull");
            dataSource.setUser(dbuser);
            dataSource.setPassword(dbpasswd);
            dataSource.setDatabaseName("fn_te");
            ConnectionProvider connectionProvider = new DataSourceConnectionProvider(dataSource);

            DbSession dbSession = new DbSession(connectionProvider);
            dbSession.beginTransaction(new DbTransactionMode().setReadOnly(false));

            try {

                DbQuery q = new DbQuery(dbSession, "SELECT DISTINCT(b.business_id) as businessId FROM fn_jr.fn_person_info p, fn_te.business b\n" +
                        "WHERE p.business_id = b.business_id\n" +
                        "AND b.business_type = 1000\n" +
                        "AND (b.sub_type = 1003 OR b.sub_type = 1004)\n" +
                        "AND p.type=3 AND p.info_id <= 4109");
                List<Map<String, Object>> businesses = q.list(new Main.MapRowMapper());


                for (Map<String, Object> business : businesses) {
                    log.info("target business {}" , BeanUtil.pojo.getProperty(business,"businessId").toString());

                    q = new DbQuery(dbSession, "SELECT * FROM fn_jr.fn_person_info WHERE business_id= :businessId");
                    q.setLong("businessId",BeanUtil.pojo.getProperty(business,"businessId"));
                    List<Map<String, Object>> persons = q.list(new Main.MapRowMapper());


                    List<Map<String,Object>> mainPersons = new ArrayList<>();
                    for (Map<String, Object> person : persons) {
                        int type = BeanUtil.pojo.getProperty(person,"type");
                        if (type == 7 || type == 8) {
                            mainPersons.add(person);
                        }
                    }

                    for (Map<String, Object> mainPerson : mainPersons) {
                        Long poid = BeanUtil.pojo.getProperty(mainPerson,"po_id");
                        if (poid == null) {
                            continue;
                        }
                        int mainType = BeanUtil.pojo.getProperty(mainPerson,"type");

                        for (Map<String, Object> person : persons) {
                            long infoId = BeanUtil.pojo.getProperty(person,"info_id");
                            int type = BeanUtil.pojo.getProperty(person,"type");

                            if (poid == infoId && type == 3) {
                                if (mainType == 7) {
                                    q = new DbQuery(dbSession, "update fn_jr.fn_person_info set type = :type WHERE info_id= :infoId");
                                    q.setInteger("type",5);
                                    q.setLong("infoId",infoId);
                                    q.executeUpdate();
                                }

                                if (mainType == 8) {
                                    q = new DbQuery(dbSession, "update fn_jr.fn_person_info set type = :type WHERE info_id= :infoId");
                                    q.setInteger("type",6);
                                    q.setLong("infoId",infoId);
                                    q.executeUpdate();
                                }
                            }
                        }

                    }
                }



                dbSession.commitTransaction();
            } catch (Throwable e) {
                log.error(e.getMessage(), e);
                dbSession.rollbackTransaction();
            } finally {
                dbSession.close();
            }

        } finally {
            session.disconnect();
        }
    }


    public static class MapRowMapper implements QueryMapper<Map<String, Object>> {
        private ColumnMapRowMapper rowMapper = new ColumnMapRowMapper();
        private int i = 0;

        @Override
        public Map<String, Object> process(ResultSet resultSet) throws SQLException {
            return rowMapper.mapRow(resultSet, i++);
        }
    }

}
