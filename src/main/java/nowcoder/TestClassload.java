package nowcoder;

public class TestClassload {
    interface interface1 {
        int A = 0;
    }

    interface interface0 extends interface1 {
        int A = 1;
    }

    static class Test implements interface0 {
        public static int A = 1;
    }

    static class Test1 extends Test implements interface0 {
        public static int A = 1;
    }
    public static void main(String[] args) {
        System.out.println(Test1.A);
    }
}
