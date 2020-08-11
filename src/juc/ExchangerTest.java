package juc;

import java.util.concurrent.Exchanger;

/**
 * @author FTH
 * @Title
 * @Description
 * @Copyright: Copyright (c) 2020
 * @Company: morelean
 * @since 2020-7-30
 */
public class ExchangerTest {
    public static void main(String[] args) {
        Exchanger<String> exchanger = new Exchanger();

        Thread t1 = new Thread(() -> {
            System.out.println("t1 run");
            try {
                String ret = exchanger.exchange("t1");
                System.out.println("t1 recevice:" + ret);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"t1");

        Thread t2 = new Thread(() -> {
            System.out.println("t2 run");
            try {
                String ret = exchanger.exchange("t2");
                System.out.println("t2 recevice:" + ret);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"t2");
        t1.start();
        t2.start();
    }
}
