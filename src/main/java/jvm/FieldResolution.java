package jvm;

public class FieldResolution {
    interface interface0 {
        int a = 0;
    }

    interface interface1 extends interface0 {
        int a = 1;
    }

    interface interface2 {
        int a = 2;
    }

    static class a implements interface1 {
        public static int a = 0;
    }

    static class b extends a implements interface2 {
        public static int a = 100;
    }

    public static void main(String[] args) {
        System.out.println(b.a);
    }
}
