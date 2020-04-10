package me.meet.leetcode.medium;

import java.util.*;

public final class Subsets {
    /**
     * url: https://www.cnblogs.com/grandyang/p/4309345.html
     * Given a set of distinct integers, S, return all possible subsets.
     *
     * Note:
     * Elements in a subset must be in non-descending order.
     * The solution set must not contain duplicate subsets.
     *
     * For example,
     * If S = [1,2,3], a solution is:
     * [
     *   [3],
     *   [1],
     *   [2],
     *   [1,2,3],
     *   [1,3],
     *   [2,3],
     *   [1,2],
     *   []
     * ]
     *
     *
     * 题意：这道求子集合的问题,由于其要列出所有结果,
     * 思路：按照以往的经验,肯定要是要用递归来做。
     * 这道题其实它的非递归解法相对来说更简单一点,下面我们先来看非递归的解法。
     *
     * 我们可以一位一位的往上叠加,比如对于题目中给的例子 [1,2,3] 来说,
     * 1. 最开始是空集,                                         []
     * 2. 那么我们现在要处理1,就在空集上加1,为 [1],现在我们有两个      [],[1],
     * 3. 下面我们来处理2,我们在之前的子集基础上,每个都加个2,可以得到    [], [1], [2], [1, 2],
     * 4. 同理处理3的情况可得                                     [], [1], [2], [1, 2], [3], [1, 3], [2, 3], [1, 2, 3],
     */
    static List<List<Integer>> getSubsets(List<Integer> s) {
        List<List<Integer>> res = new LinkedList<>();
        res.add(new LinkedList<>()); // 初始化空集合

        s.sort(Comparator.comparingInt(i -> i));
        for (Integer i : s) {
            ListIterator<List<Integer>> it = res.listIterator();
            for (; it.hasNext(); ) {
                LinkedList<Integer> tmp = new LinkedList<>(it.next());
                tmp.add(i);
                it.add(tmp);
            }
        }
        return res;
    }

    private static void testGetSubsets() {
        // prepare data
        List<Integer> s = new LinkedList<>();
        int n = 2;
        for (int i = 0; i < n; i++) {
            s.add(i);
        }

        List<List<Integer>> res = getSubsets(s);
        System.out.println(res);
    }

    /**
     * 思路：递归的解法,相当于一种深度优先搜索,
     * 由于原集合每一个数字只有两种状态,要么存在,要么不存在,那么在构造子集时就有选择和不选择两种情况,
     * 所以可以构造一棵二叉树,
     * 1. 左子树表示选择该层处理的节点,
     * 2. 右子树表示不选择,最终的叶节点就是所有子集合
     *
     * 0                        []
     *                    /          \
     *                   /             \
     *                  /                \
     * 1              [1]                 []
     *            /       \            /     \
     *           /         \          /       \
     * 2       [1 2]       [1]       [2]       []
     *       /     \     /   \     /   \      / \
     * 3  [1 2 3] [1 2] [1 3] [1] [2 3] [2] [3] []
     *
     *
     */
//    static List<List<Integer>> getSubsets2(List<Integer> s) {
//        List<List<Integer>> res = new LinkedList<>();
//        LinkedList<Integer> out = new LinkedList<>();
//        helper(res, out, s, 0);
//        return res;
//    }
//
//    private static void helper(List<List<Integer>> res, LinkedList<Integer> out, List<Integer> s, int cur) {
//        res.add(0, out);
//        for (int i = cur; i < s.size(); i++) {
//            out.push(s.get(i));
//            helper(res, out, s, i+1);
//            out.pop();
//        }
//    }
//
//    private static void testGetSubsets2() {
//        // prepare data
//        List<Integer> s = new LinkedList<>();
//        int n = 2;
//        for (int i = 0; i < n; i++) {
//            s.add(i);
//        }
//
//        List<List<Integer>> res = getSubsets2(s);
//        System.out.println(res);
//    }

    public static void main(String[] args) {
        testGetSubsets();
//        testGetSubsets2();
    }
}
