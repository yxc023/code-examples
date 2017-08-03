/**
 * @author yangxiaochen
 * @date 2017/8/3 15:25
 */
public class AddTwoNumbers_2 {
    public static void main(String[] args) {

        AddTwoNumbers_2 main = new AddTwoNumbers_2();

        main.print(main.addTwoNumbers(main.create(6, 4, 3), main.create(4, 5, 6, 2, 1, 4)));
        main.print(main.addTwoNumbers(main.create(2, 4, 3), main.create(5,6,4)));
        main.print(main.addTwoNumbers(main.create(0), main.create(0)));

    }

    public ListNode create(int... n) {
        if (n.length == 0) return null;

        ListNode head = new ListNode(n[0]);
        ListNode preNode = head;
        for (int i = 1; i < n.length; i++) {
            preNode.next = new ListNode(n[i]);
            preNode = preNode.next;
        }

        return head;
    }

    public void print(ListNode head) {
        ListNode node = head;

        while (node != null) {
            System.out.print(node.val);
            System.out.print(", ");
            node = node.next;
        }
        System.out.println();
    }


    public class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode n1 = l1;
        ListNode n2 = l2;
        ListNode retHead = null;

        ListNode preNode = null;
        int k = 0;

        while (n1 != null || n2 != null) {
            int a = n1 == null ? 0 : n1.val;
            int b = n2 == null ? 0 : n2.val;

            int result = a + b + k;
            k = result / 10;
            result = result % 10;
            ListNode resultNode = new ListNode(0);
            resultNode.val = result;

            if (retHead == null) {
                preNode = retHead = resultNode;
            } else {
                preNode = preNode.next = resultNode;
            }

            n1 = n1 == null ? null : n1.next;
            n2 = n2 == null ? null : n2.next;
        }
        if (k == 1) {
            preNode.next = new ListNode(1);
        }

        return retHead;

    }

    public ListNode addTwoNumbers2(ListNode l1, ListNode l2) {
        if (l1 == null && l2 == null) {
            return null;
        }

        int h1 = l1 == null ? 0 : l1.val;
        int h2 = l2 == null ? 0 : l2.val;

        int ret = h1 + h2;

        int k = 0;
        if (ret >= 10) {
            ret = ret - 10;
            k = 1;
        }
        ListNode retHead = new ListNode(ret);

        ListNode n1 = l1.next;
        ListNode n2 = l2.next;
        ListNode preNode = retHead;


        while (n1 != null && n2 != null) {
            int a = n1.val;
            int b = n2.val;

            int result = a + b + k;
            if (result >= 10) {
                k = 1;
                result = result - 10;
            } else {
                k = 0;
            }
//            k = result / 10;
//            result = result % 10;
            ListNode resultNode = new ListNode(result);

            preNode.next = resultNode;
            preNode = preNode.next;
            n1 = n1.next;
            n2 = n2.next;
        }

        if (n1 == null && n2 == null) {
            if (k == 1) {
                preNode.next = new ListNode(1);
            }
        } else {
            ListNode leftNode = null;
            if (n1 == null) {
                leftNode = n2;
            } else {
                leftNode = n1;
            }
            while (leftNode != null) {
                if (k == 0) {
                    preNode.next = leftNode;
                    break;
                } else {
                    int r = leftNode.val + k;
                    if (r >= 10) {
                        r = r - 10;
                        k = 1;
                    } else {
                        k = 0;
                    }
//                    k = r / 10;
//                    r = r % 10;
                    preNode.next = new ListNode(r);
                    preNode = preNode.next;
                }

                leftNode = leftNode.next;
            }

            if (k == 1) {
                preNode.next = new ListNode(1);
            }


        }


        return retHead;

    }
}
