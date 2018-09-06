package designpatterns.adaptermode;

import designpatterns.adaptermode.adapter.HandlerAdapter;
import designpatterns.adaptermode.adapter.impl.HttpHandlerAdapter;
import designpatterns.adaptermode.controller.Controller;
import designpatterns.adaptermode.controller.HttpController;

import java.util.ArrayList;

/**
 * 处理类
 */
public class DispatchServlet {
    public final static ArrayList<HandlerAdapter> handlerAdapterList = new ArrayList<>();

    public DispatchServlet() {
        handlerAdapterList.add(new HttpHandlerAdapter());
    }

    public void doDispatch() {

        //此处模拟SpringMVC从request取handler的对象，仅仅new出，可以出，
        //不论实现何种Controller，适配器总能经过适配以后得到想要的结果
        HttpController controller = new HttpController();
        //得到对应适配器
        HandlerAdapter adapter = getHandler(controller);
        //通过适配器执行对应的controller对应方法
        adapter.handle(controller);

    }

    public HandlerAdapter getHandler(Controller controller) {
        for (HandlerAdapter adapter : handlerAdapterList) {
            if (adapter.support(controller)) {
                return adapter;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        new DispatchServlet().doDispatch();
    }
}
