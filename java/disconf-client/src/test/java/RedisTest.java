import com.yangxiaochen.example.disconf.Application;
import com.yangxiaochen.example.disconfig.configs.JedisConfigWithoutInject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author yangxiaochen
 * @date 2017/6/9 16:20
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class RedisTest {
    @Autowired
    JedisConfigWithoutInject jedisConfig;

    @Test
    public void test() {
        System.out.println(jedisConfig.getHost());
        System.out.println(jedisConfig.getPort());
    }
}
