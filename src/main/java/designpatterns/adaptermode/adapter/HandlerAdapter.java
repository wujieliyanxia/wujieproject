package designpatterns.adaptermode.adapter;

/**
 * spring mvc 的handler适配器
 */
public interface HandlerAdapter {
    boolean support(Object handler);

    void handle(Object handler);
}
