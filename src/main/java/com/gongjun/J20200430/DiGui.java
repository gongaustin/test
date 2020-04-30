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
        System.out.println(fun(12));
    }
}
