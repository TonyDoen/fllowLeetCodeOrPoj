package me.meet.data.offerAlgorithm;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public final class Sort {
    private Sort() {
    }

    /**
     * 全排列
     * 027-字符串的排列
     *
     * 题目描述：
     * 输入一个字符串,按字典序打印出该字符串中字符的所有排列。
     * 例如输入字符串abc,则打印出由字符a,b,c 所能排列出来的所有字符串
     * abc,acb,bac,bca,cab和cba。
     *
     * 输入描述:
     * 输入一个字符串,长度不超过9(可能有字符重复),字符只包括大小写字母。
     */
    static List<String> permutation(String src) {
        if (null == src || src.isEmpty()) {
            return Collections.emptyList();
        }
        List<String> result = new LinkedList<>();
        helpPermutation(result, 0, src.toCharArray());
        return result;
    }

    private static void helpPermutation(List<String> result, int begin, char[] arr) {
        if (arr.length - 1 == begin) {
            result.add(String.valueOf(arr));
        }
        for (int i = begin; i < arr.length; i++) {
            if (i != begin && arr[i] == arr[begin]) { // 与begin不同位置的元素相等，不需要交换
                continue;
            }
            swap(arr, begin, i); // 交换元素
            helpPermutation(result, begin + 1, arr); // 处理后续元素
            swap(arr, begin, i); // 数组复原
        }
    }

    private static void swap(char[] arr, int i, int j) {
        if (i == j) {
            return;
        }
        char tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    private static void testPermutation() {
        String src = "abc";
        List<String> res = permutation(src);
        System.out.println(res);
    }

    /**
     * 035-数组中的逆序对(归并排序)(冒泡排序)
     *
     * 在数组中的两个数字，如果前面一个数字大于后面的数字，则这两个数字组成一个逆序对。
     * 输入一个数组,求出这个数组中的逆序对的总数P。
     * 并将P对1000000007取模的结果输出。 即输出P%1000000007
     *
     * 1. 冒泡排序：
     * 交换次数就是逆序对数，换句换说就是交换一次的两个数就是一组逆序对。
     * 时间复杂度 O(n^2) ；
     * 空间复杂度 O(1)。
     *
     * 2. 归并排序
     * 归并排序分而治之，统计逆序对数；两数组第一个数比较后数组小的时候，逆序对数加上前数组的长度，其他与归并排序完全一样。
     * 时间复杂度 O(N*logN)；
     * 空间复杂度 O(n).
     *
     *            [ 7, 5, 6, 4 ]            1> 把长度 4 的数组 拆分成 长度为 2 的数组
     *          [ 7, 5 ]; [ 6, 4 ]          2> 把长度 2 的数组 拆分成 长度为 1 的数组
     *      [ 7 ]; [ 5 ]; [ 6 ]; [ 4 ];     3> 把长度 1 的数组 合并，排序，统计逆序对
     *          [ 5, 7 ]; [ 4, 6 ]          4> 把长度 2 的数组 合并，排序，统计逆序对
     *           [ 4, 5, 6, 7 ]
     */
    private static int INVERSE_PAIR_CNT = 0;

    static int inversePair(int[] arr) {
        if (null == arr || arr.length < 1) {
            return -1;
        }
        mergeSort(0, arr.length - 1, arr);
        return INVERSE_PAIR_CNT;
    }

    private static void mergeSort(int left, int right, int[] arr) {
        if (left >= right) {
            return;
        }
        int mid = left + (right - left) / 2;
        mergeSort(left, mid, arr);
        mergeSort(mid + 1, right, arr);
        // merge
        int[] tmp = new int[right - left + 1];
        int i = left, j = mid + 1, k = 0;
        for (; i <= mid && j <= right; ) {
            if (arr[i] <= arr[j]) {
                tmp[k] = arr[i];
                k++;
                i++;
            } else { // 左比右大，左之后所有的都比右大，有mid-i+1个逆序对
                tmp[k] = arr[j];
                k++;
                j++;
                // TODO:
                INVERSE_PAIR_CNT += mid - i + 1;
                INVERSE_PAIR_CNT %= 1000000007; // 防止溢出，每一步都要取余
            }
        }
        for (; i <= mid; ) {
            tmp[k] = arr[i];
            k++;
            i++;
        }
        for (; j <= right; ) {
            tmp[k] = arr[j];
            k++;
            j++;
        }
        int tmpLength = tmp.length;
        if (tmpLength >= 0) System.arraycopy(tmp, 0, arr, left, tmpLength);
    }

    private static void testInversePair() {
        int[] arr = new int[]{7, 5, 6, 4};
        int res = inversePair(arr);
        System.out.println(res);
    }

    /**
     * 029-最小的K个数(堆排序)(快速排序)
     *
     * 输入n个整数，找出其中最小的K个数。
     * 例如输入 4,5,1,6,2,7,3,8 这8个数字，则最小的4个数字是 1,2,3,4 。
     *
     * 思路1:
     * 按照升序排序，然后取前K个数，就是我们最终想要的到的结果，
     * 现在较好一点的排序方法时间复杂度是N*logN
     *
     */
    /**
     * 思路2:
     * 根据一次快排(Partition)的想法，可知一次随机快速排序可以确定一个有序的位置，这个位置的左边都小于这个数，右边都大于这个数，
     * 我们如果能找到随机快速排序确定的位置等于k-1的那个位置，那么0~k-1个数就是我们要找的数。
     * 怎么能找到那个位置：
     * - 如果Partition确定的位置小于K-1，说明k-1这个位置在它的右边，我们继续在右边进行查找。
     * - 如果Partition确定的位置大于K-1，说明k-1这个位置在它的左边，我们继续在左边进行查找。
     *
     * 缺点： 时间复杂度虽然是O(n)，但是找出来的最小的K个数却不是排序过的。而且这种方法有个限制，就是必须修改给的数组。
     *
     */
    static int[] findKthNumber0(int[] arr, int k) {
        if (null == arr || arr.length < 1 || k < 1 || k > arr.length) {
            return null;
        }
        int left = 0, right = arr.length - 1, _1k = k - 1;
        int pivot = quickPartition(left, right, arr);
        for (; _1k != pivot; ) {
            if (_1k > pivot) {
                left = pivot + 1;
                pivot = quickPartition(left, right, arr);
            } else {
                right = pivot - 1;
                pivot = quickPartition(left, right, arr);
            }
        }

        int[] result = new int[pivot + 1];
        System.arraycopy(arr, 0, result, 0, pivot + 1);
        return result;
    }

    static void quickSort(int left, int right, int[] arr) {
        if (left < right) {
            int pivot = quickPartition(left, right, arr);
            quickSort(left, pivot, arr);
            quickSort(pivot + 1, right, arr);
        }
    }

    private static int quickPartition(int left, int right, int[] arr) {
        int pivot = arr[left];
        for (; left < right; ) {
            for (; left < right && arr[right] >= pivot; ) {
                right--;
            }
            arr[left] = arr[right];
            for (; left < right && arr[left] <= pivot; ) {
                left++;
            }
            arr[right] = arr[left];
        }
        arr[left] = pivot;
        return left;
    }

    private static void testFindKthNumber0() {
        int[] arr = new int[]{4, 5, 1, 6, 2, 7, 3, 8};
        int k = 4;
        int[] res = findKthNumber0(arr, k);
        for (int i : res) {
            System.out.print(i + " ");
        }
        System.out.println();

        quickSort(0, arr.length-1, arr);
        for (int i : arr) {
            System.out.print(i + " ");
        }
        System.out.println();
    }

    /**
     *
     * 思路3:
     * 是建一个K个数的大顶堆，每次拿一个数和堆顶元素比较，如果这个数比堆顶元素大，则必然不是最小的K个数，如果这个数比堆顶元素小，则与堆顶元素交换，然后在向下调整一次建成新的大堆，然后遍历所有的数，直到最小的K个数都进堆里。
     * 最大的K个数 ---- 建小顶堆
     * 最小的K个数 ---- 建大顶堆
     * 优点：海量数据不占内存；实时判别新产生的数据；时间复杂度O(N*logK)。
     *
     *
     * 堆元素下标从0开始，习惯问题，此时除根节点外的任意节点的索引位置关系为
     * 父节点位置：ceiling(i/2) - 1; 左孩子：i*2 + 1; 右孩子：i*2 + 2; 最后一个分支节点位置为：n/2 - 1;
     */
    static int[] findKthNumber1(int[] arr, int k) {
        if (null == arr || arr.length < 1 || k < 1 || k > arr.length) {
            return null;
        }
        int[] result = new int[k];
        System.arraycopy(arr, 0, result, 0, k);

        int _1k = k - 1;
        for (int i = (k - 2) / 2; i >= 0; i--) { // 构建堆
            downAdjust(result, i, _1k);
        }
        for (int i = k; i < arr.length; i++) { // 进行堆排序
            if (arr[i] < result[0]) { // 最小的K个数
                result[0] = arr[i];
                downAdjust(result, 0, _1k);
            }
        }
        return result;
    }

    static void heapSort(int[] arr) {
        if (null == arr || arr.length < 1) {
            return;
        }
        for (int i = (arr.length - 2) / 2, _1l = arr.length - 1; i >= 0; i--) { // 构建堆
            downAdjust(arr, i, _1l);
        }
        for (int i = arr.length - 1; i > 0; i--) {
            int tmp = arr[i];
            arr[i] = arr[0];
            arr[0] = tmp;
            downAdjust(arr, 0, i - 1);
        }
    }

    private static void downAdjust(int[] arr, int parent, int n) {
        // 临时保存要下沉的元素; 定位左孩子节点的位置
        int tmp = arr[parent], child = 2 * parent + 1;
        for (; child <= n; ) {
            if (child + 1 <= n && arr[child] < arr[child + 1]) { // 如果左孩子小于右孩子，则定位到右孩子
                child++;
            }
            if (arr[child] <= tmp) { // 如果孩子节点小于或等于父节点，则下沉结束
                break;
            }
            // 父节点进行下沉
            arr[parent] = arr[child];
            parent = child;
            child = 2 * parent + 1;
        }
        arr[parent] = tmp;
    }

    private static void testFindKthNumber1() {
        int[] arr = new int[]{4,5,1,6,2,7,3,8};
        int k = 4;
        int[] res = findKthNumber1(arr, k);
        assert res != null;
        for (int i : res) {
            System.out.print(i + " ");
        }
        System.out.println();

        heapSort(arr);
        for (int i : arr) {
            System.out.print(i + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        // 027-字符串的排列
        testPermutation();
        // 035-数组中的逆序对(归并排序)
        testInversePair();
        // 029-最小的K个数(堆排序)(快速排序)
        testFindKthNumber0();
        testFindKthNumber1();
    }
}
