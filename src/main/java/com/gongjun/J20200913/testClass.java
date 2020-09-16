package com.gongjun.J20200913;

import org.junit.Test;

/**
 * @Description:IoC相互依赖
 * @Author:GongJun
 * @Date:2020/9/13
 */
public class testClass {
    @Test
    public void test(){
        //A、B循环依赖
        A a = new A();
        B b = new B();
        a.b = b;
        b.a = a;
    }
}
