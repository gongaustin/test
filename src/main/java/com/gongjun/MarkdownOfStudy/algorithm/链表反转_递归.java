package com.gongjun.MarkdownOfStudy.algorithm;

/**
 * @Description:
 * @Author:GongJun
 * @Date:2021/12/10
 */
public class 链表反转_递归 {

    static class ListNode{
        int val; //值
        ListNode next; //指针
        public ListNode(int val,ListNode next){
            this.val = val;
            this.next = next;
        }
    }

    static ListNode recursion(ListNode node){
        //获取最后一个节点
        if(node == null || node.next == null ) return node;
        ListNode new_node = recursion(node.next);
        //反转指针
        node.next.next = node;
        node.next = null;

        return new_node;

    }



    public static void main(String[] args) {
        ListNode node5 = new ListNode(5,null);
        ListNode node4 = new ListNode(4,node5);
        ListNode node3 = new ListNode(3,node4);
        ListNode node2 = new ListNode(2,node3);
        ListNode node1 = new ListNode(1,node2);

        ListNode prev = recursion(node1);
        System.out.println(prev);

    }


}
