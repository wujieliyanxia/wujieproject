package webservicedemo.interceptors;

import org.apache.commons.io.IOUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author JIE WU
 * @create 2018-04-16
 * @desc 拦截器
 **/
public class MyPhaseInterceptor extends AbstractPhaseInterceptor<Message> {

    public MyPhaseInterceptor(String phase) {
        super(phase);
    }

    @Override
    public void handleMessage(Message message) throws Fault {
        InputStream is = message.getContent(InputStream.class);
        if (is != null) {
            try {
                String str = IOUtils.toString(is);
                System.out.println("请求报文为：" + str);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}