package me.meet.test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public final class MultiThreadTest2 {
    private MultiThreadTest2() {
    }

    static class MThread extends Thread {
        private String name;
        MThread() {}
        MThread(String name) { this.name = name; }

        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(name);
                super.run();
            }
        }
    }

    private static void testThread() {

        MThread aThread = new MThread("A");
        MThread bThread = new MThread("B");
        MThread cThread = new MThread("C");

        aThread.start();
        bThread.start();
        cThread.start();
    }

    private static void testThreadPool() {

        MThread aThread = new MThread("A");
        MThread bThread = new MThread("B");
        MThread cThread = new MThread("C");

        ExecutorService executorService = new ThreadPoolExecutor(2, 4,
                3L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>());
        executorService.submit(aThread);
        executorService.submit(bThread);
        executorService.submit(cThread);
    }


    public static void main(String[] args) {

//        testThread();
        testThreadPool();
        System.out.println("hi");
    }
}
