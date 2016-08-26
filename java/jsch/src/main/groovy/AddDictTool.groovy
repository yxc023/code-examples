import com.jcraft.jsch.Session
import com.pastdev.jsch.DefaultSessionFactory
import com.yangxiaochen.examples.jsch.config.SshTunnelDbConfig
import groovy.sql.Sql

/**
 * @author yangxiaochen
 * @date 16/8/24 下午4:46
 */
class AddDictTool {

    public static void main(String[] args) {
        //        SshTunnelDbConfig config = SshTunnelDbConfig.createOnlineConfig();
        SshTunnelDbConfig config = SshTunnelDbConfig.createDevConfig();
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
            def url = "jdbc:mysql://localhost:10240/FN_TE?useUnicode=true&characterEncoding=utf-8&allowMultiQueries=true&zeroDateTimeBehavior=convertToNull"
            def user = dbuser
            def password = dbpasswd
            def driver = "com.mysql.jdbc.Driver"

            def dicTypeBean = [data_key: 174, data_value: "证件类型", type: 174, app_id: 20, create_userid: 0]
            def valueRaw = """澳门居民身份证   11
                        法人身份证   2
                        港澳居民来往内地通行证 13
                        港澳台通行证  4
                        港澳通行证   24
                        工作居住证或暂住证   9
                        公司营业执照  8
                        公司组织机构代码证    5
                        护照   6
                        警官退休证   17
                        军(警)身份证 15
                        军/警官证   7
                        军官退休证   16
                        港澳居民来往内地通行证 13
                        旅行证 14
                        其他  100
                        商业登记证   21
                        身份证 1
                        士兵证 20
                        台湾居民来往大陆通行证 12
                        文职干部退休证     19
                        文职干部证   18
                        无   23
                        香港居民身份证 10
                        注册证 22
                        统一社会信用代码证   30
                        往来台湾通行证   31
                        税务登记证   32
                        """

            def dictValueBeans = []
            valueRaw.eachLine({ line ->
                dictValueBeans << line.split(" +")
            })
            dictValueBeans.sort({ b1, b2 ->
                return b1[1].toInteger() - b2[1].toInteger()
            })
            def sql = Sql.newInstance(url, user, password, driver)

            sql.withTransaction {

                sql.execute("INSERT INTO \
                        fn_config.t_dictionary_type\
                        (data_key, data_value, type, app_id, create_userid, create_time) \
                        VALUES (?, ?, ?, ?, ?, now())",
                        dicTypeBean["data_key"], dicTypeBean["data_value"], dicTypeBean["type"], dicTypeBean["app_id"], dicTypeBean["create_userid"])
                dictValueBeans.each {
                    sql.execute("INSERT INTO fn_config.t_dictionary_dict(dict_type, dict_name, dict_code, dict_display_order, create_userid, create_time) VALUES (?, ?, ?, ?, ?, now())",
                            dicTypeBean["data_key"], it[0], it[1], 0, 0)
                }
            }
            sql.close()
        } finally {
            session.disconnect();
        }
    }
}
