package com.gongjun.MultiThread;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.lang.reflect.Method;

/**
 * @Description:多线程
 * @Author: GongJun
 * @Date: Created in 15:38 2020/11/24
 */
@Slf4j
public class MyThread {
    @Test
    public void test() throws Exception {
        //获取外部类的实例
        Class outer = Class.forName("com.gongjun.MultiThread.MyThread");
        MyThread mt = (MyThread) outer.newInstance();
       //获取内部类的实例，外部类与内部类用“$”
        Class innerTtOne = Class.forName("com.gongjun.MultiThread.MyThread$TestThread");

        //获取Method
        Method methodOne = innerTtOne.getDeclaredMethod("run");
        methodOne.invoke(innerTtOne.getDeclaredConstructors()[0].newInstance(mt));//调用方法
    }

    //Thread实际也是实现Runnable接口的
    public class TestThread extends Thread {
        public void run() {
            log.info("Thread线程");
        }
    }

    //实现Runnable接口必须重写run()方法
    public class TestRunnable implements Runnable {
        @Override
        public void run() {
            log.info("Runnable线程");
        }
    }


}
