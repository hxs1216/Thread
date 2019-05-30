package com.biyao;

/**
 * @Author:hxs
 * @Description: volatile 关键字：当多个线程进行操作共享数据时，可以保证内存中的数据可见。
 * 相较于 synchronized 是一种较为轻量级的同步策略。
 *  注意:
 *  1、volatile 不具备互斥性
 *  2、volatile 不能保证变量的原子性
 */
public class MyVolatile {
    public static void main(String[] args) {
        ThreadDemo demo = new ThreadDemo();
        new Thread(demo).start(); //开启线程

        while(true){ //while底层机制较高效，未重复去获取主内存flag值，除了volatile，另一种方法是加synchronized
            if(demo.isFlag()){
                System.out.println("main thread is break");
                break;
            }
        }
    }
}

class ThreadDemo implements Runnable{

    //当flag未加上volatile时，结果：flag is true，加上volatile结果：main thread is break  flag is true
    private volatile boolean flag = false;

    @Override
    public void run() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        flag = true;
        System.out.println("flag is true");
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

}
