package com.gongjun.J20190508;

/**
 * @Description:
 * @Author:GongJun
 * @Date:2019/5/8
 */
public class demoB extends demoA {
    String name = "lee";

    void ss(String name){
        System.out.println("this.name:"+this.name);
        System.out.println("super.name:"+super.name);
        System.out.println("ss.name:"+name);
    }
}
