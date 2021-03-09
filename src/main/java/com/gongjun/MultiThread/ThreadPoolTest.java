package com.gongjun.MultiThread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Description:多线程 newCachedThreadPool newFixedThreadPool newSingleThreadExecutor 执行速度对比
 * @Author: GongJun
 * @Date: Created in 14:41 2021/2/5
 */
public class ThreadPoolTest {
    /**
     * @param: [args]
     * @description: main方法
     * @author: GongJun
     * @time: Created in 17:00 2021/3/3
     * @modified: 
     * @return: void
     **/
    public static void main(String[] args) {
        ExecutorService cacheService = Executors.newCachedThreadPool();  //快速  有多少任务创建多少非核心线程 （核心线程为0）
        ExecutorService fixService = Executors.newFixedThreadPool(10);  //比较慢   创建10个核心线程执行任务 （队列每10个执行）
        ExecutorService singleService = Executors.newSingleThreadExecutor();  //最慢    只创建一个核心线程执行任务 （队列依次一个一个执行）
        //进入循环，创建线程执行任务
        for (int i = 1; i <= 100; i++) {
            fixService.execute(new TestTaskThread(i));
        }
    }

}

/**面向对象OOP小知识点：
                  父类:处理的对象（单继承）
                  接口:处理的方法（多实现）
 **/

//多线程，实现Runnable接口
class TestTaskRunnable implements Runnable{
    int i = 0;
    //构造方法
    public TestTaskRunnable(int i){
        this.i = i;
    }
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName()+"--"+i);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}

//多线程，继承Thread类
class TestTaskThread extends Thread{
    int i=0;
    public TestTaskThread(int i){
        this.i = i;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName()+"--"+i);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    } 
}
