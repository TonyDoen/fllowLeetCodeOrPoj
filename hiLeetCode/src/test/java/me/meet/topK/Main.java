package me.meet.topK;

public class Main {
    static class LinkNode {
        Integer val;
        LinkNode next;

        LinkNode() {
        }

        LinkNode(Integer val, LinkNode next) {
            this.val = val;
            this.next = next;
        }
    }

    static LinkNode combine(LinkNode l1, LinkNode l2) {
        if (null == l1 || null == l2) {
            return null == l2 ? l1 : l2;
        }
        LinkNode res = new LinkNode();
        LinkNode tmp = res;

        LinkNode t1 = l1, t2 = l2;
        for (; null != t1 && null != t2; ) {
            if (t1.val > t2.val) {
                tmp.next = t2;
                t2 = t2.next;
            } else {
                tmp.next = t1;
                t1 = t1.next;
            }
            tmp = tmp.next;
        }
        if (null != t1) {
            tmp.next = t1;
        }
        if (null != t2) {
            tmp.next = t2;
        }
        return res;
    }

    static void testCombine() {
        LinkNode _7 = new LinkNode(7, null);
        LinkNode _4 = new LinkNode(4, _7);
        LinkNode _1 = new LinkNode(1, _4);

        LinkNode _8 = new LinkNode(8, null);
        LinkNode _5 = new LinkNode(5, _8);
        LinkNode _2 = new LinkNode(2, _5);

        LinkNode res = combine(_1, _2);
        for (; null != res; ) {
            System.out.println(res.val);
            res = res.next;
        }
    }

    static double pow2(int base, int n) {
        if (0 == n) {
            return 1;
        }
        if (1 == n) {
            return base;
        }
        if (n >= 0) {
            if (1 == n % 2) {
                return pow2(base * base, n / 2) * base;
            } else {
                return pow2(base * base, n / 2);
            }
        } else {
            return 1 / pow2(base, -n);
        }
    }

    private static void testPow() {
        double res = pow2(2, -3);
        System.out.println(res);
    }

    static final class Holder1 {
        final Integer val;

        //        Holder1() {}
        Holder1(Integer val) {
            this.val = val;
        }
    }

    static class Holder2 {
        Integer val = 0;
    }

    private static void change(Object obj) {
        if (obj instanceof Holder1) {
            obj = new Holder1(2333);
//            ((Holder1)obj).val = 2333;
        } else if (obj instanceof Holder2) {
            ((Holder2) obj).val = 2333;
        }
    }

    private static void testHolder() {
        Holder1 h1 = new Holder1(1);
        Holder2 h2 = new Holder2();

        change(h1);
        change(h2);
        System.out.println(h1.val);
        System.out.println(h2.val);
    }

    public static void main(String[] args) {
        //Scanner in = new Scanner(System.in);
        //int a = in.nextInt();
        //System.out.println(a);
//        testCombine();
//        testPow();
        testHolder();
        System.out.println("Hello World!");
    }
}