package com.gongjun.J20190522;


/**
 * @Description:
 * @Author:GongJun
 * @Date:2019/6/5
 */
public class test {

    /**
     * 取随机数
     * */
    private static int random(){
        int[] a = new int[]{1,2,3};
        int b = a[(int)(Math.random()*a.length)];
        return b;
    }

    /**
     * 初始报数
     * */
    public static void baoshu(int c){
        int count = 1;
        while (c<50){
            c=random()+c;
            count++;
            if(c>=47) {
                System.out.println("第"+(count%2==0?2:1)+"个报数的人赢了!");
                break;
            }
        }
    }

    public static void main(String[] args) {

        for (int i = 0; i < 100; i++) {

            baoshu(2);

        }


    }



}
