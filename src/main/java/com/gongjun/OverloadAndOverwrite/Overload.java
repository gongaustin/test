package com.gongjun.OverloadAndOverwrite;

import org.junit.Test;

/**
 * @Description:
 * @Author: GongJun
 * @Date: Created in 17:51 2021/6/29
 */
public class Overload {

    public String test(int a,int b){
        return "a";
    }

    private String test(int a,String b){
        return "b";
    }

    @Test
    public void a(){
        String a = test(1,"2");
        System.out.println(a);
    }
}