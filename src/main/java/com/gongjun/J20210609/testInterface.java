package com.gongjun.J20210609;

/**
 * @Description:
 * @Author: GongJun
 * @Date: Created in 9:31 2021/7/13
 */
public interface testInterface {
    static String a = "b";
    default String test(){
        return "aa";
    }
    void aa();
    default void bb(){

    }
}
