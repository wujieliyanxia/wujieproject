package designpatterns.decoratormode;

/**
 * 男人
 */
public class Man extends Humman{

    @Override
    void eat() {
        System.out.println("用手抓饭吃");
    }
}
