package com.gongjun.J20200901;

import org.junit.Test;

/**
 * @Description:基本数据类型测试类
 * @Author:GongJun
 * @Date:2020/9/3
 */
public class BasicDataTypeTest {

    @Test
    public void a(){
        int a = 1;
        Integer b = 1;
        System.out.println(a == b);  //向下拆箱
    }


}
