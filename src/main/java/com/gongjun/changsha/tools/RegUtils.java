package com.gongjun.changsha.tools;

import org.junit.Test;

/**
 * @Description:
 * @Author: GongJun
 * @Date: Created in 9:46 2020/10/20
 */
public class RegUtils {

    /**
     * @param: [s]
     * @description: 字符串去除空格
     * @author: GongJun
     * @time: Created in 16:48 2020/10/20
     * @modified: 
     * @return: java.lang.String
     **/
    public static String delAllSpaceForString(String s){
        if(s==null) return null;
        return s.replaceAll("[　*| *| *|//s*]*", "")
                .replaceAll("^[　*| *| *|//s*]*", "")
                .replaceAll("[　*| *| *|//s*]*$", "")
                //针对工业处理
                .replaceAll("\\:|\\：|\\、|\\#","")
                ;
    }


    @Test
    public void test(){
        String s = "：：：：abcdefg:12345：ABCDEFG、!@#$%^&*()、、、、、";
        s = s.replaceAll("\\:|\\：|\\、","");
        System.out.println(s);
    }
}
