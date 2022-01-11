package com.gongjun.MarkdownOfStudy.algorithm.QueueByArray;

/**
 * @ClassName:Queue
 * @Description://使用数组实现一个一次性的队列
 * @Author:AustinGong
 * @Date:10:15 AM 2022/1/11
 * @Version:1.0
 **/

public class Queue {

    int size;//队列的Size
    int front;//队列首
    int rear;//队列尾
    int[] arr;

    Queue(int size){
        this.size = size;
        arr = new int[this.size];
        this.front = -1;
        this.rear = -1;
    }
    //添加元素
    void addQueue(int ele){

    }

    void getQueue(){

    }


    void showQueue(){

    }

}
