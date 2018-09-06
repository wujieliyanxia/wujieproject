package current.createthread;

public class Test {
    public static void main(String[] args) {
        // 创建线程方法1 更面向对象
        Thread thread1 = new Thread(new RunnableTest());
        // 创建线程方法2 直接实现Runnable
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Hello World");
            }
        });
        thread1.run();// 相当于在本线程中启动，未新开线程
        thread1.start();// 新开线程

        thread1.stop();// 被弃用 ，无论程序进行到哪一步，锁会立刻被释放，并终止程序。太过‘暴力’

        /**
         * 什么是线程中断呢？
         * 如果不了解Java的中断机制，这样的一种解释极容易造成误解，认为调用了线程的interrupt方法就一定会中断线程。
         * 其实，Java的中断是一种协作机制。
         * 也就是说调用线程对象的interrupt方法并不一定就中断了正在运行的线程，它只是要求线程自己在合适的时机中断自己。
         * 每个线程都有一个boolean的中断状态（不一定就是对象的属性，事实上，该状态也确实不是Thread的字段），interrupt方法仅仅只是将该状态置为true。
         * 对于非阻塞中的线程, 只是改变了中断状态, 即Thread.isInterrupted()将返回true，并不会使程序停止;
         */
        thread1.interrupt();// 中断线程
        thread1.isInterrupted();// 判断是否被中断
        Thread.interrupted();// 判断是否被中断，并清除当前中断状态
    }
}
