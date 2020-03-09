import java.util.Arrays;

/**
 * https://www.cnblogs.com/guoyaohua/p/8600214.html
 */
public class SortAL {

    public static void main(String[] args) {
        int[] a = {6, 5, 4, 3, 2, 1, 0};
        insertSort(a);
        System.out.println(Arrays.toString(a));;
    }

    public static void swap(int[] array, int i, int j) {
        int tmp = array[i];
        array[i] = array[j];
        array[j] = tmp;
    }

    /**
     * 快速排序
     * 最好O(nlogn) 最坏O(n^2)
     * 不稳定
     * @param array
     */
    public static void QuickSort(int[] array, int low, int high) {
        if (array.length < 1 || low < 0 || high >= array.length || low > high) {
            return;
        }
        int mid = partition(array, low, high);
        if (mid > low) {
            QuickSort(array, low, mid - 1);
        }
        if (mid < high) {
            QuickSort(array, mid + 1, high);
        }
    }

    private static int partition(int[] array, int low, int high) {
        // 这个函数就是将数组分成比基准值大的，比基准值小的两部分
        int baseIndex =(int) (low + Math.random() * (high - low + 1));
        int posSmallerBase = low - 1; //遍历时记录的是比基准值小的index，最终和基准值交换，也就说最后就是基准值的位置
        swap(array, baseIndex, high); //基准值调到最后面
        for (int i = low; i <= high; i++) {
            if (array[i] <= array[high]) {
                posSmallerBase++; //找到比基准值小的就++，代表比基准值小的index
                if (i > posSmallerBase) { // 此时是当前遍历的值是比基准值小的，而此时的索引却比基准值大，所以要交换
                    swap(array, i, posSmallerBase);
                }
            }
        }
        return posSmallerBase;
    }

    /**
     * 归并排序
     * O(nlogn) 稳定
     */
    public static int[] MergeSort(int[] array) {
        if (array.length < 2) { // 也就说当前数组只剩一个了
            return array;
        }

        int mid = array.length / 2;
        int[] left = Arrays.copyOfRange(array, 0, mid);
        int[] right = Arrays.copyOfRange(array, mid, array.length);
        left = MergeSort(left);
        right = MergeSort(right);
        return MergeTwoArray(left, right);
    }

    private static int[] MergeTwoArray(int[] left, int[] right) {
        int[] mergeArray =  new int[left.length + right.length];
        for (int index = 0, leftIndex = 0 ,rightIndex = 0; index < mergeArray.length; index++) {
            if (leftIndex >= left.length) {
                mergeArray[index] = right[rightIndex++];
            } else if (rightIndex >= right.length) {
                mergeArray[index] = left[leftIndex++];
            } else if (left[leftIndex] > right[rightIndex]) {
                mergeArray[index] = right[rightIndex++];
            } else {
                mergeArray[index] = left[leftIndex++];
            }
        }
        return mergeArray;
    }


    /**
     * 冒泡排序
     * 就是一直相邻交换，把最大的交换到最后面
     * O（n^2） 稳定
     */
    public static void BubbleSort(int[] array) {
        if (array.length <= 0) {
            return;
        }

        for (int i = 0; i < array.length; i++) {
            int restCount = array.length - 1 - i; // 数组剩下未排序的数量
            for (int j = 0; j < restCount; j++) {
                if (array[j + 1] < array[j]) {
                    swap(array, j + 1, j);
                }
            }
        }
    }

    /**
     * 选择排序
     * 就是每次选择个最小的放到最前面
     * O（n^2） 不稳定
     */
    public static void selectionSort(int[] array) {
        if (array.length == 0) {
            return;
        }

        for (int i = 0; i < array.length; i++) {
            // i就是目前要从剩余数组中找最小的放进来的index
            int curMinIndex = i; // 记录剩下数组中最小的index
            for (int j = i; j < array.length;j++) {
                if (array[j] < array[i]) {
                    curMinIndex = j;
                }
            }
            swap(array, curMinIndex, i);
        }
    }

    /**
     * 插入排序
     * O(n^2) 稳定
     * 就是默认前面的是排好序的，从最后面往前找这个数应该插入的位置，其他往后移
     */
    public static void insertSort(int[] array) {
        if (array.length == 0) {
            return;
        }

        for (int i = 0; i < array.length - 1; i++) {
            int needSortNum = array[i + 1];
            int preIndex = i;
            while (preIndex >= 0 && array[preIndex] > needSortNum) {
                array[preIndex + 1] = array[preIndex];
                preIndex--;
            }
            array[preIndex + 1] = needSortNum;
        }
    }

}
