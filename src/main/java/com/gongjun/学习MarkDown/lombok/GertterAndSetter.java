package com.gongjun.学习MarkDown.lombok;

import lombok.*;

import java.math.BigDecimal;

/**
 * @Description:
 * @Author:GongJun
 * @Date:2021/12/27
 */
public class GertterAndSetter {

    @ToString
    @EqualsAndHashCode(of = "name",exclude = {"address","salary"})
    @RequiredArgsConstructor //需要有参构造函数
    //@NoArgsConstructor //无参构造函数
    public class User{
        @Getter(AccessLevel.PRIVATE)
        @Setter
        @NonNull private String id;
        final int id2;
        private String name;
        private String email;
        private String address;
        private BigDecimal salary;
    }


}
