#final是的作用

##修饰类 final class
>类不可以被继承

##修饰方法
>被修饰的方法不可以被重写，但是可以重载（形参列表或者返回值可以改变）

##修饰变量
>被修饰的变量的值不可以改变，使用之前必须赋值，不使用可以不用赋值也不会报错


##修饰成员变量（局部变量）
>在声明的时候就要赋值（或者构造器中赋值、代码块中赋值）
>    public final int a;
 
     public test(){
         a = 1;
     }
 
     @Test
     public void test(){
         System.out.println(a);
     }
>@Test方法中声明的变量和方法必须是public才能访问
>
>
##修饰类变量（静态变量）
>在声明时就要赋值（或者静态代码块中赋值）
>例子(静态代码块中赋值)：  
>        private static final int a;
>        static {
>            a = 2;
>        }
# 为什么局部内部类和匿名内部类只能访问final变量？