package current.interrupte;

public class InterruptedThread implements Runnable {
    /**
     * 对于可取消的阻塞状态中的线程
     * 比如等待在这些函数上的线程, Thread.sleep(), Object.wait(), Thread.join()
     * 这个线程收到中断信号后, 会抛出InterruptedException, 同时会把中断状态置回为false.
     */
    @Override
    public void run() {
        while (true) {
            if (Thread.currentThread().isInterrupted()) {
                System.out.println("被中断！");
                break;
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // 当线程sleep的时候（sleep不会释放锁，此时被阻塞），Thread.interrupt()会抛异常
                System.out.println("Interruted When Sleep");
                Thread.currentThread().interrupt();// 重新设置中断状态
            }
            /**
             * yeild是个native静态方法
             * 这个方法是想把自己占有的cpu时间释放掉，然后和其他线程一起竞争
             * (注意yeild的线程还是有可能争夺到cpu，注意与sleep区别)。
             * 在javadoc中也说明了，yeild是个基本不会用到的方法，一般在debug和test中使用
             */
            Thread.yield();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread thread1 = new Thread(new InterruptedThread());
        thread1.start();
        Thread.sleep(100);
        thread1.interrupt();
        // 运行结果
        /* Interruted When Sleep
        *  被中断！
        * */

        /**
         * join方法的意思是等待其他线程结束
         * 没有结束的话，主线程就一直阻塞在那里。
         */
        thread1.join();

    }
}
