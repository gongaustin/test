package com.gongjun.J20210609;

import org.junit.Test;

import java.util.TreeSet;

/**
 * @Description:
 * @Author: GongJun
 * @Date: Created in 15:36 2021/6/3
 */
public class testOne {

    @Test
    public void run(){
        Integer a=128,b=128,c=127,d=127;
        a = Integer.valueOf(128);
        System.out.println(a);
        System.out.println(b);
        System.out.println(c);
        System.out.println(d);
        System.out.println(a==b);
        System.out.println(a.equals(b));
        System.out.println(c==d);
    }

    TreeSet<String> ts = new TreeSet<>(
            ((o1, o2) -> Integer.compare(o1.length(),o2.length()))
    );

}
