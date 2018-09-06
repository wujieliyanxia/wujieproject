package util;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author JIE WU
 * @create 2018-04-19
 * @desc io流处理动作
 **/
public abstract class IoStreamProcessor {
    public abstract void process(InputStream inputStream, OutputStream outputStream) throws IOException;
}
