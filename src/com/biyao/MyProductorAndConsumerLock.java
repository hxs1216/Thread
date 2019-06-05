package com.biyao;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author:hxs
 * @Description:多生产多消费(使用Lock显式锁)
 */
public class MyProductorAndConsumerLock {
    public static void main(String[] args) {
        ClerkLock clerk = new ClerkLock();
        ProductorLock pro = new ProductorLock(clerk);
        ConsumerLock cus = new ConsumerLock(clerk);
        new Thread(pro,"生产者A").start();
        new Thread(cus,"消费者B").start();
        new Thread(pro,"生产者C").start();
        new Thread(cus,"消费者D").start();
    }
}
class ClerkLock{
    private int product = 0;
    private Lock lock = new ReentrantLock(); //显式锁
    private Condition condition = lock.newCondition(); //锁工具

    //进货
    public void get(){
        lock.lock();
        try {
            while(product>=1){ //不能使用if  唤醒之后直接生产可能出现异常（避免虚假唤醒问题）
                System.out.println("产品已满！");
                try {
                    condition.await(); //等待
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            //不能使用else 不然wait被唤醒之后无法生产，同时可能出现一直等待
            System.out.println(Thread.currentThread().getName()+":"+ ++product);
            condition.signalAll(); //唤醒All
        }finally {
            lock.unlock();
        }
    }

    //卖货
    public void sale(){
        lock.lock();
        try{
            while(product<=0){
                System.out.println("缺货！");
                try {
                    condition.await(); //等待
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            System.out.println(Thread.currentThread().getName()+":"+ --product);
            condition.signalAll(); //唤醒All
        }finally {
            lock.unlock();
        }
    }
}

//生产者
class ProductorLock implements Runnable{
    private ClerkLock clerk;

    public ProductorLock(ClerkLock clerk){
        this.clerk = clerk;
    }

    @Override
    public void run() {
        for(int i=0;i<20;i++){
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            clerk.get();
        }
    }
}

//消费者
class ConsumerLock implements Runnable{
    private ClerkLock clerk;

    public ConsumerLock(ClerkLock clerk){
        this.clerk = clerk;
    }

    @Override
    public void run() {
        for(int i=0;i<20;i++){
            clerk.sale();
        }
    }
}
