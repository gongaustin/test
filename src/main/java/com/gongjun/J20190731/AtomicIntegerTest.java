package com.gongjun.J20190731;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Description:AtomicInteger-保证多线程访问数据的原子性
 * @Author:GongJun
 * @Date:2019/7/31
 */
public class AtomicIntegerTest {

    public static AtomicInteger count = new AtomicInteger(0);

//    public static int count = 0;

    public static void main(String[] args) throws Exception{
        for (int i = 0; i < 1000; i++) {

            new Thread(){
                public void run(){
                    count.getAndIncrement();
//                    count++;
                }
            }.start();


        }
        Thread.sleep(2000);
        System.out.println("count value is:"+count);
    }

}


class Test{

    public static int count = 0;
    public static void main(String[] args) throws Exception{
        for (int i = 0; i < 1000; i++) {

            new Thread(){
                public void run(){
                    synchronized(this){
                        count++;
                    }
                }
            }.start();

        }
        Thread.sleep(2000);
        System.out.println("the count value is:"+count);
    }

}
