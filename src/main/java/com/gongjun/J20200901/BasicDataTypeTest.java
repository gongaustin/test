package com.gongjun.J20200901;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * @Description:基本数据类型测试类
 * @Author:GongJun
 * @Date:2020/9/3
 */
public class BasicDataTypeTest {

    @Test  //整型
    public void intTest(){
        int a = 1;
        Integer b = 1;
        System.out.println(a == b);  //向下拆箱
    }

    @Test //字符型
    public void charTest(){
        char a = 'a';
        System.out.println((int)a); //向上强转
    }

    @Test //
    public void byteTest(){

        String s = "abcdefg";

        byte[] ss = s.getBytes();

        System.out.println(new String(ss));

        List l = Arrays.asList(ss);

        System.out.println(l);

        byte[] a = s.getBytes();

        System.out.println(ss.equals(a)); //不管==还是equals都是false

        byte[] b = {1,2,3};
        Arrays.asList(b).stream().forEach(e->{
            System.out.println(Arrays.toString(e));
        });


    }

    @Test
    public void doubleTest(){
        double a = 9;
        double b = Math.pow(a,a);
        System.out.println((int)b);  //向下强转1
        //向下强转2
        char c = (char)b;
        System.out.println(c);

        long d = (long)b;
        System.out.println(d);

    }

    @Test
    public void longTest(){
        long a = 1;
    }

    @Test
    public void shortTest(){
        short a = 1;
    }

    @Test
    public void floatTest(){
        float a = 1;

    }

    @Test
    public void booleanTest(){
        boolean a = false;

    }

    @Test
    public void byteArrayTest(){
        byte[] b = {1,2,3};
        Arrays.asList(b).stream().forEach(e->{
            System.out.println(e);
        });
        System.out.println("*******分割线*******");
        Arrays.asList(b).stream().forEach(e->{
            System.out.println(Arrays.toString(e));
        });
    }





}
