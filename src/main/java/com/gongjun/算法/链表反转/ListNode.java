package com.gongjun.算法.链表反转;

/**
 * @ClassName:ListNode
 * @Description://TODO
 * @Author:AustinGong
 * @Date:10:11 2022/5/11
 * @Version:1.0
 **/

//初始化
public class ListNode {        //类名 ：Java类就是一种自定义的数据结构
    public int val;            //成员变量：数值
    public ListNode next;      //对象 ：引用下一个节点对象。在Java中没有指针的概念，Java中的引用和C语言的指针类似


    public ListNode(int val){   //一个参数的构造方法
        this.val=val;     //把接收的参数赋值给当前类的val变量
    }
    public ListNode(int val, ListNode next) { this.val = val; this.next = next; }//这个就是包含两个参数的构造方法

}