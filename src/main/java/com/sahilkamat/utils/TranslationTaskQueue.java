package com.sahilkamat.utils;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class TranslationTaskQueue {
    private static final BlockingQueue<Runnable> queue = new LinkedBlockingQueue<>();

    private static Thread worker;

    static {
        Thread worker = new Thread(() -> {
            while (true) {
                try {
                    Runnable task = queue.take(); // Blocks until a task is available
                    task.run();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt(); // Restore interrupted status
                    break;
                }
            }
        });
        worker.setDaemon(true); // Ensures the thread doesn't block JVM shutdown
        worker.start();
    }

    public static void submitTask(Runnable task) {
        queue.offer(task);
    }

    public static void shutdown() {
        worker.interrupt(); // Interrupt the worker thread to stop it
    }


}