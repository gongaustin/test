package com.gongjun.J20190731;

import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Test;

/**
 * @Description:AtomicInteger-保证多线程访问数据的原子性
 * @Author:GongJun
 * @Date:2019/7/31
 */
public class AtomicIntegerTest {

    public int count1 = 0;


    @Test
    public void aa() throws Exception{
        for (int i = 0; i < 1000; i++) {
            new Thread(()->{
                count1++;
            }).start();
        }
        Thread.sleep(2000);
        System.out.println("the count value is:"+count1);
    }

    public AtomicInteger count2 = new AtomicInteger(0);

    @Test
    public void bb() throws Exception{
        for (int i = 0; i < 1000; i++) {

            new Thread(() -> {
                count2.getAndIncrement();
            }).start();

        }
        Thread.sleep(2000);
        System.out.println("count value is:"+count2);
    }

    public int count3 = 0;

    @Test
    public void cc() throws Exception{
        for (int i = 0; i < 1000; i++) {

            new Thread(){
                public void run(){
                    synchronized(this){
                        count3++;
                    }
                }
            }.start();

        }
        Thread.sleep(2000);
        System.out.println("the count value is:"+count3);
    }

    @Test
    public void dd(){

    }

}

