package com.gongjun.J20200916;

/**
 * @Description: final在Java中有哪些作用？  修饰变量，变量必须有初始值，不可重新赋值；修饰方法，方法不可重写；修饰类，类不可被继承
 * @Author: GongJun
 * @Date: Created in 14:22 2020/9/16
 */
public class finalTest {
    //修饰变量,必须赋值,且赋值后不可在赋值
    final int a = 1;

    final void aa(){
        // a = 2; ->Wrong
        System.out.println();
    }
    void cc(){};

}

final class bb extends finalTest{
    void cc(){
        System.out.println(a);

    }
    /**  -> Wrong 普通类不允许抽象方法，因为有专门定义了抽象方法的抽象类abstract,普通类使用抽象方法就冲突了
    @Override
    void cc();
    */
    /**  -> Wrong final修饰的方法不可以被重写
    @Override
    void aa(){
        System.out.println("new cc");
    }
    */
}
