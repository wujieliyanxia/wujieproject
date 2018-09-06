package current.synchronizedtest;

/**
 * synchronized的三种用法
 */
public class TestSynchronized {
    private final Object object = new Object();
    public int count = 0;

    public void test() {
        // 对象枷锁
        synchronized (object) {
            count++;
        }
    }

    // 实例加锁，只能new一个实例 不要new两个不同的实例
    public synchronized void test1() {
        count++;
    }

    public void test2() {
        // 类加锁 ，可以new多个实例
        synchronized (TestSynchronized.class) {
            count++;
        }
    }
}
