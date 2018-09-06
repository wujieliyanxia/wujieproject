package exception;

/**
 * @author JIE WU
 * @create 2018-06-05
 * @desc 异常类
 **/
public class MyException extends Exception {
    public String getMessage() {
        return "wujie";
    }

    public static void main(String[] args) throws MyException{
        MyException myException = new MyException();
        throw myException;
    }
}
