package t3_concurrent_java;

import java.util.concurrent.locks.ReentrantLock;

public class Topic3 {
    /*
        5. concurrent包
        （1）ReentrantLock（可重入锁）
            注意点：
                对同一个锁上锁
                finally解锁

            ReentrantLock和synchronized的异同：
            同：
                1.都可以实现同步
                2.都是可重入的

            异：
                1.synchronized是JVM层面实现的机制，ReentrantLock是依赖CAS类实现的类；
                2.当竞争不是很激烈的时候，synchronized性能更好；当竞争激烈时，ReentrantLock性能更好；
                3.synchronized只有一个条件变量，ReentrantLock由多个条件变量；
                4.ReentrantLock能实现公平锁和非公平锁，而synchronized只能实现非公平锁（公平锁的代价是效率的降低，往往不使用）；
                5.synchronized不可以被中断，ReentrantLock可以；
                6.使用上前者更简单，后者更灵活

        （2）ReentrantReadWriteLock
            ReentrantReadWriteLock持有内部一个读锁、一个写锁；
            其中，读锁是一个共享锁，写锁是一个排他锁。

            ReentrantReadWriteLock允许多个读线程同时访问，但不允许写线程和读线程、写线程和写线程同时访问。
            相对于排他锁，提高了并发性。

        （3）线程安全的集合
            同步包装器(装饰器)
            ConcurrentHashMap，HashTable，
            ConcurrentSkipListSet
            ConcurrentLinkedQueue
            等。

        （4）BlockingQueue
            BlockingQueue是一个接口，继承自Queue。（BlockingQueue ---> Queue --> Collection）
            阻塞队列，顾名思义，它是一个队列，即有先进先出的特征；
            与普通队列不同的是，它是线程安全的，并且当队列为空时出队会阻塞，当队列为满时，入队会阻塞；
            借助阻塞队列我们可以实现线程间安全的数据交换，常见的有生产者消费者模式。

            对于入队、出队的操作，除了阻塞，BlockingQueue还提供了其它方式（直到即可），共四种：
                阻塞、抛异常、返回特殊值、定时返回（超时返回特殊值）

            几种常见的BlockingQueue：
                LinkedBlockingQueue
                ArrayBlockingQueue
                SynchronousQueue

        （5）线程池
            如果并发的线程数量很多，并且每个线程都是执行一个时间很短的任务就结束了，这样频繁创建线程就会大大降低系统的效率，因为频繁创建线程和销毁线程需要时间。

            Java为我们提供了线程池，顾名思义，这是一个线程的池子，它包含若干个线程，向其提交任务，然后安排线程执行。

            线程池的核心类是ThreadPoolExecutor(ThreadPoolExecutor --> ExecutorService --> Executor)

            工厂类Executors提供了若干个生产特定线程池的方法。
                newCachedThreadPool创建一个可缓存线程池，如果线程池长度超过处理需要，可灵活回收空闲线程，若无可回收，则新建线程。
                newFixedThreadPool 创建一个定长线程池，可控制线程最大并发数，超出的线程会在队列中等待。
                newScheduledThreadPool 创建一个定长线程池，支持定时及周期性任务执行。
                newSingleThreadExecutor 创建一个单线程化的线程池，它只会用唯一的工作线程来执行任务，保证所有任务按照指定顺序(FIFO, LIFO, 优先级)执行。

            在实际中，阿里推荐开发者使用ThreadPoolExecutor生成线程池，这是因为我们要合理使用其参数：

            public ThreadPoolExecutor(int corePoolSize,    核心池的大小
                                      int maximumPoolSize, 线程池最大线程数
                                      long keepAliveTime,  表示核心线程池外的线程，没有任务执行时最多保持多久时间会终止
                                      TimeUnit unit,       时间单位
                                      BlockingQueue<Runnable> workQueue,  阻塞队列，用来存储等待执行的任务
                                      ThreadFactory threadFactory,        线程工厂，主要用来创建线程（给线程起名字，帮助我们维护日志）
                                      RejectedExecutionHandler handler)   表示当拒绝处理任务时的策略

            ThreadPoolExecutor组成：（1）线程池（核心线程池。总线程池）（2）阻塞队列   （3）拒绝策略
            重要方法：execute()、submit()、shutdown()、shutdownNow()

            线程池执行任务（Runnable\Callable）的过程：
                （1）如果当前线程池中的线程数目小于corePoolSize，则每来一个任务，就会创建一个线程去执行这个任务；
                （2）如果当前线程池中的线程数目>=corePoolSize，则每来一个任务，会尝试将其添加到任务阻塞队列当中，
                    若添加成功，则该任务会等待空闲线程将其取出去执行；若添加失败（一般来说是任务缓存队列已满），则会尝试创建新的线程去执行这个任务；
                （3）如果当前线程池中的线程数目达到maximumPoolSize，则会采取任务拒绝策略进行处理；如果没达到，则新建线程处理；
                （4）另外，当池子的线程数大于corePoolSize的时候，多余的空闲线程会等待keepAliveTime长的时间，如果无请求可处理就自行销毁。

            由上，可知2个重要的参数：corePoolSize、maximumPoolSize。（corePoolSize、maximumPoolSize往往设置为同一个数，避免频繁关闭创建线程）

            还有一个重要参数：
            workQueue：
                （1）SynchronousQueue
                    用于生产者-消费者场景，本身只存储一个Task，往往要求线程池有较大的maximumPoolSize，否则提交的任务很可能被拒绝策略处理；

                （2）LinkedBlockingQueue
                    默认长度无限，导致maximumPoolSize无效，但不推荐这样做，当消费者处理速度过慢时，导致大量堆积，容易引发OOM。

                （3）ArrayBlockingQueue（实际使用较多）
                    长度有限，要合理配置线程数目和队列长度。
                      如果是CPU密集型任务，就需要尽量压榨CPU，参考值可以设为 Ncpu+1
                      如果是IO密集型任务，参考值可以设置为2*Ncpu

            线程池的状态（RUNNING、SHUTDOWN、STOP、TERMINATED）：
                   当创建线程池后，初始时，线程池处于RUNNING状态；
                   如果调用了shutdown()方法，则线程池处于SHUTDOWN状态，此时线程池不能够接受新的任务，它会等待所有任务执行完毕；
                   如果调用了shutdownNow()方法，则线程池处于STOP状态，此时线程池不能接受新的任务，并且会去尝试终止正在执行的任务；
                   当线程池处于SHUTDOWN或STOP状态，并且所有工作线程已经销毁，任务缓存队列已经清空或执行结束后，线程池被设置为TERMINATED状态。

            课后可以使用线程池和阻塞队列，自己写一个生产者-消费者的小例子。

        6.原子类
            CAS（compare and set）
            AtomicInteger、AtomicReference...等
            调用底层unsafe的原子方法（原子方法有底层实现，不用管）
     */

    static class Item{
        int i=0;

        public Item() {
        }

        public void incre(){
            i++;
        }

        @Override
        public String toString() {
            return "Item{" +
                    "i=" + i +
                    '}';
        }
    }

    static class A implements Runnable{
        Item item;
        ReentrantLock lock;
        public A(Item item, ReentrantLock lock) {
            this.item=item;
            this.lock=lock;
        }

        public void run() {
            for(int i=0;i<1000;i++){
                try{
                    lock.lock();
                    System.out.println(Thread.currentThread().getName());
                    this.item.incre();
                }finally {
                    lock.unlock();
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ReentrantLock lock=new ReentrantLock();
        Item item=new Item();
        A a1=new A(item,lock);
        A a2=new A(item,lock);
        Thread t1=new Thread(a1);
        Thread t2=new Thread(a2);
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(item);
    }
}
