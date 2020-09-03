package com.gongjun.J20200901;

import org.junit.Test;

import java.util.Hashtable;

/**
 * @Description: 散列表（哈希表）测试类
 * @Author:GongJun
 * @Date:2020/9/3
 */
public class HashTableTest {
    @Test
    public void test(){
        Hashtable<String,Integer> ht_one = new Hashtable<>();
        ht_one.put("aaa",1);
        int hscode_one = ht_one.hashCode();
        System.out.println(hscode_one);

        Hashtable<String,Integer> ht_two = new Hashtable<>();
        ht_two.put("aaa",1);
        int hscode_two = ht_two.hashCode();
        System.out.println(hscode_two);


        System.out.println("ht_one == ht_two is:"+(ht_one == ht_two));
        System.out.println("ht_one.equals(ht_two) is:"+(ht_one.equals(ht_two)));

        //Q&A:Q-思考两个hashtable的hashcode相等，键值对一定相等吗？A-不一定，

    }
}
