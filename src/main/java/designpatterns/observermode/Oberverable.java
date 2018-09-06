package designpatterns.observermode;

import java.util.ArrayList;

public  interface Oberverable {


    // 注册
     void register(Observer o);

    // 通知
    void notifyObserver();

    // 取消注册
   void remove(Observer o);

   void push();

}
