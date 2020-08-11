package juc.interview01;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.LockSupport;

/**
 * @author FTH
 * @Title
 * @Description
 * @Copyright: Copyright (c) 2020
 * @Company: morelean
 * @since 2020-7-31
 */
public class LockSupportWakeUp {
    private static List<Integer> list = new ArrayList();
    static Thread t1 ,t2;
    public static void main(String[] args) {
         t2 = new Thread(() -> {
            System.out.println("t2 run ");
            LockSupport.park();
            System.out.println("t2 end");
            LockSupport.unpark(t1);
        }, "t2");

         t1 = new Thread(() -> {
            System.out.println("t1 run");
            for (int i = 0; i < 10; i++) {
                list.add(i);
                System.out.println("add " + i);
                if(list.size() == 5){
                    LockSupport.unpark(t2);
                    LockSupport.park();
                }
            }
            System.out.println("t1 end");
        }, "t1");

        t1.start();
        t2.start();

    }

}
