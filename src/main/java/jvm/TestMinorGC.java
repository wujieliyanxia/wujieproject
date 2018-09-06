package jvm;

/**
 * 测试新生代GC(新生代是值存活时间短的对象 一般新生代GC使用复制算法)
 */
public class TestMinorGC {
//    private static final int _1MB = 1024 * 1024;

    /**
     * VM参数 -XX:+PrintGCDetails -verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:SurvivorRatio=8
     */
    public static void testAllocation() {
        /**
         * allocation1,allocation2,allocation3存放在Eden区和其中一个survivor区中
         *
         * 然后allocation4 这个对象来了。优先存入Eden区。但是发现空间不够（最大为9M）
         *
         * 发动一次minor gc垃圾收集。结果发现allocation1、allocation2、allocation3不能放入survivor中
         * （survivor只有1M）,于是将这个三个对象放入老年代区。将allocation4放入Eden区
         */
        byte[] allocation1, allocation2, allocation3, allocation4;
        allocation1 = new byte[2 * 1024 * 1024];
        allocation2 = new byte[2 * 1024 * 1024];
        allocation3 = new byte[2 * 1024 * 1024];
        allocation4 = new byte[4 * 1024 * 1024];
    }

    public static void main(String[] args) {
        testAllocation();
    }
}
