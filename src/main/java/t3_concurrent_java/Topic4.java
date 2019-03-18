package t3_concurrent_java;

public class Topic4 {
    /*
        7.一些不常考，但真的会考的点（前面问不倒你，面试官就可能问你，前三个问的多些）
        （1）Semaphore
                Semaphore类是一个计数信号量，必须由获取它的线程释放，
                通常用于限制可以访问某些资源（物理或逻辑的）线程数目。

        （2）CountDownLatch（例子）
                CountDownLatch是一个计数器，它的构造方法需要传入一个int值，表示计数，
                调用其实例的await方法，阻塞当前线程，
                直到其它线程调用该实例的countDown方法，将计数降为0，被阻塞的线程才继续执行。
                Callable，Future

        （3）CyclicBarrier
                CyclicBarrier能阻塞一组线程直到指定数目的线程到达。所有的线程必须同时到达栅栏位置，才能继续执行。

                CyclicBarrier可以使一定数量的线程在栅栏位置处汇集。当线程到达栅栏位置时将调用await方法，这个方法将阻塞直到所有线程都到达栅栏位置。
                如果所有线程都到达栅栏位置，那么栅栏将打开，此时所有的线程都将被释放。

                而栅栏将被重置以便下次使用。

        （4）Exchanger
                Exchanger可以在两个线程之间交换数据，只能是2个线程，他不支持更多的线程之间互换数据。
                一个线程中，调用Exchanger的exchange方法，进入阻塞状态，直到另一个持有该实例的线程也调用exchange方法。

        （5）SynchronousQueue
                阻塞队列的一种。
                容量为1，一个入队操作，必须对应一个出队操作；是一种在生产者和消费者之间快速传递元素的方式

     */
}
