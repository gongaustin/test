package com.gongjun.J20200819;

/**
 * @Description:
 * @Author:GongJun
 * @Date:2020/8/21
 */
abstract class testAbstractClass {
    int a = 1;
    String b = "b";
    abstract void aa(); //抽象方法
    void bb(){    //具体方法
        System.out.println(a+b);
    }
}
