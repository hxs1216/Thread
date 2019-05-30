package com.biyao;
/**
 * @Author:hxs
 * @Description:多生产多消费
 */
public class MyProducerConsumer {
    public static void main(String[] args) {
        MyResource resource = new MyResource();
        producer producer = new producer(resource);
        consumer consumer = new consumer(resource);
        Thread thread1 = new Thread(producer); //生产者1
        Thread thread2 = new Thread(producer); //生产者2
        Thread thread3 = new Thread(consumer); //消费者1
        Thread thread4 = new Thread(consumer); //消费者2
        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
    }
}
class MyResource{
    private String name;
    private int count = 1;
    private boolean flag = false;

    //生产面包
    public synchronized void set(String name) {
        if(flag){
            try{
                this.wait(); //等待锁释放
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }
        this.name = name + count;
        count++;
        System.out.println(Thread.currentThread().getName()+"...生产"+this.name);
        flag = true;
        notifyAll(); //防止死锁问题，唤醒线程池中的全部线程
    }

    //消费面包
    public synchronized void out() {
        if(!flag){
            try{
                this.wait(); //等待锁释放
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }
        System.out.println(Thread.currentThread().getName()+"...消费"+this.name);
        flag = false;
        notifyAll(); //防止死锁问题，唤醒线程池中的全部线程
    }
}

//生产者
class producer implements Runnable{
    private MyResource resource; //访问同一个资源
    public producer(MyResource resource){
        this.resource = resource;
    }
    @Override
    public void run() {
        while(true){
            resource.set("面包");
        }
    }
}

//消费者
class consumer implements Runnable{

    private MyResource resource; //访问同一个资源
    public consumer(MyResource resource){
        this.resource = resource;
    }

    @Override
    public void run() {
        while(true){
            resource.out();
        }
    }
}
