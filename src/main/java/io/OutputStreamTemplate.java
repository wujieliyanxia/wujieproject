package io;

import java.io.IOException;
import java.io.OutputStream;

/**
 * @author JIE WU
 * @create 2018-04-12
 * @desc 输出流模板
 **/
public interface OutputStreamTemplate {
    /**
     * 处理输出流
     *
     * @param out 输出流
     * @throws IOException
     */
    void dealwithOutputStream(OutputStream out) throws IOException;
}
