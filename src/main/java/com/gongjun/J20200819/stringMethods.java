package com.gongjun.J20200819;

import com.sun.xml.internal.ws.api.model.wsdl.WSDLOutput;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @Description:String的方法有哪些？
 * @Author:GongJun
 * @Date:2020/8/19
 */
public class stringMethods {

    @Test
    public void methodsTest(){
        String s = "abcdefghijk";

        int a = s.length(); //length()方法,获取长度
        System.out.println(a);

        char b = s.charAt(2); //charAt()方法
        System.out.println(b);

        List<String> ss = Arrays.asList(s.split("g"));//split()方法分割字符串

        ss.stream().forEach(e-> System.out.println(e));

        System.out.println("**********************");

        s = s.replace("abc","ABC");//还有replaceAll()

        System.out.println("new s is:"+s);

        s = s.toLowerCase();
        System.out.println("小写:"+s);

        s = s.toUpperCase();

        System.out.println("大写:"+s);

        String subs = s.substring(2,5); //截取字符串
        System.out.println("sub s is:" + subs);

        s = "     324353454365436 3 436 36 34 63     ";
        System.out.println(s.length());
        //trim()方法去除两端的空白
        System.out.println(s.trim().length());

        //String重写了equals()方法

        System.out.println(s.equals("abc"));

        System.out.println(s.replace(" ","")); //3243534543654363436363463  效果同replaceAll()

        Arrays.asList(1,2,3,4)
                .stream()
                .map(n->n*n)
                .collect(Collectors.toList())
                .forEach(e-> System.out.println(e));
        /*
         1
         4
         9
         16
         */


    }
}
