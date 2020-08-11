package juc.interview02;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author FTH
 * @Title
 * @Description
 * @Copyright: Copyright (c) 2020
 * @Company: morelean
 * @since 2020-7-31
 */
public class MyContainer02<T> {

    private List<T> list = new ArrayList<>();
    private int MAX = 10;
    private int count = 0;

    private  Lock lock = new ReentrantLock();
    private  Condition produce = lock.newCondition();
    private  Condition comsumer = lock.newCondition();


    public void put(T t) {
        try{
            lock.lock();
            while (list.size() == MAX) {
                try {
                    produce.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            list.add(t);
            ++count;
            comsumer.signalAll();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }

    }

    public T get() {
        T removed = null;
        try{
            lock.lock();
            while (list.size() == 0) {
                comsumer.await();
            }
            removed = list.remove(0);
            count--;
            produce.signalAll();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
        return removed;
    }

    public int getCount() {
        return list.size();
    }

    public static void main(String[] args) {
        MyContainer02<String> myContainer = new MyContainer02();
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + " run ...");
                for (int j = 0; j < 5; j++) {
                    String get = myContainer.get();
                    System.out.println(Thread.currentThread().getName() + " consume " + get);
                    try {
                        TimeUnit.MILLISECONDS.sleep(new Random().nextInt(10000));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }).start();
        }

        for (int i = 0; i < 2; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + " run ...");
                for (int j = 0; j < 25; j++) {
                    myContainer.put(Thread.currentThread().getName() + " produce-" + j);

                    try {
                        TimeUnit.MILLISECONDS.sleep(new Random().nextInt(500));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }

    }
}
