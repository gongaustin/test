package com.gongjun.J20200824;

/**
 * @Description:Java IO流的学习
 * @Author:GongJun
 * @Date:2020/8/21
 */
public class JavaIO {
    /* 功能区分：输入流input
               输出流output
     * 类型区分：字节流 byte (8bit) 传输单位：8个字节
               字符流 String (16bit) 传输单位：16个字节
     */

    //BIO、NIO、AIO区别

    /*
     * AIO 是彻底的异步通信。
     * NIO 是同步非阻塞通信。
     * 有一个经典的举例。烧开水。
     * 假设有这么一个场景，有一排水壶（客户）在烧水。

     * AIO的做法是，每个水壶上装一个开关，当水开了以后会提醒对应的线程去处理。
     * NIO的做法是，叫一个线程不停的循环观察每一个水壶，根据每个水壶当前的状态去处理。
     * BIO的做法是，叫一个线程停留在一个水壶那，直到这个水壶烧开，才去处理下一个水壶。
     * */

    /*
     * BIO:Block IO,同步阻塞式IO，就是平常使用的传统IO，特点是模式简单方便，并发处理能力低
     * */

    /*
     * NIO 即New IO,新IO
     * */

    /*
     * AIO 混合IO
     * */


}
