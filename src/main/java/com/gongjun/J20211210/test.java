package com.gongjun.J20211210;

public class test {
    public static void main(String[] args) {
        //目前lambda无法重写接口两个以上的抽象方法
        AiInterface ai = ()-> System.out.println("Austin");
    }
}
