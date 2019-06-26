package com.biyao;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @Author:hxs
 * @Description:利用读写锁手写一个简单缓存
 * 写写/读写需要互斥
 * 读读不需要互斥
 */
public class MyCache {
    private volatile Map<String,Object> cache = new HashMap<String,Object>();
    ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();

    //写
    public void put(String key, Object value){
        rwLock.writeLock().lock();
        try {
            System.out.println(Thread.currentThread().getName()+"\t 正在写入："+key);
            cache.put(key,value);
            Thread.sleep(1000);
            System.out.println(Thread.currentThread().getName()+"\t 已写");
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            rwLock.writeLock().unlock();
        }
    }

    //读
    public void get(String key){
        rwLock.readLock().lock();
        try {
            System.out.println(Thread.currentThread().getName()+"\t 正在读："+key);
            cache.get(key);
            Thread.sleep(1000);
            System.out.println(Thread.currentThread().getName()+"\t 已读："+key);
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            rwLock.readLock().unlock();
        }
    }

    public static void main(String[] args) {
        MyCache cache = new MyCache();
        for(int i=0;i<5;i++){
            final int temp = i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    cache.put(temp+"",temp+"");
                }
            }, "线程"+i).start();
        }

        for(int i=5;i<10;i++){
            final int temp = i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    cache.get(temp+"");
                }
            }, "线程"+i).start();
        }
    }

}
