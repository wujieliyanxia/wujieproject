package designpatterns.adaptermode.controller;

public class HttpController implements Controller {
    public void doHttpHandler() {
        System.out.println("处理http请求");
    }
}
