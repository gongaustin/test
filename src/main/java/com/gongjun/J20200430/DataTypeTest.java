package com.gongjun.J20200430;


import org.junit.Test;

/**
 * @Description:数据类型测试题目
 * @Author:GongJun
 * @Date:2020/4/30
 */
public class DataTypeTest {
    @Test
    public void a(){
        float a = 0.125f;
        double b = 0.125d;
        System.out.println((a-b) == 0.0); //true
        //首先浮点数是由符号位、指数位、有效数字三个部分组成，而0.125f、0.125d均可以精确的表示出来，不存在精度丢失，因而a-b==0.0。
    }
    @Test
    public void b(){
        double a = .5d;
        double b = .4d;
        double c = .3d;
        System.out.println((a-b) == (b-c)); //false
        System.out.println("a-b="+(a-b));
        System.out.println("b-c="+(b-c));
        //类似十进制里面的分数1/3，就是无限循环数，无法精确表示出来，同理浮点数里有些数值也没法精确表示出来。
    }
    @Test
    public void c(){
        System.out.println(1.0 / 0);  //Infinity(无穷大)
        //在整型运算中，除数是不能为0的，否则直接运行异常。但是在浮点数运算中，引入了无限这个概念。可以看一下源码Double的定义, public static final double POSITIVE_INFINITY = 1.0 / 0.0；

        System.out.println("-----------------------------------------");

        System.out.println(1 / 0);//java.lang.ArithmeticException: / by zero
    }

    @Test
    public void d(){

        System.out.println(0 / 0.0); //NaN
        System.out.println("-----------------------------------------");

        System.out.println(0.0 / 0); //NaN
        System.out.println("-----------------------------------------");

        System.out.println(0.0 / 0.0); //NaN
        System.out.println("-----------------------------------------");

        System.out.println(0 / 0); //java.lang.ArithmeticException: / by zero

        /*
        java源码，Double包装类下，public static final double NaN = 0.0d / 0.0；NAN表示非数字，它与任何值都不相等，甚至不等于它自己。
         */

    }

    @Test
    public void e(){
        /*
         >>和>>>的区别是？
            A. 任何整数没有区别
            B. 负整数一定没有区别
            C. 浮点数可以>>运算，但是不可以>>>运算
            D. 正整数一定没有区别
            (D)
            解释：>>>表示不带符号向右移动二进制数，移动后前面统统补0；>>表示带符号移动，正整数的符号位是0，因而两者没有区别
         * */
        System.out.println(5>>>2); //1
        System.out.println(5>>2);  //1

        System.out.println(-5>>>2);  //1073741822
        System.out.println(-5>>2);  //-2
        System.out.println("-----------------------------------------");
        /*
         某个类有两个重载方法：void f(String s) 和 void f(Integer i)，那么f(null)的会调用哪个方法？
            A. 前者
            B. 后者
            C. 随机调用
            D. 编译出错
            (D)
            解释：1）精确匹配->2）基本数据类型（自动转换成更大范围）->3）封装类（自动拆箱与装箱）->4）子类向上转型依次匹配->5）可变参数匹配。子类向上转型，两者的父类都是object类（null默认类型是object），因而会同时匹配上两者，编译器会报Ambiguous method call. Both错误
         * */
        System.out.println("-----------------------------------------");
        /*
        某个类有两个重载方法：void g(double d) 和 void g(Integer i)，那么g(1)的会调用哪个方法？
            A. 前者
            B. 后者
            C. 随机调用
            D. 编译出错
            (A)
            解释：1）精确匹配->2）基本数据类型（自动转换成更大范围）->3）封装类（自动拆箱与装箱）->4）子类向上转型依次匹配->5）可变参数匹配，本题是进行到第二步匹配上了。
        * */
        System.out.println("-----------------------------------------");
        /*
        String a = null; switch(a)匹配case中的哪一项？
            A. null
            B. "null"
            C. 不与任何东西匹配，但不抛出异常
            D. 直接抛出异常
            (D)
            解释：在Java编程语言的设计者的判断中，这比静默跳过整个开关语句要合理，因为使用null作为开关标签的话，编写的代码将永远不会执行。
        * */
        String bb = "123";
        final String aa = "21321";
        switch (bb){
            case aa: //null不能通过编译
                System.out.println(1);
            default:
                System.out.println(0);
        }


        System.out.println("-----------------------------------------");
        /*
        <String, T, Alibaba> String get(String string, T t) { return string; } 此方法：
            A. 编译错误，从左往右第一个String处
            B. 编译错误，T处
            C. 编译错误，Alibaba处
            D. 编译正确
            (D)
            解释：尖括号里的每个元素都指代一种未知类型，在定义处只具备执行Object方法的能力，在编译期间，所有的泛型信息都会被擦除，编译后，get()的两个参数是Object，返回值也是Object。
        * */

        System.out.println("-----------------------------------------");
        /*
        HashMap初始容量10000即new HashMap(10000)，当往里put 10000个元素时，需要resize几次（初始化的那次不算）？
            A. 1次
            B. 2次
            C. 3次
            D. 0次
            (D)
            解释：比10000大，且最接近的2的n次方数是16384，默认负载因子是0.75，16384*0.75 = 12288>10000，因而不需要扩容。
        * */
    }


    //编译通过，虽然Alibaba是未知类型的变量，编译时所有泛型将被擦除
    @Test
    <String,T,Alibaba> String get(String s,T t){
        return  s;
    }
}
