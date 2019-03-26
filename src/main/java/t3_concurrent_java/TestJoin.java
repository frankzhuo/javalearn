package t3_concurrent_java;

public class TestJoin implements Runnable {

    public static void main(String[] sure) throws InterruptedException {
        Thread t = new Thread(new TestJoin());
        long start = System.currentTimeMillis();
        t.start();
        Thread.sleep(1000);
        t.join(2000);//等待线程t 2000毫秒
        System.out.println(System.currentTimeMillis() - start);//打印出时间间隔
        System.out.println("Main finished");//打印主线程结束
    }

    public void run() {
        synchronized (Thread.currentThread()) {
            for (int i = 1; i <= 5; i++) {
                try {
                    Thread.sleep(1000);//睡眠5秒，循环是为了方便输出信息
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("睡眠" + i);
            }
            System.out.println("TestJoin finished");//t线程结束
        }
        while (true){

        }
    }
}

