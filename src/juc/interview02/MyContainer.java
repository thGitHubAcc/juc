package juc.interview02;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author FTH
 * @Title
 * @Description
 * @Copyright: Copyright (c) 2020
 * @Company: morelean
 * @since 2020-7-31
 */
public class MyContainer<T> {
    private List<T> list = new ArrayList<>();
    private int MAX = 10;
    private static int count = 0;

    public synchronized void put(T t){
        while(list.size() == MAX){
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        list.add(t);
        count++;
        this.notifyAll();
    }

    public synchronized T get(){
        while(list.size() == 0){
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        T removed = list.remove(0);
        count--;
        this.notifyAll();
        return removed;
    }

    public int getCount(){
        return list.size();
    }

    public static void main(String[] args) {
        MyContainer<String> myContainer = new MyContainer();
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
