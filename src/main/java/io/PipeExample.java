package io;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

/**
 * @author JIE WU
 * @create 2018-04-06
 * @desc NIO管道测试
 **/
public class PipeExample {
    public static void main(String[] args) {
        try {
            final PipedOutputStream pos = new PipedOutputStream();
            final PipedInputStream pis = new PipedInputStream(pos);
            Thread thread1 = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        pos.write("Hello World".getBytes());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            Thread thread2 = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        int read = pis.read();
                        while (read != -1) {
                            System.out.println(read);
                            read = pis.read();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            thread1.start();
            thread2.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
