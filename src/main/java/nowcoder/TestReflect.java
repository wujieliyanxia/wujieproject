package nowcoder;

public class TestReflect {
    public TestReflect(){}

    public TestReflect(String a){
        System.out.println(a);
    }

    public void test(){
        System.out.println("Hello");
    }

    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        Class<?> aClass = contextClassLoader.loadClass("nowcoder.TestReflect");
        TestReflect o = (TestReflect)aClass.newInstance();
        o.test();
    }
}
