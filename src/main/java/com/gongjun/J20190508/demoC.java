package com.gongjun.J20190508;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * @Description:
 * @Author:GongJun
 * @Date:2019/5/8
 */
public class demoC {
    public static void main(String[] args) {
        /**
         * 利用反射获取实例
         * */
        try {
            Class clazz = Class.forName("com.gongjun.J20190508.demoB");
            Constructor<demoB> b = clazz.getConstructor();
            demoB bb = b.newInstance();
            bb.ss("red");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        long  a =  Math.round(1.5);
        long b = Math.round(-1.5);
        System.out.println(a);
        System.out.println(b);
    }
}
