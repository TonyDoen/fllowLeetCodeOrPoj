package me.meet.labuladong._0;

public final class _00000 {
    private _00000() {
    }

    /**
     * 此算法有什么缺陷?
     * 比如说给你有序数组 nums = [1,2,2,2,3] , target 为 2,
     *
     * 希望返回的左侧边界,即索引 1,
     * 或者希望右侧边界,即索引 3,
     * 这样的话此算法是无法处理的。
     */
    static int binarySearch0(int[] nums, int target) {
        int left = 0, right = nums.length - 1; // 注意

        while (left <= right) { // 注意
            int mid = left + (right - left) / 2;
            if (target == nums[mid]) {
                return mid;
            } else if (target > nums[mid]) {
                left = mid + 1; // 注意
            } else if (target < nums[mid]) {
                right = mid - 1; // 注意
            }
        }
        return -1;
    }

    /**
     *
     * 寻找左侧边界的二分搜索
     *
     * 为什么该算法能够搜索左侧边界?
     * 关键在于对于 nums[mid] == target 这种情况的处理
     * if (target == nums[mid]) {
     *     right = mid;
     * }
     * 找到 target 时不要立即返回,而是缩小「搜索区间」的上界 right. 在区间 [left, mid) 中继续搜索,即不断向左收缩,达到锁定左侧边界的目的
     *
     */
    static int leftBinarySearch0(int[] nums, int target) {
        if (0 == nums.length) {
            return -1;
        }

        int left = 0, right = nums.length; // 注意
        while (left < right) { // 注意
            int mid = left + (right - left) / 2;
            if (target == nums[mid]) {
                right = mid;
            } else if (target > nums[mid]) {
                left = mid + 1;
            } else if (target < nums[mid]) {
                right = mid; // 注意
            }
        }

        if (left >= nums.length || target != nums[left]) {
            return -1;
        }
        return left;
    }

    /**
     * 寻找右侧边界的二分查找
     *
     * 为什么这个算法能够找到右侧边界?
     * if (target == nums[mid]) {
     *     left = mid + 1;
     * }
     * 当 nums[mid] == target 时,不要立即返回,而是增大「搜索区间」的下界 left, 使得区间不断向右收缩,达到锁定右侧边界的目的
     */
    static int rightBinarySearch0(int[] nums, int target) {
        if (0 == nums.length) {
            return -1;
        }

        int left = 0, right = nums.length; // 注意
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (target == nums[mid]) {
                left = mid + 1; // 注意
            } else if (target > nums[mid]) {
                left = mid + 1;
            } else if (target < nums[mid]) {
                right = mid;
            }
        }


        if (left >= nums.length || target != nums[left]) {
            return -1;
        }
        return left - 1; // 注意
    }

    private static void testBinarySearch0() {
        int[] nums = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        int target = 6;
        int idx = binarySearch0(nums, target);
        System.out.println(idx);
    }

    private static void testLeftBinarySearch0() {
        int[] nums = new int[]{0, 1, 2, 3, 4, 5, 6, 6, 6, 6, 6, 6, 6, 7, 8, 9};
        int target = 16;
        int idx = leftBinarySearch0(nums, target);
        System.out.println(idx);
    }

    private static void testRightBinarySearch0() {
        int[] nums = new int[]{0, 1, 2, 3, 4, 5, 6, 6, 6, 6, 6, 6, 6, 7, 8, 9};
        int target = 16;
        int idx = rightBinarySearch0(nums, target);
        System.out.println(idx);
    }

    /**
     * 统一模板二分查找法
     */
    static int normalBinarySearch1(int[] nums, int target) {
        int left = 0, right = nums.length-1;
        while (left <= right) {
            int mid = left + (right-left)/2;
            if (target > nums[mid]) {
                left = mid + 1;
            } else if (target < nums[mid]) {
                right = mid - 1;
            } else if (target == nums[mid]) {
                return mid; // 直接返回
            }
        }
        return -1; // 直接返回
    }

    static int leftBinarySearch1(int[] nums, int target) {
        int left = 0, right = nums.length-1;
        while(left <= right) {
            int mid = left + (right-left)/2;
            if (target > nums[mid]) {
                left = mid + 1;
            } else if (target < nums[mid]) {
                right = mid - 1;
            } else if (target == nums[mid]) {
                right = mid - 1; // 别返回,锁定左侧边界
            }
        }

        // 最后要检查 left 越界的情况
        if (left >= nums.length || target != nums[left]) {
            return -1;
        }
        return left;
    }

    static int rightBinarySearch1(int[] nums, int target) {
        int left = 0, right = nums.length-1;
        while(left <= right) {
            int mid = left + (right-left)/2;
            if (target > nums[mid]) {
                left = mid + 1;
            } else if (target < nums[mid]) {
                right = mid - 1;
            } else if (target == nums[mid]) {
                left = mid + 1; // 别返回,锁定右侧边界
            }
        }

        // 最后要检查 right 越界的情况
        if (right < 0 || target != nums[right]) {
            return -1;
        }
        return right;
    }

    /**
     * 1、分析二分查找代码时,不要出现 else,全部展开成 else if 方便理解。
     * 2、注意「搜索区间」和 while 的终止条件,如果存在漏掉的元素,记得在最后检查。
     * 3、如需定义左闭右开的「搜索区间」搜索左右边界,只要在 target == nums[mid] 时做修改即可,搜索右侧时需要减一。
     * 4、如果将「搜索区间」全都统一成两端都闭,好记,只要稍改 target == nums[mid] 条件处的代码和返回的逻辑即可,推荐拿小本本记下,作为二分搜索模板。
     */
    private static void testNLRBinarySearch1() {
        int[] nums = new int[]{0, 1, 2, 3, 4, 5, 6, 6, 6, 6, 6, 6, 6, 7, 8, 9};
        int target = 6;

        int idx0 = normalBinarySearch1(nums, target);
        System.out.println(idx0);

        int idx1 = leftBinarySearch1(nums, target);
        System.out.println(idx1);

        int idx2 = rightBinarySearch1(nums, target);
        System.out.println(idx2);
    }

    public static void main(String[] args) {
        testBinarySearch0();
        testLeftBinarySearch0();
        testRightBinarySearch0();
        testNLRBinarySearch1();
    }
}
