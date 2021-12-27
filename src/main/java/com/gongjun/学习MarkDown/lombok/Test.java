package com.gongjun.学习MarkDown.lombok;

import org.apache.commons.lang3.ObjectUtils;

import java.math.BigDecimal;

/**
 * @Description:
 * @Author:GongJun
 * @Date:2021/12/27
 */
public class Test {

    @org.junit.Test
    public void test(){

        Person personOne = Person.builder()
                .id(132432344l)
                .age(21)
                .email("232432@qq.com")
                .name("King")
                .address("华阳街道")
                .salary(BigDecimal.valueOf(15000))
                .build();


        Person personTwo = Person.builder()
                .id(132432344l)
                .age(21)
                .email("232432@qq.com")
                .name("King")
                .address("郫筒街道")
                .salary(BigDecimal.valueOf(15000))
                .build();
        System.out.println(ObjectUtils.equals(personOne,personTwo));


    }
}
