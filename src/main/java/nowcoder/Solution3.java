package nowcoder;

/**
 * @author JIE WU
 * @create 2018-05-31
 * @desc Given a singly linked list L: L 0→L 1→…→L n-1→L n
 * reorder it to: L 0→L n →L 1→L n-1→L 2→L n-2→…
 * You must do this in-place without altering the nodes' values.
 * For example, Given{1,2,3,4}, reorder it to{1,4,2,3}.
 **/
public class Solution3 {

    public void reorderList(ListNode head) {
        if (head == null || head.next == null || head.next.next == null) {
            return;
        }
        ListNode last = head;
        ListNode preLast = head;
        while (last.next != null) {
            preLast = last;
            last = last.next;
        }
        ListNode temp = head.next;
        head.next = last;
        last.next = temp;
        preLast.next = null;
        reorderList(temp);
    }

    private class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
            next = null;
        }
    }

    public static void main(String[] args) {
        Solution3 solution3 = new Solution3();
        ListNode listNode1 = solution3.new ListNode(1);
        ListNode listNode2 = solution3.new ListNode(2);
        ListNode listNode3 = solution3.new ListNode(3);
        ListNode listNode4 = solution3.new ListNode(4);
        ListNode listNode5 = solution3.new ListNode(5);
        listNode1.next = listNode2;
        listNode2.next = listNode3;
        listNode3.next = listNode4;
        listNode4.next = listNode5;
        solution3.reorderList(listNode1);
    }
}
