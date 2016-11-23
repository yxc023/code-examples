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

    public static void main(String[] args) throws Exception {
        while (true) {
            try {
                Be b = new Be();
                b.a = "1";
                b.b = 1;

                try {
                    throw new RuntimeException();
                } catch (Throwable e) {
                    b.a = "2";
                    b.b = 2;
                    throw new Exception(e);
                } finally {
                    if (b.b != 2) {
                        System.out.println(b.a + " " + b.b);
                    }
                }
            } catch (Exception e) {

            }
        }
    }
}
