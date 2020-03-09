import java.util.ArrayList;
import java.util.Stack;

public class LinkedListAL {

    public class ListNode {
        int val;
        ListNode next = null;

        ListNode(int val) {
            this.val = val;
        }
    }

    /**
     * 从尾到头打印链表
     * 思路：头插入或者用递归，栈，从后遍历加入
     * @param listNode
     * @return
     */
    private ArrayList<Integer> list = new ArrayList<>();
    public ArrayList<Integer> printListFromTailToHead(ListNode listNode) {
        if (listNode != null) {
            printListFromTailToHead(listNode.next);
            list.add(listNode.val);
        }
        return list;
    }

    public ArrayList<Integer> printListFromTailToHead2(ListNode listNode) {
        ArrayList<Integer> arrayList = new ArrayList<>();
        while(listNode != null) {
            arrayList.add(0, listNode.val);
            listNode = listNode.next;
        }
        return arrayList;
    }

    /**
     * 链表中的环入口结点
     * @param pHead
     * @return
     */
    public ListNode EntryNodeOfLoop(ListNode pHead)
    {
        ArrayList<ListNode> arrayList = new ArrayList<>();
        ListNode node = null;
        while (pHead != null) {
            if (arrayList.size() != 0 && arrayList.contains(pHead)) {
                node = pHead;
            } else {
                arrayList.add(pHead);
            }
            pHead = pHead.next;
        }
        return node;
    }

    // 1.快慢指针找到相遇结点
    // 2.设置首节点，和相遇结点一步步走，相遇地方就是入口
    // 自己画图推导一下吧，公式为是s=vt, S(fast) = 2S(low)
    public ListNode EntryNodeOfLoop2(ListNode pHead)
    {
        if (pHead == null) {
            return  null;
        }
        ListNode slow = pHead;
        ListNode fast = pHead;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) {
                break;
            }
        }
        if (fast == null || fast.next == null) {
            return null;
        }
        fast = pHead;
        while (slow != fast) {
            fast = fast.next;
            slow = slow.next;
        }
        return fast;
    }

    /**
     * 删除重复结点
     * 思路：就是组建链表，遍历链表找到不重复的组建一下
     * @param pHead
     * @return
     */
    public ListNode deleteDuplication(ListNode pHead)
    {
        if (pHead == null || pHead.next == null) {
            return pHead;
        }

        ListNode newHead = new ListNode(-1);
        ListNode nextIndex = newHead;

        //外层遍历源链表
        ListNode cur = pHead;
        while (cur != null) {
            ListNode nextNode = cur.next;
            // 找到不重复的结点
            boolean hasRepeat = false;
            while (nextNode != null && cur.val == nextNode.val) {
                nextNode = nextNode.next;
                hasRepeat = true;
            }
            if (!hasRepeat) {
               nextIndex.next = cur;
               nextIndex = nextIndex.next;
            }
            cur = nextNode;
        }
        nextIndex.next = null;
        return newHead.next;

    }

    public ListNode deleteDuplication2(ListNode pHead)
    {
        if (pHead == null || pHead.next == null) {
            return pHead;
        }

        if (pHead.val == pHead.next.val) {
            ListNode node = pHead.next;
            while (node != null && pHead.val == node.val) {
                node = node.next;
            }
            return deleteDuplication(node);
        } else {
            pHead.next = deleteDuplication(pHead.next);
        }
        return pHead;
    }


    /**
     * 链表中的倒数第k个结点
     * 思路：就是让一个指针先走k-1步，再让另外一个从头开始走
     * @param head
     * @param k
     * @return
     */
    public ListNode FindKthToTail(ListNode head,int k) {
        ListNode fast = head;
        ListNode low = head;
        int count = 0;
        int a = k;
        while (fast != null) {
            fast = fast.next;
            if (k < 1) {
                low = low.next;
            }
            count++;
            k--;
        }
        return count < a ? null : low;
    }

    /**
     * 倒置链表
     * 思路：考虑最后一个结点是怎么处理指针的，再用递归就好
     * 重点：处理是从最后一个结点的上一个结点开始处理的，所以处理的是传进来的结点，也就是上一个结点
     * @param head
     * @return
     */
    public ListNode ReverseList(ListNode head) {
        if (head == null || head.next == null) {
            return head;        // 这里到尾部结点，返回去，作为新链头结点
        }
        ListNode node = ReverseList(head.next);
        head.next.next = head;      //head是当前结点，这句的意思就是把当前结点的和原来下结点的倒装指针
        head.next = null;           // 这句是把当前结点的后面结点置空
        return node;                // 每层返回的都是遍历到尾部的指针，保持不变，直到最上层
    }

    /**
     * 合并两个排序链表
     * 归并排序
     * 思路就是：把它想成动态规划，当前头结点的链表是由当前两条中最小的当的，链表组成= 当前小的结点 + f（小节点的next，另外一条）
     * @param list1
     * @param list2
     * @return
     */
    public ListNode Merge(ListNode list1, ListNode list2) {
        if (list1 == null) { // 如果一条链已经空了，返回另一条
            return list2;
        }
        if(list2 == null) {
            return list1;
        }

        // 链表向下走，当前层判断大小，分割链
        ListNode head = null;
        if (list1.val < list2.val) {
            head = list1;
            head.next = Merge(list1.next, list2);
        } else {
            head = list2;
            head.next = Merge(list1, list2.next);
        }
        return head;
    }


    public class RandomListNode {
        int label;
        RandomListNode next = null;
        RandomListNode random = null;

        RandomListNode(int label) {
            this.label = label;
        }
    }

    /**
     * 复杂链表的复制
     * 思路： 1.复制同样的结点在原来的后面
     *       2.处理复杂结点
     *       3.分离链表
     * @param pHead
     * @return
     */
    public RandomListNode Clone(RandomListNode pHead)
    {
        if (pHead == null) {
            return null;
        }

        RandomListNode curNode = pHead;
        while (curNode != null) {
            RandomListNode newNode = new RandomListNode(curNode.label);
            RandomListNode curNext = curNode.next;
            curNode.next = newNode;
            newNode.next = curNext;
            curNode = curNext;
        }

        curNode = pHead;
        while(curNode != null) {
            RandomListNode cloneNode = curNode.next;
            cloneNode.random = curNode.random == null ? null : curNode.random.next;
            curNode = cloneNode.next;
        }

        curNode = pHead;
        RandomListNode cloneHead = pHead.next;
        while(curNode != null) {
            RandomListNode cloneNode = curNode.next;
            curNode.next = cloneNode.next;
            cloneNode.next = cloneNode.next == null ? null : cloneNode.next.next;
            curNode = curNode.next;
        }
        return cloneHead;
    }

    /**
     * 找到两条链表的第一个公共结点
     * 思路：用两个栈，或者遍历两遍，一条走到头就遍历另一条
     */
    public ListNode FindFirstCommonNode(ListNode pHead1, ListNode pHead2) {
        Stack<ListNode> stack1 = new Stack<>();
        Stack<ListNode> stack2 = new Stack<>();
        while(pHead1 != null) {
            stack1.push(pHead1);
            pHead1 = pHead1.next;
        }
        while(pHead2 != null) {
            stack2.push(pHead2);
            pHead2 = pHead2.next;
        }
        ListNode curSame = null;
        while (!stack1.isEmpty() && !stack2.isEmpty()) {
            ListNode first = stack1.peek();
            ListNode second = stack2.peek();
            if (first == second) {
                curSame = stack1.pop();
                stack2.pop();
            } else {
                break;
            }
        }
        return curSame;
    }

    // 思路是将两条链表连起来，遍历两遍就知道公共结点在哪里了
    public ListNode FindFirstCommonNode2(ListNode pHead1, ListNode pHead2) {
        if(pHead1 == null || pHead2 == null)return null;
        ListNode p1 = pHead1;
        ListNode p2 = pHead2;
        while(p1!=p2){
            p1 = p1.next;
            p2 = p2.next;
            if(p1 != p2){
                if(p1 == null)p1 = pHead2;
                if(p2 == null)p2 = pHead1;
            }
        }
        return p1;

    }
}
