package com.gongjun.J20211210;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class test {
    public static void main(String[] args) {
        //目前lambda无法重写接口两个以上的抽象方法
        AiInterface ai = ()-> System.out.println("Austin");
        SimpleDateFormat sdf_hm = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date time = null;
        try {
            time = sdf_hm.parse("2022-02-01 12:12:12");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println(time);
    }
}
