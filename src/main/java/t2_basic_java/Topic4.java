package t2_basic_java;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class Topic4 {
    /*
        1.集合
           （1）类图结构
                 Iterator：只能正向遍历集合，适用于获取移除元素。
                 ListIerator：继承Iterator，可以双向列表的遍历，同样支持元素的修改。
                 接口：Collection、List、Set、Map、Iterator
                 Collection继承自Iterator接口，List Set Queue 继承自Collection接口,list是有序集合元素可以重复 set是无序
                 集合元素不可以重复,map是存储键值映射关系的数据结构key不能重复 list的实现类有ArrayList 底层是由数组实现
                 随机访问快增删慢 LinkedList底层是由链表实现增删快查找慢 HashMap是基于hash算法实现 底层的数据结构是桶数组
                 计算key的hash值然后对数组的长度取模 根据结果找到数组的位置存储键值 如果遇到冲突采用拉链法来解决 构造结点
                 并插入链表的头部 hashMap不是线程安全的
                 Vector是线程安全的 使用synchronized进行同步 效率较低 不建议使用 Vector扩容是俩倍 ArrayList扩容是1.5倍
                 TreeMap 是有序的map底层使用红黑树实现
                 LinkedHashMap 维护了元素的插入顺序
                 重要的实现类：
                    ArrayList、LinkedList（和数组的区别，他们之间的区别）、Vector
                    HashMap、TreeMap、LinkedHashMap
                    学会使用Java Doc
           （1）HashMap和HashTable的区别
                HashMap不是线程安全的 HashTable是线程安全的
                HashMap键值可以为空 HashTable键值不能为空
                HashMap采用了快速失败机制 HashTable不具有快速失败机制

                何为快速失败机制？
                java中的快速失败机制和迭代器有关，当获得了一个集合的迭代器，如果其他线程结构化修改（删除 增加）集合，java
                中会抛出并发修改异常（ConcurrentModificationException）

                如何使HashMap同步 使用Collections的同步方法生成同步的Map
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
            泛型的实现方式
            类型膨胀 参数化类型ArrayList<Integer> ArrayList<String>是两个不同的类型
            类型擦除 参数化类型ArrayList<Integer> ArrayList<String>在代码运行时都擦除为原生类型ArrayList是同一个类型
            java中泛型的实现方式
            （1）不再完全擦除泛型信息，而是让Class文件可以记录完整的、结构化的泛型信息，并且让编译器可以指定默认擦除类型
            （2）在运行时，根据Class文件记录的泛型信息进行特化
            （3）默认的Java实现会让泛型对原始类型特化，而对引用类型保持之前擦除式实现的行为，但运行时可以反射获取的泛型信息会增加
            （4）其它在JVM上运行的语言，其编译器可以在生成Class文件时指定泛型要完全特化，这样到运行时这些泛型类型就会得到完全特化
            <?>无界通配符
            <? extends 类型> 界定通配符的上边界
            <? super 类型> 界定通配符的下边界
     */

    public static void main(String[] args) throws IllegalArgumentException, SecurityException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        ArrayList<Integer> arrayList3 = new ArrayList<Integer>();
        arrayList3.add(1);//这样调用add方法只能存储整形，因为泛型类型的实例为Integer
        arrayList3.getClass().getMethod("add", Object.class).invoke(arrayList3, "asd");
        for (int i = 0; i < arrayList3.size(); i++) {
            System.out.println(arrayList3.get(i));
        }
        new Thread();
    }
}
