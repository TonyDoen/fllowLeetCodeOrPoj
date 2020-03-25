package me.meet.test;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

public final class MultiThreadTest1 {
    private MultiThreadTest1() {
    }

    static class LockSupportOuch extends Thread {
        private static Object flag = new Object();

        public LockSupportOuch(String name) {
            super.setName(name);
        }

        public void run() {
            synchronized (flag) {
                System.out.println(getName());
                LockSupport.park();
            }
        }
    }

    /**
     * LockSupport
     */
    private static void testLockSupportOuch() {
        final LockSupportOuch lo1 = new LockSupportOuch("work-1");
        final LockSupportOuch lo2 = new LockSupportOuch("work-2");

        lo1.start();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        lo2.start();

        LockSupport.unpark(lo1);
        LockSupport.unpark(lo2);

        try {
            lo1.join();
            lo2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * JAVA 中断实现
     */
    private static void testInterrupt() {

        // 填充数组
        int len = 80000;
        final int[] array = new int[len];
        Random random = new Random();
        for (int i = 0; i < array.length; i++) {
            array[i] = random.nextInt(i + 1);
        }

        final Thread t = new Thread() {
            public void run() {
                try {
                    // 冒泡排序
                    for (int i = 0; i < array.length - 1; i++) {
                        for (int j = 0; j < array.length - i - 1; j++) {
                            if (array[j] < array[j + 1]) {
                                int temp = array[j];
                                array[j] = array[j + 1];
                                array[j + 1] = temp;
                            }
                        }
                    }

                    System.out.println((array));
                } catch (Error err) {
                    err.printStackTrace();
                }
                System.out.println("in thread t");
            }
        };

        t.start();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t.stop();

    }

    private static void testInterrupt1() {
        class MyThread extends Thread {
            @Override
            public void run() {
                super.run();
                for (int i = 0; i < 500000; i++) {
                    if (this.interrupted()) {
                        System.out.println("should be stopped and exit");
                        break;
                    }
                    System.out.println("i=" + (i + 1));
                }
                System.out.println("this line is also executed. thread does not stopped"); // 尽管线程被中断,但并没有结束运行。这行代码还是会被执行
            }
        }

        MyThread thread = new MyThread();
        thread.start();
        try {
            Thread.sleep(20);//modify 2000 to 20
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();//这样处理比较好
        }
        thread.interrupt();//请求中断MyThread线程
    }

    private static void testInterrupt2() {
        class MyRunnable implements Runnable {
            @Override
            public void run() {
                System.err.println("start work");
                while (!Thread.currentThread().isInterrupted()) {
                    System.out.println("doing work");
                }
                System.err.println("done work");
            }
        }

        Thread thread = new Thread(new MyRunnable());
        thread.start();
        long i = System.currentTimeMillis();
        while (System.currentTimeMillis() - i < 10 * 1000) {
            thread.isAlive();
        }
        thread.interrupt();
    }

    /**
     * 我们来看下线程中断最重要的 3 个方法，它们都是来自 Thread 类！
     * <p>
     * 1、java.lang.Thread#interrupt       中断目标线程，给目标线程发一个中断信号，线程被打上中断标记。
     * 2、java.lang.Thread#isInterrupted() 判断目标线程是否被中断，不会清除中断标记。
     * 3、java.lang.Thread#interrupted     判断目标线程是否被中断，会清除中断标记。
     */
    // 示例1（中断失败）
    private static void testInterruptFail1() {
        Thread thread = new Thread(() -> { // 没有对应的中断处理
            while (true) { // 因为虽然给线程发出了中断信号，但程序中并没有响应中断信号的逻辑，所以程序不会有任何反应
                Thread.yield();
            }
        });
        thread.start();
        thread.interrupt();
    }

    // 示例2：（中断成功）
    private static void testInterruptSuccess1() {
        Thread thread = new Thread(() -> {
            while (true) {
                Thread.yield();

                // 响应中断
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println("Java技术栈线程被中断，程序退出。");
                    return;
                }
            }
        });
        thread.start();
        thread.interrupt();
    }

    // 示例3（中断失败）
    private static void testInterruptFail2() {
        Thread thread = new Thread(() -> {
            while (true) {
                // 响应中断
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println("Java技术栈线程被中断，程序退出。");
                    return;
                }

                try {
                    Thread.sleep(3000); // sleep() 方法被中断后会清除中断标记
                } catch (InterruptedException e) {
                    System.out.println("Java技术栈线程休眠被中断，程序退出。");
                }
            }
        });
        thread.start();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        thread.interrupt();
    }

    // 示例4（中断成功）
    private static void testInterruptSuccess2() {
        Thread thread = new Thread(() -> {
            while (true) {
                // 响应中断
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println("Java技术栈线程被中断，程序退出。");
                    return;
                }

                try {
                    Thread.sleep(3000); // sleep() 方法被中断后会清除中断标记
                } catch (InterruptedException e) {
                    System.out.println("Java技术栈线程休眠被中断，程序退出。");
                    Thread.currentThread().interrupt(); // 中断目标线程，给目标线程发一个中断信号，线程被打上中断标记。
                }
            }
        });
        thread.start();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
        thread.interrupt();
    }


    public static void main(String[] args) {
//        testLockSupportOuch();
//        testInterrupt();
//        testInterrupt1();
//        testInterrupt2();
//        testInterruptFail1();
//        testInterruptFail2();
        testInterruptSuccess1();
        testInterruptSuccess2();

        System.out.println("end.");
        ThreadLocal<String> local = new ThreadLocal<>();
        local.set("tony");
        System.out.println(local.get());
    }
}
