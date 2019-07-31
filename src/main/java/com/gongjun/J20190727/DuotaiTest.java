package com.gongjun.J20190727;

/**
 * @Description:Java的多态
 * @Author:GongJun
 * @Date:2019/7/27
 */
public class DuotaiTest {
    public static void main(String[] args) {
        Dog d = new Dog();
        AnimalTool.useAnimal(d);
    }

}

interface  Animal{
    default void defaultMethohd(){
        System.out.println("默认的方法");
    }
    default void defaultMethod2(){
        System.out.println("默认的方法2");
    }
     void eat();
     void sleep();
}

class Dog implements Animal{
    public void eat(){
        System.out.println("狗吃肉");
    }
    public void sleep(){
        System.out.println("狗站着睡觉");
    }
}


class Cat implements Animal{

    public void eat(){
        System.out.println("猫吃鱼");
    }
    public void sleep(){
        System.out.println("猫趴着睡觉");
    }

}

class AnimalTool{
    private AnimalTool(){

    }
    public static void useAnimal(Animal a){
        a.eat();
        a.sleep();
    }
}