package me.meet.leetcode.easy;

public final class JumpGame2 {
    /**
     * Jump Game II
     * Given an array of non-negative integers, you are initially positioned at the first index of the array.
     * Each element in the array represents your maximum jump length at that position.
     * Your goal is to reach the last index in the minimum number of jumps.
     *
     * For example:
     * Given array A = [2,3,1,1,4]
     * The minimum number of jumps to reach the last index is 2. (Jump 1 step from index 0 to 1, then 3 steps to the last index.)
     *
     * Note:
     * You can assume that you can always reach the last index.
     */
    /**
     * 非负数组的最少跳跃步数
     *
     * 思路:
     * 第一个节点必须要进入，每次进入一个起点i之后，下一步的落点可以是[i+1, i+nums[i]]，找到这个范围内的可以跳跃最远的点作为下一步。循环直到跳跃到最后。
     */
    static int jump(int[] arr) {
        if (null == arr || arr.length < 1) {
            return -1;
        }
        // cnt:跳跃次数; reach:当前所能到达的最远坐标; last:上一跳可达最远坐标
        int length = arr.length, cnt = 0, reach = 0, last = 0;
        for (int i = 0; i < length; i++) {
            if (i > reach) {
                return -1;
            }
            if (i > last) {
                cnt++;
                last = reach;
            }
            int canReach = i + arr[i];
            if (canReach > reach) {
                reach = canReach;
            }
        }
        return cnt;
    }

    private static void testJump() {
        int[] arr = {2,3,1,1,4,0,0,0,0,0};
        int res = jump(arr);
        System.out.println(res);
    }

    public static void main(String[] args) {
        testJump();
    }
}
