package t3_concurrent_java;

public class Topic6 {
    /*
        1.ConcurrentHashMap
        2.ThreadPoolExecutor、BlockingQueue
        3.AQS、ReentrantLock、ReentrantReadWriteLock
        4.synchronized
        重要性（易考）由高到低
        关键实现：
        1.synchronized

            Java6之前只存在重量级锁，Java1.6引入了"偏向锁"、"轻量级锁"。
            背景：
                1."线程的阻塞和唤醒"需要CPU从用户态转为内核态，频繁的阻塞和唤醒对CPU来说是一件负担很重的工作，给系统的并发性能带来很大的压力。
                同时在许多应用上面，对象锁的锁状态只会持续很短一段时间，为了这一段很短的时间频繁地阻塞和唤醒线程是非常不值得的。所以引入"自旋锁"。

                2.自旋：while循环中重复同一操作，自旋锁，避免了线程的阻塞和唤醒带来的性能损耗，但同时也让CPU空转，浪费了CPU资源。

                3.Hotspot虚拟机的对象头主要包括两部分数据：MarkWord （标记字段）、Klass Pointer（类型指针），
                MarkWord 用于存储对象自身的运行时数据，如哈希码（HashCode）、GC分代年龄、锁状态标志、线程持有的锁、偏向线程 ID、偏向时间戳等等。
                synchronized机制就是借助 MarkWord 实现的。
                -----------------------------------------------
                            MarkWord部分标志位
                   其它         是否是偏向锁  锁标志位
                                0           01       无锁
                    线程ID       1          01       偏向锁
                    锁记录指针               00       轻量级锁
                                           10       重量级锁
                                           11       GC标志位
                ———————————————————————————————————————————————

            偏向锁：
                第一次获取某个对象的锁，那么该线程会自动获取偏向锁，并且不会主动释放偏向锁。

                偏向锁的获取是通过CAS操作，修改 MarkWord 中的"偏向线程 ID"来实现的，修改成功则获取锁，
                并且不会主动释放偏向锁，只有其它线程竞争时，才会释放锁。

                具体获取锁步骤
                    1.检测Mark Word是否为可偏向状态，即是否偏向锁位为1，锁标识位为01，即biasable；
                    2.若为biasable，查看MarkWord中线程ID，
                        （1）如果线程ID为当前线程ID，执行步骤5；
                        （2）否则，执行步骤3
                    3.如果线程ID不为当前线程ID，则通过CAS操作修改线程ID由0到当前线程ID以竞争锁，
                        （1）竞争成功，则执行步骤5；
                        （2）否则执行步骤4（说明此处已有其它线程ID，即已有其它线程获取偏向锁）；
                    4.请求暂停获取偏向锁的线程，当获得偏向锁的线程到达全局安全点，就会挂起，
                        检查之前拥有偏向锁的线程是否还活着，若没活着，则设置为无锁；
                        否则，偏向锁偏向于其它线程，或者，偏向锁升级为轻量级锁，然后被阻塞在安全点的线程继续往下执行同步代码块；
                    5.执行同步代码块


                "偏向锁"适用于只有一个线程访问同步块的场景，降低获取锁的代价。

            轻量级锁：
                    1.拷贝对象当前的 MarkWord 到锁记录；
                    2.CAS将MarkWord设置为锁记录的指针，若成功执行步骤3，否则执行步骤4；
                    3.设置锁标志位，获得轻量级锁；
                    4.自旋获取锁，若自旋超过一定次数，锁膨胀为 重量级锁。

            重量级锁：
                    Synchronized是通过对象内部的一个叫做监视器锁（monitor）来实现的。
                    但是监视器锁本质又是依赖于底层的操作系统的Mutex Lock来实现的。
                    而操作系统实现线程之间的切换这就需要从用户态转换到核心态，这个成本非常高，状态之间的转换需要相对比较长的时间，
                    这就是为什么Synchronized效率低的原因，因此引入了轻量级锁和偏向锁。


            偏向锁 -> 轻量级锁 -> 重量级锁  竞争强度依次增强，升级后，不会再降级。

          ————————————————————————————————————————————————————————————————————————————————————————————————————————————
            锁           优点                              缺点                       适用场景

            偏向锁     加锁和解锁不需要额外的消耗，     如果线程间存在锁竞争，              适用于只有一个线程访问同步块场景。
                      和执行非同步方法比仅存在         会带来额外的锁撤销的消耗。
                      纳秒级的差距。
          ————————————————————————————————————————————————————————————————————————————————————————————————————————————
            轻量级锁   竞争的线程不会阻塞，            如果始终得不到锁竞争的线程          追求响应时间。
                      提高了程序的响应速度。          使用自旋会消耗CPU。                同步块执行速度非常快。
          ————————————————————————————————————————————————————————————————————————————————————————————————————————————
            重量级锁   线程竞争不使用自旋，            线程阻塞，响应时间缓慢。            追求吞吐量。
                      不会消耗CPU。                                                  同步块执行速度较长。
          ————————————————————————————————————————————————————————————————————————————————————————————————————————————

            其它锁的优化：
                1.自适应性自旋
                2.锁粗化
                3.锁消除

            补充：
                CAS的三大问题：（1）ABA问题，（2）循环时间过长，浪费CPU，（3）只能保证一个变量的原子操作

                compareAndSet(A,B)      compareAndSet(B,A) compareAndSet(A,B)
                引入版本号


            锁升级的过程： https://www.cnblogs.com/pureEve/p/6421273.html
            MarkWord：   http://www.cnblogs.com/paddix/p/5405678.html
            偏向锁：      http://www.cnblogs.com/javaminer/p/3892288.html
            重量级锁，其它锁优化：http://www.cnblogs.com/paddix/p/5405678.html

        2.Object的notify/wait
            同步队列，阻塞队列
            t3_concurrent_java/wait_notify.jpg

        3.volatile
            Java内存模型（JMM，Java Memory Model）简介（逻辑上的概念）：
                线程之间共享变量存储于"主内存"，每个线程都有自己的"私有的本地内存"
                线程读写的是"本地内存"，本地内存会按照一定规则刷新到"主内存"

            volatile读写语义：
                volatile 读会从主内存中读最新的数据到本地内存，
                volatile 写会立刻把本地内存最新的数据刷新到主内存。

            volatile 能够保证"可见性"，"有序性"；不能保证"原子性"。
            volatile 能保证单个读写的原子性，但不能保证 i++ 这样的复合操作的"原子性"。

        4.ConcurrentHashMap
            JDK1.7中HashMap在并插入的情况下，可能会造成死循环。
            解释：在并发的情况，发生扩容时，可能会产生循环链表，(两个线程同时扩容，由于扩容时链表转移会颠倒顺序，导致两个线程同时对一个链表)
                 在执行get的时候，会触发死循环，引起CPU的100%问题，所以一定要避免在并发环境下使用HashMap。
            https://blog.csdn.net/silyvin/article/details/79102415

            HashTable,
            Collections.synchronizedMap : java.carrot.util.Collections.synchronizedMap
                |
                | 上面两个都是通过synchronized实现同步，
                | 下面采用分段锁，提高并发度
                |
            JDK1.7
                分段锁、两次哈希，segment  java.carrot.util.concurrent.ConcurrentHashMap.Segment
                segment数目代表了ConcurrentHashMap的并发度(2^n，默认16)，
                    get操作：
                            先定位segment，在定位HashEntry，
                            注意get方法没有加锁，而是采用 UNSAFE.getObjectVolatile ，保证每次读取到的都是最新的数据
                            (同样使用的还有final和volatile)

                    put操作：
                            Segment 继承自ReentrantLock，
                            定位segment, 若为空，则并发安全的创建 Segment ，
                            不为空，则对Segment上锁（分段锁一名的来源），线程安全的put key-value对；

                    扩容操作：
                            put时，若segment对应的数组大于threshold， 对segment指向的数组扩容；

                    size操作：
                            ConcurrentHashMap的size操作的实现方法也非常巧妙，
                            一开始并不对Segment加锁，而是直接尝试将所有的Segment元素中的count相加，
                            这样执行两次，然后将两次的结果对比，如果两次结果相等则直接返回；
                            而如果两次结果不同，则再将所有Segment加锁，然后再执行统计得到对应的size值。

                    https://blog.csdn.net/bill_xiang_/article/details/81122044

            JDK1.8
                    1.8中放弃了Segment臃肿的设计，取而代之的是采用Node + CAS + Synchronized来保证并发安全进行实现

                    改进一：取消segments字段，直接采用transient volatile HashEntry<K,V>[] table保存数据，
                    采用table数组元素作为锁，从而实现了对每一行数据进行加锁，进一步减少并发冲突的概率。

                    改进二：将原先table数组＋单向链表的数据结构，变更为table数组＋单向链表＋红黑树的结构。
                    当链表元素个数超过8(默认值)时，jdk1.8中采用了红黑树的结构；
                    此举防止不理想的情况下，一些队列长度过长的情况下，查询某个节点的时间复杂度O(n)过高；
                    那么红黑树的查询的时间复杂度可以降低到O(logN)，可以改进性能。

                    1.8中ConcurrentHashMap加锁时，采用的是数组元素的对象锁（synchronized）


                    get操作：
                            与1.7类似，不上锁，借助UNSAFE.getObjectVolatile、final和volatile，保证每次读取到的都是最新的数据

                    put操作：
                            （1）未初始化，则初始化
                            （2）数组中对应的位置为null，生成新的Node，并CAS设置，成功则停止，失败往后走
                            （3）正在扩容，则帮助扩容
                            （4）加锁，插入新元素
                            （5）最后检查容量，决定是否扩容

                    扩容操作：
                            当新增元素时，如果链表元素大于等于TREEIFY_THRESHOLD（默认为8），则进行扩容；
                            （1）当数组大小小于MIN_TREEIFY_CAPACITY（默认64）时，首先对数组扩容；
                                这里的数组扩容，允许使用多个线程进行并发扩容
                            （2）当数组大小等于MIN_TREEIFY_CAPACITY（默认64）时，加锁，将链表变为红黑树；


        5.AQS（AbstractQueuedSynchronizer）*
            队列同步器，借助队列实现同步操作。

            ReentrantLock，Semaphore，ReentrantReadWriteLock等等皆是基于AQS的

            AQS维护一个队列，完成对互斥资源的排队获取。

            模版模式（设计模式）：定义模板结构，具体内容延迟到子类实现，提高了代码的复用性，符合"开闭原则"。

            需要重写的方法：
                tryAcquire、tryRelease、tryAcquireShared、tryReleaseShared、isHeldExclusively

            模版方法举例：
                acquire、release、acquireShared、releaseShared等

            解析：
                acquire：尝试获取同步状态，获取不到，则进入队列，并阻塞此线程（调用的是linux内核方法）；
                release：释放同步状态，并唤醒队列头部的线程；
                tryAcquire：获取同步状态的操作
                tryRelease：释放同步状态的操作

            http://www.cnblogs.com/chengxiao/archive/2017/07/24/7141160.html

            ReentrantLock、ReentrantReadWriteLock实现的是我们如何取获取同步状态。

            同步状态、等待队列，模版设计模式

        6.ReentrantLock*
                内部抽象类Sync
                    内部类FairSync

                    内部类NonfairSync

        7.ReentrantReadWriteLock
                state：高16位表示读状态，低16位表示写状态
                内部抽象类Sync
                    内部类FairSync

                    内部类NonfairSync

                内部类 ReadLock
                    获得锁，检查state读写状态，获得则对读状态+1，如果写状态有值，则获得不到锁，进入队列
                    释放锁，就是对读状态-1的过程，为0，读锁被释放

                内部类 WriteLock
                    获得锁，检查state读写状态，获得则对写状态+1，读、写状态有值，都获取不到锁，进入队列
                    释放锁，就是对写状态-1的过程，为0，写锁被释放

                写状态有值，但是持有锁的线程是当前线程的话，那还是可以获取锁的。

        8.BlockingQueue
            以ArrayBlockingQueue为例：
                ArrayBlockingQueue
                核心实现：
                        一个锁ReentrantLock，
                        由锁生成两个条件变量(Condition) —— notEmpty，notFull（构造函数中实现）；
                        借助putIndex，takeIndex维护一个环形队列。

                具体：
                    put(E e)方法:
                        入队，获取锁
                            （1）当count==队列长度，调用notFull.await()释放锁，并等待notFull.signal()通知；
                            （2）否则，调用enqueue入队，将元素放入环形队列，接着调用notEmpty.signal()，通知阻塞的出队线程队列非空。
                        释放锁。

                    take()方法：
                        出队，获取锁
                            （1）当count==0，调用notEmpty.await()释放锁，并等待notEmpty.signal()通知；
                            （2）否则，调用dequeue出队，调用notFull.signal()，通知阻塞的入队线程队列非满，接着将环形队列尾部元素返回。
                        释放锁。

                    可以采用类似方法分析offer，poll等方法。

        9.ThreadPoolExecutor
                ThreadPoolExecutor 的核心方法是execute方法，其实现其实就是按照前面的执行策略，

                    execute(Runnable command)：
                        查看池中线程数目ctl（AtomicInteger）：

                            建立线程（Worker）并执行该任务——addWorker，

                            或者将任务添加到阻塞队列，（这里有一个double-check）

                            或者尝试启动非核心池线程，失败则将任务交给拒绝策略。

                    内部类Worker：
                        run方法通过调用runWorker，执行firstTask，之后不断从阻塞队列中取任务，

                        添加Work的过程需要对mainLock加锁（mainLock用于控制对Worker集合的访问）。

                        Worker 实现了Runnable接口，并继承了 AbstractQueuedSynchronizer 类，用于实现shutdown时避免，需要获取该Worker的锁，
                        才能中断该线程，避免中断正在执行的线程。


                ThreadPoolExecutor的设计又很多巧妙的地方，在此不一一列举，可以思考一个问题：
                    ThreadPoolExecutor用哪些方法实现了同步，并且为什么在那种情况下用这种同步方法？

        10. ThreadLocal
            每个线程可以往其内部存入一个对象，其它线程获取不到，只有本线程可以获取
            内存泄漏

     */
}
