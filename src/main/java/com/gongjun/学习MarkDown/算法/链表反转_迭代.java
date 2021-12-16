package com.gongjun.学习MarkDown.算法;

import com.aspose.cells.ListBox;

/**
 * @Description:
 * @Author:GongJun
 * @Date:2021/12/10
 */
public class 链表反转_迭代 {
    /*
    将单链表的连接顺序反转（reverse）过来
    1-2-3-4-5
    * **/
    static class ListNode{
        int val; //值
        ListNode next; //指针
        public ListNode(int val,ListNode next){
            this.val = val;
            this.next = next;
        }
    }

    //方法一，迭代 传入一个节点head(链表)

    public static ListNode iterate(ListNode head){
        ListNode prev = null,next;
        ListNode curr = head;
        while (curr != null){
            next = curr.next; // 保存指针
            curr.next = prev;  //赋值
            //移动
            prev = curr;
            curr = next;
        }
        return prev;
    }


    public static void main(String[] args) {
        ListNode node5 = new ListNode(5,null);
        ListNode node4 = new ListNode(4,node5);
        ListNode node3 = new ListNode(3,node4);
        ListNode node2 = new ListNode(2,node3);
        ListNode node1 = new ListNode(1,node2);


        ListNode prev = iterate(node1);
        System.out.println(prev);
    }
}
