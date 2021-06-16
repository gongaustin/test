package com.gongjun.jvm.gc;

import org.junit.Test;

/**
 * @Description:GC循环引用的问题
 * @Author: GongJun
 * @Date: Created in 10:05 2021/3/29
 */
public class ReferenceCounterProblem {


    @Test
    public void test(){
        TestObject two = new TestObject();
        TestObject one = new TestObject();
        one = two;
        two = one;
        System.out.println(one.childNode);
        System.out.println(two.childNode);
    }


}
