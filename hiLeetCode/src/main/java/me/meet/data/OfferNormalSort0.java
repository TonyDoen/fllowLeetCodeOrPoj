package me.meet.data;

public final class OfferNormalSort0 {
    /**
     * 快速排序
     * 1、时间复杂度：O(n*logn)  2、空间复杂度：O(logn)  3、非稳定排序  4、原地排序
     */
    static int[] quickSort(int[] arr, int left, int right) {
        if (left < right) {
            int mid = partition(arr, left, right); // 获取中轴元素所处的位置
            arr = quickSort(arr, left, mid - 1);
            arr = quickSort(arr, mid + 1, right);
        }
        return arr;
    }

    private static int partition(int[] arr, int left, int right) {
        int pivot = arr[left], i = left + 1, j = right;

        for (; ; ) {
            for (; i <= j && arr[i] <= pivot; ) {
                i++; // 向右找到第一个小于等于 pivot 的元素位置
            }
            for (; i <= j && arr[j] >= pivot; ) {
                j--; // 向左找到第一个大于等于 pivot 的元素位置
            }
            if (i >= j) {
                break;
            }
            // swap 交换两个元素的位置，使得左边的元素不大于pivot,右边的不小于pivot
            int tmp = arr[i];
            arr[i] = arr[j];
            arr[j] = tmp;
        }
        arr[left] = arr[j];
        arr[j] = pivot;
        return j;
    }

    private static void testQuickSort() {
        int[] arr = new int[]{4, 3, 1, 2, 3, 3, 5, 6, 8, 7};
        quickSort(arr, 0, arr.length - 1);
        for (int i : arr) {
            System.out.print(i + " ");
        }
        System.out.println();
    }

    /**
     * 堆排序
     * 1、时间复杂度：O(n*logn)  2、空间复杂度：O(1)  3、非稳定排序  4、原地排序
     */
    static int[] heapSort(int[] arr) {
        // 构建堆
        int length = arr.length;
        for (int i = (length - 2) / 2; i >= 0; i--) {
            downAdjust(arr, i, length - 1);
        }
        //进行堆排序
        for (int i = length - 1; i >= 1; i--) {
            int tmp = arr[i];
            arr[i] = arr[0];
            arr[0] = tmp;
            downAdjust(arr, 0, i - 1);
        }
        return arr;
    }

    private static void downAdjust(int[] arr, int parent, int n) {
        int tmp = arr[parent]; // 临时保存要下沉的元素
        int child = 2 * parent + 1; // 定位左孩子节点的位置
        for (; child <= n; ) {
            if (child + 1 <= n && arr[child] < arr[child + 1]) {
                child++; // 如果右孩子节点比左孩子大，则定位到右孩子
            }
            if (arr[child] <= tmp) {
                break; // 如果孩子节点小于或等于父节点，则下沉结束
            }
            // 父节点进行下沉
            arr[parent] = arr[child];
            parent = child;
            child = 2 * parent + 1;
        }
        arr[parent] = tmp;
    }

    private static void testHeapSort() {
        int[] arr = new int[]{4, 3, 1, 2, 3, 3, 5, 6, 8, 7};
        heapSort(arr);
        for (int i : arr) {
            System.out.print(i + " ");
        }
        System.out.println();
    }


    /**
     * 归并排序
     * 1、时间复杂度：O(nlogn)  2、空间复杂度：O(n)  3、稳定排序  4、非原地排序
     */
    // 归并排序
    public static int[] mergeSort(int[] arr, int left, int right) {
        // 如果 left == right，表示数组只有一个元素，则不用递归排序
        if (left < right) {
            // 把大的数组分隔成两个数组
            int mid = (left + right) / 2;
            // 对左半部分进行排序
            arr = mergeSort(arr, left, mid);
            // 对右半部分进行排序
            arr = mergeSort(arr, mid + 1, right);
            //进行合并
            merge(arr, left, mid, right);
        }
        return arr;
    }

    // 合并函数，把两个有序的数组合并起来
    // arr[left..mid]表示一个数组，arr[mid+1 .. right]表示一个数组
    private static void merge(int[] arr, int left, int mid, int right) {
        //先用一个临时数组把他们合并汇总起来
        int[] a = new int[right - left + 1];
        int i = left;
        int j = mid + 1;
        int k = 0;
        while (i <= mid && j <= right) {
            if (arr[i] < arr[j]) {
                a[k++] = arr[i++];
            } else {
                a[k++] = arr[j++];
            }
        }
        while (i <= mid) a[k++] = arr[i++];
        while (j <= right) a[k++] = arr[j++];
        // 把临时数组复制到原数组
        for (i = 0; i < k; i++) {
            arr[left++] = a[i];
        }
    }

    private static void testMergeSort() {
        int[] arr = new int[]{4, 3, 1, 2, 3, 3, 5, 6, 8, 7};
        arr = mergeSort(arr, 0, arr.length - 1);
        for (int i : arr) {
            System.out.print(i + " ");
        }
    }

    public static void main(String[] args) {
        testQuickSort();
        testHeapSort();
        testMergeSort();
    }
}
