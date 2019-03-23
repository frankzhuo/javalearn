package t2_basic_java;

public class Topic2 {
    /*
        0. Java执行过程、Java组成、Java的特点、Java和C++、C的区别。
            1.所有属性的设为默认值
            2.父类中的静态属性初始化，静态代码块，静态方法的声明（按照出现顺序）
            3.子类中的静态属性初始化，静态代码块，静态方法的声明（按照出现顺序）
            4.父类中的非静态属性初始化，普通代码块，普通方法的声明（按照出现顺序），父类的构造方法
            5.子类中的非静态属性初始化，普通代码块，普通方法的声明（按照出现顺序），子类的构造方法

            1.java编程语言
            2.java文件格式
            3.Java虚拟机
            4.java应用程序接口javaAPI

            1.Java是面向对象的语言
            2.java内置对多线程的支持
            3.java是静态语言，类型的判断是在编译期进行，java的反射机制又体现了一定的动态性，通过反射可以获得运行类的信息
              例如类的属性，类的方法信息，类的构造信息。

            1.解释和编译 java是一种跨平台语言，java源文件编译成字节码文件后，可以在不同的平台上运行，依靠的是jvm将字节码
              文件翻译成二进制执行，c++是编译语言，程序只能在特定的操作系统上编译和运行
            2.java内存安全性性更高，当出现数组越界的时候会返回错误提示，c++更为灵活，但可能访问非法内存并引起严重错误
              java虚拟机会进行垃圾回收，C++需要程序员进行内存的回收操作
            3.java程序的执行效率低于c++，但即时编译等技术使得java执行效率大大提高
            4.c++中可以使用指针，java中只有值传递
            5.c++可以对运算符进行重载，java不能对运算符重载

        1. 八种基本类型，每种类型都有对应的对象
            整型 byte（8） short（16） int（32） long（64）
            浮点型 float（32） double（64）
            boolean 单个boolean使用的是int类型，boolean数组使用的是byte 一个元素8位
            char （16）
            基本类型强转可能会出现精度问题
            float、double的精度问题（符号位、指数位、尾数位）float可能不能精确表示一个数 比如0.6 只能无限接近
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
                都是final的不能被继承
                前者线程安全（Synchronized实现）、后者性能更好（无锁）。
            （5）用equals比较
                Object中的equals比较的是是否是同一个对象
                String中的equals进行了重写 先比较是否是同一对象 不是在比较内容是否相等
            （6）String的常用API
                toCharArray() 生成字符数组
                equals() 判断是否相等
                indexof() 字符序列在字符串中的起始位置
                length() 字符串的长度
                valueOf() 其它类型转变为字符串
                chatAt() 返回某个位置的字符
                contains() 是否包含

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
        new StringBuilder();
        new StringBuffer();
    }

    static void swap(String a, String b){
        String temp=a;
        a=b;
        b=temp;
        new Object();
        new String();
    }

}
