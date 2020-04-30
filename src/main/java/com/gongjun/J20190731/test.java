package com.gongjun.J20190731;

import java.util.Scanner;

/**
 * @Description:
 * @Author:GongJun
 * @Date:2019/12/20
 */
public class test {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        if(scanner.hasNextInt()){
            int a = scanner.nextInt();
            int b = a*a;
            for (int j = 1; j <= b; j++) {
                 System.out.print(j+" ");
                 if(j%a==0) System.out.println();
            }
        }
    }
}
