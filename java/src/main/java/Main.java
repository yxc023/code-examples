import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yangxiaochen
 * @date 2016/11/23 12:51
 */
public class Main {

    static class Be {
        private String a;
        private int b;

        public String getA() {
            return a;
        }

        public void setA(String a) {
            this.a = a;
        }

        public int getB() {
            return b;
        }

        public void setB(int b) {
            this.b = b;
        }
    }


    static class TException extends RuntimeException {
        public TException() {
        }

        public TException(String message) {
            super(message);
        }

        public TException(String message, Throwable cause) {
            super(message, cause);
        }

        public TException(Throwable cause) {
            super(cause);
        }

        public TException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
            super(message, cause, enableSuppression, writableStackTrace);
        }
    }

    public static void main(String[] args) {
        int d = LocalDateTime.now().getDayOfYear() - LocalDateTime.parse("2016-03-14 00:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")).getDayOfYear() - 1;
        System.out.println(d/365.0 * 1500);
//        try {
//            throw new TException();
////        } catch (TException e) {
////            throw e;
//        } catch (RuntimeException e) {
//            throw new TException(e);
//        }



        List<Integer> list = new ArrayList();
        list.add(0);
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(3);

        int size = list.size();
        for (int i = 0; i < size; i++) {
            System.out.println(list.get(i));
            if (list.get(i) == 3) {
                list.remove(i);
                i--;
                size--;
            }
        }


        System.out.println();
        for (Integer integer : list) {
            System.out.println(integer);
        }
    }
}
