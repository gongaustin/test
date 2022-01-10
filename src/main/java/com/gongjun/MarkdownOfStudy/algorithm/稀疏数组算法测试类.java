package com.gongjun.MarkdownOfStudy.algorithm;

import org.junit.Test;

import java.util.Arrays;

/**
 * @ClassName:稀疏数组算法测试类
 * @Description://TODO
 * @Author:AustinGong
 * @Date:2:33 PM 2022/1/10
 * @Version:1.0
 **/

public class 稀疏数组算法测试类 {
    //创建一个二维数组 11*11
    int[][] createData() {
        int[][] originArray = new int[11][11];
        originArray[1][2] = 1;
        originArray[2][3] = 2;
        //打印原始数组
        for (int[] row : originArray) {
            for (int i : row) {
                System.out.print(i + "\t");
            }
            System.out.println();
        }

        return originArray;

    }

    @Test
    public void test() {
        int[][] originArray = createData();

        //二维数组转稀疏数组
        //1.
        int sum = 0;
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 11; j++) {
                if (originArray[i][j] != 0) {
                    sum++;
                    System.out.println("非0值位置：i=" + i + ",j=" + j);
                }
            }
        }

        //创建稀稀疏数组
        int[][] sparseArray = new int[sum + 1][3];
        //记录原始二维数组的信息
        sparseArray[0][0] = 11;
        sparseArray[0][1] = 11;
        sparseArray[0][2] = sum;
        //记录非0值得信息
        int count = 0;
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 11; j++) {
                if (originArray[i][j] != 0) {
                    count++;
                    //记录非0值的行列值信息[行 列 值]
                    sparseArray[count][0] = i; //行
                    sparseArray[count][1] = j; //列
                    sparseArray[count][2] = originArray[i][j]; //值
                }
            }
        }
        System.out.println("--------------------");
        //打印稀疏数组
        for (int[] row : sparseArray
        ) {
            for (int i : row
            ) {
                System.out.print(i + "\t");
            }
            System.out.println();
        }

        System.out.println("--------------------");
        //另一种打印法
        for (int i = 0; i < sparseArray.length; i++) {
            System.out.printf("%d\t%d\t%d\t\n",sparseArray[i][0],sparseArray[i][1],sparseArray[i][2]);
        }

        //稀疏数组转二维数组，过程免，很简单


    }
}
