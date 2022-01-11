package com.gongjun.MarkdownOfStudy.HundredMillionObjectsIntoRAM;

import jdk.nashorn.internal.ir.debug.ObjectSizeCalculator;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * @Description:测试类
 * @Author:GongJun
 * @Date:2021/12/24
 */
public class ArrayListTest {
    public static void main(String[] args) {
        ArrayList<Person> arrayList = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            arrayList.addAll(CreatePersons.getPersonList(500000));
        }
        //add一个确定的person
        arrayList.add(CreatePersons.getFixedPerson(LocalDateTime.now()));
        long start = System.currentTimeMillis();
        System.out.println(arrayList.contains(CreatePersons.getFixedPerson(LocalDateTime.now())));
        System.out.println("arrayList内对象数量" + arrayList.size());
        long end = System.currentTimeMillis() - start;
        System.out.println("耗时(ms)：" + end + ",消耗内存(m)：" + (ObjectSizeCalculator.getObjectSize(arrayList) / (1024 * 1024)));
    }

}
