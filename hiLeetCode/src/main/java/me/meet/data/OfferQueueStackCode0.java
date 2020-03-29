package me.meet.data;

import java.util.LinkedList;
import java.util.List;

/**
 * url: https://blog.csdn.net/zangdaiyang1991/article/details/91904596
 */
public final class OfferQueueStackCode0 {
    /**
     * 滑动窗口的最大值
     *
     * 给定一个数组和滑动窗口的大小，找出所有滑动窗口里数值的最大值。
     * 例如，如果输入数组{2,3,4,2,6,2,5,1}及滑动窗口的大小3，
     * 那么一共存在6个滑动窗口，他们的最大值分别为{4,4,6,6,6,5}；
     * 针对数组{2,3,4,2,6,2,5,1}的滑动窗口有以下6个：
     * {[2,3,4],2,6,2,5,1}，
     * {2,[3,4,2],6,2,5,1}，
     * {2,3,[4,2,6],2,5,1}，
     * {2,3,4,[2,6,2],5,1}，
     * {2,3,4,2,[6,2,5],1}，
     * {2,3,4,2,6,[2,5,1]}。
     */
    /**
     * 思路： 
     * 1、遍历数组，每次取滑动窗口的所有值，遍历窗口中的元素，取出最大值
     */
    static List<Integer> maxInWindow(int[] arr, int size) {
        List<Integer> res = new LinkedList<>();
        if (null == arr || size < 1) {
            return res;
        }

        // find max
        if (arr.length <= size) {
            int max = Integer.MIN_VALUE;
            for (int i : arr) {
                max = Math.max(max, i);
            }
            res.add(max);
            return res;
        }

        int maxIdx = arr.length - size;
        for (int i = 0; i <= maxIdx; i++) {
            int curMax = arr[i];
            for (int j = i + 1; j < i + size; j++) {
                curMax = curMax > arr[j] ? curMax : arr[j];
            }
            res.add(curMax);
        }
        return res;
    }

    private static void testMaxInWindow() {
        int[] arr = new int[]{2, 3, 4, 2, 6, 2, 5, 1};
        int size = 3;
        List<Integer> res = maxInWindow(arr, size);
        System.out.println(res);
    }

    /**
     * 思路：
     * 1、双端队列保存滑动窗口的最大值(保存在头部)，次大值数据
     * 2、窗口滑动，从右侧遍历，比当前值小的移出队列，队首元素过期 移出队列，当前元素的索引加入队列
     */
    static List<Integer> maxInWindow2(int[] arr, int size) {
        List<Integer> res = new LinkedList<>();
        if (null == arr || size < 1) {
            return res;
        }
        LinkedList<Integer> queue = new LinkedList<>();                     // 使用双端队列 缓存滑动窗口，最大值保存在头部
        for (int i = 0; i < arr.length; i++) {
            for (; !queue.isEmpty() && arr[queue.peekLast()] <= arr[i]; ) { // 从后面依次弹出队列中比当前num值小的元素，同时也能保证队列首元素为当前窗口最大值下标
                queue.pollLast();
            }
            if (!queue.isEmpty() && i - queue.peekFirst() + 1 > size) {     // 当队首元素坐标对应的num不在窗口中，需要弹出
                queue.pollFirst();
            }
            queue.offerLast(i);                                             // 把每次滑动的num下标加入队列
            if (i + 1 >= size) {                                            // 当滑动窗口首地址i大于等于size时才开始写入窗口最大值
                res.add(arr[queue.peekFirst()]);
            }
        }
        return res;
    }

    private static void testMaxInWindow2() {
        int[] arr = new int[]{2, 3, 4, 2, 6, 2, 5, 1};
        int size = 3;
        List<Integer> res = maxInWindow2(arr, size);
        System.out.println(res);
    }


    public static void main(String[] args) {
        testMaxInWindow();
        testMaxInWindow2();
    }
}
