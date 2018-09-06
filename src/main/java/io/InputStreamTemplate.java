package io;


import java.io.IOException;
import java.io.InputStream;

/**
 * @author JIE WU
 * @create 2018-04-12
 * @desc 输入流模板
 **/
public interface InputStreamTemplate {
    /**
     * 处理输入流
     *
     * @param in 输入流
     * @throws IOException
     */
    void dealwithInputStream(InputStream in) throws IOException;
}
