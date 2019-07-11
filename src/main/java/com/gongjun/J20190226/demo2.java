package com.gongjun.J20190226;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * @Description:输入几个数找最大值
 * @Author:GongJun
 * @Date:2019/2/26
 */
public class demo2 {
    public static void main(String[] args) {
        while (true){
            Scanner s = new Scanner(System.in);
            String numbers = s.nextLine();
            List<Float> ss = Arrays.asList(numbers.split(",")).stream().map(e->Float.valueOf(e)).sorted().collect(Collectors.toList());
            System.out.println(ss.get(ss.size()-1));
            break;
        }
    }
}
