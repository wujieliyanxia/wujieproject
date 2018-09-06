package designpatterns.decoratormode;

/**
 * 装饰者
 */
public class Decorator extends Humman {
    Humman humman;// 被装饰者对象

    public Decorator(Humman humman) {
        this.humman = humman;
    }

    @Override
    void eat() {
        System.out.println("洗好手了");
        humman.eat();
        System.out.println("吃完饭再洗手");
    }

    public static void main(String[] args) {
        Humman man = new Man();
        man = new Decorator(man);
        man.eat();
    }
}
