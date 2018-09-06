package jvm;

/**
 * 引用计数法
 */
public class ReferenceCountingGc {
    private Object instance = null;

    private static final int _1mb = 1024 * 1024;

    private byte[] a = new byte[2 * _1mb];

    public static void main(String[] args) {
        ReferenceCountingGc a = new ReferenceCountingGc();
        ReferenceCountingGc b = new ReferenceCountingGc();
        a.instance = b;
        b.instance = a;
        System.gc();
    }
}
