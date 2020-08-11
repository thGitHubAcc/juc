package juc.interview01;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class ListWithVolatile {

    private volatile static List<Integer> listWithVolitile = new ArrayList<>();

    public static void main(String[] args) {
        ListWithVolatile v1 = new ListWithVolatile();
        v1.listWithoutVolitile(new ArrayList<Integer>());
        v1.listWithVolitile();
    }

    public void listWithoutVolitile(List<Integer> list){
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                list.add(i);
                System.out.println("t1 add " + i);
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(() -> {
            while (true){
                if(list.size() == 5){
                    break;
                }
            }
            System.out.println("t2 end");
        }).start();
    }


    public void listWithVolitile(){
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                listWithVolitile.add(i);
                System.out.println("t1 add " + i);
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(() -> {
            while (true){
                if(listWithVolitile.size() == 5){
                    break;
                }
            }
            System.out.println("t2 end");
        }).start();
    }

}
