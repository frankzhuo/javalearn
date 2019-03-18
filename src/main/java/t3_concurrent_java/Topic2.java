package t3_concurrent_java;


public class Topic2 {
    /*
        2.何谓同步？
            同一进程内的线程共享同一个内存空间（堆、常量池等），这样多个线程并发执行，对同一资源进行访问时，会产生资源竞争问题，
            究其深层次而言，是因为一个线程使用了旧版本的数据，而另一个线程已经修改了该数据。

            Java内存模型（主内存 工作内存）
               Java内存模型中规定了所有的变量都存储在主内存中，每条线程还有自己的工作内存（可以与前面将的处理器的高速缓存类比），
               线程的工作内存中保存了该线程使用到的变量到主内存副本拷贝，线程对变量的所有操作（读取、赋值）都必须在工作内存中进行，而不能直接读写主内存中的变量。
               不同线程之间无法直接访问对方工作内存中的变量，线程间变量值的传递均需要在主内存来完成。

               这里的主内存、工作内存与Java内存区域的Java堆、栈、方法区不是同一层次内存划分
               https://www.cnblogs.com/nexiyi/p/java_memory_model_and_thread.html

        3.synchronized（例子）
            1.使用方法
              synchronized的使用位置：
                a.用在实例方法上，锁住实例对象
                b.用在静态方法上，锁住类对象
                c.synchronized(object)，锁住onject对象
              synchronized又叫对象锁，它锁住的是一个实例对象或者一个类对象：
                a.要执行被锁住的方法或者代码段，就必须要获取锁，
                b.对象锁只能被一个线程所获取，并且对象锁是可以"重入"的（避免死锁）。

            2.notify、wait（条件变量）
              一个线程拿到"对象锁"，如果要等待达成某个条件，再继续执行。
              如果条件符合，继续执行。
              若条件没达成，调用"锁对象的wait方法"等待并"释放对象锁"，等待其它线程达成该条件后通知notify/notifyAll，再次竞争锁。
              注意⚠️：
                虚假唤醒：
                wait要放在while中，因为如果多个线程等待某一条件，另一线程达成条件，并调用notifyAll，
                此时所有等待条件的线程退出等待队列，并竞争锁对象，进入锁的阻塞队列，仅有一个线程能够获得锁，当这个线程执行完毕，其它线程
                继续竞争锁，并执行，若没在while中，则可能会在条件不符合的情况下就执行了后面的代码。

                sb问题：wait()和sleep的区别?
                    1.概念完全不同

                    2.wait方法会释放锁，sleep会继续持有锁



        4. volatile
             保证可见性、有序性(禁止重排序)，
             不保证原子性。
             结合"Java内存模型"理解。
     */
static class Item {
    int i = 0;

    public Item() {
    }

    public synchronized void incre() {
        i++;
    }

    public synchronized void decre() {
        i--;
    }


    @Override
    public String toString() {
        return "Item{" +
                "i=" + i +
                '}';
    }
}

static class A implements Runnable {
    Item item;

    public A(Item item) {
        this.item = item;
    }

    public void run(){
        for (int i = 0; i < 100; i++) {
            this.item.incre();
        }
    }
}


    public static void main(String[] args) throws InterruptedException {
        Item item = new Item();
        A a1 = new A(item);
        A a2 = new A(item);
        Thread t1 = new Thread(a1);
        Thread t2 = new Thread(a2);
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(item);
    }


}