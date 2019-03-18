package t2_basic_java;

public class Topic2 {
    /*
        0. Java执行过程、Java组成、Java的特点、Java和C++、C的区别。
        1. 八种基本类型，每种类型都有对应的对象
            基本类型强转可能会出现精度问题
            float、double的精度问题（符号位、指数位、底数位）
            涉及金钱交易会使用类BigDecimal
        2. Java字符串
            （1）char数组
            （2）字符串常量
                a.编译期能确定——字符串常量
                    String a = "aaa";
                    String b = "aaa" + "a";
                b.运行时确定
                    String c1 = new String("aaa")
                    String c2 = new String(c1)
                    String d = "bbb".subString(0,2) + "c";
                    String e = a + b;
                c.intern方法
                    查看此字符串是否已存在于字符串常量池，若存在则返回；否则创建再返回。
            （3）String为什么是不可变的？
                a.常量池节省资源
                b.线程安全
                c.保证了安全性——密码、Socket端口号、类全限定名
                d.作为HashMap的key，必须不可更改（否则hashcode改变）
            （4）StringBuffer和StringBuilder
                前者线程安全（Synchronized实现）、后者性能更好（无锁）。
            （5）用equals比较
            （6）String的常用API

         3.传值调用
            即把变量的值，复制给形参，传入方法
                swapInt(int a,int b) 无效
            特别要注意的是对于对象类型，我们传入的是对象的地址，所以对对象进行操作的时候，其实会改变原对象
            在开发时，我们要格外注意这一问题:
                (1)无意义的交换
                (2)修改了外部的对象，对外部产生影响

         4.空指针问题
     */
    public static void main(String[] args) {
        String a = "aaa";
        String b = "bbb";
        swap(a,b);
        System.out.println(a+"  "+b);
    }

    static void swap(String a, String b){
        String temp=a;
        a=b;
        b=temp;
    }

}
