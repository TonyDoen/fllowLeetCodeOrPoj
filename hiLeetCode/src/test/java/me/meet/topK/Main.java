package me.meet.topK;

import java.util.HashSet;

public class Main {
    public static int getNum(int[] nums) {
        HashSet<Integer> numSet = new HashSet();

        for (int n = 0; n < nums.length; n++) {
            numSet.add(nums[n]);
        }
        int i = Integer.MAX_VALUE;
        for (int n = 0; n < nums.length; n++) {
            if (nums[n] > 1) {
                if (!numSet.contains(nums[n] - 1)) {
                    if (nums[n] - 1 < i)
                        i = nums[n] - 1;
                }
            }
            if (nums[n] >= 0) {
                if (!numSet.contains(nums[n] + 1)) {
                    if (nums[n] + 1 < i)
                        i = nums[n] + 1;
                }
            }
        }
        return i;
    }

    public static void main(String[] args) {
//        int[] nums = new int[]{-1, 8, 2, 5, 3, 10};
//        System.out.println(getNum(nums));

        int res = power(2, 10);
        System.out.println(res);
    }

    public static int power(int base, int n){
        if(n <= 0 ) return 1;
        else if(n == 1) return base;
        return power(base, n/2)* power(base, n - n/2);
    }
}