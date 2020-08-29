package com.gongjun.J20200825;

import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

/**
 * @Description:
 * @Author:GongJun
 * @Date:2020/8/29
 */
public class testClass {
    @Test
    public void aa() throws Exception{
     Class clazz =  Class.forName("com.gongjun.J20200825.finalCLass");
     Constructor<finalCLass> cf = clazz.getConstructor();
     finalCLass fc = cf.newInstance();
     fc.b();
    }

    @Test
    public void bb() throws Exception{
        Class<?> clazz = finalCLass.class;
        Field field = clazz.getDeclaredField("a");
        String name = field.getName();
        System.out.println(name);
        System.out.println("*********************************************");
        System.out.println(field.getInt(new finalCLass())); //必须传类的实例
        System.out.println("*********************************************");
        field.setAccessible(true);
        finalCLass fc = new finalCLass();
        field.setInt(fc,3);
        System.out.println(fc.a);

//        field.setAccessible(true);
//        System.out.println(field.getInt("a"));


    }
}
