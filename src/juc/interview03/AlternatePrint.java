package juc.interview03;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Exchanger;
import java.util.concurrent.locks.LockSupport;

/**
 * @author FTH
 * @Title
 * @Description
 * @Copyright: Copyright (c) 2020
 * @Company: morelean
 * @since 2020-7-31
 */
public class AlternatePrint {

    static Thread lockSuport1,lockSuport12,exchanger1,exchanger2,latch1,latch2 = null;

    static CountDownLatch cd1 = new CountDownLatch(1);
    static CountDownLatch cd2 = new CountDownLatch(1);
    public static void main(String[] args) {
        char[] letter = new char[]{'A','B','C','D','E','F','G'};
        int[] number = new int[]{1,2,3,4,5,6,7};

//       <<<<============ available ============>>>>

        lockSuport1 = new Thread(() -> {
            for (int i = 0; i < letter.length; i++) {
                System.out.print(letter[i]);
                LockSupport.unpark(lockSuport12);
                LockSupport.park();
            }
        });

        lockSuport1 = new Thread(() -> {
            for (int i = 0; i < number.length; i++) {
                LockSupport.park();
                System.out.print(number[i]);
                LockSupport.unpark(lockSuport1);
            }
        });

//        lockSuport1.start();
//        lockSuport12.start();

//       <<<<============ UnAvailable ============>>>>
        Exchanger exchanger = new Exchanger();

        exchanger1 = new Thread(() -> {
            for (int i = 0; i < letter.length; i++) {
                try {
                    Object exchange = exchanger.exchange(letter[i]);
                    System.out.print(exchange);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        exchanger2 = new Thread(() -> {
            for (int i = 0; i < number.length; i++) {
                try {
                    Object exchange = exchanger.exchange(number[i]);
                    System.out.print(exchange);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
//        exchanger1.start();
//        exchanger2.start();

//       <<<<============ available ============>>>>

        latch1 = new Thread(() -> {
            for (int i = 0; i < number.length; i++) {
                try {
                    System.out.print(number[i]);
                    cd2.countDown();
                    cd2 = new CountDownLatch(1);
                    cd1.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            cd2.countDown();
        });

        latch2 = new Thread(() -> {
            for (int i = 0; i < letter.length; i++) {
                try {
                    System.out.print(letter[i]);
                    cd1.countDown();
                    cd1 = new CountDownLatch(1);
                    cd2.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            cd1.countDown();
        });
        latch1.start();
        latch2.start();




    }
}
