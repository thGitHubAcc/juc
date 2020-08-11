package juc;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * @author FTH
 * @Title
 * @Description
 * @Copyright: Copyright (c) 2020
 * @Company: morelean
 * @since 2020-7-31
 */
public class LockSupportTest {
    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            System.out.println("t1 running...");
            LockSupport.park();
            System.out.println("t1 ending...");

        },"t1");

        
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    TimeUnit.MILLISECONDS.sleep(new Random().nextInt(5000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("t2 " + i);
                if(i == 3){
                    LockSupport.unpark(t1);
                }
            }
        },"t2");

        t1.start();
        t2.start();
    }
}
