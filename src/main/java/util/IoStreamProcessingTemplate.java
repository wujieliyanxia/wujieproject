package util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author JIE WU
 * @create 2018-04-19
 * @desc Io流处理模板
 **/
public class IoStreamProcessingTemplate {
    /**
     * 流处理模板
     *
     * @param inputStream  输入流
     * @param outputStream 输出流
     */
    public static void process(InputStream inputStream, OutputStream outputStream, IoStreamProcessor processor) throws IOException {
        IOException processException = null;
        try {
            processor.process(inputStream, outputStream);
        } catch (IOException e) {
            processException = e;
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    if(processException!=null){
                        throw processException;
                    }else{
                        throw e;
                    }
                } finally {
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                            e.printStackTrace();
                            if(processException!=null){
                                throw processException;
                            }else{
                                throw e;
                            }
                        }
                    }
                }
            }
            if(processException != null){
                throw processException;
            }
        }
    }
}
