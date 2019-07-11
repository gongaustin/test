package com.gongjun.J20190522;

import com.sun.deploy.util.ArrayUtil;

import java.util.Arrays;

/**
 * @Description:快速排序算法
 * @Author:GongJun
 * @Date:2019/5/22
 */
public class quiCKSort {

    public  static void sort(int[] aa){

        int temp;
        for (int i = 0; i < aa.length; i++) {
            for (int j = 0; j < aa.length-1; j++) {
                if(aa[j]<aa[i]){
                    temp =  aa[j];
                    aa[j] = aa[i];
                    aa[i] = temp;
                }
            }

        }

        Arrays.stream(aa).forEach(e-> System.out.println(e));

    }

    public static void main(String[] args) {
//        sort(new int[]{1,78,5,3,9});
        System.out.println(Math.round(1.5));
        float a = 6.56f;
        int b = (int)a;
        System.out.println("a="+a);
        System.out.println("b="+b);

        double d = 5.7687654365645765768768679879789870;   //双精度

        double c = a;

        float e = (float) d; //单精度
        System.out.println("d="+d);
        System.out.println("e="+e);

        String s = "abcdefghijklmn1234567890";
        StringBuffer sb = new StringBuffer(s);
        sb.reverse();
        System.out.println(sb.toString());

    }



}
