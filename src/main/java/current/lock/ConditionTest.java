package current.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * lock和condition的使用
 */
public class ConditionTest<T> {
    private final Lock lock = new ReentrantLock();
    // 条件1
    private final Condition notFull = lock.newCondition();
    // 条件2
    private final Condition notEmpty = lock.newCondition();
    // 数组
    private final T[] items = (T[]) new Object[3];
    // 队列中个数
    private int count, tail, head;

    // 存 阻塞并到not full
    public void put(T t) throws InterruptedException {
        lock.lock();
        try {
            while (count == items.length)
                notFull.await();
            items[tail] = t;
            if (++tail == items.length)
                tail = 0;
            count++;
            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }

    // 取 直到notEmpty
    public T take() throws InterruptedException {
        lock.lock();
        try {
            while (count == 0)
                notEmpty.await();
            count--;
            T t = items[head];
            items[head] = null;
            if (++head == items.length)
                head = 0;
            notFull.signal();
            return t;
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ConditionTest<Integer> myBockedQueue = new ConditionTest<>();
        myBockedQueue.put(1);
        myBockedQueue.put(1);
        myBockedQueue.put(1);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println(myBockedQueue.take());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        Thread.sleep(1000);
        myBockedQueue.put(2);

    }
}
