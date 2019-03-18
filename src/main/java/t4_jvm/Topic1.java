package t4_jvm;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

public class Topic1 {
    /*
        1.JVM组成
            程序计数器：线程私有，当前线程执行的字节码行号指示器

            虚拟机栈：Java线程私有，描述java方法执行的内存模型，每个方法执行的同时，都会创建一个栈帧保存相关信息。

            本地方法栈：本地方法私有，描述本地方法执行的内存模型。

            堆：一个JVM内的所有线程所共有，用于存储所有的对象实例及数组。是垃圾回收机制主要管理的区域，
               也是各种考察和使用的重中之重。

            方法区：各个线程共享的内存区域，它用于存储已被虚拟机加载的类信息、常量、静态变量、即时编译器编译后的代码等数据。

            直接内存（堆外内存）：直接内存（Direct Memory）并不是虚拟机运行时数据区的一部分，也不是Java虚拟机规范中定义的内存区域，
                   但是这部分内存也被频繁地使用，而且也可能导致OutOfMemoryError 异常出现。

                   直接内存的使用，避免了在Java 堆和Native 堆中来回复制数据；在一些场景中能显著提高性能。

                   直接内存的分配不会受到Java 堆大小的限制，但是，还是会受到本机总内存（包括RAM 及SWAP 区或者分页文件）的大小及处理器
                   寻址空间的限制。服务器管理员配置虚拟机参数时，一般会根据实际内存设置-Xmx等参数信息，但经常会忽略掉直接内存，
                   使得各个内存区域的总和大于物理内存限制（包括物理上的和操作系统级的限制），从而导致动态扩展时出现OutOfMemoryError异常。

                   一般的应用开发用的少。

        2.对象的垃圾回收（GC）规则
            引用计数法：通过引用计数来判断一个对象是否可以被回收。

                      简单高效，但无法解决循环引用问题。
                      A->B B->A

            可达性分析法：通过一系列的GC Roots的对象作为起始点，从这些根节点开始向下搜索，搜索所走过的路径称为引用链（Reference Chain），
                        当一个对象到GC Roots没有任何引用链相连时，则证明此对象是不可用的。
                GC Roots：
                    1.虚拟机栈中引用的对象
                    2.本地方法栈中的对象
                    3.方法区中类静态属性引用的对象
                    4.方法区中的常量对象


        3.垃圾回收算法与垃圾收集器
            垃圾回收算法：
                1.标记清除算法
                    容易造成空间碎片，以至于频繁触发垃圾回收；

                2.标记整理算法
                    标记后，还要进行整理，效率更低；但不会造成空间上的碎片；

                3.复制算法
                    在对象存活率较低的情况下效率高，且不会有碎片，但是浪费存储空间；

                4.分代收集算法
                    根据对象生存周期的长短，对对象进行分代，JVM中将对象分为"年轻代"和"老年代"；
                    年轻代采用"复制算法"，
                    老年代采用"标记清除算法"或者"标记整理算法"

            Java堆的组成
                年轻代（8:1:1）：
                    特点：朝生夕死，生命极短。
                    Eden区：
                    From Survivor区：
                    To Survivor区：
                    结合新生代对象九死一生的特点，采用复制算法，同时8:1:1的比例最大化利用内存空间。
                    每次只浪费十分之一的新生代内存，同时保证垃圾回收的速度。

                老年代：
                    1.长期存活的对象进入老年代
                    2.大对象直接进入老年代   生命周期短的大对象对生命周期伤害较大。

                垃圾回收：
                    young GC：

                    full GC：

            垃圾回收器：
                注意的问题：
                    1.垃圾回收是否是多线程并行处理
                    2.垃圾回收线程和用户线程是否可以并发进行  ————> Stop the world问题，造成系统的延迟卡顿
                （并行：同时进行；并发：交替执行，不一定并行）
                    3.管理的区域，采用的垃圾回收算法

                Serial收集器：
                    年轻代，单线程，Stop the world，复制算法，Client模式下年轻代默认垃圾回收器

                ParNew收集器：
                    年轻代，多线程，Stop the world，复制算法，Server模式下年轻代默认垃圾回收器
                    Serial收集器的多线程版本

                Parallel Scavenge收集器（吞吐量优先收集器）：
                    年轻代，多线程，Stop the world，复制算法，
                    设置参数，动态调节，以获得更高的吞吐量。（其它垃圾回收器关注缩短垃圾回收时间）
                    吞吐量=程序运行时间/（程序运行时间+垃圾回收时间）
                    不能与CMS共用（https://www.zhihu.com/question/43492129   框架不兼容）

                Serial Old收集器：
                    老年代，单线程，Stop the world，标记-整理算法，Client模式下老年代默认垃圾回收器
                    CMS收集器发生CMF后使用的老年代回收器

                Parallel Old收集器：
                    老年代，多线程，Stop the world，标记-整理算法，Parallel Scavenge的老年代版本
                    https://blog.csdn.net/qq_33915826/article/details/79672772（各种垃圾回收器的组合）

                CMS收集器（Concurrent Mark Sweep，并发标记清除）：
                    老年代，多线程，Stop the world，Server模式下老年代默认垃圾回收器，标记清除算法
                    以获得"最短停顿时间"为目标。
                    其垃圾回收过程相对较复杂，分为四个步骤：
                        （1）初始标记（Stop the world，GC Roots）
                            收集GC Roots直接关联的对象，停顿时间很短；
                        （2）并发标记(不停顿)
                            并发收集关联的对象，不停顿；
                        （3）重新标记（Stop the world）
                            对之前的可达性分析进行校正，停顿时间较短；
                        （4）并发清除（不停顿）
                            并发清除不可达的对象。

                    缺点：
                        （1）对CPU资源敏感
                        （2）浮动垃圾可能导致CMF
                        （3）采用标记-清除算法，会带来碎片
                            不过，通过JVM参数设置，我们可以让JVM定期在Full GC时对老年代进行压缩


                    CMF（Concurrent Mode Failure）：
                         该问题是在执行CMS GC的过程中同时业务线程将对象放入老年代，而此时老年代空间不足，
                         或者在做Minor GC的时候，新生代Survivor空间放不下，需要放入老年代，而老年代也放不下而产生的。

                         发生CMF时，会采用Serial Old收集器收集器。

                         解决：
                            降低CMS阈值、减小年轻代大小

                G1收集器（Garbage First）：
                        G1收集器是一个全代收集器。低停顿，用于服务端。
                        将GC堆分为很多Region，一个Region可以归属于新生代，也可能归属为老年代，GC对Region进行管理。

        4.引用
            强引用：
                 强引用有引用变量指向时永远不会被垃圾回收，JVM宁愿抛出OOM错误也不会回收这种对象。

            软引用（SoftReference）：
                 如果一个对象只具有软引用，内存空间足够，垃圾回收器就不会回收它；
                 内存空间不足时，JVM就会回收它。

                 https://blog.csdn.net/he_world/article/details/50543793(利用软引用和ReferenceQueue实现高速缓存)

            弱引用（WeakReference）：
                 弱引用也是用来描述非必需对象的，当JVM进行垃圾回收时，无论内存是否充足，都会回收被弱引用关联的对象。

            虚引用（PhantomReference）：
                 虚引用不影响对象的生命周期。

                 虚引用必须和引用队列关联使用，当垃圾回收器准备回收一个对象时，
                 如果发现它还有虚引用，就会把这个虚引用加入到与之关联的引用队列中。

        5.常见问题及排查工具
            jps：   JVM Process Status Tool，显示系统内所有的JVM进程；
            jstat： JVM Statistics Monitoring Tool，可以收集JVM相关的运行数据；
            jinfo： Configuration Info for Java，显示JVM配置信息；
            jmap：  Memory Map for Java，用于生成JVM的内存快照；
            jhat：  JVM Heap Dump Browser，用于分析heapdump文件，它可以建立一个http/html服务，使用者可以在浏览器上查看分析结果；
            jstack：Stack Trace for Java，显示JVM的线程快照。
            https://www.cnblogs.com/zengweiming/p/8946195.html

        6.性能调优
          目标；
            GC的时间足够的小
            GC的次数足够的少
            发生Full GC的周期足够的长

          合理分配年轻代、老年代比例，默认是1：2，一般不用改变
          减少大对象的存储
          及时收回过期对象的引用，避免内存泄漏
          选择合适的GC收集器

        7.Java的内存泄漏问题
            内存泄漏：不再使用的变量仍然占用内存空间。
            不再使用，然而引用仍然长期存在的对象不会被GC所回收，这就造成了内存泄漏。
                比如，单例模式中的单例对象，静态属性，以及静态的集合对象中引用的对象，ThreadLocal
            总之，Java中产生内存泄露，究其原因是我们没有及时释放过期对象的引用。

        8.类加载机制
            类加载机制：把描述类的数据加载到内存中，并对数据进行校验，转换解析和初始化，最终形成可以在Java虚拟机中使用的Java类型。

            类的生命周期：
                加载、连接（验证、准备、解析）、初始化、使用、卸载
               |————————类加载起作用的阶段—————————|
                加载：查找并加载类的二进制数据
                验证：验证数据的正确性
                准备：为类的静态变量分配内存，并将其初始化为默认值
                解析：把类中的符号引用变为直接引用
                初始化：为静态变量赋予初始值

            类初始化执行时机：
                遇到以下情况，一个类如果之前没有被初始化，那么这个类就会进行初始化：
                    （1）new来创建对象
                    （2）使用反射对一个类进行操作
                    （3）初始化一个类，它的父类还没有初始化
                    （4）虚拟机启动时会加载main方法所在的主类
                有些类似背书的规则没在这里列举。

            类加载器：
                查找并加载类的二进制数据

            双亲委派模型：
                除了顶层的类加载器，每个类都有自己的父加载器；
                一个加载起加载类的时候会首先从其父加载器中加载这个类  -->  保证了基础类的安全加载，并且保证使用的基础类相同。
                                                                   （Java中判断两个类是否是同一个，由加载器和限定名共同决定）
            类加载器种类：
                https://www.cnblogs.com/fingerboy/p/5456371.html
                1.启动类加载器(Bootstrap ClassLoader)
                  加载基础的类
                2.扩展类加载器(Extendsion ClassLoader)
                  https://bbs.csdn.net/topics/390774641/（类似于Python的全局安装）
                3.应用程序类加载器(Application ClassLoader)
                  一般地，我们自己写的类，都是由这个类加载器加载的
                4.线程上下文类加载器（Thread Context ClassLoader）
                  场景：基础类应用到用户自定义的类，导致其所在的类加载器无法加载用户类。
                  SPI， Service Provider Interface

                Tomcat的类加载机制也比较有意思，有兴趣的同学下来可以看下
                https://blog.csdn.net/qq_38182963/article/details/78660779

            类加载器使用场景：
                早起Applet（早已废弃），现在：Java应用服务器（如Tomcat）、OSGi等

            new 一个对象的过程
                https://www.cnblogs.com/JackPn/p/9386182.html

        9.java内存模型
             主内存、工作内存。
             Java内存模型是围绕着并发编程中原子性、可见性、有序性这三个特征来建立的。

             volatile：读，会将工作内存失效
                       写，立即将工作内存的修改更新到主内存

             synchronized：获取锁时，会将工作内存失效
                           释放锁时，会将共享变量的值刷新到主内存中

             final：final 变量一经初始化，就不能改变其值，是线程安全的；
                    但对于对象和数组，final变量存储的是引用地址，他们内部是可以被改变的，也就不是线程安全的类。

        10.ThreadLocal

            实现原理：
                每个线程内部包含一个Map（结构类似于1.7版本的HashMap）  threadLocals（ java.lang.Thread.threadLocals ）
                （1）向 ThreadLocal set对象时，向这个map放入该对象，并以 ThreadLocal 为 key。
                （2）从 ThreadLocal get对象时，以 ThreadLocal 为 key从 threadLocals 取出对象。

            内存泄漏：
                ThreadLocalMap 中的Entry继承自 WeakReference，
                WeakReference指向的是ThreadLocal实例，这是为了确保当外界不再存在ThreadLocal强引用时，ThreadLocal能够被JVM回收。
                ThreadLocal能够被JVM回收，然而其对应的Value却不能被回收。
                Value被回收的时机：
                    （1）map中元素数目超过threshold，map会清理key为null的entry
                    （2）set时遇到key为null的entry
                    （3）set时，没有遇到相同key的entry，也没有遇到key为null的Entry，此时，会对 ThreadLocalMap进行清理。
                    （4）调用ThreadLocal的remove方法
                内存泄漏的场景：
                    线程使用了ThreadLocal，然而当ThreadLocal失去引用后，没有再调用能使value被回收的方法
                解决办法，当ThreadLocal使用完毕后，调用ThreadLocal的remove方法。

            https://blog.csdn.net/zhailuxu/article/details/79067467

     */
    public static void main(String[] args) throws InterruptedException {

        SoftReference<Map> reference2=new SoftReference(new HashMap());
        System.out.println(reference2.get());
        System.gc();//通知GVM回收资源
        System.out.println(reference2.get());

        WeakReference<Map> reference1=new WeakReference(new HashMap());
        System.out.println(reference1.get());
        System.gc();//通知GVM回收资源
        System.out.println(reference1.get());

        ReferenceQueue queue = new ReferenceQueue();
        PhantomReference<Map> reference=new PhantomReference(new HashMap(), queue);
        while(true){
            HashMap m=new HashMap();
            Thread.sleep(10);
        }

    }
}
