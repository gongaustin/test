package com.gongjun.J20200819;

import org.junit.Test;

/**
 * @Description:
 * @Author:GongJun
 * @Date:2020/8/19
 */
public class reverseString {
    @Test
    public void reverse(){
        String s = "abcdefg";
        StringBuilder sb = new StringBuilder(s); //利用StringBuffer或者StringBuilder的reverse()方法对字符串进行反转
        sb.reverse();
        System.out.println(sb.toString());
    }
}
