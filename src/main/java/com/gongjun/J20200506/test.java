package com.gongjun.J20200506;

import org.junit.Test;


/**
 * @Description:冒泡排序
 * @Author:GongJun
 * @Date:2020/5/6
 */
public class test {
    @Test
    public void a(){

        int[] arr = {0,4,8,9,2,89,56};
        int temp;
        for (int i = 0; i< arr.length;i++){
            for (int j = 0; j < arr.length; j++) {
                if(arr[j]>arr[i]){
                    temp = arr[j];
                    arr[j] = arr[i];
                    arr[i] = temp;
                }
            }
        }
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i]+"\t");
        }
    }

}
