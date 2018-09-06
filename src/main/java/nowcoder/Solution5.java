package nowcoder;

/**
 * 单例模式,只能new 一个对象
 */
public class Solution5 {
    private static Solution5 solution5;

    // 构造函数设为private
    private Solution5() {

    }

    // NotThreadSafe
    public static Solution5 getInstance() {
        if (solution5 == null) {
            solution5 = new Solution5();
        }
        return solution5;
    }

    // ThreadSafe not best 多线程一次只能进去一个，其他的阻塞了，性能不好
    public static Solution5 getInstanceForSafe() {
        synchronized (Solution5.class) {
            if (solution5 == null) {
                solution5 = new Solution5();
            }
        }
        return solution5;
    }

    // NotThreadSafe
    // solution5 = new Solution5();不是原子性操作。
    // ①分配空间给对象
    //②在空间内创建对象
    //③将对象赋值给引用sole
    // 2和3的顺序不一定（重排序） 当3在2前面的时候，另外一个线程进去判断solution5 == null 为false。返回一个不可用对象

    public static Solution5 getInstanceForSafeBetter() {
        // 双重否定 -- 不安全
        if (solution5 == null) {
            synchronized (Solution5.class) {
                if (solution5 == null) {
                    solution5 = new Solution5();
                }
            }
        }
        return solution5;
    }

    // 天生线程安全的单例
    static {
        solution5 = new Solution5();
    }

    public static Solution5 getSolution5(){
        return solution5;
    }
}
