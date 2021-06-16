package com.gongjun.mianshi;

import com.google.common.collect.Maps;
import org.apache.commons.compress.utils.Lists;
import org.junit.Test;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @Description:ConcurrentMap
 * @Author: GongJun
 * @Date: Created in 21:20 2021/5/31
 */
public class ConcurrentHashMapTest {
    @Test
    public void test(){
        /*
        * ConcurrentMap是个接口
        * ConcurrentHashMap是分段设计线程安全的
        * */
       ConcurrentMap cm = Maps.newConcurrentMap();

    }
}
