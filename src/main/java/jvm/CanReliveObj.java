package jvm;

/**
 * finalize方法会在gc的时候执行一次，下一次不会执行。
 */
public class CanReliveObj {
    public static CanReliveObj obj;

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println("CanReliveObj finalize called");
        obj = this;   //当执行GC时，会执行finalize方法，然后这一行代码的作用是将null的object复活一下，然后变成了可触及性
    }

    @Override
    public String toString() {
        return "I am CanReliveObj";
    }

    public static void main(String[] args) throws InterruptedException {
        obj = new CanReliveObj();
        obj = null;
        System.gc();// 第一次gc
        Thread.sleep(1000);
        if(obj == null){
            System.out.println("obj为null");
        }else{
            System.out.println("obj可用");
        }
        obj = null;
        System.gc();// 第二次gc
        Thread.sleep(1000);
        if(obj == null){
            System.out.println("obj为null");
        }else{
            System.out.println("obj可用");
        }
    }
}
