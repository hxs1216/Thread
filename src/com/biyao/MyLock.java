package com.biyao;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author:hxs
 * @Description:用于解决多线程安全问题的方式：
 * synchronized：隐式锁
 * 1、同步代码块
 * 2、同步方法
 * jdk1.5后：
 * 3、同步锁lock
 * 注意：是一个显示锁，需要通过lock()方法上锁，必须通过unlock()方法进行释放锁
 */
public class MyLock {
    public static void main(String[] args) {
        Ticket myTicket = new Ticket();
        new Thread(myTicket).start();
        new Thread(myTicket).start();
        new Thread(myTicket).start();
    }
}
class Ticket implements Runnable{
    private int tick = 100;
    private Lock lock = new ReentrantLock(); //生成锁ReentrantLock

    @Override
    public void run() {
        while(true){
            lock.lock(); //上锁
            try {
                if(tick>0){
                    System.out.println(Thread.currentThread().getName()+"获取票据,余票还有"+--tick);
                }
            }finally {
                lock.unlock(); //去锁
            }
        }
    }
}
