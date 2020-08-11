package juc;

import java.util.concurrent.Semaphore;

/**
 * @author FTH
 * @Title
 * @Description
 * @Copyright: Copyright (c) 2020
 * @Company: morelean
 * @since 2020-7-30
 */
public class SemaphoreTest {
    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(1);

        Thread t1 = new Thread(() -> {
            System.out.println("t1 run");
            try {
                semaphore.acquire();
                System.out.println("t1 end");
                semaphore.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"t1");

        Thread t2 = new Thread(() -> {
            System.out.println("t2 run");
            try {
                semaphore.acquire();
                System.out.println("t2 end");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"t2");


        t1.start();
        t2.start();
    }
}
