package com.biyao;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * @Author:hxs
 * @Description:实现Callable创建线程
 * 一、创建执行线程的方式三：实现Callable接口。相较于实现Runnable接口的方式，方法可以有返回值，并且可以跑出异常
 * 二、执行Callable方式，需要FutureTask 实现类的支持，用于接受运算结果。FutureTask是Future接口的实现类
 */
public class MyCallable {
    public static void main(String[] args) {
        CallableDemo demo = new CallableDemo();

        //执行Callable方式，需要FutureTask实现类的支持，用于接受运算结果
        FutureTask<Integer> result = new FutureTask<Integer>(demo);
        new Thread(result).start();
        try {

            //接受线程运算后的结果
            Integer sum = result.get(); //FutureTask 可用于闭锁
            System.out.println(sum);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
class CallableDemo implements Callable<Integer>{ //泛型返回值

    @Override
    public Integer call() throws Exception {
        int sum = 0;
        for(int i=0;i<=100;i++){
            sum += i;
        }
        return sum;
    }
}
