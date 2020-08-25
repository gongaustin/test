package com.gongjun.J20200819;

import org.junit.Test;

/**
 * @Description: 继承抽象类,访问抽象类的属性和方法可以通过子类的实例访问
 * @Author:GongJun
 * @Date:2020/8/21
 */
public class testAbstractClassImpl extends testAbstractClass{
    //继承抽象类的抽象方法必须重写
    @Override
    void aa() {
        System.out.println("重写了抽象方法aa()");
    }
    @Override
    void bb(){
        System.out.println("重写了具体方法bb()");
    }

    @Test
    public void cc(){
        //无需创建实例
        bb();
        System.out.println(super.a);
        super.bb(); //调用
    }
}
