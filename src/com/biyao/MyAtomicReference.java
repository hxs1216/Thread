package com.biyao;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @Author:hxs
 * @Description: AtomicInteger原理是CAS，除了已存在的Atomic对象，我们还可以将CAS原理引用到我们创建的类中
 */
public class MyAtomicReference {
    public static void main(String[] args) {
        User user1 = new User("A",21);
        User user2 = new User("B",22);
        AtomicReference<User> atomicUser = new AtomicReference<User>();
        atomicUser.set(user1); //先初始化

        //将atomicUser改成user2利用CAS原理
        System.out.println( atomicUser.compareAndSet(user1,user2)+" "+atomicUser.get().toString());
        System.out.println( atomicUser.compareAndSet(user1,user2)+" "+atomicUser.get().toString());
    }

}
class User{
    private String userName;
    private Integer age;

    public User(String userName, Integer age) {
        this.userName = userName;
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", age=" + age +
                '}';
    }
}