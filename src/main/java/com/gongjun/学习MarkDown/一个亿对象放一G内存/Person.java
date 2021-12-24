package com.gongjun.学习MarkDown.一个亿对象放一G内存;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @Description:Person对象
 * @Author:GongJun
 * @Date:2021/12/24
 */
@Builder
@Data
public class Person {
    //id
    private Long id;
    //姓名
    private String name;
    //年龄
    private Integer age;
    //地址
    private String address;
    //性别
    private Integer sex;
    //是否单身
    private Integer isSingle;
    //电话号码
    private Long cellPhone;
    //薪水
    private BigDecimal salary;
    //公司
    private String company;
    //创建时间
    private LocalDateTime createTime;
    //创建用户
    private String createUser;

}
