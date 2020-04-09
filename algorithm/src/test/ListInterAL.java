import java.util.ArrayList;
import java.util.List;

public class ListInterAL {

    public static class ListNode {
        int val;
        ListNode next = null;

        ListNode(int val) {
            this.val = val;
        }
    }

    public static void main(String[] args) {
        ListNode head = initLinedList();
        showLinedList(head);

        System.out.print("逆转输出链表：");
        printListFromTailToHead(head);
        System.out.println("\n");

        System.out.print("环的入口结点：");
        ListNode loopHead = initLoopList();
        ListNode enter = EntryNodeOfLoop(loopHead);
        System.out.println(enter.val);
        System.out.println("\n");

        System.out.print("倒数第3个结点");
        ListNode newHead = head;
        ListNode tmp = FindKthToTail(newHead, 3);
        System.out.println(tmp.val);

        System.out.print("逆转");
        tmp = ReverseList(newHead);
        showLinedList(tmp);
        System.out.println("\n");
    }

    private static ListNode initLinedList() {
        ListNode head = new ListNode(0);
        ListNode cur = head;
        for (int i = 1; i < 10; i++) {
            cur.next = new ListNode(i);
            cur = cur.next;
        }
        return head;
    }

    /**
     * 这里4是环结点入口
     * @return
     */
    private static ListNode initLoopList() {
        ListNode head = new ListNode(0);
        ListNode cur = head;
        ListNode loopEnter = null;
        for (int i = 1; i < 10; i++) {
            cur.next = new ListNode(i);
            if (i == 5) {
                loopEnter = cur;
            }
            if (i == 9) {
                cur.next.next = loopEnter;
            } else  {
                cur = cur.next;
            }
        }
        return head;
    }

    private static void showLinedList(ListNode head) {
        ListNode cur = head;
        System.out.print("链表为：");
        while (cur != null) {
            System.out.print(cur.val + ", ");
            cur = cur.next;
        }
        System.out.print("\n");
    }

    /**
     * 遍历
     * @param listNode
     * @return
     */
    public static ListNode printListFromTailToHead(ListNode listNode) {
        if (listNode == null) {
            return null;
        }
        ListNode node = printListFromTailToHead(listNode.next);
        if (node != null) {
            System.out.print(node.val + ", ");
        }
        return listNode;
    }

    /**
     * 结点相关
     */
    public static ListNode EntryNodeOfLoop(ListNode head) {
        ListNode pHead = head;
        if (pHead == null || pHead.next == null) {
            return null;
        }

        ListNode fast = pHead;
        ListNode slow = pHead;
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
            if (fast.val == slow.val) {
                break;
            }
        }
        fast = pHead;
        while (fast != null && fast != slow) {
            fast = fast.next;
            slow = slow.next;
        }
        return fast;
    }

    private static int count = 0;
    public static ListNode FindKthToTail(ListNode head, int k) {
        if (head == null || k < count) {
            return null;
        }
        ListNode node = FindKthToTail(head.next, k);
        if (node != null) {
            count++;
        }
        if (count >= k) {
            return node;
        } else {
            return head;
        }
    }

    public ListNode FindFirstCommonNode2(ListNode pHead1, ListNode pHead2) {
        ListNode head1 = pHead1;
        ListNode head2 = pHead2;
        while (head1 != head2) {
            head1 = head1.next;
            head2 = head2.next;
            if (head1 == null) {
                head1 = pHead2;
            }
            if (head2 == null) {
                head2 = pHead1;
            }
        }
        return head1;
    }

    /**
     * digui
     */
    public static ListNode ReverseList(ListNode head) {
        ListNode tmp = head;
        if (tmp == null || tmp.next == null) {
            return tmp;
        }
        ListNode node = ReverseList(head.next);
        if (node != null) {
            tmp.next.next = tmp;
            tmp.next = null;
        }
        return node;
    }

    public ListNode deleteDuplication2(ListNode pHead) {
        if (pHead == null || pHead.next == null) {
            return pHead;
        }
        ListNode pNext = pHead.next;
        if (pHead.val == pHead.val) {
            while (pNext != null && pNext.val == pHead.val) {
                pNext = pNext.next;
            }
            return deleteDuplication2(pNext);
        } else {
            pHead.next = deleteDuplication2(pHead.next);
            return pHead;
        }
    }

    public ListNode Merge(ListNode list1, ListNode list2) {
        if (list1 == null) {
            return list2;
        }
        if (list2 == null) {
            return list1;
        }
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

    public static ListNode swap(ListNode head) {
        if (head == null || head.next == null) {
            return null;
        }
        ListNode tmp = head.next;
        head.next = tmp.next;
        tmp.next = head;
        head.next = swap(head.next);
        return head;
    }

}
