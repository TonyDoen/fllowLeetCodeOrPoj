package me.meet.test;

import java.util.Date;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public final class MultiThreadTest {
    private MultiThreadTest() {}

    static class Reenter1 implements Runnable {
        private final static int SIZE = 10000;
        private final static ReentrantLock lock = new ReentrantLock();
        private static int i = 0;

        public void run() {
            for (int j = 0; j < SIZE; j++) {
                lock.lock();
                try {
                    i++;
                } finally {
                    lock.unlock();
                }
            }
            System.out.println(Thread.currentThread().getName() + "; i=" + i);
        }
    }

    static class Reenter2 implements Runnable {
        private final static ReentrantLock lock0 = new ReentrantLock();
        private final static ReentrantLock lock1 = new ReentrantLock();
        private volatile int i;

        Reenter2(int i) {
            this.i = i;
        }

        public void run() {
            try { // dead lock
                if (0 == i) {
                    lock0.lockInterruptibly();
                    Thread.sleep(500);
                    lock1.lockInterruptibly();
                } else if (1 == i) {
                    lock1.lockInterruptibly();
                    Thread.sleep(500);
                    lock0.lockInterruptibly();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                if (lock0.isHeldByCurrentThread()) {
                    lock0.unlock();
                }
                if (lock1.isHeldByCurrentThread()) {
                    lock1.unlock();
                }
                System.out.println(Thread.currentThread().getName() + "; i=" + i + "; exit.");
            }
        }
    }

    static class Reenter3 implements Runnable {
        private final static ReentrantLock lock = new ReentrantLock();
        private final static int MILLISECONDS = 3000;

        public void run() {
            try {
                boolean tf = lock.tryLock(MILLISECONDS, TimeUnit.MILLISECONDS);
                if (tf) {
                    Thread.sleep(MILLISECONDS);
                    System.out.println(Thread.currentThread().getName() + " try to get lock succeed");
                } else {
                    System.out.println(Thread.currentThread().getName() + " try to get lock failed");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                if (lock.isHeldByCurrentThread()) {
                    lock.unlock();
                }
            }
        }
    }

    static class Reenter4 implements Runnable {
        private final static int MILLISECONDS = 300;
        private final static ReentrantLock lock0 = new ReentrantLock();
        private final static ReentrantLock lock1 = new ReentrantLock();
        private volatile int i;

        Reenter4(int i) {
            this.i = i;
        }

        public void run() {
            if (0 == i) {
                while (true) {
                    lock(lock0, lock1);
                }
            } else if (1 == i) {
                while (true) {
                    lock(lock1, lock0);
                }
            }
        }

        private void lock(ReentrantLock lock0, ReentrantLock lock1) {
            long start = System.currentTimeMillis();

            if (lock0.tryLock()) {
                try {
//                    System.out.println(Thread.currentThread().getName() + " get first lock spend " + (System.currentTimeMillis()-start));

                    Thread.sleep(MILLISECONDS);

                    start = System.currentTimeMillis();
                    if (lock1.tryLock()) {
                        try {
//                            System.out.println(Thread.currentThread().getName() + " get second lock spend " + (System.currentTimeMillis()-start));

                            Thread.sleep(MILLISECONDS);

                            System.out.println(Thread.currentThread().getName() + " get job done at " + (new Date()));
                            start = System.currentTimeMillis();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } finally {
                            lock1.unlock();

//                            System.out.println(Thread.currentThread().getName() + " release second lock spend " + (System.currentTimeMillis()-start));
                            start = System.currentTimeMillis();
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock0.unlock();

//                    System.out.println(Thread.currentThread().getName() + " release first lock spend " + (System.currentTimeMillis()-start));
                    start = System.currentTimeMillis();
                }

                System.out.println(Thread.currentThread().getName() + " yield at " + (new Date()));
                Thread.yield();
            }
        }
    }

    static class Reenter5 implements Runnable {
        private final static int MILLISECONDS = 300;
        private final static ReentrantLock lock = new ReentrantLock(true);

        public void run() {
            while (true) {
                try {
                    lock.lock();
                    System.out.println(Thread.currentThread().getName() + " get lock");
                    Thread.sleep(MILLISECONDS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        }
    }

    static class Reenter6 implements Runnable {
        private final static ReentrantLock lock = new ReentrantLock();
        private final static Condition condition = lock.newCondition();

        public void run() {
            try {
                lock.lock();
                condition.await();
                System.out.println(Thread.currentThread().getName() + " is going on. ");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }

    static class Reenter7 implements Runnable {
        private final static int MILLISECONDS = 300;
        private final static int SIZE = 200;
        private final static Semaphore semaphore = new Semaphore(SIZE);

        public void run() {
            try {

                semaphore.acquire();
                Thread.sleep(MILLISECONDS);
                System.out.println(Thread.currentThread().getName() + " get job done. ");
                semaphore.release();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // 读锁/写锁 分离
    static class Reenter8 {
        private final static ReentrantLock lock = new ReentrantLock();

        private final static ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
        private final static Lock readLock = readWriteLock.readLock();
        private final static Lock writeLock = readWriteLock.writeLock();

        private int i = 0;

        private int read(Lock lock) {
            try {
                lock.lock();
                Thread.sleep(1000);
                System.out.println(Thread.currentThread().getName() + " read " + i);
                return i;
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
            return 0;
        }

        private void write(Lock lock, int i) {
            try {
                lock.lock();
                Thread.sleep(1000);
                this.i = i;
                System.out.println(Thread.currentThread().getName() + " write " + i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }

    static class Reenter9 implements Runnable {
        private final static int SIZE = 5;
        private final static CountDownLatch LATCH = new CountDownLatch(SIZE);

        public void run() {
            try {
                Thread.sleep(new Random().nextInt(10) * 1000); //
                System.out.println(Thread.currentThread().getName() + " check complete. ");
                LATCH.countDown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static class Reenter0 {

        static class Soldier implements Runnable {
            private final String name;
            private final CyclicBarrier barrier;

            Soldier(String name, CyclicBarrier barrier) {
                this.name = name;
                this.barrier = barrier;
            }

            public void run() {
                try {
                    barrier.await(); // 等待所有士兵到齐

                    Thread.sleep(Math.abs(new Random().nextInt() % 10000)); // 士兵开始任务
                    System.out.println(Thread.currentThread().getName() + " get job done. ");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }
        }

        static class General implements Runnable {
            private boolean flag;
            private int num;

            General(boolean flag, int num) {
                this.flag = flag;
                this.num = num;
            }

            public void run() {
                if (flag) {
                    System.out.println("General: [Soldier] size: " + num + " task complete.");
                } else {
                    System.out.println("General: [Soldier] size: " + num + " got here.");
                    flag = true;
                }
            }
        }
    }

    private static void testReenter0() {
        final int NUM = 50;
        final Thread[] all = new Thread[NUM];

        CyclicBarrier barrier = new CyclicBarrier(NUM, new Reenter0.General(false, NUM)); // 设置屏障点
        System.out.println("General: [Soldier] team up ");

        for (int i = 0; i < NUM; i++) {
            String name = "soldier-" + i;
            System.out.println("Soldier [" + name + "] here. ");
            all[i] = new Thread(new Reenter0.Soldier(name, barrier), name);
            all[i].start();
        }

        System.out.println("exit");
    }

    // 重入锁
    private static void testReenter1() {
        Reenter1 r1 = new Reenter1();
        for (int i = 0; i < 10; i++) {
            Thread t = new Thread(r1, "worker-" + i);
            t.start();

            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // 中断响应, 避免死锁
    private static void testReenter2() {
        Thread t0 = new Thread(new Reenter2(0), "worker-" + 0);
        Thread t1 = new Thread(new Reenter2(1), "worker-" + 1);

        t0.start();
        t1.start();

        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        t1.interrupt();
    }

    // 锁申请等待限时, 避免死锁
    private static void testReenter3() {
        Reenter3 r = new Reenter3();

        for (int i = 0; i < 10; i++) {
            Thread t = new Thread(r, "worker-" + i);
            t.start();
        }
    }

    // 锁申请等待限时, 避免死锁
    private static void testReenter4() {
        Thread t0 = new Thread(new Reenter4(0), "worker-" + 0);
        Thread t1 = new Thread(new Reenter4(1), "worker-" + 1);

        t0.start();
        t1.start();
    }

    // 使用公平锁
    private static void testReenter5() {
        Reenter5 r = new Reenter5();
        for (int i = 0; i < 10; i++) {
            Thread t = new Thread(r, "worker-" + i);
            t.start();
        }
    }

    // 使用 Condition
    private static void testReenter6() {
        Reenter6 r = new Reenter6();
        new Thread(r, "worker-0").start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Reenter6.lock.lock();
        Reenter6.condition.signalAll();
        Reenter6.lock.unlock();
    }

    // 使用 Semaphore
    private static void testReenter7() {
        ExecutorService service = Executors.newFixedThreadPool(500);
        Reenter7 r = new Reenter7();
        for (int i = 0; i < 1000; i++) {
            service.submit(r);
        }
    }

    private static void testReenter8() {
        final Reenter8 r = new Reenter8();

        for (int i = 0; i < 20; i++) {
            new Thread(new Runnable() {
                public void run() {
                    r.write(Reenter8.writeLock, new Random().nextInt());
//                    r.write(Reenter8.lock, new Random().nextInt());
                }
            }).start();
        }
        for (int i = 0; i < 20; i++) {
            new Thread(new Runnable() {
                public void run() {
                    r.read(Reenter8.readLock);
//                    r.read(Reenter8.lock);
                }
            }).start();
        }
    }

    private static void testReenter9() {
        ExecutorService service = Executors.newFixedThreadPool(500);
        final Reenter9 r = new Reenter9();

        for (int i = 0; i < Reenter9.SIZE; i++) { // 火箭发射前，各项检查工作
            service.submit(r);
        }

        try {
            Reenter9.LATCH.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(Thread.currentThread().getName() + " fire now. "); // 检查工作完成，发射
        service.shutdown();
    }

    public static void main(String[] args) {
//        testReenter1();
        testReenter2();
//        testReenter3();
//        testReenter4();
//        testReenter5();
//        testReenter6();
//        testReenter7();
//        testReenter8();
//        testReenter9();
        testReenter0();
        System.out.println();
    }

    /**
     * ReentrantLock
     * lock()
     * lockInterruptibly()
     * tryLock()
     * tryLock(long timeout, TimeUnit unit)
     * unlock()
     *
     * Condition
     * await()
     * awaitUninterruptibly()
     * awaitNanos(long nanosTimeout)
     * await(long time, TimeUnit unit)
     * awaitUntil(Date deadline)
     * signal()
     * signalAll()
     *
     * Semaphore
     * acquire()
     * acquireUninterruptibly()
     * tryAcquire()
     * tryAcquire(long timeout, TimeUnit unit)
     * release()
     */
}
