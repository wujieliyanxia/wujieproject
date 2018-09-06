package current.synchronizedtest;

/**
 * 可使用volatile可见性
 */
public class VisibilityTest extends Thread {
    private boolean stop;
//    private volatile boolean stop;

    public void run() {
        int i = 0;
        while (!stop) {
            i++;
        }
        System.out.println("finish loop,i=" + i);
    }

    public void stopIt() {
        stop = true;
    }

    public boolean getStop() {
        return stop;
    }

    public static void main(String[] args) throws Exception {
        VisibilityTest v = new VisibilityTest();
        v.start();

        Thread.sleep(1000);
        // main线程中修改了 stop的值之后，但是还没来得及写入主存当中，
        // 线程2转去做其他事情了，那么线程1由于不知道线程2对stop变量的更改，因此还会一直循环下去。
        v.stopIt();
        // 但是在VisibilityTest线程中 读取的是本地内存的共享副本
        Thread.sleep(2000);
        System.out.println("finish main");
        System.out.println(v.getStop());
        // 一般是输出 ， 不会输出 finish loop,i=
        /**
         * finish main
         * true
         */
    }
}
