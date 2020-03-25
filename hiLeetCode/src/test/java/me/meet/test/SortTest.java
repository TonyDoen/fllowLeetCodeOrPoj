package me.meet.test;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class SortTest {

    class Kstr {
        Double k;
        String v;

    }

    static void sort(List<String> list) {
        list.sort(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                if (null == o1 || null == o2) {
                    return 0;
                }
                String[] p1 = o1.split(",");
                String[] p2 = o2.split(",");
                Double score1 = Double.parseDouble(p1[2]);
                Double score2 = Double.parseDouble(p2[2]);
                return score1 > score2 ? -1 : 1;
            }
        });
    }

    static List<String> paginate(int resultsPerPage, List<String> results) {
        if (resultsPerPage < 0 || null == results || results.isEmpty()) {
            return results;
        }
        Map<String, List<String>> map = new HashMap<>();
        LinkedList<String> remain = new LinkedList<>();
        for (String str : results) {
            if (null == str) {
                continue;
            }
            String[] piece = str.split(",");
            if (piece.length < 4) {
                continue;
            }
            remain.add(str);
            String hostId = piece[0];

            List<String> list = map.computeIfAbsent(hostId, k -> new LinkedList<>());
            list.add(str);
            sort(list);
        }


        List<String> res = new ArrayList<>();
//        while (!results.isEmpty()) {
        int i = 0;
        for (Map.Entry<String, List<String>> entry : map.entrySet()) {
            List<String> value = entry.getValue();
            if (null == value || value.isEmpty()) {
                continue;
            }
            i++;
            String _0 = value.get(0);
            value.remove(_0);
            remain.remove(_0);
            res.add(_0);

            if (i >= resultsPerPage) {
                res.add("");
                i = 0;
                break;
            } else {

                Iterator<String> it = remain.iterator();
                while (it.hasNext()) {
                    i++;
                    String bak = it.next();
                    value.remove(bak);
                    it.remove();
                    res.add(bak);
                    if (i >= resultsPerPage || remain.isEmpty()) {
                        res.add("");
                        i = 0;
                        break;
                    }
                }

            }
//            }
        }

        return res;
    }

    private static void check() {
        String[] str = new String[]{
                "1,28,300.6,San Francisco",
                "4,5,209.1,San Francisco",
                "20,7,203.4,Oakland",
                "6,8,202.9,San Francisco",
                "6,10,199.8,San Francisco",
                "1,16,190.5,San Francisco",
                "6,29,185.3,San Francisco",
                "7,20,180.0,Oakland",
                "6,21,162.2,San Francisco",
                "2,18,161.7,San Jose",
                "2,30,149.8,San Jose",
                "3,76,146.7,San Francisco",
                "2,14,141.8,San Jose"};

        int resultsPerPage = 5;
        List<String> results = Arrays.asList(str);

        List<String> list = paginate(resultsPerPage, results);

        System.out.println(list);
    }

    public static void checkMonthFirstDayIsSunday() {
        /**
         * 1901-2019
         * 月第一天 是星期日
         */
        Date start = new Date(1901, 1, 1);
        Date end = new Date(2020, 1, 1);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Calendar st = Calendar.getInstance();
        Calendar ed = Calendar.getInstance();
        st.set(1901, 1, 1);
        ed.set(2020, 1, 1);
        int i = 0;
        for (; st.before(ed); ) {
            int date = st.get(Calendar.DATE);
            int w = st.get(Calendar.DAY_OF_WEEK) - 1; // 指示一个星期中的某天。

            if (0 == w && 1 == date) {
                i++;
                Date now = st.getTime();
                System.out.println(dateFormat.format(now) + "; " + st.getTime());
            }
            st.add(Calendar.DAY_OF_YEAR, 1);
        }
        System.out.println(i);
    }

    public static void wave() {
        for (; true; ) {
            for (int i = 0; i < 9600000; i++) {

            }
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static void testStop() throws InterruptedException {
        final int[] array = new int[80000];
        Random random = new Random();
        for (int i = 0; i < array.length; i++) {
            array[i] = random.nextInt(i + 1);
        }

        final Thread t = new Thread(() -> {
            try {
                // sort
                for (int i = 0; i < array.length - 1; i++) {
                    for (int j = 0; j < array.length - i - 1; j++) {
                        if (array[j] < array[j + 1]) {
                            int temp = array[j];
                            array[j] = array[j + 1];
                            array[j + 1] = temp;
                        }
                    }
                }
                // print
                for (int i : array) {
                    System.out.println(i);
                }
            } catch (Error err) {
                err.printStackTrace();
            }
            System.out.println("in thread t");
        });

        t.start();
        TimeUnit.SECONDS.sleep(1);

        System.out.println("go to stop thread t");
        t.stop();
        System.out.println("finish main");
    }

    /**
     * 中断的使用场景有以下几个：
     * <p>
     * 1. 点击某个桌面应用中的取消按钮时；
     * 2. 某个操作超过了一定的执行时间限制需要中止时；
     * 3. 多个线程做相同的事情，只要一个线程成功其它线程都可以取消时；
     * 4. 一组线程中的一个或多个出现错误导致整组都无法继续时；
     * 5. 当一个应用或服务需要停止时。
     */
    static void testInterrupt() {
        final Thread fileIteratorThread = new Thread() {
            public void run() {
                try {

                    String path = "/Users/tonto/java/github.com";

                    final File f = new File(path);
                    String[] ss = f.list();
                    if (ss == null) return;
                    List<File> fList = new ArrayList<>();
                    for (String s : ss) {
                        fList.add(new File(f, s));
                    }
                    ss = null; // help gc
                    AtomicInteger cnt = new AtomicInteger();
                    AtomicLong size = new AtomicLong();

                    for (; !fList.isEmpty(); ) {

                        ListIterator<File> it = fList.listIterator();
                        for (; it.hasNext(); ) {
                            File fe = it.next();
                            it.remove();

                            if (fe.isFile()) {
                                System.out.println(fe + " is a file.");
                                cnt.getAndAdd(1);
                                size.getAndAdd(fe.getTotalSpace());
//                                return;
                            } else {
//                                System.out.println(fe + " is a dir.");
                                String[] tmpS = fe.list();
                                if (null == tmpS) {
                                    continue;
                                }
                                for (String s : tmpS) {
                                    it.add(new File(fe, s));
                                }
                            }

                            // interrupted
                            if (Thread.interrupted()) {
                                System.out.println("has count file: " + cnt.get() + "; file's size: " + size.get());
                                throw new InterruptedException("file scan has been interrupt");
                            }
                        }
                    }
                    System.out.println("total file count: " + cnt.get() + "; file's size: " + size.get());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        final Thread cmdThread = new Thread(() -> {
            while (true) {
//                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
//                String cmd;
//                try {
//                    cmd = reader.readLine();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    return ;
//                }
//
//
//                if("quit".equalsIgnoreCase(cmd.trim())) {
//
//
//
//                } else {
//                    System.out.println("输入 quit 退出文件扫描");
//                }
                try {
                    Thread.sleep(10 * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("10 sec start");
                if (fileIteratorThread.isAlive()) {
                    fileIteratorThread.interrupt();
                    return;
                }

            }
        });

//        cmdThread.start();
        fileIteratorThread.start();
    }

//
//    public static void main(String[] args) throws Exception {
////        checkMonthFirstDayIsSunday();
////        check();
////
////        System.out.println(0^1);
////        System.out.println(1^1);
////        System.out.println(2^1);
////        System.out.println(3^1);
////        wave();
//
////        testStop();
////        testInterrupt();
//
//        String s = longestPalindrome("dabbae");
//        System.out.println(s);
//    }

    public static void main(String[] args) {
//        Scanner sc = new Scanner(System.in);
//        String s = sc.next();
//        System.out.println(longestPalindrome(s));

//        testQuickSort();
//        testHeapSort();
//        testMergeSort();
    }

    static String longestPalindrome(String s) {
        int len = s.length();
        boolean[][] dp = new boolean[len][len];
        String result = s.substring(0, 1);
        for (int i = 0; i < len; i++) {
            dp[i][i] = true;
        }
        for (int i = 0; i < len - 1; i++) {
            dp[i][i + 1] = s.charAt(i) == s.charAt(i + 1);
            if (dp[i][i + 1]) {
                result = s.substring(i, i + 1 + 1);
            }
        }

        for (int k = 3; k <= len; k++) {
            for (int i = 0; (i + k) <= len; i++) {
                int j = i + k - 1;
                dp[i][j] = dp[i + 1][j - 1] && s.charAt(i) == s.charAt(j);
                if (dp[i][j] && (j - i + 1) > result.length()) {
                    result = s.substring(i, j + 1);
                }
            }
        }
        return result;
    }
}
