package com.gongjun.changsha.tools;

import org.junit.Test;

/**
 * @Description:
 * @Author: GongJun
 * @Date: Created in 9:46 2020/10/20
 */
public class RegUtils {

    //去除字符串的空格
    public static String delAllSpaceForString(String s){
        if(s==null) return null;
        return s.replaceAll("[　*| *| *|//s*]*", "")
                .replaceAll("^[　*| *| *|//s*]*", "")
                .replaceAll("[　*| *| *|//s*]*$", "");
    }
}
