package com.gongjun.J20211210;

public interface AiInterface {
    void austin();

    default void aa(){
        System.out.println("aa");
    }
    static void bb(){
        System.out.println("bb");
    }
}
