package designpatterns.observermode;

public class PhoneWeatherObserver implements Observer {
    @Override
    public void update(Oberverable o, Object arg) {
        System.out.println(arg);
    }

    public static void main(String[] args) {
        Oberverable oberverable = new Weather();
        oberverable.register(new PhoneWeatherObserver());
        oberverable.push();
    }
}
