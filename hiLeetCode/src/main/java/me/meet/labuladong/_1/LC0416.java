package me.meet.labuladong._1;

import java.util.Arrays;

public final class LC0416 {
    private LC0416() {
    }

    /**
     * 经典动态规划， 0-1背包问题的变体
     *
     * LeetCode 416 分割等和子集
     *
     * 给定一个只包含正整数的非空数组，是否可以将这个数组分割成2个子集，使得2个子集的元素和想等
     * 注意：
     * 1. 每个数组中的元素不会超过100
     * 2. 数组的大小不会超过200
     *
     * 示例1：
     * input:  [1, 5, 11, 5]
     * output: true
     * explain:数组可以分割成[1, 5, 5] 和 [11]
     *
     * 示例2：
     * input:  [1, 2, 3, 5]
     * output: false
     * explain:数组不能分割成2个元素和想等的子集
     *
     *
     * 这个问题看起来和背包问题没有关系
     * 背包问题描述： 给你一个可装载重量为W的背包和N个物品，每个物品有重量和价值2个属性。其中第i个物品的重量为wt[i]，价值val[i]，现在让你用这个背包装物品，最多能装的价值。
     *
     * 416 分割等和子集 可以先对集合求和,得到sum,把问题转化为背包问题： 给一个可装载重量为sum/2的背包和N个物品，每个物品的重量为nums[i]。现在让你装物品，是否存在一种装法，能够恰好将背包装满。
     *
     *
     * 思路：
     * <1> 第一步明确2点：[状态]; [选择]
     *     状态：就是 [背包的容量] 和 [可选择的物品]
     *     选择：就是 [装进背包] 和 [不装进背包]
     *
     * <2> 第二步明确dp数组的定义
     *     按照背包问题的套路，给出如下定义：
     *     dp[i][j] = x 表示，对于前i个物品，当前背包的容量为j时，若x=true，则说明恰好将背包装满。若x=false，则说明不能恰好将背包装满。
     *
     *     例如，如果dp[4][9]=true，其含义是： 对于容量为9的背包，若只是用钱4个物品，可以有一种方法把背包恰好装满。
     *     或者对于本题目，含义是对于给定的集合中，若只对前4个数字进行选择，存在一个子集的和可以恰好凑出9，
     *     根据这个定义，我们想求的最终答案就是dp[N][sum/2]， base case 就是dp[...][0]=true 和 dp[0][...]=false，因为背包没有空间，就相当于装满。而当没有物品可以选择，肯定没办法装满背包
     *
     * <3> 第三步根据 [选择]，思考状态转移的逻辑
     *     根据dp数组的含义，[选择] 对 dp[i][j] 得到下面状态转移
     *     若不把 nums[i] 算入子集(你把这第i个物品装入背包)，那么是否能够恰好装满背包，取决于上一个状态dp[i-1][j]，继承之前的结果。
     *     若把 nums[i] 算入子集(你把这第i个物品装入背包)，那么是否能够恰好装满背包，取决于状态 dp[i-1][j-nums[i-1]]。
     *     首先，由于i是从1开始，而数组索引是从0开始，所以第i个物品的重量应该是 nums[i-1]， 这一点不要搞混。
     *     dp[i-1][j-nums[i-1]]也很好理解，若你装了第i个物品，就要看背包的剩余重量 j-nums[i-1] 限制下是否能够恰好装满。
     *     换句话，若 j-nums[i-1] 的重量可以恰好装满，那么只要把第i个物品装进去，也可恰好装满j的重量，否则，重量j肯定是装不满的。
     *
     * <4> 把伪代码翻译成代码，处理边界情况。
     *
     */
    static boolean canPartition(int[] nums) {
        int sum = 0;
        for (int n : nums) {
            sum += n;
        }
        // 和是奇数，不可能划分为2个和相等的集合
        if (0 != sum%2) {
            return false;
        }

        int n = nums.length;
        sum = sum/2;
        boolean[][] dp = new boolean[n+1][sum+1];
        for (boolean[] booleans : dp) {
            Arrays.fill(booleans, false);
        }

        // base case
        for (int i = 0; i <= n; i++) {
            dp[i][0] = true;
        }
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= sum; j++) {
                if (j - nums[i-1] < 0) {
                    // 背包容量不足，不能装入第i个物品
                    dp[i][j] = dp[i-1][j];
                } else {
                    // 装入或者不装入 背包
                    dp[i][j] = dp[i-1][j] | dp[i-1][j-nums[i-1]];
                }
            }
        }
        return dp[n][sum];
    }

    /**
     * 状态压缩
     *
     * 进一步，观察上面的代码，可以注意到 dp[i][j] 都是通过上一行 dp[i-1][...] 转移过来的。之前的数据都不会再使用。
     * 所以，我们可以进行状态压缩，将二维dp 数组压缩为一维，减少空间复杂度。
     *
     * 下面的代码和上面的思路完全相同，只是在一行dp数组上操作，i 每一轮迭代，dp[j]其实相当于dp[i-1][j]，所以只要一维数组就够用。
     * 唯一需要注意的是j 应该从后往前反向遍历，因为每个物品(或者数字)只能用一次，以免之前的结果影响其他的结果
     *
     * 子集切割问题就解决了。时间复杂度O(n*sum), 空间复杂度O(sum)
     */
    static boolean canPartition2(int[] nums) {
        int sum = 0, n = nums.length;
        for (int i : nums) {
            sum += i;
        }
        if (0 != sum%2) {
            return false;
        }
        sum = sum/2;
        boolean[] dp = new boolean[sum+1];
        Arrays.fill(dp, false);

        // base case
        dp[0] = true;

        for (int num : nums) {
            for (int j = sum; j >= 0; j--) {
                if (j - num >= 0) {
                    dp[j] = dp[j] || dp[j - num];
                }
            }
        }
        return dp[sum];
    }

    private static void testCanPartition() {
        int[] arr = new int[]{1, 5, 11, 5};
        boolean res = canPartition(arr);
        System.out.println(res);

        arr = new int[]{1, 5, 11, 5};
        res = canPartition2(arr);
        System.out.println(res);

        arr = new int[]{1, 2, 3, 5};
        res = canPartition(arr);
        System.out.println(res);

        arr = new int[]{1, 2, 3, 5};
        res = canPartition2(arr);
        System.out.println(res);
    }

    public static void main(String[] args) {
        testCanPartition();
    }
}
