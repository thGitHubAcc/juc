package juc;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

/**
 * @author FTH
 * @Title
 * @Description
 * @Copyright: Copyright (c) 2020
 * @Company: morelean
 * @since 2020-7-29
 */
public class PhaserTest {
    public static void main(String[] args) {
        TestPhaser phaser = new TestPhaser(10);
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                try {
                    TimeUnit.MILLISECONDS.sleep(new Random().nextInt(10000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(new Random().nextInt() % 5 == 0){
                    System.out.println(Thread.currentThread().getName() + " : leave 1");
                    phaser.arriveAndDeregister();
                    return;
                }else {
                    System.out.println(Thread.currentThread().getName() + " : arrive 1");
                    phaser.arriveAndAwaitAdvance();
                }
                try {
                    TimeUnit.MILLISECONDS.sleep(new Random().nextInt(10000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(new Random().nextInt() % 5 == 0){
                    System.out.println(Thread.currentThread().getName() + " : leave 2");
                    phaser.arriveAndDeregister();
                    return;
                }else {
                    System.out.println(Thread.currentThread().getName() + " : arrive 2");
                    phaser.arriveAndAwaitAdvance();
                }

                try {
                    TimeUnit.MILLISECONDS.sleep(new Random().nextInt(10000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(new Random().nextInt() % 5 == 0){
                    System.out.println(Thread.currentThread().getName() + " : leave 3");
                    phaser.arriveAndDeregister();
                    return;
                }else {
                    System.out.println(Thread.currentThread().getName() + " : arrive 3");
                    phaser.arriveAndAwaitAdvance();
                }

            }).start();
        }
    }



    static class TestPhaser extends Phaser{


        public TestPhaser() {
            super();
        }

        public TestPhaser(int parties) {
            super(parties);
        }

        public TestPhaser(Phaser parent) {
            super(parent);
        }

        public TestPhaser(Phaser parent, int parties) {
            super(parent, parties);
        }

        @Override
        protected boolean onAdvance(int phase, int registeredParties) {
            switch (phase){
                case 0:
                    System.out.println("arrive 0 and advance next 1 . left parties - " + registeredParties  );
                    return false;
                case 1:
                    System.out.println("arrive 2 and advance next 3 . left parties - " + registeredParties  );
                    return false;
                case 2:
                    System.out.println("arrive 3 and  return . left parties - " + registeredParties );
                    return true;
                default:
                    return true;
            }
        }
    }
}
