import javax.xml.bind.annotation.XmlID;
import java.util.*;

public class ArrayAL {

    public static void main(String[] args) {
        int[] array = {1, 2, 3, 4, 5, 6};
        reOrderArray(array);
    }

    /**
     * 交换两个数， 异或的交换律和结合律
     * @param a
     * @param b
     */
    public static void swap(int a, int b) {
        a = a ^ b;
        b = a ^ b;
        a = a ^ b;
    }

    /**
     * 数字在排序数组出现的次数
     * 思路：二分查找，向左向右+1
     */
    public int GetNumberOfK(int [] array , int k) {
        if (array == null || array.length == 0) {
            return 0;
        }
        int index = Arrays.binarySearch(array, k);
        if (index < 0) {
            return 0;
        }
        int count = 1;
        for (int i = index + 1; i < array.length && array[i] == k; i++) {
            count++;
        }
        for (int i = index - 1; i >= 0 && array[i] == k;i--) {
            count++;
        }
        return count;
    }

    /**
     * 递增二维数组的查找
     * 思路：从右上角开始找，逐渐向要找的数组靠近
     * @param target
     * @param array
     * @return
     */
    public boolean Find(int target, int [][] array) {
        int lineLength = array.length;
        int colLength  = array[0].length;
        if (colLength <=0 || lineLength <= 0) {
            return false;
        }

        int row = 0;
        int col = colLength - 1;
        while (row < lineLength && col >=0) {
            int num = array[row][col];
            if (num > target) {
                col--;
            } else if (num < target) {
                row++;
            } else {
                return true;
            }
        }
        return false;
    }

    /***
     * 数组中的重复数字
     * 思路：排序一次遍历相邻的
     * @param numbers
     * @param length
     * @param duplication
     * @return
     */
    public boolean duplicate(int numbers[], int length, int [] duplication) {
        if (numbers == null || length == 0) {
            return false;
        }
        Arrays.sort(numbers);
        for (int i = 0; i < length - 1; i++) {
            if (numbers[i] == numbers[i++]) {
                duplication[0] = numbers[i];
                return true;
            }
        }
        return false;
    }

    /**
     * 利用题目的特性，下标和数值是包含关系
     * @param numbers
     * @param length
     * @param duplication
     * @return
     */
    public boolean duplicate2(int numbers[], int length, int [] duplication) {
        if (numbers == null || length == 0) {
            return false;
        }
        for (int i = 0; i < length; i++) {
            int num = numbers[i];
            while (num != i) {
                if (num == numbers[num]) {
                    duplication[0] = num;
                    return true;
                }
                // 将数值归位
                int tmp = numbers[num];
                numbers[num] = num;
                num = tmp;
            }
        }
        return false;
    }

    /**
     * 旋转数组最小值
     * 思路：二分，去除这个最小值不在的区间
     * @param array
     * @return
     */
    public int minNumberInRotateArray(int [] array) {
        if (array == null || array.length == 0) {
            return 0;
        }

        int low = 0;
        int high = array.length - 1;
        while (low < high) {
            if (array[low] < array[high]) {
                return array[low];
            }
            int mid = (low + high) / 2;
            if (array[mid] > array[low]) {
                low = mid + 1;
            } else if (array[mid] < array[high]) {
                high = mid;
            } else {
                low++;
            }
        }
        return array[low];
    }

    /**
     * 调整数组使奇数处于偶数之前
     * 思路：1。遍历数组，
     * 2.记录偶数的位置，再去找奇数的位置
     * 3.找到奇数位置后，将偶数位置和奇数位置的数向后移，再把奇数移到偶数位置上
     * @param array
     */
    public static void reOrderArray(int [] array) {
        if (array == null || array.length == 0) {
            return;
        }
        int curEvenIndex = 0;
        for (int i = 0; i < array.length; i++) {
            int curNum = array[i];
            if ((curNum & 1) == 0) { // 找到偶数的位置
                curEvenIndex = i;
                int curOrderIndex = i + 1;
                while (curOrderIndex < array.length && (array[curOrderIndex] & 1) == 0) { // 找到奇数位置
                    curOrderIndex++;
                }

                if (curOrderIndex >= array.length) {
                    return;
                }

                int tmp = array[curOrderIndex];
                while (curOrderIndex > curEvenIndex) {              // 奇数偶数之间部分后移
                    array[curOrderIndex] = array[curOrderIndex - 1];
                    curOrderIndex--;
                }
                array[curEvenIndex] = tmp;
            }
        }
    }


    /**
     * 整数幂
     * 思路：按照数学的计算方法实现一下就好了
     * @param base
     * @param exponent
     * @return
     */
    public static double Power(double base, int exponent) {
        if(base == 0) {
            return 0;
        }
        double result = 1.0d;
        boolean isNative = exponent < 0;
        int abs = Math.abs(exponent);
        while (abs > 0) {
            result *= base;
            abs--;
        }
        return !isNative ? result : (1f / result);
    }

    /**
     * 顺时针打印矩阵
     * 思路：设置4个指针，向上下左右走，一次改变指针的值，直到超出范围
     * @param matrix
     * @return
     */
    public ArrayList<Integer> printMatrix(int [][] matrix) {
        ArrayList<Integer> result = new ArrayList<>();
        if(matrix == null || matrix.length == 0 || matrix[0].length == 0){
            return result;
        }
        int left = 0;
        int right = matrix[0].length - 1;
        int up  = 0;
        int down = matrix.length - 1;
        while(true) {

            // 向右走
            for (int i = left; i <= right;i++) {
                result.add(matrix[up][i]);
            }
            up++;
            if(up > down) {
                break;
            }

            //向下走
            for(int i = up; i <= down; i++) {
                result.add(matrix[i][right]);
            }
            right--;
            if (right < left) {
                break;
            }

            //向左走
            for(int i = right; i >= left; i--) {
                result.add(matrix[down][i]);
            }
            down--;
            if (down < up) {
                break;
            }

            //向上走
            for(int i = down; i >= up;i--) {
                result.add(matrix[i][left]);
            }
            left++;
            if (left > right) {
                break;
            }
        }
        return result;
    }

    /**
     * 数组出现一半的数字
     * 思路：保存当前遍历的出现多的数字，相同加1，不同-1，count等于0的话重新计数
     * 最后要判断下是不是真的一半
     * @param array
     * @return
     */
    public int MoreThanHalfNum_Solution(int [] array) {
        if (array == null || array.length == 0) {
            return 0;
        }
        int curMostNum = array[0];
        int count = 1;
        for (int i = 1; i < array.length; i++) {
            if (curMostNum == array[i]) {
                count++;
            } else {
                count--;
                if (count == 0) {
                    curMostNum = array[i];
                    count = 1;
                }
            }
        }
        int num = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i] == curMostNum) {
                num++;
            }
        }
        return num > array.length / 2 ? curMostNum : 0;
    }

    /**
     * 最小的K个数
     * 思路：排序加一下前k个数就好了
     * @param input
     * @param k
     * @return
     */
    public ArrayList<Integer> GetLeastNumbers_Solution(int [] input, int k) {
        ArrayList<Integer> sortList = new ArrayList<>();
        if (input == null || input.length == 0 || k > input.length) {
            return sortList;
        }
        Arrays.sort(input);
        for (int i = 0; i < k; i++) {
            sortList.add(input[i]);
        }
        return sortList;
    }

    /**
     * 给出一组数，将这组数排列组合成最小数的字符串
     * 思路：将数组排序，排序的标准是：两个数组合，小的话，就放前面，这里用String的compareTo()就好了
     */
    public String PrintMinNumber(int [] numbers) {
        if (numbers == null || numbers.length == 0) {
            return "";
        }
        ArrayList<String> numList = new ArrayList<>();
        for (int i = 0; i < numbers.length; i++) {
            numList.add(String.valueOf(numbers[i]));
        }

        Collections.sort(numList, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                // 返回负数的话，o1要比o2小，所以排前面
                String s1 = o1 + o2;
                String s2 = o2 + o1;
                return s1.compareTo(s2);
            }
        });

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < numList.size(); i++) {
            sb.append(numList.get(i));
        }
        return sb.toString();
    }

    /**
     * 数组中逆序对
     * 思路：用归并排序（分组，分成最小的，再根据条件组合），在归并过程中算这个逆序对的数量
     */
    private static int niCount  = 0;
    public int InversePairs(int [] array) {
        MergeSort(array);
        return niCount;
    }

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
                // 如果是右边数组插入到合并数组，说明这个数比左边数组剩余的数还要小，所以可以组成左边剩余数组个数个的逆序对
                niCount = (niCount + left.length - leftIndex + 1) % 1000000007;
                mergeArray[index] = right[rightIndex++];
            } else {
                mergeArray[index] = left[leftIndex++];
            }
        }
        return mergeArray;
    }

    /**
     * 扑克牌的顺子（也就是判断数组是不是5个连起来的数字）
     * 思路：1.判断是不是5个
     * 2.判断有没有重复
     * 3.判断最大最小是不是相差5
     * @param numbers
     * @return
     */
    public boolean isContinuous(int [] numbers) {
        if(numbers == null || numbers.length != 5) {
            return false;
        }
        ArrayList<Integer> list = new ArrayList<>();
        int countOfZero = 0;
        for (int i = 0; i < numbers.length;i++) {
            if (numbers[i] == 0) {
                countOfZero++;
                continue;
            }
            //有重复的返回false
            if (list.contains(numbers[i])) {
                return false;
            }
            list.add(numbers[i]);
        }
        Collections.sort(list);
        if (list.size() + countOfZero < 5) {
            return false;
        }
        if (list.get(list.size() - 1) - list.get(0) < 5) {
            return true;
        }
        return false;
    }

    /**
     * 和为sum的连续整数数列
     * 双指针法
     * 思路：前后设置个指针，多了前面加一，少了后面加1
     * @param sum
     * @return
     */
    public ArrayList<ArrayList<Integer>> FindContinuousSequence(int sum) {
        ArrayList<ArrayList<Integer>> result = new ArrayList<>();
        int start = 1;
        int end = 2;
        int cur = 3;
        while (end > start) {
            cur = (start + end) * (end - start + 1) / 2;
            if (cur == sum) {
                ArrayList<Integer> list = new ArrayList<>();
                for (int i = start; i <= end; i++) {
                    list.add(i);
                }
                result.add(list);
                start++;
            } else if (cur < sum) {
                end++;
            } else {
                start++;
            }
        }
        return result;
    }

    /**
     * 递增序列，和为S的两个值
     * 思路：还是双指针法，一个在0，一个在最后
     * @param array
     * @param sum
     * @return
     */
    public ArrayList<Integer> FindNumbersWithSum(int[] array, int sum) {
        ArrayList<Integer> result = new ArrayList<>();
        int start = 0;
        int end = array.length - 1;
        int cur = 0;
        while (end > start) {
            cur = array[start] + array[end];
            if (cur == sum) {
                result.add(array[start]);
                result.add(array[end]);
                break;
            } else if (cur < sum) {
                start++;
            } else {
                end--;
            }
        }
        return result;
    }

    /**
     * 数据流中的中位数
     * 思路：用大顶堆存较小的部分，用小顶堆存较大的部分
     * 然后保证两个堆平衡，这样中位数为两个堆顶数据的平均
     * @param num
     */
    // 保证小顶堆的数据都比大顶堆的大
    private PriorityQueue<Integer> minHeap = new PriorityQueue<>();
    private PriorityQueue<Integer> maxHeap = new PriorityQueue<>(new Comparator<Integer>() {
        @Override
        public int compare(Integer o1, Integer o2) {
            return o2 - o1;
        }
    });
    private int count = 0;
    public void Insert(Integer num) {
        if ((count & 1) == 0) {
            maxHeap.offer(num);
            int max = maxHeap.poll();
            minHeap.offer(max);
        } else {
            minHeap.offer(num);
            int min = minHeap.poll();
            maxHeap.offer(min);
        }
        count++;
    }

    public Double GetMedian() {
        if ((count & 1) == 0) {
            return new Double((maxHeap.peek() + minHeap.peek())) / 2;
        } else {
            return new Double(minHeap.peek());
        }
    }

    /**
     * 找丑数
     * 思路：设置2，3，5指针，记录由这三个基本丑数相乘出来的index，由小到大组成个丑数数组
     * @param index
     * @return
     */
    public int GetUglyNumber_Solution(int index) {
        if (index == 0) {
            return 0;
        }

        // 记录丑数的数组
        int[] chouNum = new int[index];
        chouNum[0] = 1;
        int pos2 = 0;
        int pos3 = 0;
        int pos5 = 0;
        for (int i = 1; i < index; i++) {
            // 算出下一个丑数
            chouNum[i] = Math.min(chouNum[pos2] * 2, Math.min(chouNum[pos3] * 3, chouNum[pos5] * 5));

            // 当前丑数是由哪个乘出来的
            if (chouNum[i] == chouNum[pos2] * 2) {
                pos2++;
            }

            if (chouNum[i] == chouNum[pos3] * 3) {
                pos3++;
            }
            if (chouNum[i] == chouNum[pos5] * 5) {
                pos5++;
            }
        }
        return chouNum[index - 1];
    }
}
