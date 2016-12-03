package lambda;

import javafx.scene.control.Button;

import java.math.BigDecimal;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author yangxiaochen
 * @date 2016/12/1 11:38
 */
public class LambdaTest {

    public static void main(String[] args) {


//        sum((a,b) -> a + b);
        Sum sum = (a,b) -> a + b;

        System.out.println(new BigDecimal(1.00000000).toString());


        Comparator<String> comp = (String first, String second) -> {


            if (first.length() < second.length()) return -1;
            else if (first.length() > second.length()) return 1;
            else return 0;
        };


        Comparator<Integer> ccc = LambdaTest::compara;

        List<String> labels = Arrays.asList("111","2222");
        Stream<Button> stream = labels.stream().map(Button::new);
        Button[] buttons = stream.toArray(Button[]::new);


//        labels.forEach();
        System.out.println(Paths.get("jdk1.8.0", "jre", "bin"));

    }

    static int sum(Sum sum) {
        return 0;
    }

    static int compara(int a, int b) {
        return 0;
    }
}


interface Sum {
    int sum(int a, int b);
}