package nowcoder;

import java.util.Iterator;
import java.util.Stack;

public class Solution6 {
    private final static Stack<Integer> stack1 = new Stack<>();// 存放元素
    private final static Stack<Integer> stack2 = new Stack<>();// 存放最小元素

    public void push(int node) {
        if (stack2.isEmpty() || stack2.peek() > node) {
            if (!stack2.isEmpty()) stack2.pop();
            stack2.push(node);
        }
        stack1.push(node);
    }

    public void pop() {
        stack1.pop();
        stack2.pop();
        // 查找最小元素
        if (!stack1.isEmpty()) {
            int minNode = Integer.MAX_VALUE;
            Iterator<Integer> iterator = stack1.iterator();
            while (iterator.hasNext()) {
                minNode = Math.min(minNode, iterator.next());
            }
            stack2.push(minNode);
        }
    }

    public int top() {
        return stack1.peek();
    }

    public int min() {
        return stack2.peek();
    }


    public static void main(String[] args) {
        Solution6 solution6 = new Solution6();
        solution6.push(1);
        solution6.push(2);
        solution6.push(3);
        solution6.push(4);
        solution6.min();


        //
        Integer a = 1;
        Integer b = 2;
        Integer c = 3;
        Integer d = 3;
        Integer e = 321;
        Integer f = 321;
        Long g = 3L;
        System.out.println(c == d);
        System.out.println(e == f);
        System.out.println(c == (a + b));
        System.out.println(c.equals(a + b));
        System.out.println(g == (a + b));
    }
}