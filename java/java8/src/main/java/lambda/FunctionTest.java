package lambda;

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


        Function<String, Integer> getlength = String::length;
        Function kkk = getlength.compose(b -> b.toString());


        kkk.apply(FunctionTest.class);
//        FourFunction f = FunctionTest::fff;

//        f = (a,b,c,d) -> {};







    }

    private void test() {

    }

    static void fff(int a, int b, double c, Object d) {

    }

    @FunctionalInterface
    public static interface FourFunction {
        void apply(int a, int b, double c, Object d);
    }

}

