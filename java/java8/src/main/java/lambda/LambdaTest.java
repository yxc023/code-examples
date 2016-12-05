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


        class Worker implements Runnable {
            @Override
            public void run() {
                for (int i = 0; i < 1000; i++) {
                    doWork();
                }
            }
        }

        Worker worker = new Worker();
        new Thread(worker).start();

        new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                doWork();
            }
        }).start();

        new Thread(() -> System.out.println("do work"));
//        (String first, String second) -> {
//            if (first.length() < second.length()) return -1;
//            else if (first.length() > second.length()) return 1;
//            else return 0;
//        };

        Runnable work = () -> System.out.println("do work");

        String[] strings = new String[10];

        class LengthComparator implements Comparator<String> {
            public int compare(String first, String second) {
                return Integer.compare(first.length(), second.length());
            }
        }

        Arrays.sort(strings, new LengthComparator());

        Arrays.sort(strings, Comparator.comparingInt(String::length));

        Button button = new Button();
        button.setOnAction(event -> System.out.println("Thanks for clicking!"));


        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 1000; i++) {
                    doWork();
                }
            }
        }).start();


//        sum((a,b) -> a + b);
        Sum sum = (a, b) -> a + b;

        System.out.println(new BigDecimal(1.00000000).toString());


        Comparator<String> comp = (String first, String second) -> {


            if (first.length() < second.length()) return -1;
            else if (first.length() > second.length()) return 1;
            else return 0;
        };


        Comparator<Integer> ccc = LambdaTest::compara;

        List<String> labels = Arrays.asList("111", "2222");
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


    public static void doWork() {

    }
}


interface Sum {
    int sum(int a, int b);
}