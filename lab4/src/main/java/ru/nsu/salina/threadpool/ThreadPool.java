package ru.nsu.salina.threadpool;

import java.util.concurrent.BlockingQueue;

public class ThreadPool {
    private BlockingQueue<Runnable> taskQueue;
    private int threadsLimit;
    public ThreadPool(int threadsLimit) { //TODO add BlockingQueue<Runnable> taskQueue
        //this.taskQueue = taskQueue;
        this.threadsLimit = threadsLimit;
    }
}
