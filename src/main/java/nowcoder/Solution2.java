package nowcoder;

import java.util.ArrayList;
import java.util.Stack;

/**
 * @author JIE WU
 * @create 2018-05-15
 * @desc Given a binary tree, return the postorder traversal of its nodes' values.
 **/
public class Solution2 {
    /**
     * 后序遍历  左右根 递归方式
     *
     * @param root 根节点
     * @return ArrayList<Integer> returnList
     */
    public ArrayList<Integer> postOrderTraversal(TreeNode root) {
        ArrayList<Integer> returnList = new ArrayList<>();
        if (root == null) {
            return returnList;
        }
        TreeNode left = root.left;
        TreeNode right = root.right;
        if (left != null) {
            ArrayList<Integer> leftList = postOrderTraversal(left);

        }
        if (right != null) {
            ArrayList<Integer> rightList = postOrderTraversal(right);
            returnList.addAll(rightList);
        }
        returnList.add(root.val);
        return returnList;
    }

    /**
     * 后序遍历  左右根 迭代方式
     *
     * @param root 根节点
     * @return ArrayList<Integer> returnList
     */
    public ArrayList<Integer> postOrderTraversalByIterative(TreeNode root) {
        ArrayList<Integer> returnList = new ArrayList<>();
        if (root == null) {
            return returnList;
        }
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        // 当前节点
        TreeNode pCur;
        // 上一个访问的节点
        TreeNode preNode = null;
        while (!stack.empty()) {
            pCur = stack.lastElement();
            if ((pCur.left == null && pCur.right == null)
                    || (preNode != null && (pCur.right == preNode || pCur.left == preNode))) {
                returnList.add(pCur.val);
                preNode = pCur;
                stack.pop();
            } else {
                if (pCur.right != null) {
                    stack.push(pCur.right);
                }
                if (pCur.left != null) {
                    stack.push(pCur.left);
                }
            }
        }
        return returnList;
    }

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    public static void main(String[] args) {
        Solution2 solution2 = new Solution2();
        TreeNode root = solution2.new TreeNode(1);
        TreeNode left = solution2.new TreeNode(2);
        TreeNode right = solution2.new TreeNode(3);
        root.left = left;
        root.right = right;
        ArrayList<Integer> integers = solution2.postOrderTraversalByIterative(root);
    }
}
