package designpatterns.commandmode.execute;

/**
 * 实际执行命令的类
 */
public class Light {
    public void on() {
        System.out.println("灯泡点亮");
    }

    public void off() {
        System.out.println("灯泡灭了");
    }
}
