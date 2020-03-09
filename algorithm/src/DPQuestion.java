import netscape.security.UserTarget;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * 动态规划就是将问题分解成小问题，或者是分几步，求出f(n)的解或者通项，然后递归或者循环实现
 */
public class DPQuestion {

    /**
     * 斐波那契数列第N项
     * 思路：设置first,seconed指针，将second - first就完事了
     * @param n
     * @return
     */
    public int Fibonacci(int n) {
        int first = 0;
        int second = 1;
        if (n <= 0) {
            return 0;
        }
        while (n > 1) {
            second = first + second;
            first = second - first;
            n--;
        }
        return second;
    }

    public static int Fibonacci2(int n) {
        return tailFibonacci(n, 0, 1);
    }

    // 尾递归优化
    // 就是把结果放在参数列表里，进行尾递归
    private static int tailFibonacci(int n, int first, int second) {
        if (n <= 0) {
            return 0;
        }
        if (n == 1) {
            return second;
        }

        return tailFibonacci(n - 1, second, first + second);
    }


    /**
     * 跳台阶
     * 思路：就是考虑最后一步的方案，f(n) = f(n - 1) + f(n - 2)
     */
    public int JumpFloor(int target) {
        int oneTarget = 1;
        int twoTarget = 2;

        if (target <= 0) {
            return 0;
        }
        if (target == 1) {
            return oneTarget;
        }
        if (target == 2) {
            return twoTarget;
        }
        while (target > 2) {
            twoTarget = twoTarget + oneTarget;
            oneTarget = twoTarget - oneTarget;
            target--;
        }
        return twoTarget;
    }

    /**
     * 变态跳台阶
     * f(n)=f(n-1)+f(n-2)+...+f(1)
     * f(n-1)=f(n-2)+...f(1)
     * 得:f(n)=2*f(n-1)
     * @param target
     * @return
     */
    public int JumpFloorII(int target) {
        return 1<<(target - 1);
    }

    /**
     * 我们可以用2*1的小矩形横着或者竖着去覆盖更大的矩形。请问用n个2*1的小矩形无重叠地覆盖一个2*n的大矩形，总共有多少种方法？
     * f(1) = 1
     * f(2) = 2
     * 重点：涂掉最后一块矩形的时候，是用什么方式完成的？
     * 如果是占用一块的，剩2块：f(n) = f(n - 1) eg:f(3) = 2
     * 如果是占用2块的，剩一块：f(n) = f(n - 2) eg:f(3) = 1;
     */
    public int RectCover(int target) {
        int oneP = 1;
        int twoP = 2;
        if (target <= 0) {
            return 0;
        }
        if (target == 1) {
            return 1;
        }
        if (target == 2) {
            return 2;
        }

        while (target > 2) {
            twoP = oneP + twoP;
            oneP = twoP - oneP;
            target--;
        }
        return twoP;
    }

    /**
     * 剪绳子(即剪成几段的乘积最大)
     * * @param target
     *      * @return
     *      *
     *      * 分类：最值型、划分型
     *      *
     *      * 考虑最后一步：
     *      * 必然有一个点，把target分成两段，两段分别构成最小子问题。
     *      * 两段的最大值的乘积，也是target所求的最大值。
     *      * 设划分点为i,f[i]表示长度为i的绳子的乘积最大值。
     *      *
     *      * 转移方程：
     *      * f[i] = MAX{f[j]*f[i-j]}|0<j<i
     *      *
     *      * 那么我们求的就是f[target]
     *      *
     *      * 初始值：
     *      * f[0]=1
     *      * f[1]=1
     *      *
     *      * 边界情况：
     *      * 无
     *      *
     *      * 计算顺序:
     *      * i从1到target
     *      * j从1到i
     */
    public int cutRope(int target) {
        int[] result = new int[target + 1];
        result[0] = 0;
        result[1] = 1;

        for (int i = 1; i <= target; i++) {
            if (target != i) {
                result[i] = i; // 不分割的情况，直接记录当前最大值，即多少米
            }
            for(int j = 1; j < i; j++){
                result[i] = Math.max(result[i], result[j] * result[i - j]);
            }
        }
        return result[target];
    }

    public int cutRope2(int target) {
        int[] result = new int[target + 1];
        if (target < 2) {
            return 0;
        } else if (target == 2) {
             return 1;
        } else if (target == 3) {
            return 2;
        }

        result[1] = 1;
        result[2] = 2;
        result[3] = 3;

        for (int i = 4; i <= target; i++) {
            for(int j = 1; j < i / 2; j++){ // 这里就是算长度为i，在j分割点，分成的两段乘积，是不是比当前的值大
                result[i] = Math.max(result[i], result[j] * result[i - j]);
            }
        }
        return result[target];
    }

    /**
     * 字符全排列
     * @param str
     * @return
     */
    public ArrayList<String> Permutation(String str) {
        ArrayList<String> list = new ArrayList<>();
        if (str == null || str.length() == 0) {
            return list;
        }
        char[] chars = str.toCharArray();
        PemutationCore(chars, 0, list);
        Collections.sort(list);
        return list;
    }

    private void PemutationCore(char[] chars, int index, List<String> result) {
        if (index == chars.length - 1) { // 表示已经遍历到剩一个字符了
            String str = String.valueOf(chars);
            if (!result.contains(str)) {
                result.add(str);
            }
        } else {
            // 将后面每个字符和首字符交换，再递归剩下字符，注意递归回来要交换回来
            for (int i = index; i < chars.length; i++) {
                swap(chars, index, i);
                PemutationCore(chars, index + 1, result);
                swap(chars, i, index);
            }
        }
    }

    private void swap(char[] chars, int i, int j) {
        char tmp = chars[i];
        chars[i] = chars[j];
        chars[j] = tmp;
    }


    /**
     * 构建乘积数组
     * 也就是说把A[i]的地方单做1，A[i]前后两部分乘积当做新数组的项
     * A {1,2,3,4}
     * B[i] = c[i] * d[i]
     *
     * c[i] = c[i-1] * A[i-1]
     * d[i] = d[i + 1] * A[i + 1]
     * @param A
     * @return
     */
    public int[] multiply(int[] A) {
        if (A == null || A.length == 0) {
            return null;
        }

        int length = A.length;
        int[] C = new int[length];
        C[0] = 1;
        for (int i = 1; i < length; i++) {
            C[i] = C[i - 1] * A[i - 1];
        }

        int[] D = new int[length];
        D[length - 1] = 1;
        for (int i = length - 2; i >= 0; i--) {
            D[i] = D[i + 1] * A[i + 1];
        }

        int[] B = new int[length];
        for (int i = 0; i < length; i++) {
            B[i] = C[i] * D[i];
        }

        return B;
    }

    /**
     * 连续子数组最大的和
     * @param array
     * @return
     */
    public int FindGreatestSumOfSubArray(int[] array) {
        if (array == null || array.length == 0) {
            return 0;
        }
        int[] dp = new int[array.length]; //记录加上每个数组的数字的最大值
        dp[0] = array[0];
        int max = array[0];
        for (int i = 1; i < array.length; i++) {
            int newMax = dp[i - 1] + array[i];
            if (newMax > array[i]) {
                dp[i] = newMax;
            } else {
                dp[i] = array[i];
            }
            if (dp[i] > max) {
                max = dp[i];
            }
        }
        return max;
    }

    /**
     * 约瑟夫环问题
     * @param n 人数
     * @param m 每报多少数，即间隔
     * @return
     */
    public int LastRemaining_Solution(int n, int m) {
        LinkedList<Integer> linkedList = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            linkedList.add(i);
        }

        int removeIndex = 0;
        while(linkedList.size() > 1) {
            removeIndex = (removeIndex + m - 1) % linkedList.size();
            linkedList.remove(removeIndex);
        }
        return linkedList.size() == 1 ? linkedList.get(0) : -1;
    }

    // f(n, m) = (f(n - 1, m) + m ) % n;
    public int LastRemaining_Solution2(int n, int m) {
        if (n == 0 || m == 0) {
            return -1;
        }
        // 这个记录的是获胜者的下标，当数组只有一个人时，获胜者下标就是0
        int curWinnerIndex = 0;
        for (int i = 2; i <= n; i++) {
            curWinnerIndex = (curWinnerIndex + m) % i;
        }
        return curWinnerIndex;
    }

}
