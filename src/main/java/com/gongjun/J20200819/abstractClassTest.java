package com.gongjun.J20200819;

import org.junit.Test;

import java.lang.reflect.Constructor;

/**
 * @Description:抽象类
 * @Author:GongJun
 * @Date:2020/8/21
 */
public class abstractClassTest {
    @Test
    public void aa(){
        try {

            /**
             *  java.lang.InstantiationException
             * 	at sun.reflect.InstantiationExceptionConstructorAccessorImpl.newInstance(InstantiationExceptionConstructorAccessorImpl.java:48)
             * 	at java.lang.reflect.Constructor.newInstance(Constructor.java:423)
             * 	at com.gongjun.J20200819.abstractClass.aa(abstractClass.java:18)
             * 	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
             * 	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
             * 	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
             * 	at java.lang.reflect.Method.invoke(Method.java:498)
             * 	at org.junit.runners.model.FrameworkMethod$1.runReflectiveCall(FrameworkMethod.java:50)
             * 	at org.junit.internal.runners.model.ReflectiveCallable.run(ReflectiveCallable.java:12)
             * 	at org.junit.runners.model.FrameworkMethod.invokeExplosively(FrameworkMethod.java:47)
             * 	at org.junit.internal.runners.statements.InvokeMethod.evaluate(InvokeMethod.java:17)
             * 	at org.junit.runners.ParentRunner.runLeaf(ParentRunner.java:325)
             * 	at org.junit.runners.BlockJUnit4ClassRunner.runChild(BlockJUnit4ClassRunner.java:78)
             * 	at org.junit.runners.BlockJUnit4ClassRunner.runChild(BlockJUnit4ClassRunner.java:57)
             * 	at org.junit.runners.ParentRunner$3.run(ParentRunner.java:290)
             * 	at org.junit.runners.ParentRunner$1.schedule(ParentRunner.java:71)
             * 	at org.junit.runners.ParentRunner.runChildren(ParentRunner.java:288)
             * 	at org.junit.runners.ParentRunner.access$000(ParentRunner.java:58)
             * 	at org.junit.runners.ParentRunner$2.evaluate(ParentRunner.java:268)
             * 	at org.junit.runners.ParentRunner.run(ParentRunner.java:363)
             * 	at org.junit.runner.JUnitCore.run(JUnitCore.java:137)
             * 	at com.intellij.junit4.JUnit4IdeaTestRunner.startRunnerWithArgs(JUnit4IdeaTestRunner.java:68)
             * 	at com.intellij.rt.junit.IdeaTestRunner$Repeater.startRunnerWithArgs(IdeaTestRunner.java:33)
             * 	at com.intellij.rt.junit.JUnitStarter.prepareStreamsAndStart(JUnitStarter.java:230)
             * 	at com.intellij.rt.junit.JUnitStarter.main(JUnitStarter.java:58)
             *  抽象类无法实例化
             *
             * */
            Class clazz = Class.forName("com.gongjun.J20200819.Dog");
            Constructor<Dog> dog = clazz.getConstructor();
            Dog g = dog.newInstance();
            System.out.println(g.wangwang());
            System.out.println("**************分割线*************");



        } catch (Exception e) {

            e.printStackTrace();

        }

        //但是可以通过子类的实例调用抽象类的属性和方法，即多态
        try {
            Class clazzY = Class.forName("com.gongjun.J20200819.YellowDog");
            Constructor<YellowDog> dogY = clazzY.getConstructor();
            YellowDog y = dogY.newInstance();
            System.out.println(y.wangwang());
            System.out.println(y.yellowWangWang());
        }catch (Exception e){

        }
    }

    //普通类和抽象类的区别

    class normalClass{
        //不能有抽象方法
//        abstract void aa(){
//
//        }

        void bb(){

        }
    }
    abstract class abstractClass{
        String aa = "I'm aa";
        String bb;
        //可以有非抽象方法
        void aa(){

        }
        abstract void bb();

    }


    //抽象类和接口的区别

    interface imInterface{
        //不能有属性，方法都是抽象方法(木有方法体)，默认为abstract，可以不用abstracts显性修饰，接口被实现implement
        void aa();
        String bb();
    }

    abstract class vsClass{
        //抽象类被继承extends
        //可以有属性
        int a = 1;
        //可以有具体方法
        void aa(){
            System.out.println(a);
        }
        // 抽象方法必须用abstract修饰
        abstract void bb();
    }
}


