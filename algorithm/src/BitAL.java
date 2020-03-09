import java.util.ArrayList;

public class BitAL {

    /**
     * 位运算（算二进制数里1的个数）
     * 思路： 1.n&(n -1)
     * 2.flag = 1, 不断向左移位
     * @param n
     * @return
     */
    public int NumberOf1(int n) {
        int count = 0;
        int flag = 1;
        while (flag != 0) {
            if ((n & flag) != 0) {
                count++;
            }
            flag = flag << 1;
        }
        return count;
    }

    public int NumberOf2(int n) {
        int count = 0;
        while (n != 0) {
            count++;
            n = n & (n - 1);
        }
        return count;
    }

    /**
     * 整数中出现1的个数
     * 思路：就是计算个十百千万等位数出现的1
     * 以某一位为位分割点，将数字分为高位和低位
     * 高位：1.等于0 2.等于1，3.>=2的：high / 10 + 1,所以统一就加8进位在除以10
     * 低位：如果是1，就代表有低位那么多个1加上本身这个位的1
     * @param n
     * @return
     */
    public int NumberOf1Between1AndN_Solution(int n) {
        int count = 0;
        // pos为位数，个，十，百
        for (int pos = 1; pos <= n; pos *= 10) {
            int high = n / pos; // 将n分成高位，和低位
            int low = n % pos;
            // 因为每个位的一数量 = (a / 10) + 1，然后分那一位是0，1和2-9的情况，综合一下直接加8进位就能满足了
            // 第二段相加是因为那一位是1是，总共有low那么多个1出现再加上本身当前位的1
            count += (high + 8) / 10 * pos + (high % 10 == 1 ? low + 1 : 0);
        }
        return count;
    }



    /**
     * 数组中出现一次的数字
     * 1.全组进行异或，得到A^B，
     * 2.异或的结果中1，代表的是A，B不同的位
     * 3.根据这个位，分组异或得到A和B
     *
     * @param array
     * @param num1
     * @param num2
     */
    public void FindNumsAppearOnce(int[] array, int num1[], int num2[]) {
        int xor = 0;
        for (int i = 0; i < array.length; i++) {
            xor ^= array[i];
        }
        int bit = 1;
        while ((bit & xor) == 0) {
            bit = bit << 1;
        }
        int A = 0;
        int B = 0;
        for (int i = 0; i < array.length; i++) {
            if ((bit & array[i]) == 0) {
                A ^= array[i];
            } else {
                B ^= array[i];
            }
        }
        num1[0] = A;
        num2[0] = B;
    }



    /**
     * 求1+2+3+4。。。+n，不用各种语句和乘除法
     */
    public int Sum_Solution(int n) {
        int up = (int)(Math.pow(n , 2)) + n;
        return up >> 1;
    }

    // 短路递归
    public int Sum_Solution2(int n) {
        int sum = n;
        boolean flag = (sum > 0) && (sum += Sum_Solution(n -  1)) > 0;
        return sum;
    }

    /**
     * 不用加减乘除算加法
     * 用异或和相与进位处理
     * @param num1
     * @param num2
     * @return
     */
    public int Add(int num1,int num2) {
        int result = 0;
        int carry = 0;
        do {
            result = num1 ^ num2;
            carry = (num1 & num2) << 1;
            num1 = result;
            num2 = carry;
        } while(carry != 0);
        return result;
    }
}