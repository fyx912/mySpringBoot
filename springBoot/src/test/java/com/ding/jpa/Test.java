package com.ding.jpa;


import java.time.LocalDate;
import java.util.concurrent.CountDownLatch;

public class Test {
    private static int sum =20;
    private CountDownLatch countDownLatch = new CountDownLatch(sum);

    protected void test(){
        for (int i=0;i<sum;i++){
            new Thread(new UserRequest()).start();
            countDownLatch.countDown();
        }
        try {
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.exit(0);
    }

    public  class UserRequest implements Runnable{
        @Override
        public void run() {
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("----------------"+Thread.currentThread().getName());
        }
    }

    public static void main(String[] args){
        new Test().test();
    }
}
