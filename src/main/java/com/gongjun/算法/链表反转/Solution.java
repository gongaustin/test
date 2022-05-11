package com.gongjun.算法.链表反转;

import org.junit.Test;

/**
 * @ClassName:Solution
 * @Description://TODO  反转链表数据，如果null则返回null,实例：{1,2,3}->{3,2,1}
 * @Author:AustinGong
 * @Date:10:07 2022/5/11
 * @Version:1.0
 **/

public class Solution {
    public ListNode reverseListNode(ListNode head){

        if(head == null)
            return null;
        ListNode cur = head;
        ListNode pre = null;
        //while循环置换位置
        while(cur != null){
            //下个指针存放到temp中
            ListNode temp = cur.next;

            //指针指向前一个
            cur.next = pre;
            //向后移动
            pre = cur;

            cur = temp;




        }

        return pre;

    }


    @Test
    public void test(){
        ListNode newnode = this.reverseListNode(new ListNode(1,new ListNode(2)));
        System.out.println(newnode);
    }
}
