package me.meet.data;

import java.util.*;

public final class OfferArrayCode1 {
    private OfferArrayCode1() {}
    /**
     * 数据流中的中位数
     *
     * 如何得到一个数据流中的中位数？
     * 如果从数据流中读出奇数个数值，那么中位数就是所有数值排序之后位于中间的数值。
     * 如果从数据流中读出偶数个数值，那么中位数就是所有数值排序之后中间两个数的平均值。
     * 我们使用Insert()方法读取数据流，使用GetMedian()方法获取当前读取数据的中位数。
     *
     */
    /**
     * 思路：
     * 1、使用LinkedList缓存数据流，新加入时有序添加
     * 2、取中位数时，根据size是奇数/偶数，得到中位数
     */
    static class MedianNumber {
        private final LinkedList<Integer> list = new LinkedList<>();

        public void insert(Integer num) {
            if (list.isEmpty() || list.getFirst() >= num) { // 列表为空或者列表第一个元素大于等于当前要插入的元素，插入list头部
                list.addFirst(num);
                return;
            }
            for (Integer i : list) {
                if (i >= num) {
                    int idx = list.indexOf(i);
                    list.add(idx, num);
                    return;
                }
            }
            list.addLast(num);
        }

        public Double getMedianNumber() {
            int size = list.size();
            if (1 > size) {
                return 0d;
            }
            int mid = size/2;
            if (0 == size%2) {
                return (list.get(mid-1) + list.get(mid))/2d;
            }
            return Double.valueOf(list.get(mid));
        }

    }
    private static void testMedianNumber() {
        MedianNumber number = new MedianNumber();
        for (int i = 9; i > 0; i--) {
            number.insert(i);
        }
        System.out.println(number.getMedianNumber());
    }

    /**
     * 数组中重复的数字
     *
     * 题目描述：
     * 在一个长度为n的数组里的所有数字都在0到n-1的范围内。 数组中某些数字是重复的，
     * 但不知道有几个数字是重复的。也不知道每个数字重复几次。请找出数组中任意一个重复的数字。
     * 例如，如果输入长度为7的数组{2,3,1,0,2,5,3}，那么对应的输出是第一个重复的数字2。
     */
    static Set<Integer> findDuplicate(int[] arr) {
        Set<Integer> result = new HashSet<>();
        BitSet bSet = new BitSet(Integer.MAX_VALUE);
        for (int val : arr) {
            if (bSet.get(val)) {
                result.add(val);
            } else {
                bSet.set(val, true);
            }
        }
        return result;
    }

    private static void testFindDuplicate() {
        int[] arr = new int[]{2,3,1,0,2,5,3};
        Set<Integer> res = findDuplicate(arr);
        System.out.println(res);
    }

    /**
     * 求1+2+3+...+n
     *
     * 求1+2+3+...+n，要求不能使用乘除法、for、while、if、else、switch、case等关键字及条件判断语句（A?B:C）。
     * 
     * 思路：
     * 1、利用 && 的短路特性实现递归，n<=0时，不运行后面的语句
     *
     * 思路：
     * 1、使用公式 1+2+...+n = n*(n+1)/2 = (n^2 + n) >> 1
     */
    static int countN(int n) {
        int sum = n;
        boolean flag = (n > 0) &&  (sum += countN(n-1)) > 0;
        return sum;
    }

    static int countN1(int n) {
        return ((int)(Math.pow(n, 2)+n)) >> 1;
    }

    /**
     * 有个游戏是这样的:首先,让小朋友们围成一个大圈。
     * 然后,他随机指定一个数m,让编号为0的小朋友开始报数。
     * 每次喊到m-1的那个小朋友要出列唱首歌,然后可以在礼品箱中任意的挑选礼物,并且不再回到圈中,
     * 从他的下一个小朋友开始,继续0...m-1报数....这样下去....
     * 直到剩下最后一个小朋友,可以不用表演,并且拿到牛客名贵的“名侦探柯南”典藏版
     *
     *
     * 思路2、利用链表，每次移动m-1次，删除元素即可
     */
    static int lastRemaining(int n, int m) {
        if (n < 1 || m < 1) {
            return -1;
        }

        List<Integer> tmp = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            tmp.add(i);
        }

        int idx = -1;
        while (tmp.size() > 1) {
            idx = (idx + m) % tmp.size();
            tmp.remove(idx);
            idx--;
        }

        return tmp.get(0);
    }


    public static void main(String[] args) {
        testMedianNumber();
        testFindDuplicate();
    }
}
