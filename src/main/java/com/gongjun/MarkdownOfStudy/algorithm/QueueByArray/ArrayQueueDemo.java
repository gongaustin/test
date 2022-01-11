package com.gongjun.MarkdownOfStudy.algorithm.QueueByArray;


import java.util.Scanner;

/**
 * @ClassName:Queue
 * @Description://使用数组实现一个一次性的队列
 * @Author:AustinGong
 * @Date:10:15 AM 2022/1/11
 * @Version:1.0
 **/

public class ArrayQueueDemo {

    public static void main(String[] args) {

        ArrayQueue arrayQueue = new ArrayQueue(3);

        char key = ' ';//接收用户输入
        Scanner scanner = new Scanner(System.in);

        boolean loop = true;

        //输出一个菜单
        while(loop){
            System.out.println("s(show):显示队列");
            System.out.println("e(exit):退出程序");
            System.out.println("a(add):添加数据到队列");
            System.out.println("g(get):从队列中取数据");
            System.out.println("h(head):查看队列头数据");

            key = scanner.next().charAt(0);
            switch (key){
                case 's':
                    arrayQueue.showQueue();
                    break;
                case 'a':
                    System.out.println("请输入一个数：");
                    int n = scanner.nextInt();
                    arrayQueue.addQueue(n);
                    break;
                case 'g':
                    try {
                        int res = arrayQueue.getQueue();
                        System.out.printf("取出的数据是:%d\n",res);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 'h':
                    try {
                        int head = arrayQueue.headQueue();
                        System.out.printf("队列头的数据是:%d\n",head);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 'e':
                    scanner.close();
                    loop = false;
                default:break;
            }
        }
        System.out.println("程序退出");
    }



}


//编写一个类，使用数组模拟队列

class ArrayQueue{
    int size;//队列容量
    int front;//队列头
    int rear;//队列尾
    int[] arr;//模拟队列的数组

    public ArrayQueue(int size){
        this.size = size;
        arr = new int[size];
        front = -1; //指向队列头部，指向队列头的前一个位置，不含队列头的数据
        rear = -1; //指向队列尾部，包含队列尾的数据
    }
    //判断是否为满
    boolean isFull(){
        return rear == size - 1;
    }
    //队列是否为空
    boolean isEmpty(){
        return rear == front;
    }
    //添加数据到队列
    void addQueue(int n){
        if(isFull()){
            System.out.println("队列满，无法加入数据");
            return;
        }
        rear++;//尾部指针后移
        arr[rear] = n;

    }

    //数据出队列
    int getQueue() throws Exception {
        if(isEmpty()){
            throw new Exception("队列空，无法取出数据");
        }
        front++;//队列首后移
        return arr[front];
    }
    //遍历队列
    void showQueue(){
        if(isEmpty()){
            System.out.println("队列空，无数据");
            return;
        }
        for (int i = 0; i < arr.length; i++) {
            System.out.printf("队列arr[%d]=%d\n",i,arr[i]);
        }

    }

    //显示队列的头部数据（不是取出数据）
    int headQueue() throws Exception {
        if(isEmpty()){
            throw new Exception("队列空，无数据");
        }
        return arr[front+1];
    }

}
