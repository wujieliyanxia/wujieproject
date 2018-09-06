package nowcoder;

/**
 * @author JIE WU
 * @create 2018-05-31
 * @desc Given a linked list, return the node where the cycle begins.
 * If there is no cycle, return null.
 * Follow up: Can you solve it without using extra space?
 **/
public class Solution4 {
    public void detectCycle(ListNode head) {
        head = new ListNode(3);
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
        Solution4 solution4 = new Solution4();
        ListNode head = solution4.new ListNode(5);
        solution4.detectCycle(head);
        System.out.println(head.val);

    }
}
