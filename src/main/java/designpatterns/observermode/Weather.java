package designpatterns.observermode;

import java.util.ArrayList;

/**
 * 天气
 */
public class Weather implements Oberverable {
    // 观察者
    private final static ArrayList<Observer> observerList = new ArrayList<>();

    public static volatile boolean hasChange;

    private String data;

    @Override
    public synchronized void register(Observer o) {
        observerList.add(o);
    }

    @Override
    public void notifyObserver() {
        if (hasChange) {
            if (observerList.isEmpty()) return;
            for (Observer o : observerList) {
                o.update(this, data);
            }
        }
    }

    @Override
    public synchronized void remove(Observer o) {
        observerList.remove(o);
    }
    @Override
    public synchronized void push() {
        data = "呜啦啦啦";
        hasChange = true;
        notifyObserver();
    }

    public String getData() {
        return data;
    }
}
