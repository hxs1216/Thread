package com.biyao;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

/*
 * @Author:hxs
 * @Description: 6辆车抢3个车位 Semaphore信号量主要用于两个目的，一个是用于多个资源的互斥作用，另一个用于并发线程数的控制
 */
public class MySemaphone {
    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(3); //3个车位
        for(int i=1;i<7;i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        semaphore.acquire();
                        System.out.println(Thread.currentThread().getName()+"抢到车位");
                        Thread.sleep(3000);
                        System.out.println("3秒后"+Thread.currentThread().getName()+"释放车位");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }finally {
                        semaphore.release();
                    }
                }
            },String.valueOf(i)).start();

        }

    }
}
