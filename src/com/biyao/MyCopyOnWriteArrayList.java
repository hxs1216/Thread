package com.biyao;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @Author:hxs
 * @Description:CopyOnWriteArrayList:"写入并复制"
 * 注意：添加操作多时，效率低，因为每次添加时都会进行复制，开销非常大，并发迭代多时可以选择
 */
public class MyCopyOnWriteArrayList {
    public static void main(String[] args) {
        HelloThread helloThread = new HelloThread();
        for(int i=0;i<3;i++){
            new Thread(helloThread).start();
        }
    }
}
class HelloThread implements Runnable{
//    private static List list = Collections.synchronizedList(new ArrayList()); //同步
    private static CopyOnWriteArrayList list = new CopyOnWriteArrayList();
    static {
        list.add("aa");
        list.add("bb");
        list.add("cc");
        list.add("dd");
    }

    @Override
    public void run() {
        Iterator iterator = list.iterator();
        while(iterator.hasNext()){
            System.out.println(iterator.next());
            list.add("AA"); //CopyOnWriteArrayList会先写入并复制，当List会报错
        }
    }
}
