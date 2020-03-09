import netscape.security.UserTarget;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.regex.Pattern;

public class StringAL {

    /**
     * 替换空格
     * @param str
     * @return
     */
    public String replaceSpace(StringBuffer str) {
        return str.toString().replace(" ", "%20");
    }

    public String replaceSpace2(StringBuffer str) {
        int length = str.length();
        if (length == 0) {
            return null;
        }

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            char c = str.charAt(i);
            if (c == ' ') {
                builder.append("%20");
            } else {
                builder.append(c);
            }
        }
        return builder.toString();
    }

    /**
     * 循环左移，左旋转字符串
     * @param str
     * @param n
     * @return
     */
    public String LeftRotateString(String str,int n) {
        if (str == null) {
            return "";
        }
        if (str.length() <= 1 || n == 0) {
            return str;
        }
        int index = n % str.length();
        return str.substring(index) + str.substring(0, index);
    }

    /**
     * 翻转单词序列
     */
    public String ReverseSentence(String str) {
        if (str == null) {
            return "";
        }
        if (str.trim().equals("")) {
            return str;
        }
        String[] strings = str.split(" ");
        StringBuilder sb = new StringBuilder();
        for (int i = strings.length - 1; i >= 0; i--) {
            sb.append(strings[i]);
            if (i > 0) {
                sb.append(" ");
            }
        }
        return sb.toString();
    }

    /**
     * 正则表达式匹配
     * 思路就是两个字符串递归遍历，分下个字符是不是*，还有是不是.，当前字符是不是等于正则的字符
     * @param str
     * @param pattern
     * @return
     */
    public boolean match(char[] str, char[] pattern)
    {
        if (str == null || pattern == null) {
            return false;
        }
        return match(str, 0, pattern, 0);
    }

    private boolean match(char[] str, int sIndex, char[] pattern, int pIndex) {
        if (sIndex >= str.length && pIndex >= pattern.length) {
            return true;
        }
        if (sIndex < str.length && pIndex >= pattern.length) {
            return false;
        }

        //当前模式字符的下个字符是不是*
        boolean nextSpecialChar = (pIndex + 1 < pattern.length && pattern[pIndex + 1] == '*');
        if (nextSpecialChar) {
            if (sIndex >= str.length) {
                return match(str, sIndex, pattern, pIndex + 2);
            } else {
                boolean curIsSameChar = pattern[pIndex] == '.' || str[sIndex] == pattern[pIndex];
                if (curIsSameChar) {
                    return match(str,sIndex + 1, pattern,pIndex + 2)
                            || match(str,sIndex + 1, pattern, pIndex)
                            || match(str, sIndex, pattern, pIndex + 2);
                } else {
                    return match(str, sIndex, pattern, pIndex + 2);
                }
            }
        }

        if (sIndex >= str.length) {
            return false;
        } else {
            boolean curIsSameChar   = pattern[pIndex] == '.' || str[sIndex] == pattern[pIndex];
            if (curIsSameChar) {
                return match(str, sIndex + 1, pattern, pIndex + 1);
            }
        }
        return false;
    }

    /**
     * 表示数值的字符串
     * 正则表达式的写法：
     * ^:指以这个开头
     * $:以这个结尾
     * []?:括号里可选
     * (?:) 就是说这个只是个分组
     * ?:表示可选 *：0次或多次 +: 至少出现一次
     * 字符串里\\编译成\，所以正则里用\\当\
     *  @param str
     * @return
     */
    public boolean isNumeric(char[] str) {
        String pattern = "^[-+]?\\d*(?:\\.\\d*)?(?:[eE][+\\-]?\\d+)?$";
        String s = new String(str);
        return Pattern.matches(pattern,s);
    }

    public boolean isNumeric2(char[] str) {
        String num=new String(str);
        try{
            BigDecimal bd = new BigDecimal(num);
        }catch(Exception e){
            return false;
        }
        return true;

    }


    /**
     * 字符流中第一个不重复的字符
     * @param ch
     * 思路：队列保存重复的字符串，并且用字符做下标建立一个数量的数组，当做是map映射
     */
    private LinkedList<Character> charQueue = new LinkedList<>();
    private int[] count = new int[256];
    public void Insert(char ch)
    {
        count[ch]++;
        if (count[ch] == 1) {
            charQueue.addLast(ch);
        }
    }

    //return the first appearence once char in current stringstream
    public char FirstAppearingOnce()
    {
        while (!charQueue.isEmpty() && count[charQueue.getFirst()] > 1) {
            charQueue.removeFirst();
        }
        if(charQueue.isEmpty()) {
            return '#';
        }
        return charQueue.getFirst();
    }

    /**
     * 字符串转整数
     * @param str
     * @return
     */
    public int StrToInt(String str) {
        String pattern = "[1-9,+,-]\\d+";
        if (str == null || str.isEmpty() || !str.matches(pattern)) {
            return 0;
        }

        int len = str.length();
        int index = len - 1;
        long res = 0;
        boolean isNative = str.charAt(0) == '-';

        while(index >=0 && str.charAt(index) >='0' && str.charAt(index) <='9'){
            res += Math.pow(10, len - 1 -  index) * (str.charAt(index) - '0');
            index--;
        }
        res = (isNative ? -res : res);

        //溢出就返回0，用long类型的res来比较，
        //如果定义为int res,那再比较就没有意义了，int范围为[-2147483648,2147483647]
        if(res>Integer.MAX_VALUE|| res<Integer.MIN_VALUE)return 0;
        return (int) res;
    }

    /**
     * 第一次只出现一次的字符
     * 思路：从前，从后找的index都是同一个
     * @param str
     * @return
     */
    public int FirstNotRepeatingChar(String str) {
        if (str == null || str.length() == 0) {
            return -1;
        }
        HashSet<Character> set = new HashSet<>(128);
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (set.contains(c)) {
                continue;
            }
            // 从后面找的index应该也是同一个
            if (str.lastIndexOf(c) == i) {
                return i;
            }
            set.add(c);
        }
        return -1;
    }

}
