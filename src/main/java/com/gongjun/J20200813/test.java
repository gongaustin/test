package com.gongjun.J20200813;

import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * @Description:
 * @Author:GongJun
 * @Date:2020/8/13
 */
public class test {


    @Test
    public void someothing() throws ClassNotFoundException {
        String a = "abc";
        String b = "abc";
        System.out.println(a == b);

        String c = new String("abc");
        System.out.println(a == c);

        System.out.println(a.equals(c));  //String类的equals方法被重写了

        Cat cat_one = new Cat("Mr.G");
        Cat cat_two = new Cat("Mr.G");

        System.out.println(cat_one == cat_two);
        System.out.println(cat_one.equals(cat_two));

//        Class clazz = ClassLoader.getSystemClassLoader().loadClass("com.gongjun.J20200813.Cat");

        Class klass = Class.forName("com.gongjun.J20200813.Cat");

        try {
            //获取带参构造方法
            Constructor<Cat> cat_three_1 = klass.getConstructor(String.class);
            Cat cat_three = cat_three_1.newInstance("喵先森");

            Constructor<Cat> cat_four_1 = klass.getConstructor(String.class);
            Cat cat_four = cat_four_1.newInstance("喵先森");

            Cat cat_five = cat_four_1.newInstance("喵先森");
            System.out.println("---------------------------------------");
            System.out.println(cat_three == cat_four);
            System.out.println(cat_three.equals(cat_four));
            System.out.println(cat_four.equals(cat_five));

            Constructor<Cat> cat_many = klass.getConstructor(String.class,int.class,String.class);//int.class不能写成Integer.class,Integer是封装类

            Cat cat_m = cat_many.newInstance("喵先森",2,"黄色");

            System.out.println(cat_m.getColor());

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }


    }

}

