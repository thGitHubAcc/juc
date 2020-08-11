package juc.interview01;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @author FTH
 * @Title
 * @Description
 * @Copyright: Copyright (c) 2020
 * @Company: morelean
 * @since 2020-7-31
 */
public class TwoLatch {
    private static List<Integer> list = new ArrayList();

    public static void main(String[] args) {

        CountDownLatch cd1 = new CountDownLatch(1);
        CountDownLatch cd2 = new CountDownLatch(1);

        Thread t2 = new Thread(() -> {
            System.out.println("t2 run ");
            try {
                if(list.size() != 5){
                    cd2.await();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("t2 end");
            cd1.countDown();

        }, "t2");

        Thread t1 = new Thread(() -> {
            System.out.println("t1 run");
            for (int i = 0; i < 10; i++) {
                list.add(i);
                System.out.println("add " + i);
                if(list.size() == 5){
                    cd2.countDown();
                    try {
                        cd1.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            System.out.println("t1 end");
        }, "t1");

        t2.start();
        t1.start();
    }
}
