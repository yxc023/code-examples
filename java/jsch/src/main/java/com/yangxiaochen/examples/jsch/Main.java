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
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.ColumnMapRowMapper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @author yangxiaochen
 * @date 16/6/28 上午11:21
 */
public class Main {

    private static Logger log = LogManager.getLogger(Main.class);

    public static void main(String[] args) throws Exception {

        ClassPathResource resource = new ClassPathResource("insert.sql");
        BufferedReader reader = new BufferedReader(new FileReader(resource.getFile()));

        StringBuilder sb = new StringBuilder();
        String line = reader.readLine();
        while (line != null) {
            sb.append(line).append("\n");
            line = reader.readLine();
        }
        String insertSql = sb.toString();

        log.info(insertSql);



        SshTunnelDbConfig config = SshTunnelDbConfig.createOnlineConfig();
//        SshTunnelDbConfig config = SshTunnelDbConfig.createDevConfig();
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

                DbQuery q = new DbQuery(dbSession, "SELECT * FROM fn_te.business WHERE business_code LIKE '%JR0010011606270059-0001%'");
                Map<String, Object> business = q.find(new MapRowMapper());

                long business_id = BeanUtil.pojo.getProperty(business, "business_id");
                long contract_id = BeanUtil.pojo.getProperty(business, "entity_id");
                log.info("business_id: {}", business_id);

                q = new DbQuery(dbSession, "SELECT * FROM fn_te.business_variable WHERE business_id = :business_id ");
                q.setLong("business_id", business_id);
                List<Map<String, Object>> variables = q.list(new MapRowMapper());

                String jyno = null;
                for (Map<String, Object> variable : variables) {
                    if ("BUSINESS_JIAOYI_CODE".equals(variable.get("name"))) {
                        jyno = BeanUtil.pojo.getProperty(variable, "value");
                    }
                }
                if (jyno == null) {
                    return;
                }

                q = new DbQuery(dbSession, "SELECT * FROM fn_te.business WHERE business_code LIKE :jy_no");
                q.setString("jy_no", "%" + jyno + "%");
                business = q.find(new MapRowMapper());
                long jy_id = BeanUtil.pojo.getProperty(business, "business_id");
                log.info("jy_id: {}", jy_id);


                q = new DbQuery(dbSession, "SELECT * FROM fn_te.person_info WHERE business_id = :business_id");
                q.setLong("business_id", jy_id);
                List<Map<String, Object>> persons = q.list(new MapRowMapper());
                log.info(persons);

                q = new DbQuery(dbSession, "SELECT * FROM fn_jr.fn_person_info WHERE business_id = :business_id");
                q.setLong("business_id", business_id);
                List<Map<String, Object>> fnPersons = q.list(new MapRowMapper());
                log.info(fnPersons);


                // do add person
                for (Map<String, Object> person : persons) {
                    q = new DbQuery(dbSession, insertSql);
                    person.put("create_userid", 0L);
                    person.put("business_id", business_id);
                    person.put("contract_id", contract_id);
                    q.setMap(person);
                    log.info(q.executeUpdate());

                    q = new DbQuery(dbSession, "select last_insert_id()");
                    Long infoId = q.find(new SingleLongRowMapper());
                    log.info("info_id: {}", infoId);
                    person.put("info_id", infoId);
                }

                // do update po_id
                for (Map<String, Object> person1 : persons) {
                    long info_id1 = BeanUtil.pojo.getProperty(person1, "info_id");
                    int type1 = BeanUtil.pojo.getProperty(person1, "type");
                    if (type1 == 5) {
                        persons.stream().filter(pserson2 -> {
                            int type2 = BeanUtil.pojo.getProperty(pserson2, "type");
                            return type2 == 7;
                        }).findFirst().ifPresent(pserson2 -> {
                            long info_id2 = BeanUtil.pojo.getProperty(pserson2, "info_id");
                            updatePoId(dbSession, info_id1, info_id2);
                        });

                    } else if (type1 == 6) {
                        persons.stream().filter(pserson2 -> {
                            int type2 = BeanUtil.pojo.getProperty(pserson2, "type");
                            return type2 == 8;
                        }).findFirst().ifPresent(pserson2 -> {
                            long info_id2 = BeanUtil.pojo.getProperty(pserson2, "info_id");
                            updatePoId(dbSession, info_id1, info_id2);
                        });
                    }
                }

                // 删除老数据
                for (Map<String, Object> fnPerson : fnPersons) {
                    long info_id = BeanUtil.pojo.getProperty(fnPerson, "info_id");
                    long b_id = BeanUtil.pojo.getProperty(fnPerson, "business_id");
                    DbQuery query = new DbQuery(dbSession, "update fn_jr.fn_person_info set business_id=:business_id where info_id = :info_id");
                    query.setLong("business_id",-b_id);
                    query.setLong("info_id", info_id);
                    query.executeUpdate();
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

    private static void updatePoId(DbSession dbSession, long info_id1, long info_id2) {
        DbQuery query = new DbQuery(dbSession, "update fn_jr.fn_person_info set po_id=:po_id where info_id = :info_id");
        query.setLong("po_id", info_id1);
        query.setLong("info_id", info_id2);
        query.executeUpdate();

        query = new DbQuery(dbSession, "update fn_jr.fn_person_info set po_id=:po_id where info_id = :info_id");
        query.setLong("info_id", info_id1);
        query.setObject("po_id", info_id2);
        query.executeUpdate();
    }

    public static class SingleLongRowMapper implements QueryMapper<Long> {
        //        private SingleColumnRowMapper<Long> singleColumnRowMapper = new SingleColumnRowMapper<>();
        @Override
        public Long process(ResultSet resultSet) throws SQLException {
            return resultSet.getLong(1);
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

