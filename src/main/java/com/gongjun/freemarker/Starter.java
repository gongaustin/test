package com.gongjun.freemarker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Description:
 * @Author: GongJun
 * @Date: Created in 10:47 2021/8/24
 */
@EnableAutoConfiguration
@ComponentScan("com.gongjun.freemarker")
public class Starter {

    public static void main(String[] args) {
        SpringApplication.run(Starter.class,args);
    }

}
