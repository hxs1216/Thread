package com.biyao;

/**
 * @Author:hxs
 * @Description:模拟CAS算法
 */
public class MyCompareAndSwap {

    public static void main(String[] args) {
        CompareAndSwap cas = new CompareAndSwap();
        for(int i=0;i<10;i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    int expectedValue = cas.get(); //获取预期值
                    boolean b = cas.compareAndSet(expectedValue, (int)(Math.random() * 100));
                    System.out.println(b);
                }
            }).start();
        }
    }
}

class CompareAndSwap{
    private int value;

    //获取内存值
    public synchronized  int get(){
        return this.value;
    }

    //比较
    public synchronized int compareAndSwap(int expectedValue, int newValue){
        int oldValue = value;
        if(oldValue == expectedValue){
            this.value = newValue; //内存值与预期值相等则覆盖
        }
        return oldValue;
    }

    //设置
    public synchronized boolean compareAndSet(int expectedValue, int newValue){
        return expectedValue == compareAndSwap(expectedValue, newValue); //是否设置成功
    }
}
