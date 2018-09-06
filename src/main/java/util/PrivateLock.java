package util;

public class PrivateLock {
    public final Object object = new Object();
    int a;

    public void doSomeThing() {
        synchronized (object) {
            a++;
        }
    }
}
