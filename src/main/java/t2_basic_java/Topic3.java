package t2_basic_java;

import java.io.*;

public class Topic3 {
    /*
        0.为什么需要面向对象？
          面向过程：
            优点：性能比面向对象高，因为类调用时需要实例化，开销比较大，比较消耗资源;比如单片机、嵌入式开发、
                 Linux/Unix等一般采用面向过程开发，性能是最重要的因素。
            缺点：没有面向对象易维护、易复用、易扩展
          面向对象：
            优点：易维护、易复用、易扩展，由于面向对象有封装、继承、多态性的特性，可以设计出低耦合的系统，使系统 更加灵活、更加易于维护
            缺点：性能比面向过程低（可以忽略）
          面向过程的语言不适合开发大型应用。
            原文：https://blog.csdn.net/jerry11112/article/details/79027834

        1.面向对象三大特征
            封装（private、protected、默认、public）
            继承（复用逻辑）
            多态（Cat、Dog、Sheep都继承于Animal类，一个Animal变量引用不同子类时，他的eat方法表现不同）
                基于继承、接口实现。

        2.对象初始化步骤(1)：
            静态成员变量>静态初始化块>成员变量>初始化块>构造方法
            一般顺序为 先静态，后非静态，先变量，后初始化块，再是构造方法
            （1）静态成员变量
            （2）然后静态初始化块
            （3）所有域被初始化为默认值（0，false，null）
            （4）按照声明顺序，依次执行初始化语句、初始化块
            （5）执行构造器
        3.方法
            （1）Java中，方法的签名是：方法名+参数类型列表（多做OJ，找到自己对语法理解有误的地方）
            （2）方法的重载
        4.继承
            一个类如果没有声明构造函数。会默认提供一个无参构造函数，一旦声明了构造函数，那就不会再提供无参的构造函数了。
            子类的构造方法要调用父类的构造方法，不显示写，会调用父类的无参构造函数，若父类没有，不能通过编译。

            访问权限不同的方法覆盖，子类只能相对父类越来越宽松，例如父类是public，子类就不能是缺省或protect，private

        5.有继承的情况下，对象初始化步骤(2)：
            （1）父类静态成员和静态初始化块，按在代码中出现的顺序依次执行。
            （2）子类静态成员和静态初始化块，按在代码中出现的顺序依次执行。
            （3）父类的实例成员和实例初始化块，按在代码中出现的顺序依次执行。
            （4）执行父类的构造方法。
            （5）子类实例成员和实例初始化块，按在代码中出现的顺序依次执行。
            （6）执行子类的构造方法。
            https://www.cnblogs.com/mcxiaotan/p/8059173.html

        7.Object
            (1)Object是所有类的父类；
            (2)有哪些方法？他们的作用？

        8.接口

        8.对象克隆
            Cloneable接口，标记接口；
                实际克隆的步骤：
                    （1）实现Cloneable接口；
                    （2）clone方法改为public；
                    （3）调用父类的clone方法，并合理实现深克隆。

            class A implements Cloneable{
                B b;
                int a;

                public A(B b,int a){
                    ...
                }

                public Object clone()...{
                    B b=this.b.clone();
                    return new A(b,this.a);
                }
            }

        9.序列化
            把Java对象转换为字节序列，用于存储或网络传输。（序列化的是实例属性）
            Java的序列化是比较低效的，其他的选择有Thrift、Hessian、Protobuf等
            Serializable接口，标记接口；
                （1）serialVersionUID：
                    默认由类名、接口名、成员属性、方法名共同生成；
                    一般，我们会将此值设为一个固定的值，让该对象可以保证版本兼容性
                    对于删除的属性，那我们会忽略它；对于新增属性，会设置为null或0。
                （2）transient：表示该属性不会被序列化
                （3）writeObject、readObject
                        a.数组不能向我们预期的那样进行序列化，和反序列化（待考证，下午给答案）
                        b.对于一个属性，它是一个稀疏的数据结构，我们按照默认方式对其进行序列化和反序列化，会产生大量冗余的比特位，造成存储和网络传输的低效；
                          所以，我们通过把它定义为transient，使其不参加默认的序列化过程，而通过writeObject、readObject实现

        10.反射
            提供了"运行时"分析以及操纵类和对象的能力。

            如何提高反射的性能？
                （1）使用缓存：缓存class对象
                （2）尽量在启动阶段使用反射（Spring）
                （3）使用更高版本的JDK

        11.异常
            结构
                Throwable
                Error：系统内部错误、资源耗尽错误
                Exception
                    RuntimeException：空指针、数组越界等

                    Other Exception：

            另一种分类：
                Checked Exception：必须捕获，强迫我们对一些不受控的异常情况进行处理；
                Unchecked Exception：

            常见的Exception
                NPE（NullPointerException）
                ClassNotFoundException
                IOException
                ArrayIndexOutOfBoundException
                OutOfMemoryError（OOM）
                InterruptedException

            try-catch-finally
                先产生的异常被抑制，后产生的异常被抛出；
                （有可能导致我们真正关心的异常被抑制，解决办法：try-with-resource，资源变量要实现AutoClosable接口）

     */


    public static void main(String[] args) throws IOException, ClassNotFoundException {
        A a1;
        A a = new A(1,2);
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("a.txt"));
        oos.writeObject(a);
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream("a.txt"));
        Object o = ois.readObject();
        a1 = (A) o;
        System.out.println(a1);
    }

}

class A implements Serializable{
    transient int a;

    static int aa;

    int b;

    public A(int a, int b) {
        this.a = a;
        this.b = b;
    }

    private void writeObject(java.io.ObjectOutputStream s)
            throws java.io.IOException{
        s.defaultWriteObject();
        s.writeInt(a);
    }

    private void readObject(java.io.ObjectInputStream s)
            throws java.io.IOException, ClassNotFoundException {
        s.defaultReadObject();
        a=s.readInt();
    }

}