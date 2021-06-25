package me.meet.labuladong._2;

import java.util.LinkedList;

public final class LC1118 {
    /*
     * 读完本文，可以去力扣解决如下题目：
     *
     * 496.下一个更大元素I（Easy）
     * 503.下一个更大元素II（Medium）
     * 1118.一月有多少天（Medium）
     *
     *
     * 单调栈实际上就是栈，只是利用了一些巧妙的逻辑，使得每次新元素入栈后，栈内的元素都保持有序（单调递增或单调递减）。
     *
     *
     * 单调栈模板
     *
     * 首先，看一下 Next Greater Number 的原始问题，
     * 这是力扣第 496 题「下一个更大元素 I」：
     * 给你一个数组，返回一个等长的数组，对应索引存储着下一个更大元素，如果没有更大的元素，就存 -1。
     *
     * 函数签名如下： vector<int> nextGreaterElement(vector<int>& nums);
     * 比如说，输入一个数组nums = [2,1,2,4,3]，你返回数组[4,2,4,-1,-1]。
     *
     * 解释：第一个 2 后面比 2 大的数是 4; 1 后面比 1 大的数是 2；第二个 2 后面比 2 大的数是 4; 4 后面没有比 4 大的数，填 -1；3 后面没有比 3 大的数，填 -1。
     *      这道题的暴力解法很好想到，就是对每个元素后面都进行扫描，找到第一个更大的元素就行了。但是暴力解法的时间复杂度是O(n^2)。
     *
     * 这个问题可以这样抽象思考：把数组的元素想象成并列站立的人，元素大小想象成人的身高。这些人面对你站成一列，如何求元素「2」的 Next Greater Number 呢？
     * 很简单，如果能够看到元素「2」，那么他后面可见的第一个人就是「2」的 Next Greater Number，因为比「2」小的元素身高不够，都被「2」挡住了，第一个露出来的就是答案。
     *                          _ _ _
     *                         |
     *           _ _ _ _ _ _ _ |    |
     *          |     _ _ |    |    |
     *          |    |    |    |    |
     * nums:    2    1    2    4    3
     * res:     4    2    4    -1   -1
     *
     *
     * 这就是单调队列解决问题的模板。
     * for 循环要从后往前扫描元素，因为我们借助的是栈的结构，倒着入栈，其实是正着出栈。
     * while 循环是把两个「个子高」元素之间的元素排除，因为他们的存在没有意义，前面挡着个「更高」的元素，
     * 所以他们不可能被作为后续进来的元素的 Next Great Number 了。
     *
     *
     * 这个算法的时间复杂度不是那么直观，如果你看到 for 循环嵌套 while 循环，可能认为这个算法的复杂度也是O(n^2)，
     * 但是实际上这个算法的复杂度只有O(n)。
     *
     * 分析它的时间复杂度，要从整体来看：总共有n个元素，每个元素都被push入栈了一次，而最多会被pop一次，没有任何冗余操作。
     * 所以总的计算规模是和元素规模n成正比的，也就是O(n)的复杂度。
     *
     *
     */
    static int[] nextGreaterElement(int[] nums) {
        // 存放答案的数组
        int[] rs = new int[nums.length];
        LinkedList<Integer> sk = new LinkedList<>();
        // 倒着往栈里放
        for (int i = nums.length - 1; i >= 0; i--) {
            // 判定个子高矮
            while (!sk.isEmpty() && sk.getLast() <= nums[i]) {
                // 矮个看不到，反正也被挡着了。。。
                sk.removeLast();
            }
            // nums[i] 身后的 next great number
            rs[i] = sk.isEmpty() ? -1 : sk.getLast();
            sk.addLast(nums[i]);
        }
        return rs;
    }

    private static void testNextGreaterElement() {
        int[] nums = new int[]{2, 1, 2, 4, 3};
        int[] rs = nextGreaterElement(nums);
        for (int i : rs) {
            System.out.print(i + " ");
        }
        System.out.println();
    }

    /*
     * 问题变形
     * 单调栈的使用技巧差不多了，来一个简单的变形，力扣第 1118 题「一月有多少天」：
     *
     * 给你一个数组T，这个数组存放的是近几天的天气气温，你返回一个等长的数组，计算：对于每一天，你还要至少等多少天才能等到一个更暖和的气温；如果等不到那一天，填 0。
     *
     * 函数签名如下： vector<int> dailyTemperatures(vector<int>& T);
     * 比如说给你输入T = [73,74,75,71,69,76]，你返回[1,1,3,2,1,0]。
     * 解释：第一天 73 华氏度，第二天 74 华氏度，比 73 大，所以对于第一天，只要等一天就能等到一个更暖和的气温，后面的同理。
     *
     * 这个问题本质上也是找 Next Greater Number，只不过现在不是问你 Next Greater Number 是多少，而是问你当前距离 Next Greater Number 的距离而已。
     *
     * 相同的思路，直接调用单调栈的算法模板，稍作改动就可以，直接上代码吧：
     *
     */
    static int[] dailyTemperatures(int[] t) {
        int[] rs = new int[t.length];
        // 这里放元素索引，而不是元素
        LinkedList<Integer> sk = new LinkedList<>();
        // 单调栈模板
        for (int i = t.length - 1; i >= 0; i--) {
            while (!sk.isEmpty() && t[sk.getLast()] <= t[i]) {
                sk.removeLast();
            }
            // 得到索引间距
            rs[i] = sk.isEmpty() ? 0 : (sk.getLast()-i);
            // 将索引入栈，而不是元素
            sk.addLast(i);
        }
        return rs;
    }

    private static void testDailyTemperatures() {
        int[] t = new int[]{73, 74, 75, 71, 69, 76};
        int[] rs = dailyTemperatures(t);
        for (int i : rs) {
            System.out.print(i + " ");
        }
        System.out.println();
    }

    /*
     * 如何处理环形数组
     * 同样是 Next Greater Number，现在假设给你的数组是个环形的，如何处理？
     * 力扣第 503 题「下一个更大元素 II」就是这个问题：
     *
     * 比如输入一个数组[2,1,2,4,3]，你返回数组[4,2,4,-1,4]。拥有了环形属性，最后一个元素 3 绕了一圈后找到了比自己大的元素 4。
     *
     * 一般是通过 % 运算符求模（余数），来获得环形特效：
     * int[] arr = {1,2,3,4,5};
     * int n = arr.length, index = 0;
     * while (true) {
     *     print(arr[index % n]);
     *     index++;
     * }
     *
     * 这个问题肯定还是要用单调栈的解题模板，但难点在于，比如输入是[2,1,2,4,3]，对于最后一个元素 3，如何找到元素 4 作为 Next Greater Number。
     *
     * 对于这种需求，常用套路就是将数组长度翻倍：
     *                          _ _ _ _ _ _ _ _ _ _ _ _ _
     *                         |     _ _ _ _ _ _ _ _ _ _|
     *           _ _ _ _ _ _ _ |    |                   |    |
     *          |     _ _ |    |    |    |         |    |    |
     *          |    |    |    |    |    |    |    |    |    |
     * nums:    2    1    2    4    3    2    1    2    4    3
     * res:     4    2    4    -1   4    duplicate
     *
     * 这样，元素 3 就可以找到元素 4 作为 Next Greater Number 了，而且其他的元素都可以被正确地计算。
     * 有了思路，最简单的实现方式当然可以把这个双倍长度的数组构造出来，然后套用算法模板。
     * 但是，我们可以不用构造新数组，而是利用循环数组的技巧来模拟数组长度翻倍的效果。
     *
     * 直接看代码吧：
     * 这样，就可以巧妙解决环形数组的问题，时间复杂度O(N)。
     *
     */
    static int[] nextGreaterElementCycle(int[] nums) {
        int n = nums.length;
        int[] rs = new int[n];
        LinkedList<Integer> sk = new LinkedList<>();
        // 假装这个数组长度翻倍了
        for (int i = 2 * n - 1; i >= 0; i--) {
            // 索引要求模，其他的和模板一样
            while (!sk.isEmpty() && sk.getLast() <= nums[i % n]) {
                sk.removeLast();
            }
            rs[i % n] = sk.isEmpty() ? -1 : sk.getLast();
            sk.addLast(nums[i % n]);
        }
        return rs;
    }

    private static void testNextGreaterElementCycle() {
        int[] nums = new int[]{2, 1, 2, 4, 3};
        int[] rs = nextGreaterElementCycle(nums);
        for (int i : rs) {
            System.out.print(i + " ");
        }
        System.out.println();

    }

    public static void main(String[] args) {
        // 496 题「下一个更大元素 I」
        testNextGreaterElement();
        // 1118 题「一月有多少天」
        testDailyTemperatures();
        // 503 题「下一个更大元素 II」 496 题的数组是个环形的，如何处理？
        testNextGreaterElementCycle();
    }
}
