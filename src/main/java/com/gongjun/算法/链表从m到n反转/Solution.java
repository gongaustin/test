package com.gongjun.算法.链表从m到n反转;

import com.gongjun.算法.链表反转.ListNode;

/**
 * @ClassName:Solution
 * @Description://TODO 链表从m到n反转
 * @Author:AustinGong
 * @Date:11:09 2022/5/11
 * @Version:1.0
 **/

public class Solution {
    public ListNode reverseNodeFromMtoN(ListNode head,int m,int n){
        //设置一个node的默认值
        ListNode res = new ListNode(-1);
        //res的指针
        res.next = head;
        //前序节点
        ListNode pre = res;
        //当前节点
        ListNode cur = head;



        if(head == null) return null;
        //cur指向m后的节点
        for (int i = 0; i < m; i++) {

            pre = cur;
            cur = cur.next;

        }

        //从M遍历到N
        for (int i = m; i < n; i++) {
            ListNode temp = cur.next;
            cur.next = temp.next;
            temp.next = pre.next;
            pre.next = temp;

        }

        return res.next;
    }

}
