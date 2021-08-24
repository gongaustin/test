package com.gongjun.DatastructureAndAlgorithm;

import org.junit.Test;

/**
 * @Description:
 * @Author: GongJun
 * @Date: Created in 13:57 2021/8/23
 */
public class kmp {
    @Test
    public void test(){
        String a = "rweferge fgrthtyjryujryjryung gongjun fhrj ty ";
        String b = "gongjun";
        int c = a.indexOf(b);
        /**
         * >  0 : 含有且返回位置
         * = -1 : 不含有
         * */
        System.out.println(c);
    }
}
