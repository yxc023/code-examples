package lambda;

import java.util.Optional;

/**
 * @author yangxiaochen
 * @date 2017/11/22 13:59
 */
public class OptionalTest {
    static class User {
        String name;
        Integer age;
    }
    public static void main(String[] args) {
        User user = new User();
        System.out.println(Optional.ofNullable(user).map(u -> u.name).map(n -> n.toUpperCase()).orElse("no name"));
    }
}
