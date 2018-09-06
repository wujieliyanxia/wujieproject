package current.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 使用condition实现信号量。。。。信号量的实现看Test.java
 */
public class ConditionForSemaphore {
    private final Lock lock = new ReentrantLock();
    private final Condition permitAvailable = lock.newCondition();
    private int permits;

    ConditionForSemaphore(int permits) {
        lock.lock();
        try {
            this.permits = permits;
        } finally {
            lock.unlock();
        }
    }

    // 获取令牌。令牌数小于等于 0的时候睡眠
    public void acquire() throws InterruptedException {
        lock.lock();
        try {
            while (permits <= 0)
                permitAvailable.await();
            permits--;
        } finally {
            lock.unlock();
        }
    }

    // 释放令牌
    public void release() throws InterruptedException {
        lock.lock();
        try {
            permits++;
            permitAvailable.signal();
        } finally {
            lock.unlock();
        }
    }
}
