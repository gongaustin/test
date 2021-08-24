package com.gongjun.J20210609;

import org.junit.Test;
import org.w3c.dom.ls.LSOutput;

import java.util.function.Consumer;

/**
 * @Description:
 * @Author: GongJun
 * @Date: Created in 9:22 2021/7/13
 */
public class testTwo {
    @Test
     public void test(){

         testInterface a = System.out::println;
        System.out.println("a = " + a);
     }

}
