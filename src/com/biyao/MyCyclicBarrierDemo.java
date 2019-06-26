package com.biyao;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @Author:hxs
 * @Description:CyclicBarrier的字面意思是可循环使用的屏障。他要做事情是：让一组线程到达一个屏障时被阻塞，直到最后
 *  一个线程到达屏障时，屏障才会开门，所有被屏障拦截的线程才会继续工作，线程进入屏障通过CyclicBarrier的await方法
 */
public class MyCyclicBarrierDemo {
    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(7, new Runnable() {
            @Override
            public void run() {
                System.out.println("收集到7颗龙珠召唤神龙"); //执行到第7线程就会执行该线程，并唤醒await状态线程
            }
        });

        for(int i=1;i<9;i++){
            final int temp = i;
            new Thread(new Runnable() {
               @Override
               public void run() {
                   System.out.println("收集到第"+temp+"颗龙珠！");
                   try {
                       cyclicBarrier.await(); //阻塞
                   } catch (Exception e) {
                       e.printStackTrace();
                   }
                   System.out.println("开心！");
               }
           }).start();
        }
    }
}
