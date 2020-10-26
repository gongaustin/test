package com.gongjun.J20200916;

import org.junit.Test;

/**
 * @Description:
 * @Author: GongJun
 * @Date: Created in 14:05 2020/10/26
 */
public class VolatileExample {

    int x = 0;
    volatile boolean v = false;
    public void writer() {
        x = 42;
        v = true;
    }
    public void reader() {
        if (v == true) {
            // 这里x会是多少呢？
            System.out.println(x);
        }
    }


    @Test
    public void test(){
        writer();
        reader();
    }
}
