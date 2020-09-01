package com.gongjun.J20200430;

import org.junit.Test;

/**
 * @Description:递归测试
 * @Author:GongJun
 * @Date:2020/4/30
 */
public class DiGui {

    public int sum(int a){
        if(a<100) {
            System.out.println(a++);
            return sum(a);
        }
        if(a>=100) System.out.println(a);
        return a;
    }



    @Test
    public void a(){
        sum(1);
    }

    public int fun(int m){
        if(m<2) return 1;
        return fun(m-1)+fun(m-2);
    }

    @Test
    public void other(){
        System.out.println(fun(2));
    }

    //计算1^2+2^2+3^2+4^2+...+n^2
    public int b(int n){
        int sum = 0;
        if(n == 1) sum = 1;
        else{
            sum = new Double(Math.pow(n,2)).intValue()+b(n-1);
        }
        return sum;
    }

    @Test
    public void square(){
        System.out.println(b(122));
    }
}
