package com.gongjun.J20200824;

import org.junit.Test;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @Description:
 * @Author:GongJun
 * @Date:2020/8/26
 */
public class JavaStream {

    @Test
    public void aa(){
        //stream应用
        Arrays.asList(1,2,3,4)
                .stream()
                .map(n -> Math.pow(n,2))
                .collect(Collectors.toList())
                .forEach(e -> System.out.println(this.doubleToInt(e)));
        /*
         1
         4
         9
         16
         */
    }
    private int doubleToInt(Double d){
        Double dle = new Double(d);
        return dle.intValue();
    }
}
