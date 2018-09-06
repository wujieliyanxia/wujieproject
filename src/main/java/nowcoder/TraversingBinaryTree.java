package nowcoder;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

/**
 * 遍历二叉树 递归方式实现
 */
public class TraversingBinaryTree {
    //递归 前序遍历 根左右
    void prologueTraversal1(TreeNode pRoot, List<TreeNode> nodeList) {
        if (pRoot == null) return;
        nodeList.add(pRoot);
        prologueTraversal1(pRoot.left, nodeList);
        prologueTraversal1(pRoot.right, nodeList);
    }

    // 递归中序遍历 左根右
    void mediumOrderTraversal1(TreeNode pRoot, List<TreeNode> nodeList) {
        if (pRoot == null) return;
        mediumOrderTraversal1(pRoot.left, nodeList);
        nodeList.add(pRoot);
        mediumOrderTraversal1(pRoot.right, nodeList);
    }

    // 递归后序遍历 左右根
    void lastOrderTraversal1(TreeNode pRoot, List<TreeNode> nodeList) {
        if (pRoot == null) return;
        lastOrderTraversal1(pRoot.left, nodeList);
        lastOrderTraversal1(pRoot.right, nodeList);
        nodeList.add(pRoot);
    }

    public static void main(String[] args) {
        TraversingBinaryTree test = new TraversingBinaryTree();
        TreeNode root = new TreeNode(1);
        TreeNode left = new TreeNode(2);
        TreeNode right = new TreeNode(3);
        TreeNode leftLeft = new TreeNode(4);
        TreeNode leftRight = new TreeNode(5);
        TreeNode rightLeft = new TreeNode(6);
        TreeNode rightRight = new TreeNode(7);
        root.left = left;
        root.right = right;
        left.left = leftLeft;
        left.right = leftRight;
        right.right = rightRight;
        right.left = rightLeft;
    }

/*    // 前序遍历 非递归实现 根左右
    List<TreeNode> prologueTraversal(TreeNode pRoot) {
        List<TreeNode> returnList = new LinkedList<>();// 保存遍历节点
        Stack<TreeNode> stack = new Stack<>();
        stack.push(pRoot);
        while (!stack.isEmpty()) {
            TreeNode peek = stack.peek();
            returnList.add(peek);
            if (peek.left != null) stack.push(peek.left);
            else if (peek.right != null) {
                stack.push(peek.right);
            } else {
                // 若左右节点都为null，向上寻找它的父节点
                while (!stack.isEmpty()) {
                    TreeNode pop = stack.pop();// 先弹出它
                    if (!stack.isEmpty()) {
                        if (pop == stack.peek().left && stack.peek().right != null) {
                            // 若是左边的，则把父节点的right压入
                            stack.push(stack.peek().right);
                            break;
                        }
                    }
                }
            }
        }
        return returnList;
    }

    // 中序排序 非递归实现 左根右
    List<TreeNode> mediumOrderTraversal(TreeNode pRoot) {
        List<TreeNode> returnList = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();
        stack.push(pRoot);
        while (!stack.isEmpty()) {
            TreeNode peek = stack.peek();
            if (peek.left != null)
                stack.push(peek.left);
            else {
                if (peek.right != null) {
                    returnList.add(peek);
                    stack.push(peek.right);
                } else {
                    // 到达叶子节点。往上遍历
                    TreeNode popNode = stack.pop();// 出栈
                    returnList.add(popNode);
                    while (!stack.isEmpty()) {
                        // 叶子节点是父节点的left节点
                        if (popNode == stack.peek().left) {
                            returnList.add(stack.peek());
                            // 将父节点的右节点加入栈
                            if (stack.peek().right != null)
                                stack.push(stack.peek().right);
                            break;
                        }
                        // 叶子节点是父节点的right节点
                        if (popNode == stack.peek().right) {
                            popNode = stack.pop();
                        }
                    }
                }

            }
        }
        return returnList;
    }


    // 递归后序遍历 左右根
    List<TreeNode> lastOrderTraversal(TreeNode pRoot) {
        List<TreeNode> returnList = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();
        stack.push(pRoot);
        while (!stack.isEmpty()) {
            TreeNode peek = stack.peek();
            if (peek.left != null) stack.push(peek.left);
            else if (peek.right != null) stack.push(peek.right);
            else {
                returnList.add(peek);
                TreeNode popNode = stack.pop();
                while (!stack.isEmpty()) {
                    if (popNode == stack.peek().left) {
                        // 该子节点是父节的left。将父节点的右节点放入其中
                        if (stack.peek().right != null)
                            stack.push(stack.peek().right);
                        break;
                    }
                    if(popNode == stack.peek().right){
                        // 该子节点是父节的right。
                        popNode = stack.pop();
                        returnList.add(popNode);
                    }
                }
            }
        }
        return returnList;
    }*/

    public List<TreeNode> prologueTraversal(TreeNode pRoot) {
        // 前序遍历 一直遍历左节点。然后将右节点压入栈中。
        List<TreeNode> nodeList = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();
        while (true) {
            getLeftNode(pRoot, nodeList, stack);
            if (stack.isEmpty())
                break;
            pRoot = stack.pop();
        }
        return nodeList;
    }

    public static void getLeftNode(TreeNode cur, List<TreeNode> nodeList, Stack<TreeNode> rightStack) {
        while (cur != null) {
            nodeList.add(cur);
            if (cur.right != null)
                rightStack.push(cur.right);
            cur = cur.left;
        }
    }


    // 中序遍历
    public List<TreeNode> mediumOrderTraversal(TreeNode pRoot) {
        List<TreeNode> returnList = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();
        while (true) {
            traversal(pRoot, stack);
            if (stack.isEmpty()) break;
            TreeNode popNode = stack.pop();
            returnList.add(popNode);
            pRoot = popNode.right;
        }
        return returnList;
    }

    public static void traversal(TreeNode cur, Stack<TreeNode> stack) {
        while (cur != null) {
            stack.push(cur);
            cur = cur.left;
        }
    }

    // 后序遍历 左右根
    public static ArrayList<Integer> postorderTraversal(TreeNode root) {
        ArrayList<Integer> returnList = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();
        while (true) {
            traversalForLast(root, stack);
            if (stack.isEmpty())
                break;
            TreeNode peekNode = stack.peek();
            if (peekNode.right != null) {
                // 该节点有右孩子
                root = peekNode.right;
            } else {
                // 没有右孩子
                TreeNode popNode = stack.pop();
                // 出栈
                returnList.add(popNode.val);
                while (!stack.isEmpty()) {
                    // 若出栈的是父节点的左子节点，则将父节点的右子节点放进去
                    if (popNode == stack.peek().left) {
                        root = stack.peek().right;
                        break;
                    }
                    // 若出栈的是父节点的右子节点，则将父节点出栈。同时判断父节点是祖节点的左还是右
                    if (popNode == stack.peek().right) {
                        popNode = stack.pop();
                        returnList.add(popNode.val);
                    }
                }
            }
            if (stack.isEmpty())
                break;
        }
        return returnList;
    }


    public static void traversalForLast(TreeNode cur, Stack<TreeNode> stack) {
        while (cur != null) {
            stack.push(cur);
            cur = cur.left;
        }
    }
}
