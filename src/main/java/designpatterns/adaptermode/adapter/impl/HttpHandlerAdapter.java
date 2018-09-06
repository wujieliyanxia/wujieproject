package designpatterns.adaptermode.adapter.impl;

import designpatterns.adaptermode.adapter.HandlerAdapter;
import designpatterns.adaptermode.controller.HttpController;

public class HttpHandlerAdapter implements HandlerAdapter {
    @Override
    public boolean support(Object handler) {
        return (handler instanceof HttpController);
    }

    @Override
    public void handle(Object handler) {
        ((HttpController) handler).doHttpHandler();
    }
}
