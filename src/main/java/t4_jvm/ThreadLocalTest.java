package t4_jvm;

public class ThreadLocalTest {
    static final ThreadLocal<Object> threadLocal = new ThreadLocal<Object>();

    static class T1 implements Runnable{
        public void run() {
            String s = "AAAA";
            ThreadLocalTest.threadLocal.set(s);
            System.out.println(Thread.currentThread().getName()+":"+ThreadLocalTest.threadLocal.get());
        }
    }
    static class T2 implements Runnable{
        Thread t;
        public T2(Thread t) {
            this.t=t;
        }

        public void run() {
            try {
                t.join();
            } catch (InterruptedException e) {
            }
            System.out.println(Thread.currentThread().getName()+":"+ThreadLocalTest.threadLocal.get());
            String s = "BBBB";
            ThreadLocalTest.threadLocal.set(s);
            Thread.yield();
            System.out.println(Thread.currentThread().getName()+":"+ThreadLocalTest.threadLocal.get());
        }
    }
    public static void main(String[] args) {
        Thread t1=new Thread(new T1());
        Thread t2=new Thread(new T2(t1));
        t1.start();
        t2.start();
    }
}
