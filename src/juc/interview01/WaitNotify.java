package juc.interview01;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author FTH
 * @Title
 * @Description
 * @Copyright: Copyright (c) 2020
 * @Company: morelean
 * @since 2020-7-31
 */
public class WaitNotify {
    private static List<Integer> list = new ArrayList<>();

    private static Object lock = new Object();

    public static void main(String[] args) {
        new Thread(() -> {
            synchronized (lock){
                for (int i = 0; i < 10; i++) {
                        list.add(i);
                        System.out.println("add " + i);

                        if(list.size() == 5){
                            lock.notify();
                            try {
                                lock.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }


                }
            }
        }).start();

        new Thread(() -> {

            synchronized (lock){
                if(list.size() != 5){
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                lock.notify();
            }

            System.out.println("t2 end");

        }).start();
    }
}
