import java.util.*;

public class StackAndQueueAL {

    /**
     * 用两个栈实现队列
     */
    Stack<Integer> stack1 = new Stack<Integer>();
    Stack<Integer> stack2 = new Stack<Integer>();

    public void push1(int node) {
        stack1.push(node);
    }

    public int pop1() {
        while (!stack1.isEmpty()) {
            stack2.push(stack1.pop());
        }
        int result = stack2.pop();
        while(!stack2.isEmpty()) {
            stack1.push(stack2.pop());
        }
        return result;
    }

    /**
     * 滑动窗口的最大值
     * 用双端队列保存滑动窗口的最大值的下标
     * 1.删队头：这个下标不在滑动窗口中
     * 2.删队尾：当前遍历到的数字比队列的都大
     * 3.下标入队
     * 4.到达滑动窗口大小，没移动一次，都将队首入List
     * @param num
     * @param size
     * @return
     */
    public ArrayList<Integer> maxInWindows(int [] num, int size)
    {
        ArrayList<Integer> list = new ArrayList<>();
        if (num== null|| num.length == 0 || size == 0 || size > num.length) {
            return list;
        }

        LinkedList<Integer> deque = new LinkedList<>(); // 双端队列保存下标
        for(int i = 0; i < num.length; i++) {
            if (!deque.isEmpty()) {
                if (i >= deque.peek() + size) {
                    deque.pop();
                }

                while (!deque.isEmpty() && num[i] >= num[deque.getLast()]) {
                    deque.removeLast();
                }
            }
            deque.offer(i);
            if (i + 1 >= size) {
                list.add(num[deque.peek()]);
            }
        }
        return list;
    }

    // 用最大堆来做
    public ArrayList<Integer> maxInWindows2(int [] num, int size)
    {
        ArrayList<Integer> list = new ArrayList<>();
        if (num== null|| num.length == 0 || size == 0 || size > num.length) {
            return list;
        }

        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2 - o1;
            }
        });
        for (int i = 0; i < size; i++) {
            maxHeap.offer(num[i]);
        }
        list.add(maxHeap.peek());
        for (int i = 0, last = size; last < num.length; i++, last++) {
            maxHeap.remove(num[i]);
            maxHeap.offer(num[last]);
            list.add(maxHeap.peek());
        }
        return list;
    }

    /**
     * 包含min函数的栈
     * 用个辅助栈存储较小值
     */
    private Stack<Integer> minStack = new Stack<>();
    private Stack<Integer> dataStack = new Stack<>();
    public void push(int node) {
        dataStack.push(node);
        if (minStack.isEmpty()) {
            minStack.push(node);
        } else {
            int curMin = minStack.peek();
            if(node < curMin) {
                minStack.push(node);
            } else {
                minStack.push(curMin);
            }
        }
    }

    public void pop() {
        dataStack.pop();
        minStack.pop();
    }

    public int top() {
        return dataStack.peek();
    }

    public int min() {
        return minStack.peek();
    }

    /**
     * 栈的压入弹出
     * 思路：就是用一个栈模拟push，然后看栈顶是不是和另外个数组重合，重合就弹出，另外数组往后移，最后看栈是不是空的
     * @param pushA
     * @param popA
     * @return
     */
    public boolean IsPopOrder(int [] pushA,int [] popA) {
        Stack<Integer> stack = new Stack<>();
        if (pushA == null || popA == null || popA.length != pushA.length) {
            return false;
        }
        int j = 0;
        for (int i = 0; i < pushA.length; i++) {
            stack.push(pushA[i]);
            while (!stack.isEmpty() && stack.peek() == popA[j]) {
                stack.pop();
                j++;
            }
        }
        if (stack.isEmpty()) {
            return true;
        }
        return false;
    }

}
