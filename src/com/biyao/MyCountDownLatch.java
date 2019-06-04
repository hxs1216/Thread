package com.biyao;

import java.util.concurrent.CountDownLatch;

/**
 * @Author:hxs
 * @Description:闭锁：可以用来确保某些活动直到其他活动都完成才继续执行
 *
 */
public class MyCountDownLatch {
    public static void main(String[] args) {
        CountDownLatch downLatch = new CountDownLatch(5); //闭锁,5代表等待5个线程执行完毕
        LatchDemo demo = new LatchDemo(downLatch);
        long start = System.currentTimeMillis();
        for(int i=0;i<5;i++){
            new Thread(demo).start();
        }
        try {
            downLatch.await(); //闭锁等待
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        System.out.println("耗费时间为："+(end-start));
    }

}

class LatchDemo implements Runnable{
    private CountDownLatch downLatch;

    public LatchDemo(CountDownLatch downLatch){
        this.downLatch = downLatch;
    }

    @Override
    public synchronized void run() { //同步还是要加上避免countDown出现问题
        try {
            for(int i=0;i<10000;i++){
                if(i%2==0){
                    System.out.println("线程"+Thread.currentThread().getName()+":"+i);
                }
            }
        }finally {
            downLatch.countDown(); //线程减1
        }
    }
}
