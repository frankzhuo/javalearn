package t3_concurrent_java;

public class Topic5 {
    /*
            8.单例模式（共23种）
                单例模式：
                        单例类只能有一个实例，只能创建一个实例，单例类要向外界提供其唯一实例。
                        常用的有数据库连接池，线程池等实体。

                    饿汉模式：
                            类加载时，就生成该单例。
                            优点：简单，不必使用时才初始化，避免性能损耗
                            缺点：会导致程序启动变慢；若单例不常使用，会占用一定内存资源

                    懒汉模式：
                            使用时，才生成该单例。
                            优点、缺点，反过来。（理解，再描述）

                            解决并发问题：
                                1.synchronized
                                    |
                                    | 优化
                                    |
                                2.双重检查
                                    |
                                    | 代码重排序导致的并发问题
                                    |  1.先申请内存 2.构造Singleton 3.将instance变量指向新的内存区域的地址 (1,3,2)
                                    |
                                3.解决：
                                    （1）volatile (禁止重排序)
                                    （2）使用静态类


              （未考证）
               1.5以后,执行3之前, 必须确保1,2已经执行. 但是1,2的顺序应该还是不确定的. 所以1.5之后 双重检查锁的单例是可以用的

     */

}

class StarvingSingleton {
    // 私有构造
    private StarvingSingleton() {}

    private static StarvingSingleton single = new StarvingSingleton();

    // 静态工厂方法
    public static StarvingSingleton getInstance() {
        return single;
    }
}


class LazySingleton {

    // 私有构造
    private LazySingleton() {}

    private static LazySingleton single = null;

    public static LazySingleton getInstance() {
        if(single == null){
            single = new LazySingleton();
        }
        return single;
    }
}

class LazySingleton2 {

    // 私有构造
    private LazySingleton2() {}

    private static volatile LazySingleton2 single = null;

    public static LazySingleton2 getInstance() {
        if(single == null){
            synchronized (LazySingleton2.class){
                if(single == null) {
                    single = new LazySingleton2();
                }
            }
        }
        return single;
    }
}


class LazySingleton1 {

    // 私有构造
    private LazySingleton1() {}

    private static LazySingleton1 single = null;

    public static synchronized LazySingleton1 getInstance() {
        if(single == null){
            single = new LazySingleton1();
        }
        return single;
    }
}


class LazySingleton3 {

    private static class SingletonHolder {
        /*
            采用静态初始化器的方式，它可以由JVM来保证线程的安全性。
            (classloader的机制来保证初始化instance时只有一个线程，所以也是线程安全的)
         */
        private static final LazySingleton3 INSTANCE = new LazySingleton3();
    }
    // 私有构造
    private LazySingleton3() {}

    private static volatile LazySingleton3 single = null;

    public static LazySingleton3 getInstance() {
        return SingletonHolder.INSTANCE;
    }
}

