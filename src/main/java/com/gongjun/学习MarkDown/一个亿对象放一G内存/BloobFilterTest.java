package com.gongjun.学习MarkDown.一个亿对象放一G内存;

import jdk.nashorn.internal.ir.debug.ObjectSizeCalculator;

import java.time.LocalDateTime;

/**
 * @Description:
 * @Author:GongJun
 * @Date:2021/12/24
 */
public class BloobFilterTest {
    public static void main(String[] args) {
        //实例化
        MyBloomFilter filter = new MyBloomFilter();
        for (int i = 0; i < 20; i++) {
            //push到BloomFilter
            CreatePersons.getPersonList(500000).forEach(person -> filter.push(person));
        }
        //push一个确定的对象
        filter.push(CreatePersons.getFixedPerson(CreatePersons.now));
        //判断这个对象是否存在
        long start = System.currentTimeMillis();
        System.out.println(filter.contain(CreatePersons.getFixedPerson(CreatePersons.now)));
        long end = System.currentTimeMillis() - start;
        System.out.println("bloomFilter内对象数量：" + filter.getCurrentBeanCount());
        System.out.println("耗时(ms)：" + end + ",消耗内存(m)：" + (ObjectSizeCalculator.getObjectSize(filter) / (1024 * 1024)));
    }

}
