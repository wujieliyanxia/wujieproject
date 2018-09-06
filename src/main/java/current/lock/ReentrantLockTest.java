package current.lock;

import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockTest implements Runnable {
    // 可重入 synchronized也可重入
    // 可响应中断 lock.lockInterruptibly()
    public static ReentrantLock lock = new ReentrantLock();
    public static int i = 0;

    @Override
    public void run() {
        for (int j = 0; j < 100; j++) {
            if (lock.tryLock()) {
//            lock.lockInterruptibly();
                try {
                    i++;
                } finally {
                    lock.unlock();
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(new ReentrantLockTest());
        Thread t2 = new Thread(new ReentrantLockTest());
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(i);
    }
}
