package t2_basic_java;

import java.io.*;
import java.util.ArrayList;

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
            封装（private、默认、protected、public）将数据和操作数据的方法绑定起来，隐藏一切可以隐藏的东西，对外提供访问
                接口，外界只能通过提供的接口访问类的属性，执行类的行为，外界不需要知道内部实现的细节。
            继承（复用逻辑）
                继承可以复用父类的逻辑，添加新的属性和功能，新生成的类称为子类。
            多态（Cat、Dog、Sheep都继承于Animal类，一个Animal变量引用不同子类时，他的eat方法表现不同），多个子类继承父类
                通过父类的引用调用方法，实际上会调用引用指向的具体的子类的方法，不同的子类对同一方法会有不同的相应。
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

            访问权限不同的方法覆盖，子类只能相对父类越来越宽松，例如父类是public，子类就不能是缺省或protect，private，
            想想多态

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
                1.构造器，Object()
                2.native registerNative()将java中的native方法和方法的具体实现做映射，实现方法名的解耦
                3.native clone()返回新生成的对象的引用，要实现clone()需要实现cloneable接口 只是一个声明接口
                4.native getClass()获取描述类信息的类对象
                5.equals方法判断两个对象是否相等 一般指对象内容是否相等 ==是否是同一个对象 对于基本类型两者一样
                  public boolean equals(Object obj) {return (this == obj);}
                6.native hashcode()方法 生成对象的hashcode值 hashcode主要是为了增强hash表的性能 不需要使用equals一个一个
                  去对比对象内容是否相等 O(n)->O(1)
                7.toString()由对象类型和hashcode值唯一确定 getClass().getName() + "@" + Integer.toHexString(hashCode())
                  同一类型不同对象的hashcode值可能相等
                8.wait/notify/notifyAll 线程调用wait 线程会在对象监视器上等待 notify/notifyAll唤醒在对象监视上等待的线程
                9.finalize方法jvm在对象占用的内存空间进行垃圾回收前 会调用finalize

        8.接口

        8.对象克隆
            Cloneable接口，标记接口；
                实际克隆的步骤：
                    （1）实现Cloneable接口；只是一个标志 重写父类的clone方法
                    （2）clone方法改为public；让其它类调用此类的clone方法。
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
            实现对象的深拷贝 可以自己新建对象 把引用指向新建的对象 也可以采用对象序列化的方式

        9.序列化
            把Java对象转换为字节序列，用于存储或网络传输。（序列化的是实例属性）
            Java的序列化是比较低效的，其他的选择有Thrift、Hessian、Protobuf等
            Serializable接口，标记接口；
                （1）serialVersionUID：
                    由java编译器生成，当class文件改变的时候，serialVersionUID不一样，这样当序列化对象之后，改变类的结构
                    会导致序列化的失败，可以指定serialVersionUID，这样可以使得序列化成功。
                （2）transient：表示该属性不会被序列化
                （3）writeObject、readObject
                        a.数组不能向我们预期的那样进行序列化，和反序列化（待考证，下午给答案）
                        b.对于一个属性，它是一个稀疏的数据结构，我们按照默认方式对其进行序列化和反序列化，会产生大量冗余的比特位，造成存储和网络传输的低效；
                          所以，我们通过把它定义为transient，使其不参加默认的序列化过程，而通过writeObject、readObject实现

                  首先 对象要实现Serializable接口
                  序列化的步骤：
                  （1）创建一个对象输出流 它可以包含其它的目标输出流 例如 文件输出流 字节数组输出流
                  （2）调用对象输出流的wirteObject()方法 写入对象

                  反序列化的步骤：
                  （1）创建一个对象输入流 它可以包含其它的目标输入流
                  （2）调用对象输入流的readObject()方法读取对象

                  如果对象定义了readObject()方法和writeObject()方法 那么输出流中ObjectOutputStream的writeObject会调用类的
                  writeObject() 反序列化也一样 writeObject()也可以调用对象输出流的defaultWriteObject()方法

                  另外我们也可以实现Externalizable接口 完全由自身实现序列化行为
              ArrayList中的elementData数组是transient的，这是elementData数组通常未能完全使用，如果完全序列化 会浪费存储
              空间 所以ArrayList定义了writeObject()方法，只序列化存在的元素。

        10.反射
            反射机制是在运行过程中，对于任意一个类都可以知道它的属性和方法，对于任意一个对象可以访问它的属性 调用它的方法
            提供了"运行时"分析以及操纵类和对象的能力。
            类加载的过程中，会生成类的class对象，包含类的各种信息。Class类没有公共构造方法，
            Class类的方法
            Class.forName()返回指定类名的类的class对象
            newInstance() 调用缺省构造函数 返回一个实例
            newInstance(Objects []args) 调用相应的构造函数 返回一个实例
            getSuperClass()获取父类类对象
            getInterface()获取接口
            getClassLoader()获取类加载器

            获取Class对象的三种方式
            (1) 类名.class 直接返回类的一个静态属性 效率高
            (2) 对象.getClass() 继承制自Object类
            (3) Class.forName() 静态方法

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
                Checked Exception：必须捕获，强迫我们对一些不受控的异常情况进行处理；非运行时异常
                Unchecked Exception：运行时异常和错误

            常见的Exception
                NullPointerException 运行时异常
                ClassNotFoundException 运行时异常
                IOException 非运行时异常
                ArrayIndexOutOfBoundException 运行时异常
                OutOfMemoryError（OOM）Error
                InterruptedException

            try-catch-finally
                先产生的异常被抑制，后产生的异常被抛出；
                （有可能导致我们真正关心的异常被抑制，解决办法：try-with-resource，资源变量要实现AutoClosable接口）

                try后面一定有finally ,如果try或catch中有返回值 则finally在返回返回值之前运行,如果进行文件操作的时候产生
                异常 然后finally中文件关闭的时候又产生了异常，先产生的异常会被抑制，后产生的异常被抛出。可以采用try-with-resource
                先产生的异常被抛出，后产生的异常被抑制，可以通过抛出异常的getSuppressed方法获得被抑制异常。

     */


    public static void main(String[] args) throws IOException, ClassNotFoundException {
        A a1;
        A a = new A(1,2);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(a);
        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(baos.toByteArray()));
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