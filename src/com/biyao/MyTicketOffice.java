package com.biyao;

/**
 * @Author:hxs
 * @Description:三个售票窗口同时出售20张票
 */
public class MyTicketOffice{

    public static void main(String[] args) {
        TicketOffice ticketOffice = new TicketOffice(new Object(),20);
        new Thread(ticketOffice,"窗口1").start(); //模拟三个窗口同时抢票
        new Thread(ticketOffice,"窗口2").start();
        new Thread(ticketOffice,"窗口3").start();
    }
}

class TicketOffice implements Runnable{

    private Object clock; //锁
    private int ticketNum; //车票数

    public TicketOffice(Object clock,int ticketNum){
        this.clock = clock;
        this.ticketNum = ticketNum;
    }

    @Override
    public void run() {

        //窗口一直售票直到票售完
        while(ticketNum > 0){
            synchronized(clock){
                if(ticketNum <= 0){
                    System.out.println(Thread.currentThread().getName() + "票售完了");
                }else{
                    System.out.println(Thread.currentThread().getName() + "卖出了一张票，剩余" + --ticketNum + "张票");
                    try{
                        Thread.sleep(1000L);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}