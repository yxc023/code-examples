package lambda;

import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author yangxiaochen
 * @date 2016/12/2 11:16
 */
public class FunctionTest {

    static void modifyTheValue(int value, Function<Integer, Integer> function) {
        System.out.println(function.apply(value));
    }

    public static void main(String[] args) {
        modifyTheValue(33, Integer::intValue);


        Function<String, Integer> getlength = string -> string.length();
        assert getlength.apply("a long string") == 13;

        Consumer<String> println2 = s -> System.out.println(s);

        BiFunction<String, String, String> concatString = (s1, s2) -> s1 + s2;


//        Function<String, Integer> getlength = String::length;
        Consumer<String> println = String::new;



        System.out.println(String.join("", "haha","wuwu"));

        Function kkk = getlength.compose(b -> b.toString());

//        System.out.println((System.out::println).getClass().toString());


//        BiFunction<String,String, Integer> b = (String x, String y) -> x.compareToIgnoreCase(y);


        Function<String, StringBuilder> toBuilder = StringBuilder::new;
        Function<Integer, String[]> stringArr = String[]::new;


        kkk.apply(FunctionTest.class);
        FourFunction f = FunctionTest::fff;

//        f = (a,b,c,d) -> {};


    }

    private void test() {

    }

    static void fff(int a, int b, double c, Object d) {

    }

//    @FunctionalInterface
    public static interface FourFunction {
        void apply(int a, int b, double c, Object d);
    }

}

