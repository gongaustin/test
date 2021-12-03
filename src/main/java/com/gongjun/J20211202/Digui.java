package com.gongjun.J20211202;

import org.junit.Test;

/**
 * @Description:
 * @Author:GongJun
 * @Date:2021/12/2
 */
public class Digui {

    /**
     * 程序从上往下执行的，先递归后打印，那打印出来的数字就是从小到大的顺序
     * */
    public void aa(int a){
        if(a>0)
        {
            aa(a-1);
            System.out.println(a);
        }
    }

    @Test
    public void bb(){
        aa(50);
    }

}
