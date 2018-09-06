package nowcoder;

/**
 * @author JIE WU
 * @create 2018-05-10
 * @desc insertion-sort-list
 **/
public class Solution1 {
    /**
     * 使用插入排序对链表进行排序（从小到大）
     *
     * @param head 链表
     * @return 链表
     */
    public ListNode insertionSortList(ListNode head) {

        if (head == null || head.next == null) {
            return head;
        }
        // 9->8->12->6->18，sortNode为要比较的
        ListNode sortNode = head.next;
        ListNode tempNode = new ListNode(-1);
        // 要替换的前一个tempNode
        ListNode preNode;
        tempNode.next = head;
        // 将head的后面设置为null
        head.next = null;
        while (sortNode != null) {
            ListNode tempNodeCopy = tempNode.next;
            ListNode next = sortNode.next;
            preNode = tempNode;
            while (tempNodeCopy != null && tempNodeCopy.val <= sortNode.val) {
                preNode = preNode.next;
                tempNodeCopy = tempNodeCopy.next;
            }
            preNode.next = sortNode;
            sortNode.next = tempNodeCopy;
            sortNode = next;
        }
        return tempNode.next;
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
        Solution1 solution1 = new Solution1();
        Solution1.ListNode a = solution1.new ListNode(9);
        Solution1.ListNode b = solution1.new ListNode(8);
        Solution1.ListNode c = solution1.new ListNode(12);
        Solution1.ListNode d = solution1.new ListNode(6);
        Solution1.ListNode e = solution1.new ListNode(18);
        Solution1.ListNode f = solution1.new ListNode(5);
        a.next = b;
        b.next = c;
        c.next = d;
        d.next = e;
        e.next = f;
        solution1.insertionSortList(a);
    }
}
