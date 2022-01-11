package com.gongjun.MarkdownOfStudy.lambda;

interface AustinInterface {
    void austin();

    default void aa(){
        System.out.println("aa");
    }

    static void bb(){
        System.out.println("bb");
    }

}
