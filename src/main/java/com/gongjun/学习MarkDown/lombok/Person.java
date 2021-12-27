package com.gongjun.学习MarkDown.lombok;

import lombok.*;

import java.math.BigDecimal;

/**
 * @Description:
 * @Author:GongJun
 * @Date:2021/12/27
 */
@Data
@Builder
@RequiredArgsConstructor  //需要带参数的构造函数
@ToString
@EqualsAndHashCode(of = {"id","name"})
public class Person {
    private long id;
    private String name;
    private String email;
    private Integer age;
    private BigDecimal salary;
    private String address;

    public Person(long id, String name, String email, Integer age, BigDecimal salary, String address) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.age = age;
        this.salary = salary;
        this.address = address;
    }
}
