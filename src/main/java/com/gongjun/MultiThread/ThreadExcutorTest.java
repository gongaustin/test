package com.gongjun.MultiThread;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Description:
 * @Author: GongJun
 * @Date: Created in 14:37 2020/12/14
 */
@Slf4j
public class ThreadExcutorTest {


//    @Test
    public static void main(String[] args){
        Runnable newRunnable = new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    System.out.println(Thread.currentThread().getName()+" is running");
                } catch (InterruptedException e) {
                    log.info("Thread error,message:{}",e.getMessage());
                }
            }
        };

        //核心线程数为6，最大线程数为10，超时时间为5
        ThreadPoolExecutor executor = new ThreadPoolExecutor(6,10,5, TimeUnit.SECONDS,new SynchronousQueue<Runnable>());

        executor.execute(newRunnable);
        executor.execute(newRunnable);
        executor.execute(newRunnable);
        System.out.println("*******************************先开三个线程*******************************");

        System.out.println("核心线程数："+executor.getCorePoolSize());
        System.out.println("线程池数："+executor.getPoolSize());
        System.out.println("队列任务数："+executor.getQueue().size());
        try {
            Thread.sleep(8000);
        } catch (InterruptedException e) {
            log.info("Thread error,message:{}",e.getMessage());
        }
        executor.execute(newRunnable);
        executor.execute(newRunnable);
        executor.execute(newRunnable);
        System.out.println("*******************************8秒后的三个线程*******************************");

        System.out.println("核心线程数："+executor.getCorePoolSize());
        System.out.println("线程池数："+executor.getPoolSize());
        System.out.println("队列任务数："+executor.getQueue().size());

        System.out.println(Math.ceil(3.14)); 

    }


}
