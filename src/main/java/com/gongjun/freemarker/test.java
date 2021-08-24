package com.gongjun.freemarker;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @Author: GongJun
 * @Date: Created in 10:44 2021/8/24
 */
@RestController
public class test {
    @Value("${my.name}")
    private String name;
    @Value("${my.age}")
    private int age;

    @GetMapping("/my")
    public String myinfo(){
       return this.name+"--"+this.age;
    }

}
