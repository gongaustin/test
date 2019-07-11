package com.gongjun.J20190711;

/**
 * @Description:try catch finally return的应用
 * @Author:GongJun
 * @Date:2019/7/11
 */
public class finalreturn {

    public static void main(String[] args) {

        System.out.println("基本数据类型返回结果:"+returnInt());

        System.out.println("----------------------");

        System.out.println("引用类型返回结果:"+returnSb());

    }



    //基本数据类型(不会改变结果)
    public static int returnInt(){

        int i = 1;

        try {
            throw new Exception();
        } catch (Exception e) {
            System.out.println("i="+i);
            return i;
        } finally {
            i++;
            System.out.println("i="+i);
        }
    }


    //引用类型(会改变结果)
    public static StringBuffer returnSb(){

        StringBuffer i = new StringBuffer("this is a");

        try {
            throw new Exception();
        } catch (Exception e) {
            System.out.println("i="+i);
            return i;
        } finally {
            i.append(" bitch!");
            System.out.println("i="+i);
        }
    }



}
