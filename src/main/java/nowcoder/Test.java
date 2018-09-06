package nowcoder;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.apache.poi.ss.formula.functions.T;

import java.util.*;
import java.util.concurrent.*;

public class Test extends Throwable {
    public Test(boolean a) {
        super("test");
        System.out.println(a);
    }

    public Test() {

    }

    // 查找二维数组中的数
    public static boolean Find(int target, int[][] array) {
        // [1 2 3 4]
        // [2 3 4 5]
        // [3 4 5 6]
        if (array == null) return false;
        for (int j = 0; j < array[0].length; j++) {
            if (array[0][j] > target) return false;
            if (array[array.length - 1][j] < target) continue;
            for (int i = 0; i < array.length; i++) {
                if (array[i][j] == target) return true;
            }
        }
        return false;
    }

    // 替换空格
    public static String replaceSpace(StringBuffer str) {
        // 新字符串
        /*StringBuffer tempStr = new StringBuffer();
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == ' ')
                tempStr.append("%20");
            else
                tempStr.append(str.charAt(i));
        }
        return tempStr.toString();*/
        // 在原来字符串上替换 从后往前遍历
        int spaceNum = 0;
        int oldLen = str.length() - 1;
        for (int i = 0; i <= oldLen; i++) {
            if (str.charAt(i) == ' ') spaceNum++;
        }
        int newLen = oldLen + 2 * spaceNum;
        String replaceStr = "%20";
        str.setLength(newLen + 1);
        for (; oldLen >= 0 && newLen > oldLen; oldLen--) {
            if (str.charAt(oldLen) != ' ') {
                str.setCharAt(newLen, str.charAt(oldLen));
                newLen--;
            } else {
                for (int i = replaceStr.length() - 1; i >= 0; i--) {
                    str.setCharAt(newLen, replaceStr.charAt(i));
                    newLen--;
                }
            }
        }
        return str.toString();
    }

    // 输入一个链表，按链表值从尾到头的顺序返回一个ArrayList
    public ArrayList<Integer> printListFromTailToHead(ListNode listNode) {
        /*ArrayList<Integer> HeadArray = new ArrayList<>();
        ArrayList<Integer> TailArray = new ArrayList<>();
        if (listNode == null) return TailArray;
        // 遍历链表,从头到尾放入HeadArray中
        while (listNode != null) {
            HeadArray.add(listNode.val);
            listNode = listNode.next;
        }
        // 从尾到头遍历HeadArray 放入TailArray
        for (int i = HeadArray.size() - 1; i >= 0; i--) {
            TailArray.add(HeadArray.get(i));
        }
        return TailArray;*/

        // 可以用栈 先进后出
        // 递归的本质是栈 ，可以用递归实现
        if (listNode == null) {
            ArrayList<Integer> TailArray = new ArrayList<>();
            return TailArray;
        }
        ArrayList<Integer> TailArray = printListFromTailToHead(listNode.next);
        TailArray.add(listNode.val);
        return TailArray;

    }

    // 输入某二叉树的前序遍历和中序遍历的结果，请重建出该二叉树
    public TreeNode reConstructBinaryTree(int[] pre, int[] in) {
        if (pre.length == 0) return null;
        int rootVal = pre[0];
        TreeNode root = new TreeNode(rootVal);
        TreeNode left = null;
        TreeNode right = null;
        // 查询根节点所在中序遍历数组中的位置
        int rootIndex = -1;
        for (int i = 0; i < in.length; i++) {
            if (rootVal == in[i]) {
                rootIndex = i;
                break;
            }
        }
        int leftLen = rootIndex;
        int rightLen = in.length - 1 - leftLen;
        // 递归左边
        if (leftLen > 0) {
            int[] preLeft = new int[leftLen];
            System.arraycopy(pre, 1, preLeft, 0, leftLen);
            int[] inLeft = new int[leftLen];
            System.arraycopy(in, 0, inLeft, 0, leftLen);
            left = reConstructBinaryTree(preLeft, inLeft);
        }
        // 递归右边
        if (rightLen > 0) {
            int[] preRight = new int[rightLen];
            System.arraycopy(pre, leftLen + 1, preRight, 0, rightLen);
            int[] inRight = new int[rightLen];
            System.arraycopy(in, leftLen + 1, inRight, 0, rightLen);
            right = reConstructBinaryTree(preRight, inRight);
        }
        root.left = left;
        root.right = right;
        return root;
    }

    // 用两个栈来实现一个队列，完成队列的Push和Pop操作
    Stack<Integer> stack1 = new Stack<Integer>();
    Stack<Integer> stack2 = new Stack<Integer>();

    public void push(int node) {
        // 在将元素放入stack1之前 将stack1的元素都放入stack2中
        while (!stack1.empty()) {
            Integer pop = stack1.pop();
            stack2.push(pop);
        }

        // 将元素放入stack1中
        stack1.push(node);

        // 将stack2中的元素移动回stack1
        while (!stack2.empty()) {
            Integer pop = stack2.pop();
            stack1.push(pop);
        }
    }

    public int pop() {
        return stack1.pop();
    }


    // 快速排序
    // 归并排序
    // 冒泡排序
    // 插入排序 这四个排序必须掌握，并烂熟于胸

    // 快速排序 主要思想 选中一个数 将比它小的放左边，比它大的放右边
    public static void quickSort(int[] array, int start, int end) {
        if (array == null || array.length == 0 || start < 0 || (end - start) < 0) return;
        // 前后指针 假设start=0则slow=-1
        int slow = start - 1;
        int fast = start;
        // ++fast 先走一步
        while (fast < end) {
            if (array[fast] < array[end] && ++slow != fast) {
                swap(array, fast, slow);
            }
            fast++;
        }
        swap(array, ++slow, end);
        quickSort(array, 0, slow - 1);
        quickSort(array, slow + 1, end);
    }

    // 归并排序
    public static void mergeSort(int[] array, int start, int end) {
        if (array == null || array.length <= 1 || start < 0 || (end - start) < 1) return;
        int mid = (end - start) / 2 + start;
        mergeSort(array, start, mid);
        mergeSort(array, mid + 1, end);
        // merge合并
        merge(array, start, mid, end);
    }

    // merge合并
    public static void merge(int[] array, int start, int mid, int end) {
        int len = end - start + 1;
        int temp[] = new int[len];
        int leftStart = start;
        int rightStart = mid + 1;
        for (int i = 0; i < temp.length; i++) {
            if (leftStart > mid) {
                temp[i] = array[rightStart];
                rightStart++;
                continue;
            }
            if (rightStart > end) {
                temp[i] = array[leftStart];
                leftStart++;
                continue;
            }
            if (array[leftStart] < array[rightStart]) {
                temp[i] = array[leftStart];
                leftStart++;
            } else {
                temp[i] = array[rightStart];
                rightStart++;
            }
        }
        System.arraycopy(temp, 0, array, start, temp.length);
    }

    public static void swap(int[] array, int a, int b) {
        int temp = array[a];
        array[a] = array[b];
        array[b] = temp;
    }


    // 青蛙跳台阶
    //一只青蛙一次可以跳上1级台阶，也可以跳上2级。求该青蛙跳上一个n级的台阶总共有多少种跳法
    public static int JumpFloor(int target) {
        if (target < 3) return target;
        int firstNum = 2;
        int secondNum = 1;
        int returnNum = 0;
        for (int i = 3; i <= target; i++) {
            returnNum = firstNum + secondNum;
            secondNum = firstNum;
            firstNum = returnNum;
        }
        return returnNum;
    }


    // 一只青蛙一次可以跳上1级台阶，也可以跳上2级……它也可以跳上n级。求该青蛙跳上一个n级的台阶总共有多少种跳法。
    public int JumpFloorII(int target) {

        // 每个台阶都有跳与不跳两种情况（除了最后一个台阶），最后一个台阶必须跳。所以共用2^(n-1)中情况
        int a = 1;
        return a << (target - 1);

    }

    // 我们可以用2*1的小矩形横着或者竖着去覆盖更大的矩形。请问用n个2*1的小矩形无重叠地覆盖一个2*n的大矩形，总共有多少种方法？
    public int RectCover(int target) {
        // 假设有f(n)种放法
        // 当横着放的时候 ，下面必须也要放一个 这时有f(n-2)种放法
        // 当竖着放的时候，有f(n-1)种方法。
        if (target < 3) return target;
        int returnNum = 0;
        int firstNum = 2;
        int secondNum = 1;
        for (int i = 3; i < target; i++) {
            returnNum = firstNum + secondNum;
            secondNum = firstNum;
            firstNum = returnNum;
        }
        return returnNum;
    }

    // 输入一个整数数组，实现一个函数来调整该数组中数字的顺序，使得所有的奇数位于数组的前半部分
    // 所有的偶数位于位于数组的后半部分，并保证奇数和奇数，偶数和偶数之间的相对位置不变
    public void reOrderArray(int[] array) {
        if (array == null || array.length < 2) return;
        int len = array.length;
        for (int i = 0; i < len; i++) {
            // 找到第一个奇数
            if (array[i] % 2 == 0) continue;
            // 将奇数一直和偶数交换，直到遇到奇数
            int temp = i;
            for (int j = i - 1; j >= 0; j--) {
                if (array[j] % 2 != 0) break;
                swap(array, temp, j);
                temp--;
            }
        }
    }

    //输入一个链表，输出该链表中倒数第k个结点。
    public ListNode FindKthToTail(ListNode head, int k) {
        if (k <= 0) return null;
        // 快指针先走k-1步
        int i = 0;
        ListNode preNode = head;
        while (i < k - 1 && preNode != null) {
            i++;
            preNode = preNode.next;
        }
        if (preNode == null) return null;
        // 慢指针和快指针同时走，当快指针走到最后一个节点的时候，return
        ListNode slowNode = head;
        while (preNode.next != null) {
            preNode = preNode.next;
            slowNode = slowNode.next;
        }
        return slowNode;
    }

    // 输入一个链表，反转链表后，输出新链表的表头。
    public ListNode ReverseList(ListNode head) {
        if (head == null || head.next == null) return head;
        Stack<ListNode> stack = new Stack<>();
        // 将链表放入栈中
        while (head != null) {
            stack.push(head);
            head = head.next;
        }
        // 返回栈顶元素
        ListNode returnNode = stack.pop();
        ListNode tempNode = returnNode;
        while (!stack.empty()) {
            ListNode stackNode = stack.pop();
            stackNode.next = null;
            tempNode.next = stackNode;
            tempNode = stackNode;
        }
        return returnNode;
    }

    //输入一个矩阵，按照从外向里以顺时针的顺序依次打印出每一个数字，
    // 例如，如果输入如下4 X 4矩阵： 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16
    // 则依次打印出数字1,2,3,4,8,12,16,15,14,13,9,5,6,7,11,10.
    public static ArrayList<Integer> printMatrix(int[][] matrix) {
        ArrayList<Integer> returnList = new ArrayList<>();
        if (matrix.length == 0) return returnList;
        int cellLen = matrix[0].length;
        int rowLen = matrix.length;
        int loopCount = Math.min(cellLen, rowLen) / 2 + Math.min(cellLen, rowLen) % 2;
        int i = 0;
        while (i < loopCount) {
            int cellNum = i;
            int rowNum = i;
            returnList.add(matrix[rowNum][cellNum]);
            cellNum++;
            while (cellNum <= cellLen - i - 1) {
                returnList.add(matrix[rowNum][cellNum]);
                cellNum++;
            }
            cellNum--;
            rowNum++;
            while (rowNum <= rowLen - i - 1) {
                returnList.add(matrix[rowNum][cellNum]);
                rowNum++;
            }
            cellNum--;
            rowNum--;
            if (cellNum < i || rowNum < i) break;
            while (cellNum >= i && rowNum != i) {
                returnList.add(matrix[rowNum][cellNum]);
                cellNum--;
            }
            cellNum++;
            rowNum--;
            while (rowNum > i) {
                returnList.add(matrix[rowNum][cellNum]);
                rowNum--;
            }
            i++;
        }
        return returnList;
    }

    // 输入两个整数序列，第一个序列表示栈的压入顺序，
    // 请判断第二个序列是否可能为该栈的弹出顺序。
    // 假设压入栈的所有数字均不相等。例如序列1,2,3,4,5是某栈的压入顺序，
    // 序列4,5,3,2,1是该压栈序列对应的一个弹出序列，
    // 但4,3,5,1,2就不可能是该压栈序列的弹出序列。（注意：这两个序列的长度是相等的）
    public static boolean IsPopOrder(int[] pushA, int[] popA) {
        if (pushA == null || popA == null || pushA.length == 0 || popA.length == 0) return false;
        Stack<Integer> stack = new Stack<>();
        int i = 0;
        int j = 0;
        while (i < pushA.length) {
            stack.push(pushA[i]);
            while (!stack.isEmpty() && stack.peek() == popA[j]) {
                j++;
                stack.pop();
            }
            i++;
        }
        return stack.isEmpty();
    }

    // 从上往下打印出二叉树的每个节点，同层节点从左至右打印。
    public static ArrayList<Integer> PrintFromTopToBottom(TreeNode root) {
        // 队列
        ArrayDeque<TreeNode> deque = new ArrayDeque<>();
        // 返回列表
        ArrayList<Integer> returnArray = new ArrayList<>();
        if (root == null) return returnArray;
        // 取出root，将它的子节点放入deque中。然后从队列中取数据，循环如此
        deque.add(root);
        while (!deque.isEmpty()) {
            root = deque.poll();
            returnArray.add(root.val);
            if (root.left != null) deque.add(root.left);
            if (root.right != null) deque.add(root.right);
        }
        return returnArray;
    }

    // 输入一个整数数组，判断该数组是不是某二叉搜索树的后序遍历的结果。如果是则输出Yes,否则输出No。假设输入的数组的任意两个数字都互不相同。
    public static boolean VerifySquenceOfBST(int[] sequence) {
        // 二叉搜索树的左边的值小于根节点。右边的值大于根节点
        // 根节点在最后一个
        if (sequence == null || sequence.length <= 0) return false;
        return VerifySquenceOfBST(sequence, 0, sequence.length - 1);
    }

    public static boolean VerifySquenceOfBST(int[] sequence, int start, int end) {
        if (end == start) return true;
        int rootVal = sequence[end];
        int i = 0;
        // 找出小于rootVal的左边
        for (; i < end; ++i) {
            if (sequence[i] > rootVal) break;
        }
        int j = i;
        // 判断右边的有没有小于根节点的，有返回false；
        for (; j < end; j++) {
            if (sequence[j] < rootVal) return false;
        }

        // 递归左边和右边
        boolean verifyLeft = true;
        boolean verifyRight = true;
        if (i - 1 > start) verifyLeft = VerifySquenceOfBST(sequence, start, i - 1);
        if (i < end - 1) verifyRight = VerifySquenceOfBST(sequence, i, end - 1);
        return verifyLeft && verifyRight;
    }

    // 输入一棵二叉搜索树，将该二叉搜索树转换成一个排序的双向链表。
    // 要求不能创建任何新的结点，只能调整树中结点指针的指向。
    public static TreeNode Convert(TreeNode pRootOfTree) {
        if (pRootOfTree == null) return null;
        TreeNode pre = null;
        convertHelper(pRootOfTree, pre);
        TreeNode cur = pRootOfTree;
        while (cur.left != null) {
            cur = cur.left;
        }
        return cur;
    }

    public static TreeNode convertHelper(TreeNode cur, TreeNode pre) {
        if (cur == null) return pre;
        TreeNode treeNode = convertHelper(cur.left, pre);
        pre = treeNode;
        cur.left = pre;
        if (pre != null) pre.right = cur;
        pre = cur;
        return convertHelper(cur.right, pre);
    }

    //输入一个字符串,按字典序打印出该字符串中字符的所有排列。
    // 例如输入字符串abc,则打印出由字符a,b,c所能排列出来的所有字符串abc,acb,bac,bca,cab和cba
    public ArrayList<String> Permutation(String str) {
        List<String> res = new ArrayList<>();
        if (str != null && str.length() > 0) {
            Permutation(str.toCharArray(), 0, res);
            Collections.sort(res);
        }
        return (ArrayList) res;
    }

    public static void Permutation(char[] charArray, int begin, List<String> list) {
        if (begin == charArray.length - 1) {
            String a = String.valueOf(charArray);
            if (!list.contains(a)) list.add(a);
            return;
        }
        for (int i = begin; i < charArray.length; i++) {
            swap(charArray, i, begin);
            Permutation(charArray, begin + 1, list);
            swap(charArray, i, begin);
        }
    }

    public static void swap(char[] cs, int i, int j) {
        char temp = cs[i];
        cs[i] = cs[j];
        cs[j] = temp;
    }

    //数组中有一个数字出现的次数超过数组长度的一半，请找出这个数字。
    // 例如输入一个长度为9的数组{1,2,3,2,2,2,5,4,2}。
    // 由于数字2在数组中出现了5次，超过数组长度的一半，因此输出2。如果不存在则输出0。
    public int MoreThanHalfNum_Solution(int[] array) {
        if (array == null || array.length == 0) return 0;
        int len = array.length;
        Set<Integer> notHalfNum = new HashSet<>();// 存储不合格的数字
        for (int i = 0; i < len; i++) {
            int numLen = 0;
            if (!notHalfNum.contains(array[i])) {
                for (int j = i; j < len; j++) {
                    if (array[i] == array[j]) numLen++;
                }
            }
            if (numLen > len / 2) return array[i];
            notHalfNum.add(array[i]);
        }
        return 0;
    }

    // 输入n个整数，找出其中最小的K个数。例如输入4,5,1,6,2,7,3,8这8个数字，则最小的4个数字是1,2,3,4,
    public ArrayList<Integer> GetLeastNumbers_Solution(int[] input, int k) {
        ArrayList<Integer> returnArray = new ArrayList<>();
        if (input == null || input.length == 0) return returnArray;
        if (k >= input.length) {
            for (int i : input) returnArray.add(i);
            return returnArray;
        }
        for (int i = 0; i < k; i++) {
            for (int j = i; j < input.length; j++) {
                if (input[i] > input[j]) swap(input, i, j);
            }
        }

        for (int i = 0; i < k; i++) {
            returnArray.add(input[i]);
        }
        return returnArray;
    }


    // HZ偶尔会拿些专业问题来忽悠那些非计算机专业的同学。
    // 今天测试组开完会后,他又发话了:在古老的一维模式识别中,常常需要计算连续子向量的最大和,当向量全为正数的时候,问题很好解决。
    // 但是,如果向量中包含负数,是否应该包含某个负数,并期望旁边的正数会弥补它呢？
    // 例如:{6,-3,-2,7,-15,1,2,2},连续子向量的最大和为8(从第0个开始,到第3个为止)。
    // 给一个数组，返回它的最大连续子序列的和，你会不会被他忽悠住？(子向量的长度至少是1)
    public static int FindGreatestSumOfSubArray(int[] array) {

        int res = array[0]; //记录当前所有子数组的和的最大值
        int max = array[0];//包含array[i]的连续数组最大值
        for (int i = 1; i < array.length; i++) {
            max = Math.max(max + array[i], array[i]);
            res = Math.max(max, res);
        }
        return res;
    }

    // 求出1~13的整数中1出现的次数,并算出100~1300的整数中1出现的次数？
    // 为此他特别数了一下1~13中包含1的数字有1、10、11、12、13因此共出现6次,但是对于后面问题他就没辙了。
    // ACMer希望你们帮帮他,并把问题更加普遍化,可以很快的求出任意非负整数区间中1出现的次数（从1 到 n 中1出现的次数）

    public static int NumberOf1Between1AndN_Solution(int n) {
        int oneNum = 0;// 出现次数总数
        for (int i = 0; i <= n; i++) {
            int temp = i;
            while (temp > 0) {
                int g = temp % 10;
                if (g == 1) oneNum++;
                temp = temp / 10;
            }
        }
        return oneNum;
    }


    public static String PrintMinNumber(int[] numbers) {
        StringBuffer sb = new StringBuffer();
        for (int i : numbers) sb.append(i);
        long firstNum = Long.valueOf(sb.toString());
        long minNum = getMinNum(numbers, 0, firstNum);
        return String.valueOf(minNum);
    }

    // 此方法为求出所有组合
    public static long getMinNum(int[] array, int start, long minNum) {
        if (start >= array.length - 1) {
            StringBuffer sb = new StringBuffer();
            for (int i : array) sb.append(i);
            minNum = Math.min(minNum, Long.valueOf(sb.toString()));
            return minNum;
        }
        for (int i = start; i < array.length; i++) {
            swap(array, i, start);
            minNum = getMinNum(array, start + 1, minNum);
            swap(array, i, start);
        }
        return minNum;
    }

    // 把只包含质因子2、3和5的数称作丑数（Ugly Number）。
    // 例如6、8都是丑数，但14不是，因为它包含质因子7。 习惯上我们把1当做是第一个丑数。
    // 求按从小到大的顺序的第N个丑数。
    public static int GetUglyNumber_Solution(int n) {
        if (n <= 0) return 0;
        ArrayList<Integer> list = new ArrayList<Integer>();
        list.add(1);
        int i2 = 0, i3 = 0, i5 = 0;
        while (list.size() < n)//循环的条件
        {
            int m2 = list.get(i2) * 2;
            int m3 = list.get(i3) * 3;
            int m5 = list.get(i5) * 5;
            int min = Math.min(m2, Math.min(m3, m5));
            list.add(min);
            if (min == m2) i2++;
            if (min == m3) i3++;
            if (min == m5) i5++;
        }
        return list.get(list.size() - 1);
    }

    // 在一个字符串(0<=字符串长度<=10000，全部由字母组成)中找到第一个只出现一次的字符,并返回它的位置, 如果没有则返回 -1（需要区分大小写）.
    public static int FirstNotRepeatingChar(String str) {
       /* if (str == null || str.length() == 0) return -1;
        String tempStr = str;// 存放str
        char replaceChar = ' ';
        while (str.length() > 0) {
            replaceChar = str.charAt(0);
            int oldLen = str.length();
            if (str.length() == 1) {
                break;
            }
            str = str.replaceAll(replaceChar + "", "");
            int newLen = str.length();
            if (oldLen - newLen == 1) {
                break;
            }
        }
        return str.length() > 0 ? tempStr.indexOf(replaceChar) : -1;*/


        char[] chars = str.toCharArray();
        int[] map = new int[256];
        for (int i = 0; i < chars.length; i++) {
            map[chars[i]]++;
        }
        for (int i = 0; i < chars.length; i++) {
            if (map[chars[i]] == 1) return i;
        }
        return -1;
    }


    // 在数组中的两个数字，如果前面一个数字大于后面的数字，则这两个数字组成一个逆序对。
    // 输入一个数组,求出这个数组中的逆序对的总数P。
    // 并将P对1000000007取模的结果输出。 即输出P%1000000007
    public static int InversePairs(int[] array) {
        return InversePairs(array, 0, array.length - 1);
    }

    public static int InversePairs(int[] array, int start, int end) {
        if (array == null || end - start <= 0) return 0;
        int mid = (end - start) / 2;
        int i = InversePairs(array, start, start + mid);// 左边的数组中逆序的个数
        int i1 = InversePairs(array, start + mid + 1, end);// 右边的数组中逆序的个数
        int i2 = mergeAndGetNum(array, start, start + mid, end);// 合并时候，将左右数组中的逆序算出来
        return i + i1 + i2;
    }

    public static int mergeAndGetNum(int[] array, int start, int mid, int end) {
        int[] temp = new int[end - start + 1];// 临时数组
        int left = start;
        int right = mid + 1;
        int tempIndex = 0;
        int returnNum = 0;
        // 合并,从小到大排序
        while (tempIndex < temp.length) {
            if (left > mid) {
                temp[tempIndex] = array[right];
                right++;
            } else if (right > end) {
                temp[tempIndex] = array[left];
                left++;
            } else if (array[left] > array[right]) {
                // 左边大于右边
                int inverse = mid - left + 1;
                returnNum += inverse;
                temp[tempIndex] = array[right];
                right++;
            } else if (array[left] < array[right]) {
                temp[tempIndex] = array[left];
                left++;
            }
            tempIndex++;
        }

        // 将temp数组复制到目标数组
        System.arraycopy(temp, 0, array, start, temp.length);
        return returnNum;
    }


    // 统计一个数字在排序数组中出现的次数
    public int GetNumberOfK(int[] array, int k) {
        if (array == null || array.length == 0) return 0;
        int returnNum = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i] == k) returnNum++;
        }
        return returnNum;

    }

    //输入一棵二叉树，求该树的深度。从根结点到叶结点依次经过的结点（含根、叶结点）形成树的一条路径，最长路径的长度为树的深度。
    public int TreeDepth(TreeNode root) {
        if (root == null) return 0;
        // 最长路径等于左边的最长路径和右边的最长路径中较长的那个+1
        int leftLen = TreeDepth(root.left) + 1;
        int rightLen = TreeDepth(root.right) + 1;
        return Math.max(leftLen, rightLen) + 1;
    }

    // 输入一棵二叉树，判断该二叉树是否是平衡二叉树
    public boolean IsBalanced_Solution(TreeNode root) {
        return getDepth(root) != -1;
    }

    private int getDepth(TreeNode root) {
        if (root == null) return 0;
        int left = getDepth(root.left);
        if (left == -1) return -1;
        int right = getDepth(root.right);
        if (right == -1) return -1;
        return Math.abs(left - right) > 1 ? -1 : 1 + Math.max(left, right);
    }


    // 输出所有和为S的连续正数序列。序列内按照从小至大的顺序，序列间按照开始数字从小到大的顺序
    public static ArrayList<ArrayList<Integer>> FindContinuousSequence(int sum) {
        ArrayList<ArrayList<Integer>> returnList = new ArrayList<>();
        int small = 1;
        int big = 2;
        int sumNum = small + big;
        while (small <= sum) {
            if (sumNum == sum) {
                ArrayList<Integer> a = new ArrayList<>();
                for (int i = small; i <= big; i++) {
                    a.add(i);
                }
                returnList.add(a);
                small++;
                big = small + 1;
                sumNum = small + big;
                continue;
            }
            if (sumNum < sum) {
                big++;
            } else {
                small++;
            }
            sumNum = getSum(small, big);
        }
        return returnList;
    }

    public static int getSum(int start, int end) {
        int len = end - start;
        int i = 0;
        int sum = 0;
        while (i <= len) {
            sum += start;
            start++;
            i++;
        }
        return sum;
    }

    // 输入一个递增排序的数组和一个数字S，在数组中查找两个数，使得他们的和正好是S，如果有多对数字的和等于S，输出两个数的乘积最小的。
    public static ArrayList<Integer> FindNumbersWithSum(int[] array, int sum) {
        ArrayList<Integer> returnList = new ArrayList<>();
        if (array == null || array.length < 2) return returnList;
        int returnNum = Integer.MAX_VALUE;
        int smallIndex = 0;// 小的数的下标
        int bigIndex = array.length - 1;// 大的数的下表
        int sumNum = array[smallIndex] + array[bigIndex];// 总数
        while (smallIndex < bigIndex) {
            if (sumNum == sum) {
                if (sumNum < returnNum) {
                    returnList.add(array[smallIndex]);
                    returnList.add(array[bigIndex]);
                    returnNum = sumNum;
                }
                smallIndex++;
                bigIndex--;
            } else if (sumNum < sum) smallIndex++;
            else if (sumNum > sum) bigIndex--;
            sumNum = array[smallIndex] + array[bigIndex];
        }
        return returnList;
    }


    public static String LeftRotateString(String str, int n) {
        if (str == null || str.length() == 0 || n == 0 || n % str.length() == 0) return str;
        if (n > str.length()) n = n % str.length();
        String str1 = str.substring(n, str.length());
        String str2 = str.substring(0, n);
        return str1 + str2;
    }

    // 反转单词
    public static String ReverseSentence(String str) {
        if (str == null || str.length() <= 1) return str;
        char[] chars = str.toCharArray();
        char[] tempChar = new char[chars.length];
        int tempIndex = 0;
        int j = chars.length - 1;
        while (j >= 0) {
            if (chars[j] == ' ') {
                tempChar[tempIndex++] = chars[j];
                j--;
            } else if (chars[j] != ' ') {
                int notBlankIndex = j;
                // 找到空为止
                while (j >= 0 && chars[j] != ' ') j--;
                int startIndex = j + 1;
                // 反方向复制
                while (startIndex <= notBlankIndex) tempChar[tempIndex++] = chars[startIndex++];
            }
        }
        return String.valueOf(tempChar);
    }

    // 是否为顺子 0可当任意数
    public static boolean isContinuous(int[] numbers) {
        if (numbers == null || numbers.length == 0) return false;
        // 找出数组中所有的0,并将0放在最前面
        int zeroLen = 0;
        for (int i = 0; i < numbers.length; i++) {
            if (numbers[i] == 0) swap(numbers, zeroLen++, i);
        }

        // 对0后面的数字进行排序。这使用快速排序
        quickSort1(numbers, zeroLen, numbers.length - 1);
        int temp = zeroLen;
        while (temp < numbers.length - 1) {
            int pre = numbers[temp];
            int now = numbers[temp + 1];
            if (now - pre != 1) {
                if (now - pre == 0) return false;
                zeroLen = zeroLen - (now - pre - 1);
                if (zeroLen < 0) return false;
            }
            temp++;
        }
        return true;
    }

    // 快排
    public static void quickSort1(int[] array, int start, int end) {
        if (start < 0 || end - start <= 0) return;
        int pre = start;
        int slow = start - 1;
        while (pre < end) {
            if (array[pre] < array[end] && ++slow != pre) swap(array, pre, slow);
            pre++;
        }
        swap(array, ++slow, end);
        quickSort1(array, start, slow - 1);
        quickSort1(array, slow + 1, end);
    }

    // 最小栈的实现
    static class StackForMin {
        // 存放元素
        private final static Stack<Integer> stack1 = new Stack<>();
        // 存放最小元素
        private final static Stack<Integer> stack2 = new Stack<>();

        public void push(int node) {
            if (stack2.isEmpty()) stack2.push(node);
            else if (stack2.peek() >= node) stack2.push(node);
            stack1.push(node);
        }

        public void pop() {
            if (stack1.peek() == stack2.peek()) stack2.pop();
            stack1.pop();
        }

        public int top() {
            return stack1.peek();
        }

        public int min() {
            return stack2.peek();
        }

    }

    // 探测链表中是否有环
    public static boolean hasRing(ListNode root) {
        if (root == null) return false;
        ListNode slow = root;// 慢指针
        ListNode fast = root;// 快指针
        while (fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
            if (fast == slow) return true;
        }
        return false;
    }

    // 用信号量实现生产者消费者模式
    static class SemaphoreMessageQueue<T> {
        // 消息队列
        private final Queue<T> messageQueue = new ConcurrentLinkedQueue<T>();
        private final Semaphore semaphore;
        private final Semaphore NotEmptySemaphore = new Semaphore(0);

        public SemaphoreMessageQueue(int i) {
            semaphore = new Semaphore(i);
        }

        public boolean send(T t) throws InterruptedException {
            semaphore.acquire();
            boolean wasAdded = false;
            try {
                wasAdded = messageQueue.add(t);
                return wasAdded;
            } finally {
                if (!wasAdded) {
                    // 失败 释放
                    semaphore.release();
                } else {
                    // 成功 设置不为空
                    NotEmptySemaphore.release();
                }
            }
        }

        public T poll() throws InterruptedException {
            NotEmptySemaphore.acquire();
            T t = null;
            try {
                t = messageQueue.poll();
                return t;
            } finally {
                if (t != null) {
                    semaphore.release();
                } else {
                    // 失败的话
                    NotEmptySemaphore.release();
                }
            }

        }
    }

    // 闭锁 CountDownLatch
    static class TestHarness {
        public long timeTasks(int nThread, final Runnable task) throws InterruptedException {
            CountDownLatch startGate = new CountDownLatch(1);
            CountDownLatch endGate = new CountDownLatch(20);
            for (int i = 0; i < nThread; i++) {
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            startGate.await();
                            try {
                                task.run();
                            } finally {
                                endGate.countDown();
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });
                thread.start();
            }
            long start = System.nanoTime();
            startGate.countDown();
            endGate.await();
            long end = System.nanoTime();
            return end - start;
        }
    }

    // 栅栏 五个线程都到达终点才能喝啤酒
    static class Beer {
        public static void main(String[] args) {
            int threadNum = 5;
            final CyclicBarrier cyclicBarrier = new CyclicBarrier(threadNum, new Runnable() {
                @Override
                public void run() {
                    System.out.println("喝啤酒");
                }
            });
            for (int i = 0; i < threadNum; i++) {
                Thread thread = new Thread(new Work(i, cyclicBarrier));
                thread.start();
            }
        }

        static class Work implements Runnable {
            final int id;
            final CyclicBarrier cyclicBarrier;

            public Work(int id, CyclicBarrier cyclicBarrier) {
                this.id = id;
                this.cyclicBarrier = cyclicBarrier;
            }

            @Override
            public void run() {
                System.out.println("thread" + id + "开始跑");
                try {
                    Thread.sleep(1000);
                    System.out.println("thread" + id + "跑到终点了");
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // 将字符串转成整数
    public static int StrToInt(String str) {
        if (str == null) return 0;
        char[] temp = str.toCharArray();
        int returnNum = 0;
        int x = 1;
        if (temp[0] == '+') {
            temp[0] = '0';
        } else if (temp[0] == '-') {
            temp[0] = '0';
            x = -1;
        }
        for (int i = 0; i < temp.length; i++) {
            if (temp[i] > 57 || temp[i] < 48) return 0;
            returnNum = returnNum * 10 + temp[i] - 48;
        }
        return returnNum * x;
    }

    public boolean duplicate(int numbers[], int length, int[] duplication) {
        Map<Integer, Boolean> numMap = new HashMap<>();
        for (int i = 0; i < numbers.length; i++) {
            if (numMap.get(numbers[i]) != null) {
                duplication[0] = numbers[i];
                return true;
            }
            numMap.put(numbers[i], true);
        }
        return false;
    }


    public static int[] multiply(int[] A) {
        int len = A.length;
        int[] lefts = new int[len];
        int[] rights = new int[len];
        lefts[0] = lefts[len - 1] = rights[0] = rights[len - 1] = 1;
        int l = 1;
        int r = 1;
        for (int i = 1; i < len; i++) {
            lefts[i] = A[i - 1] * lefts[i - 1];
        }

        for (int i = len - 2; i >= 0; i--) {
            rights[i] = A[i + 1] * rights[i + 1];
        }
        int[] B = new int[len];
        while (len > 0) {
            len--;
            B[len] = lefts[len] * rights[len];
        }
        return B;
    }

    // 删除排序链表中的相同节点
    public static ListNode deleteDuplication(ListNode pHead) {
        if (pHead == null || pHead.next == null) return pHead;
        ListNode yNode = new ListNode(0);// 临时node
        yNode.next = pHead;
        ListNode pre = yNode;// 前一个节点
        ListNode temp = pHead;// 遍历节点
        while (temp != null && temp.next != null) {
            int tempVal = temp.val;
            if (tempVal != temp.next.val) {
                temp = temp.next;
                pre = pre.next;
            } else {
                temp = temp.next;
                while (temp != null && temp.val == tempVal) {
                    temp = temp.next;
                }
                pre.next = temp;
            }
        }
        return yNode.next;
    }

    //给定一个二叉树和其中的一个结点，请找出中序遍历顺序的下一个结点并且返回。注意，树中的结点不仅包含左右子结点，同时包含指向父结点的指针。
    public TreeLinkNode GetNext(TreeLinkNode pNode) {
        if (pNode == null) return null;
        // 1、判断是否有右子节点
        if (pNode.right != null) {
            pNode = pNode.right;
            while (pNode != null && pNode.left != null) {
                pNode = pNode.left;
            }
            return pNode;
        }
        // 判断是否有父节点
        if (pNode.next == null) return null;
        // 判断是否是父节点的左节点
        while (pNode.next != null) {
            if (pNode.next.left == pNode) return pNode.next;
            pNode = pNode.next;
        }
        return null;
    }

    public class TreeLinkNode {
        int val;
        TreeLinkNode left = null;
        TreeLinkNode right = null;
        TreeLinkNode next = null;

        TreeLinkNode(int val) {
            this.val = val;
        }
    }

    // z字形
    public ArrayList<ArrayList<Integer>> Print(TreeNode pRoot) {
        ArrayList<ArrayList<Integer>> returnList = new ArrayList<>();
        if (pRoot == null) return returnList;
        // 创建一个双向队列
        Deque<TreeNode> mDeque = new ArrayDeque<>();
        mDeque.add(pRoot);
        int i = 1;
        while (!mDeque.isEmpty()) {
            ArrayList<Integer> flowList = new ArrayList<>();
            int loop = mDeque.size();
            int temp = 0;
            if (i % 2 == 1) {
                // 从左到右。从first开始取
                while (temp < loop) {
                    TreeNode node = mDeque.pollFirst();
                    if (node.left != null) mDeque.add(node.left);
                    if (node.right != null) mDeque.add(node.right);
                    flowList.add(node.val);
                    temp++;
                }
            } else {
                // 从右到左。从last开始取
                while (temp < loop) {
                    TreeNode node = mDeque.pollLast();
                    if (node.right != null) mDeque.addFirst(node.right);
                    if (node.left != null) mDeque.addFirst(node.left);
                    flowList.add(node.val);
                    temp++;
                }
            }
            returnList.add(flowList);
            i++;
        }
        return returnList;
    }


    // 从上到下按层打印二叉树，同一层结点从左至右输出。每一层输出一行。
    ArrayList<ArrayList<Integer>> Print1(TreeNode pRoot) {
        ArrayList<ArrayList<Integer>> returnList = new ArrayList<>();
        if (pRoot == null) return returnList;
        // 创建一个队列
        Queue<TreeNode> nodeQueue = new LinkedList<>();
        nodeQueue.add(pRoot);
        while (!nodeQueue.isEmpty()) {
            ArrayList<Integer> flowList = new ArrayList<>();
            int i = 0;
            int loop = nodeQueue.size();
            while (i < loop) {
                TreeNode node = nodeQueue.poll();
                if (node.left != null) nodeQueue.add(node.left);
                if (node.right != null) nodeQueue.add(node.right);
                i++;
                flowList.add(node.val);
            }
            returnList.add(flowList);
        }
        return returnList;
    }

    // 树的序列化 将树变成str.前序遍历
    static String Serialize(TreeNode root) {
        // 根左右
        if (root == null) return "#,";
        StringBuffer sb = new StringBuffer();
        sb.append(root.val + ",");
        // 左节点
        sb.append(Serialize(root.left));
        // 右节点使用,来拼接
        sb.append(Serialize(root.right));
        return sb.toString();
    }

    // 树的反序列化 将数组
    static int index = -1;

    static TreeNode Deserialize(String str) {
        index++;
        String[] strArray = str.split(",");
        TreeNode node = null;
        if (!"#".equals(strArray[index])) {
            node = new TreeNode(Integer.valueOf(strArray[index]));
            node.left = Deserialize(str);
            node.right = Deserialize(str);
        }
        return node;
    }

    // 给定一棵二叉搜索树，请找出其中的第k小的结点。例如， （5，3，7，2，4，6，8）    中，
    // 按结点数值大小顺序第三小结点的值为4。
    TreeNode KthNode(TreeNode pRoot, int k) {
        // 1、将pRoot排好序放List中
        // 2、取第k个元素
        List<TreeNode> returnList = new ArrayList<>();
        getSortNode(returnList, pRoot);
        return returnList.get(k - 1);
    }

    // 对二叉搜索树进行遍历排序。从小到大
    void getSortNode(List<TreeNode> sortList, TreeNode pRoot) {
        if (pRoot == null) return;
        getSortNode(sortList, pRoot.left);
        sortList.add(pRoot);
        getSortNode(sortList, pRoot.right);
    }

    // 1-n中1出现的个数
    // if n = xyzdabc
    //(1) xyz * 1000                     if d == 0
    //(2) xyz * 1000 + abc + 1           if d == 1
    //(3) xyz * 1000 + 1000              if d > 1

    public int countDigitOne(int n) {
        if (n < 1)
            return 0;
        int count = 0;// 1的个数
        int base = 1;
        int temp = n;
        while (temp > 0) {
            int weight = temp % 10;
            temp = temp / 10;
            count += temp * base;
            if (weight > 1)
                count += base;
            if (weight == 1)
                count += (n % base) + 1;
            base *= 10;
        }
        return count;
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        TreeNode left = new TreeNode(2);
        TreeNode right = new TreeNode(3);
        TreeNode leftLeft = new TreeNode(4);
        TreeNode leftRight = new TreeNode(5);
        root.left = left;
        root.right = right;
        left.left = leftLeft;
        left.right = leftRight;
        TreeNode treeNode = Deserialize("1,2,4,#,#,5,#,#,3,#,#,");
    }
}

