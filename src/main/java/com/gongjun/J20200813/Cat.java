package com.gongjun.J20200813;

/**
 * @Description:
 * @Author:GongJun
 * @Date:2020/8/13
 */
public class Cat {

    private String name;
    private int age;
    private String color;
    //rewrite constructor
    public Cat(String name){
        this.name = name;
    }

    public Cat(String name,int age,String color){
        this.name = name;
        this.age = age;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
