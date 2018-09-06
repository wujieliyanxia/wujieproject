package util;

public class TestThread {
    private final static Object object1 = new Object();
    private final static Object object2 = new Object();

    public static void main(String[] args) throws InterruptedException {
        TestThread t = new TestThread();
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 5; i++) {
                    t.testWait();
//                    t.deadLock1();
                }
            }
        });
        thread1.setName("线程1");
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                t.testWait();
//                t.deadLock2();
            }
        });
        thread2.setName("线程2");
        thread1.start();
        thread2.start();
    }

    public void testWait() {
        synchronized (object1) {
            try {
                // wait会释放锁 sleep不会释放锁
                // wait只有被锁对象才能执行，要不然会报IllegalMonitorStateException
                // 同理notify()和 notifyAll()也是如此
                object1.wait(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "获得锁");
        }
    }

    public void  deadLock1(){
        synchronized (object1){
            try {
                Thread.currentThread().sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (object2){
                System.out.println("测试死锁");
            }
        }
    }

    public void  deadLock2(){
        synchronized (object2){
            synchronized (object1){
                System.out.println("测试死锁");
            }
        }
    }
}
