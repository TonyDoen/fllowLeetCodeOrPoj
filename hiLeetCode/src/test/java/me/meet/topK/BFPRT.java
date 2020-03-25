package me.meet.topK;

import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.ThreadPoolExecutor;

public class BFPRT {
    /**
     * BFPRT算法步骤如下：
     * 而目前解决TOP-K问题最有效的算法即是BFPRT算法，其又称为中位数的中位数算法，该算法由Blum、Floyd、Pratt、Rivest、Tarjan提出，最坏时间复杂度为O(n)。
     *
     * 选取主元；
     * 1.1. 将n个元素按顺序分为$⌊\frac n5⌋$个组，每组5个元素，若有剩余，舍去；
     * 1.2. 对于这$⌊\frac n5⌋$个组中的每一组使用插入排序找到它们各自的中位数；
     * 1.3. 对于 1.2 中找到的所有中位数，调用BFPRT算法求出它们的中位数，作为主元；
     * 以 1.3 选取的主元为分界点，把小于主元的放在左边，大于主元的放在右边；
     * 判断主元的位置与k的大小，有选择的对左边或右边递归。
     *
     * BFPRT算法步骤如下：
     * （1）：选取主元；
     *   （1.1）：将n个元素划分为⌊n/5⌋个组，每组5个元素，若有剩余，舍去；
     *   （1.2）：使用插入排序找到⌊n/5⌋个组中每一组的中位数；
     *   （1.3）：对于（1.2）中找到的所有中位数，调用BFPRT算法求出它们的中位数，作为主元；
     * （2）：以（1.3）选取的主元为分界点，把小于主元的放在左边，大于主元的放在右边；
     * （3）：判断主元的位置与k的大小，有选择的对左边或右边递归。
     *
     */
    /**
     * 返回数组 array[left, right] 的第 k 小数的下标 bfprt
     */
    static int bfprt(int array[], int left, int right, int k) {
        int pivot_index = getPivotIndex(array, left, right); // 得到中位数的中位数下标（即主元下标）
        int partition_index = partition(array, left, right, pivot_index); // 进行划分，返回划分边界
        int num = partition_index - left + 1;

        if (num == k)
            return partition_index;
        else if (num > k)
            return bfprt(array, left, partition_index - 1, k);
        else
            return bfprt(array, partition_index + 1, right, k - num);
    }

    /**
     * 对数组 array[left, right] 进行插入排序，并返回 [left, right]
     * 的中位数。
     */
    static int insertSort(int array[], int left, int right) {
        int temp;
        int j;

        for (int i = left + 1; i <= right; i++) {
            temp = array[i];
            j = i - 1;
            while (j >= left && array[j] > temp)
                array[j + 1] = array[j--];
            array[j + 1] = temp;
        }

        return ((right - left) >> 1) + left;
    }

    /**
     * 数组 array[left, right] 每五个元素作为一组，并计算每组的中位数，
     * 最后返回这些中位数的中位数下标（即主元下标）。
     *
     * @attention 末尾返回语句最后一个参数多加一个 1 的作用其实就是向上取整的意思，
     * 这样可以始终保持 k 大于 0。
     */
    static int getPivotIndex(int array[], int left, int right) {
        if (right - left < 5)
            return insertSort(array, left, right);

        int sub_right = left - 1;

        // 每五个作为一组，求出中位数，并把这些中位数全部依次移动到数组左边
        for (int i = left; i + 4 <= right; i += 5) {
            int index = insertSort(array, i, i + 4);
//            swap(array[++sub_right], array[index]);
            swap(array, ++sub_right, index);
        }

        // 利用 BFPRT 得到这些中位数的中位数下标（即主元下标）
        return bfprt(array, left, sub_right, ((sub_right - left + 1) >> 1) + 1);
    }

    /**
     * 利用主元下标 pivot_index 进行对数组 array[left, right] 划分，并返回
     * 划分后的分界线下标。
     */
    static int partition(int array[], int left, int right, int pivot_index) {
//        swap(array[pivot_index], array[right]); // 把主元放置于末尾
        swap(array, pivot_index, right);

        int partition_index = left; // 跟踪划分的分界线
        for (int i = left; i < right; i++) {
            if (array[i] < array[right]) {
//                swap(array[partition_index++], array[i]); // 比主元小的都放在左侧
                swap(array, partition_index++, i);
            }
        }

//        swap(array[partition_index], array[right]); // 最后把主元换回来
        swap(array, partition_index, right);

        return partition_index;
    }

//    void swap(int i, int j) {
//    }

    static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    public static void main(String[] args) {
        int[] arr = new int[]{3, 7, 6, 8};
        swap(arr, 0, 1);
        System.out.println(arr);
        System.out.println("hello");

//        String
//        Objects.compare()
//        ArrayList
//        ThreadPoolExecutor

    }
}
