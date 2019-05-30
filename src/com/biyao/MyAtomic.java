package com.biyao;

import java.util.concurrent.atomic.AtomicInteger;

/*
 * @Author:hxs
 * @Description: 利用CAS解决原子性
 * 二、原子变量：在 java.util.concurrent.atomic 包下提供了一些原子变量。
 * 		1. volatile 保证内存可见性
 * 		2. CAS（Compare-And-Swap） 算法保证数据变量的原子性
 * 			CAS 算法是硬件对于并发操作的支持
 * 			CAS 包含了三个操作数：
 * 			①内存值  V
 * 			②预估值  A
 * 			③更新值  B
 * 		    当且仅当 V == A 时， V = B; 否则，不会执行任何操作。
 */
public class MyAtomic {
    public static void main(String[] args) {
        AtomicDemo demo = new AtomicDemo();
        for (int i = 0; i < 10; i++) {
            new Thread(demo).start();
        }
    }
}

class AtomicDemo implements Runnable{

//    private volatile int number = 0; //volatile只能保证内存可见性 无法保证原子性操作
    private AtomicInteger number = new AtomicInteger(0); //concurrent的AtomicInteger对象底层使用cas算法保证原子性操作

    @Override
    public void run() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(increment());
    }

    public int increment(){
        return number.getAndIncrement(); //atomic的原子性自增
//        return number++;
    }
}
