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

    /****************************分割线****************************/

    public int fun(int m){
        //这样效率严重有问题
        if(m == 1) return 1;
        return fun(m-1)+fun(m-2);
    }
    //计算1+2+3+...+n
    public int funNumber(int m){
        int sum = 0;
        if(m==1) sum = 1;
        else sum = m+funNumber(m-1);
        return sum;
    }

    @Test
    public void numberSum(){
        System.out.println(funNumber(100));
    }
    /****************************分割线****************************/
    //计算1^2+2^2+3^2+...+n^2
    public int squareSum(int n){
        int sum = 0;
        if(n == 1) sum = 1;
        else{
            sum = (int)Math.pow(n,2)+squareSum(n-1);
        }
        return sum;
    }

    @Test
    public void square(){
        System.out.println(squareSum(9));
    }
}
