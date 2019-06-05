package com.biyao;

/**
 * @Author:hxs
 * @Description:多生产多消费(使用synchronized隐式锁)
 */
public class MyProductorAndConsumer {
    public static void main(String[] args) {
        Clerk clerk = new Clerk();
        Productor pro = new Productor(clerk);
        Consumer cus = new Consumer(clerk);
        new Thread(pro,"生产者A").start();
        new Thread(cus,"消费者B").start();
        new Thread(pro,"生产者C").start();
        new Thread(cus,"消费者D").start();
    }

}
class Clerk{
    private int product = 0;

    //进货
    public synchronized void get(){
        while(product>=1){ //不能使用if  唤醒之后直接生产可能出现异常（避免虚假唤醒问题）
            System.out.println("产品已满！");
            try {
                this.wait(); //等待
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        //不能使用else 不然wait被唤醒之后无法生产，同时可能出现一直等待
        System.out.println(Thread.currentThread().getName()+":"+ ++product);
        this.notifyAll(); //唤醒All
    }

    //卖货
    public synchronized void sale(){
        while(product<=0){
            System.out.println("缺货！");
            try {
                this.wait(); //等待
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println(Thread.currentThread().getName()+":"+ --product);
        this.notifyAll(); //唤醒All
    }
}

//生产者
class Productor implements Runnable{
    private Clerk clerk;

    public Productor(Clerk clerk){
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
class Consumer implements Runnable{
    private Clerk clerk;

    public Consumer(Clerk clerk){
        this.clerk = clerk;
    }

    @Override
    public void run() {
        for(int i=0;i<20;i++){
            clerk.sale();
        }
    }
}
