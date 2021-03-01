package com.gongjun.basic;

import org.junit.Test;

/**
 * @Description:基础练习
 * @Author: GongJun
 * @Date: Created in 13:39 2021/2/1
 */
public class excersises {
    @Test
    public void test(){
        //& and &&
        int a = 1; int b = 1;
        System.out.println(1==++a);
        System.out.println(1==b++);
        a = 1;  b = 1;
        System.out.println(2==++a&a==2);
        System.out.println(2==b++&&b==2);
        System.out.println("a="+a);
        System.out.println("b="+b);
        System.out.println("***********************0***********************");
        // 结束循环
        over:
        for (int i = 0; i < 10; i++) {
            System.out.println(i);
            break over;
        }

        System.out.println("***********************1***********************");

        for (int i = 0; i < 10; i++) {
            System.out.println(i);
            if(i>5) break;
            System.out.println(i);
        }

        System.out.println("***********************2***********************");

        for (int i = 0; i < 10; i++) {
            System.out.println("i="+i);
            if(i>5) continue;
            System.out.println("后面的代码会执行吗？！");

        }
        System.out.println("后面的代码会执行吗？！");
        System.out.println("***********************3***********************");
        //位运算
        System.out.println(16>>3);
        System.out.println(2<<3);
        System.out.println("***********************4***********************");
    }
}
