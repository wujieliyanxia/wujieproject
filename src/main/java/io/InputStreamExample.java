package io;

import java.io.*;

/**
 * @author JIE WU
 * @create 2018-04-06
 * @desc 输入流例子(字节流)
 **/
public class InputStreamExample {
    /**
     * 原始流使用缓冲数组将srcPath的文件复制到destPath
     *
     * @param srcPath  源路径
     * @param destPath 目标路径
     */
    public void inputStreamReadWithBuffer(String srcPath, String destPath) {
//        byte[] bytes = new byte[1024000000];当过大的时候会占用io，导致卡死
        byte[] bytes = new byte[1024];
        FileInputStream inputStream = null;
        FileOutputStream outputStream = null;
        try {
            inputStream = new FileInputStream(srcPath);
            outputStream = new FileOutputStream(destPath);
            long beginTime = System.currentTimeMillis();
            int len = inputStream.read(bytes);
            while (len != -1) {
                len = inputStream.read(bytes);
                outputStream.write(bytes);
            }
            long endTime = System.currentTimeMillis();
            System.out.println(endTime - beginTime);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                outputStream.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 缓冲流，这个里面有一个缓冲数据大小8192。fill方法会将这个缓冲数组读满，然后内存通过新建的byte[]读取缓冲流中的缓冲数组
     *
     * @param srcPath  源路径
     * @param destPath 目标路径
     */

    public void bufferedInputStreamRead(String srcPath, String destPath) {
        byte[] bytes = new byte[102400];
        BufferedOutputStream bos = null;
        BufferedInputStream bis = null;
        try {
            bis = new BufferedInputStream(new FileInputStream(srcPath));
            bos = new BufferedOutputStream(new FileOutputStream(destPath));
            long beginTime = System.currentTimeMillis();
            int read = bis.read(bytes);
            while (read != -1) {
                bos.write(bytes, 0, read);
                read = bis.read(bytes, 0, bytes.length);
            }
            bos.flush();
            bos.close();
            long endTime = System.currentTimeMillis();
            System.out.println(endTime - beginTime);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        InputStreamExample inputStreamExample = new InputStreamExample();
        //inputStreamExample.inputStreamReadWithBuffer("E:\\谷歌下载\\眼科.zip", "E:\\谷歌下载\\眼科copy.zip");
        inputStreamExample.bufferedInputStreamRead("E:\\谷歌下载\\眼科.zip", "E:\\谷歌下载\\眼科copy.zip");
    }
}
