package com.gongjun.J20211013;

import org.junit.Test;

/**
 * @Description:
 * @Author: GongJun
 * @Date: Created in 18:44 2021/10/13
 */
public class iPlus {
    @Test
    public void test(){
        int i = 0;
        i = i++ + ++i;
        System.out.println(i);
    }
}
  