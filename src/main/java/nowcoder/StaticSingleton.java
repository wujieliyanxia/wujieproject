package nowcoder;

public class StaticSingleton {
    // 单例模式 延迟加载 线程安全
    private StaticSingleton() {

    }

    private static class SingletonHolder {
        private static StaticSingleton staticSingleton = new StaticSingleton();
    }

    public static StaticSingleton getInstance() {
        return SingletonHolder.staticSingleton;
    }
}
