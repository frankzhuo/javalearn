package t2_basic_java;

public class Topic4 {
    /*
        1.集合
           （1）类图结构
                 接口：Collection、List、Set、Map、Iterator
                 重要的实现类：
                    ArrayList、LinkedList（和数组的区别，他们之间的区别）、Vector
                    HashMap、TreeMap、LinkedHashMap
                    学会使用Java Doc
           （2）HashMap
                 1.散列算法
                    散列函数（Hash函数）：
                        a.依赖Key对象的hashCode方法
                        b.高低位异或，增大随机性（扰动），避免Key对象散列函数的缺陷
                        c.定位时&取余（性能好），缩小范围；java/carrot.util/HashMap.java:630  ————>  HashMap的长度必须是2^n
                          32 11111
                          31 11110
                           3 10  01、11用不上

                    处理冲突的方法：
                        a.开放地址法
                            线性探查法、二次（平方）探查法、双重散列法
                        b.拉链法
                            1.7使用，1.8借助红黑树对拉链法进行了改进，防止深度过深（到了jdk1.8，当同一个hash值的节点数不小于8时，不再采用单链表形式存储，而是采用红黑树）

                    HashMap的扩容：
                    loadFactor=0.75
                    实际负载因子>0.75，扩容，数组容量double

                 2.equals和hashcode的问题
                    重写equals后，一般都要重写hashcode，保证两个对象相等时，他们的hashcode也是相等的。
                    这一约定在HashMap中同样重要。get方法的逻辑要求我们必须保证get和hashcode的一致性。

                    两个对象equals为true, 就认为从map中可以得到同样的结果

                    if (first.hash == hash && // always check first node
                        ((k = first.key) == key || (key != null && key.equals(k))))

                    先比较hash值，再比较在内存中的地址是否相等，最后比较是否equals（性能由高到低）

                 3.HashSet实际上是基于HashMap来实现的（另几种Set应该也是）。
                 4.HashMap不是线程安全的：https://www.cnblogs.com/Xieyang-blog/p/8886921.html
                 5.JDK1.8中HashMap的实现：
                    https://www.cnblogs.com/xiaoxi/p/7233201.html

           （3）视图
                数组的视图，同步视图，子视图
                视图往往会有一些不支持的方法，UnsopportedOperationException

           （4）合理使用这些数据结构
                List实现栈、队列
                合理使用Map解决问题

        2.范型
            常和集合类一起使用。让错误在编译期就暴露而避免在JVM运行时出错。
            （1）范围：类、方法、接口
            （2）类型擦出、桥接（在继承泛型类型的时候，桥方法的合成是为了避免类型变量擦除所带来的多态灾难），理解即可
                https://blog.csdn.net/lcb1992/article/details/53116763
     */
}
