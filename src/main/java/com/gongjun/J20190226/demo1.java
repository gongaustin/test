package com.gongjun.J20190226;

import java.util.Scanner;

/**
 * @Description:比较输入两个整数的大小
 * @Author:GongJun
 * @Date:2019/2/26
 */
public class demo1 {
    public static void main(String[] args) {
        System.out.println("请输入两个整数");
        int a;
        int b;
        while (true){
            try {
                Scanner s = new Scanner(System.in);
                a = s.nextInt();
                b = s.nextInt();
            } catch (Exception e) {
                System.out.println("请输入整数！");
                continue;
            }
            break;
        }
        String result = a==b?a+"="+b:(a>b?a+">"+b:a+"<"+b);
        System.out.println(">>>:"+result);
    }
}
